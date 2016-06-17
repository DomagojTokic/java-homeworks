package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

/**
 * Intersection of ray and sphere.
 * 
 * @author Domagoj
 *
 */
public class RaySphereIntersection extends RayIntersection {

	/**
	 * Sphere
	 */
	Sphere sphere;

	/**
	 * Creates instance of {@link RaySphereIntersection}.
	 * @param sphere Sphere
	 * @param point Intersection point
	 * @param distance Distance from vector start to intersection point
	 * @param outer State of intersection - true if ray intersects from outside
	 */
	public RaySphereIntersection(Sphere sphere, Point3D point, double distance, boolean outer) {
		super(point, distance, outer);
		this.sphere = sphere;
	}

	@Override
	public Point3D getNormal() {
		return getPoint().sub(sphere.getCenter());
	}

	@Override
	public double getKdr() {
		return sphere.getKdr();
	}

	@Override
	public double getKdg() {
		return sphere.getKdg();
	}

	@Override
	public double getKdb() {
		return sphere.getKdb();
	}

	@Override
	public double getKrr() {
		return sphere.getKrr();
	}

	@Override
	public double getKrg() {
		return sphere.getKrg();
	}

	@Override
	public double getKrb() {
		return sphere.getKrb();
	}

	@Override
	public double getKrn() {
		return sphere.getKrn();
	}

}
