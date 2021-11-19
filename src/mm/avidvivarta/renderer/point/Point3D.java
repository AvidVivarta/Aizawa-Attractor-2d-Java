package mm.avidvivarta.renderer.point;

import java.util.Objects;

public class Point3D {

	private double x, y, z;

	public Point3D(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;

	}

	public double getX() {

		return x;

	}

	public double getY() {

		return y;

	}

	public double getZ() {

		return z;

	}

	@Override
	public String toString() {

		return "[x: " + this.x + ", y: " + this.y + ", z: " + this.z + "]";

	}

	@Override
	public int hashCode() {

		return Objects.hash(x, y, z);

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Point3D other = (Point3D) obj;
		return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y)
				&& Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);

	}


}
