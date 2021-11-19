package mm.avidvivarta.aizawa;

import mm.avidvivarta.renderer.point.Point3D;

public class AizawaAttractor {

	private double dt = 0.01;
	private Point3D point3d;
	private double timer = 0;

	/*
	 * constants
	 */
	double a = 0.95, b = 0.7, c = 0.6, d = 3.5, e = 0.25, f = 0.1;
	// 0.1, 0, 0
	private Transform<Point3D> transform = new Transform<Point3D>() {
		
		@Override
		public Point3D updatePoints(Point3D p) {
			
			double x = p.getX(), y = p.getY(), z = p.getZ();
			double x1 = (p.getZ() - b) * p.getX() - d * p.getY();
			double y1 = d * p.getX() + (p.getZ() - b) * p.getY();
			double z1 = c + a * p.getZ() - (Math.pow(p.getZ(), 3) / 3d)
					- (Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2)) * (1 + e * p.getZ())
					+ f * p.getZ() * (Math.pow(p.getX(), 3));
			Point3D point3d = new Point3D(x + dt * x1, y + dt * y1, z + dt * z1);
			return point3d;
			
		}
	};

	public AizawaAttractor() {

		this.point3d = new Point3D(0.1, 0, 0);

	}

	public AizawaAttractor(double dt) {

		this.point3d = new Point3D(0.1, 0, 0);
		this.dt = dt;

	}

	public AizawaAttractor(double x, double y, double z) {

		this.point3d = new Point3D(x, y, z);

	}

	public AizawaAttractor(Point3D point3d) {

		this.point3d = new Point3D(point3d.getX(), point3d.getY(), point3d.getZ());

	}

	public AizawaAttractor(double x, double y, double z, double dt) {

		this.point3d = new Point3D(x, y, z);
		this.dt = dt;

	}

	public AizawaAttractor(Point3D point3d, double dt) {

		this.point3d = new Point3D(point3d.getX(), point3d.getY(), point3d.getZ());
		this.dt = dt;

	}

	public double getLapsedTime() {

		return this.timer;

	}

	public Point3D getCurrentLocation() {

		return this.point3d;

	}

	public Point3D iterate() {

		this.timer += this.dt;
		this.point3d = this.transform.updatePoints(this.point3d);
		return this.point3d;

	}

	public double getDT() {

		return this.dt;

	}

	public void setTransform(Transform<Point3D> transform) {

		this.transform = transform;

	}

	public void setDt(double dt) {

		this.dt = dt;

	}

}
