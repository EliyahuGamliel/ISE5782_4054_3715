package scene;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import parser.SceneDescriptor;
import parser.SceneXMLHandler;
import primitives.Color;
import primitives.Double3;
import primitives.Point;

public class SceneBuilder {
    SceneDescriptor sceneDesc;
    Scene scene;
    String filePath;

    public void loadSceneFromFile(File file) throws SAXException, IOException, ParserConfigurationException {
        filePath = file.getAbsolutePath();
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        SceneXMLHandler handler = new SceneXMLHandler();
        saxParser.parse(file, handler);

        sceneDesc = handler.getSceneDescriptor();

        scene = new Scene(filePath);
        
        Color backgroundColor = makeColorFromString(sceneDesc.sceneAttributes.get("background-color"));
		scene.setBackground(backgroundColor);

		Color ambientLightColor = makeColorFromString(sceneDesc.ambientLightAttributes.get("color"));
		AmbientLight ambientLight = new AmbientLight(ambientLightColor, new Double3(1,1,1));
		scene.setAmbientLight(ambientLight);

        Geometries geometries = new Geometries();
        for (Map<String, String> sphereString : sceneDesc.spheres) {
            Sphere sphere = makeSphere(sphereString);
			geometries.add(sphere);
        }
        for (Map<String, String> triangleString : sceneDesc.triangles) {
            Triangle triangle = makeTriangle(triangleString);
			geometries.add(triangle);
        }
		scene.setGeometries(geometries);
    }

    double[] mapStringToDouble(String[] strings) {
		double[] res = new double[strings.length];
		for (int i = 0; i < strings.length; i++) {
			res[i] = Double.parseDouble(strings[i]);
		}
		return res;
	}

	Point makePointFromString(String string) {
		double[] numbers = mapStringToDouble(string.split(" "));
		return new Point(numbers[0], numbers[1], numbers[2]);
	}

	Color makeColorFromString(String string) {
		double[] numbers = mapStringToDouble(string.split(" "));
		return new Color(numbers[0], numbers[1], numbers[2]);
	}

    Sphere makeSphere(Map<String, String> sphereString) {
		String centerString = sphereString.get("center");
		Point center = makePointFromString(centerString);
		Double radius = Double.parseDouble(sphereString.get("radius"));
		return new Sphere(center, radius);
	}

	Triangle makeTriangle(Map<String, String> triangleString) {
		String p0String = triangleString.get("p0");
		Point p0 = makePointFromString(p0String);
		String p1String = triangleString.get("p1");
		Point p1 = makePointFromString(p1String);
		String p2String = triangleString.get("p2");
		Point p2 = makePointFromString(p2String);
		return new Triangle(p0, p1, p2);
	}

    public Scene getScene() {
        return scene;
    }
}
