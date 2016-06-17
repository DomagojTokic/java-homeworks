package hr.fer.zemris.java.raytracer.model;

import hr.fer.zemris.java.raytracer.RaySphereIntersection;

/**
 * Geometric shape sphere which implements methods for calculating ray
 * intersections and stores data for visualization.
 * 
 * @author Domagoj
 *
 */
public class Sphere extends GraphicalObject {

	/** Sphere center */
	private Point3D center;

	/** Sphere radius */
	private double radius;

	/** Coefficient for diffuse component for red color */
	private double kdr;

	/** Coefficient for diffuse component for green color */
	private double kdg;

	/** Coefficient for diffuse component for blue color */
	private double kdb;

	/** Coefficient for reflective component for red color */
	private double krr;

	/** Coefficient for reflective component for green color */
	private double krg;
	
	/** Coefficient for reflective component for blue color */
	private double krb;

	/** Coefficient n for reflective component */
	private double krn;

	/**
	 * Creates new instance of {@link Sphere}
	 * 
	 * @param center Sphere center
	 * @param radius Sphere Radius
	 * @param kdr Coefficient for diffuse component for red color
	 * @param kdg Coefficient for diffuse component for green color
	 * @param kdb Coefficient for diffuse component for blue color
	 * @param krr Coefficient for reflective component for red color
	 * @param krg Coefficient for reflective component for green color
	 * @param krb Coefficient for reflective component for blue color
	 * @param krn Coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr,
			double krg, double krb, double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Returns sphere center
	 * 
	 * @return sphere center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Returns sphere radius
	 * 
	 * @return sphere radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Returns coefficient for diffuse component for red color
	 * 
	 * @return coefficient for diffuse component for red color
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Returns coefficient for diffuse component for green color
	 * 
	 * @return coefficient for diffuse component for green color
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Returns coefficient for diffuse component for blue color
	 * 
	 * @return coefficient for diffuse component for blue color
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Returns coefficient for reflective component for red color
	 * 
	 * @return coefficient for reflective component for red color
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Returns coefficient for reflective component for green color
	 * 
	 * @return coefficient for reflective component for green color
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Returns coefficient for reflective component for blue color
	 * 
	 * @return coefficient for reflective component for blue color
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Returns coefficient n for reflective component
	 * 
	 * @return coefficient n for reflective component
	 */
	public double getKrn() {
		return krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D rayStart = ray.start.sub(center);
		Point3D rayDirection = ray.direction;
		double discriminant = Math.pow(rayStart.scalarProduct(rayDirection), 2)
				- rayStart.scalarProduct(rayStart) + Math.pow(radius, 2);

		boolean outer = true;
		if (ray.start.sub(center).norm() < radius) {
			outer = false;
		}

		if (Math.abs(discriminant) < 10e-5) {
			return null;
		}

		double t1 = -rayStart.scalarProduct(rayDirection) - Math.sqrt(discriminant);
		if (t1 > 10e-5) {
			Point3D point = ray.start.add(rayDirection.scalarMultiply(t1));
			return new RaySphereIntersection(this, point, t1, outer);
		}

		double t2 = -rayStart.scalarProduct(rayDirection) + Math.sqrt(discriminant);
		if (t2 > 10e-5) {
			Point3D point = ray.start.add(rayDirection.scalarMultiply(t2));
			return new RaySphereIntersection(this, point, t2, outer);
		}
		return null;
	}
}
