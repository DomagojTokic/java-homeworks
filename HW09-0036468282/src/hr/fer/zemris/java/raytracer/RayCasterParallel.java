package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Adjusted version of {@link RayCaster} for multi-threaded execution.
 * 
 * @author Domagoj
 *
 */
public class RayCasterParallel {

	/** Thread pool for multiprocessor execution */
	static ForkJoinPool pool;

	/** Red component of RGB picture result */
	static short[] red;

	/** Green component of RGB picture result */
	static short[] green;

	/** Blue component of RGB picture result */
	static short[] blue;

	/** Picture width */
	static int width;

	/** Picture height */
	static int height;

	/** Observer point of looking onto picture */
	static Point3D eye;

	/** X screen axis */
	static Point3D xAxis;

	/** Y screen axis */
	static Point3D yAxis;

	/** Z screen axis */
	static Point3D zAxis;

	/** Horizontal width of observed space */
	static double horizontal;

	/** Vertical height of observed space */
	static double vertical;

	/** Screen corner */
	static Point3D screenCorner;

	/** Scene */
	static Scene scene;

	/** Number of pixel rows for calculation in one thread */
	static int blockSize;

	/** Maximum number of threads */
	static final int MAX_NUM_OF_THREADS = 8;

	/**
	 * Entrance into {@link RayCasterParallel}
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		pool = new ForkJoinPool();
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Returns parallel implementation of {@link IRayTracerProducer}
	 * 
	 * @return implementation of {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal,
					double vertical, int width, int height, long requestNo,
					IRayTracerResultObserver observer) {

				setBlockSize(height);

				class Job extends RecursiveAction {

					private static final long serialVersionUID = 1L;
					int blockStart;
					int blockEnd;
					short[] rgb;

					public Job(int blockStart, int blockEnd, short[] rgb) {
						super();
						this.blockStart = blockStart;
						this.blockEnd = blockEnd;
						this.rgb = rgb;
					}

					@Override
					protected void compute() {
						if (blockEnd - blockStart <= width * blockSize - 1) {
							generatePixel();
							return;
						}

						Job job1 = new Job(blockStart, (blockEnd - blockStart) / 2, new short[3]);
						Job job2 = new Job((blockEnd - blockStart) / 2 + blockStart + 1, blockEnd,
								new short[3]);
						invokeAll(job1, job2);

					}

					private void generatePixel() {
						for (int j = 0; j < blockSize; j++) {
							for (int i = 0; i < width; i++) {
								int x = i % width;
								int y = (blockStart / height) + j;

								Point3D screenPoint = screenCorner
										.add(xAxis.scalarMultiply(horizontal * x / (width - 1)).sub(
												yAxis.scalarMultiply(vertical * y / (height - 1))));
								Ray ray = Ray.fromPoints(eye, screenPoint);

								tracer(ray);

								red[blockStart + i + j * width] = rgb[0] > 255 ? 255 : rgb[0];
								green[blockStart + i + j * width] = rgb[1] > 255 ? 255 : rgb[1];
								blue[blockStart + i + j * width] = rgb[2] > 255 ? 255 : rgb[2];
							}
						}
					}

					private void tracer(Ray ray) {
						RayIntersection closestIntersection = null;

						for (GraphicalObject object : scene.getObjects()) {
							RayIntersection intersection = object.findClosestRayIntersection(ray);
							if (intersection != null) {
								if (closestIntersection == null) {
									closestIntersection = intersection;
								} else if (intersection.getDistance() < closestIntersection
										.getDistance()) {
									closestIntersection = intersection;
								}
							}
						}

						if (closestIntersection != null) {
							short[] colors = determineColorFor(closestIntersection, ray);
							rgb[0] = colors[0];
							rgb[1] = colors[1];
							rgb[2] = colors[2];
						} else {
							rgb[0] = 0;
							rgb[1] = 0;
							rgb[2] = 0;
						}
					}

					private short[] determineColorFor(RayIntersection intersection, Ray ray) {
						short[] colors = { 15, 15, 15 };
						Point3D rayDirection = ray.direction;
						RayIntersection closestLightIntersection;

						for (LightSource light : scene.getLights()) {
							Ray source = Ray.fromPoints(intersection.getPoint(), light.getPoint());

							closestLightIntersection = getClosestLightIntersection(source, scene);

							// if object is in shadow of another object
							if (closestLightIntersection != null && closestLightIntersection
									.getPoint().sub(light.getPoint()).norm() < intersection
											.getPoint().sub(light.getPoint()).norm()) {
								continue;
							} else {

								double angleDifference = Math.max(
										source.direction.scalarProduct(intersection.getNormal()),
										0);

								Point3D reflection = source.direction.sub(intersection.getNormal()
										.scalarMultiply(2 * angleDifference)).normalize();

								double angleReflection = Math
										.max(reflection.scalarProduct(rayDirection), 0);
								double angleReflectionCoef = Math.pow(angleReflection,
										intersection.getKrn());

								colors[0] += light.getR() * intersection.getKdr() * angleDifference
										+ light.getR() * intersection.getKrr()
												* angleReflectionCoef;

								colors[1] += light.getG() * intersection.getKdg() * angleDifference
										+ light.getG() * intersection.getKrg()
												* angleReflectionCoef;

								colors[2] += light.getB() * intersection.getKdb() * angleDifference
										+ light.getB() * intersection.getKrb()
												* angleReflectionCoef;
							}
						}

						return colors;

					}

					private RayIntersection getClosestLightIntersection(Ray source, Scene scene2) {
						RayIntersection closestLightIntersection = null;
						for (GraphicalObject object : scene.getObjects()) {
							RayIntersection lightIntersection = object
									.findClosestRayIntersection(source);
							if (lightIntersection != null) {
								if (closestLightIntersection == null) {
									closestLightIntersection = lightIntersection;
								} else if (lightIntersection
										.getDistance() < closestLightIntersection.getDistance()) {
									closestLightIntersection = lightIntersection;
								}
							}
						}
						return closestLightIntersection;
					}
				}

				System.out.println("Započinjem izračune...");

				red = new short[width * height];
				green = new short[width * height];
				blue = new short[width * height];

				zAxis = view.sub(eye).normalize();
				yAxis = viewUp.normalize()
						.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())));
				xAxis = zAxis.vectorProduct(yAxis);
				screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];

				Job job = new Job(0, height * width - 1, rgb);
				pool.invoke(job);

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}

			private void setBlockSize(int height) {
				blockSize = height;
				int numOfJobs = 1;
				while ((blockSize & 0x00000001) == 0 && numOfJobs < MAX_NUM_OF_THREADS) {
					blockSize /= 2;
					numOfJobs *= 2;
				}
			}

		};
	}

}
