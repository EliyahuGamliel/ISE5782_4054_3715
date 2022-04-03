package parser;

import java.util.Map;

import geometries.*;
import primitives.*;

public class Utils {
    public static double[] mapStringToDouble(String[] strings) {
		double[] res = new double[strings.length];
		for (int i = 0; i < strings.length; i++) {
			res[i] = Double.parseDouble(strings[i]);
		}
		return res;
	}

	public static Point makePointFromString(String string) {
		double[] numbers = mapStringToDouble(string.split(" "));
		return new Point(numbers[0], numbers[1], numbers[2]);
	}

	public static Color makeColorFromString(String string) {
		double[] numbers = mapStringToDouble(string.split(" "));
		return new Color(numbers[0], numbers[1], numbers[2]);
	}

    public static Sphere makeSphere(Map<String, String> sphereString) {
		String centerString = sphereString.get("center");
		Point center = makePointFromString(centerString);
		Double radius = Double.parseDouble(sphereString.get("radius"));
		return new Sphere(center, radius);
	}

	public static Triangle makeTriangle(Map<String, String> triangleString) {
		String p0String = triangleString.get("p0");
		Point p0 = makePointFromString(p0String);
		String p1String = triangleString.get("p1");
		Point p1 = makePointFromString(p1String);
		String p2String = triangleString.get("p2");
		Point p2 = makePointFromString(p2String);
		return new Triangle(p0, p1, p2);
	}
}
