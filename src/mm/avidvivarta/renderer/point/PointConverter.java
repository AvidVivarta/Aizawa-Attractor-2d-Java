package mm.avidvivarta.renderer.point;

import java.awt.Point;

import mm.avidvivarta.renderer.Display;

public class PointConverter {

	public static Point convertPoint(Point3D point3d) {

		double x3d = point3d.getX();
		double y3d = point3d.getY();
		double z3d = point3d.getZ();

		int x2d = (int) (Display.width / 2 + y3d);
		int y2d = (int) (Display.height / 2 - z3d);
		return new Point(x2d, y2d);

	}

}
