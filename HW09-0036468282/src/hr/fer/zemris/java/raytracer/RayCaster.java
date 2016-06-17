package hr.fer.zemris.java.raytracer;

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
 * Program for creation of scene filled with geometric shapes. It takes
 * predefined scheme of geometric shapes and lights from {@link RayTracerViewer}
 * , calculates intersections for reflections from objects and renders the image
 * to user.
 * 
 * @author Domagoj
 *
 */
public class RayCaster {

	/**
	 * Entrance into {@link RayCaster}
	 * 
	 * @param args No arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Returns implementation of {@link IRayTracerProducer}
	 * 
	 * @return implementation of {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal,
					double vertical, int width, int height, long requestNo,
					IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.normalize()
						.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())));
				Point3D xAxis = zAxis.vectorProduct(yAxis);
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(xAxis.scalarMultiply(horizontal * x / (width - 1))
										.sub(yAxis.scalarMultiply(vertical * y / (height - 1))));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			private void tracer(Scene scene, Ray ray, short[] rgb) {
				RayIntersection closestIntersection = null;

				for (GraphicalObject object : scene.getObjects()) {
					RayIntersection intersection = object.findClosestRayIntersection(ray);
					if (intersection != null) {
						if (closestIntersection == null) {
							closestIntersection = intersection;
						} else if (intersection.getDistance() < closestIntersection.getDistance()) {
							closestIntersection = intersection;
						}
					}
				}

				if (closestIntersection != null) {
					short[] colors = determineColorFor(closestIntersection, ray, scene);
					rgb[0] = colors[0];
					rgb[1] = colors[1];
					rgb[2] = colors[2];
				} else {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				}
			}

			private short[] determineColorFor(RayIntersection intersection, Ray ray, Scene scene) {
				short[] colors = { 15, 15, 15 };
				Point3D rayDirection = ray.direction;
				RayIntersection closestLightIntersection;

				for (LightSource light : scene.getLights()) {
					Ray source = Ray.fromPoints(intersection.getPoint(), light.getPoint());

					closestLightIntersection = getClosestLightIntersection(source, scene);

					// if object is not in shadow of another object
					if (closestLightIntersection == null || !(closestLightIntersection.getPoint()
							.sub(light.getPoint())
							.norm() < intersection.getPoint().sub(light.getPoint()).norm())) {
						double angleDifference = Math
								.max(source.direction.scalarProduct(intersection.getNormal()), 0);

						Point3D reflection = source.direction
								.sub(intersection.getNormal().scalarMultiply(2 * angleDifference))
								.normalize();

						double angleReflection = Math.max(reflection.scalarProduct(rayDirection),
								0);
						double angleReflectionCoef = Math.pow(angleReflection,
								intersection.getKrn());

						colors[0] += light.getR() * intersection.getKdr() * angleDifference
								+ light.getR() * intersection.getKrr() * angleReflectionCoef;

						colors[1] += light.getG() * intersection.getKdg() * angleDifference
								+ light.getG() * intersection.getKrg() * angleReflectionCoef;

						colors[2] += light.getB() * intersection.getKdb() * angleDifference
								+ light.getB() * intersection.getKrb() * angleReflectionCoef;
					}
				}

				return colors;
			}

			private RayIntersection getClosestLightIntersection(Ray source, Scene scene) {
				RayIntersection closestLightIntersection = null;
				for (GraphicalObject object : scene.getObjects()) {
					RayIntersection lightIntersection = object.findClosestRayIntersection(source);
					if (lightIntersection != null) {
						if (closestLightIntersection == null) {
							closestLightIntersection = lightIntersection;
						} else if (lightIntersection.getDistance() < closestLightIntersection
								.getDistance()) {
							closestLightIntersection = lightIntersection;
						}
					}
				}
				return closestLightIntersection;
			}
		};
	}
}
