package renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;
import scene.SceneBuilder;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;


/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
						                          new Double3(1,1,1))) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point(0, 0, -100), 50),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
																													// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
																														// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
																													// right
		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("base render test", 1000, 1000))				
				.setRayTracer(new RayTracerBasic(scene));
		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.BLUE));
		camera.writeToImage();

		//camera.spinToTheSide(90);
		camera.renderImage();
		camera.printGrid(100, new Color(CYAN));
		camera.writeToImage();
	}

	Element openXml(String fileName) {
		File inputFile = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
		} catch (ParserConfigurationException e) {
			fail("ParserConfigurationException");
		} catch (SAXException e) {
			fail("SAXException");
		} catch (IOException e) {
			fail("IOException");
		}
		doc.getDocumentElement().normalize();

		return doc.getDocumentElement();
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

	Sphere makeSphere(Element geometryElement) {
		String centerString = geometryElement.getAttribute("center");
		Point center = makePointFromString(centerString);
		Double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
		return new Sphere(center, radius);
	}

	Triangle makeTriangle(Element geometryElement) {
		String p0String = geometryElement.getAttribute("p0");
		Point p0 = makePointFromString(p0String);
		String p1String = geometryElement.getAttribute("p1");
		Point p1 = makePointFromString(p1String);
		String p2String = geometryElement.getAttribute("p2");
		Point p2 = makePointFromString(p2String);
		return new Triangle(p0, p1, p2);
	}

	/**
	 * Test for XML based scene - for bonus
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Test
	public void basicRenderXml() throws SAXException, IOException, ParserConfigurationException {
		SceneBuilder sceneBuilder = new SceneBuilder();
		File file = new File("scenes/basicRenderTestTwoColors.xml");
		
		sceneBuilder.loadSceneFromFile(file);

		Scene scene = sceneBuilder.getScene();
		
		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPDistance(100) //
			.setVPSize(500, 500)
			.setImageWriter(new ImageWriter("xml render test", 1000, 1000))
			.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(java.awt.Color.YELLOW));
		camera.writeToImage();
	}

	// For stage 6 - please disregard in stage 5
	/**
	 * Produce a scene with basic 3D model - including individual lights of the
	 * bodies and render it into a png image with a grid
	 */
	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -100), 50),
				// up left
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
						.setEmission(new Color(GREEN)),
				// down left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
						.setEmission(new Color(RED)),
				// down right
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
						.setEmission(new Color(BLUE)));

		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("color render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(WHITE));
		camera.writeToImage();
	}

	@Test
	void cameraRotationTest() {
		Scene scene = new Scene("Test scene");

		scene.geometries.add(new Sphere(new Point(0, 0, -50), 50d) //
			.setEmission(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		scene.lights.add(new DirectionalLight(new Color(400, 0, 0), new Vector(-1, 1, -1)));
		scene.lights.add(new PointLight(new Color(500, 500, 0), new Point(0, 30, 10))
				.setkL(0.0000003).setkQ(0.0000001));
		scene.lights.add(new SpotLight(new Color(0, 900, 0), new Point(-100, -70, 50), new Vector(1, -1, -2))
				.setkL(0.0000000001).setkQ(0.000000001));

		String imageName = new String("rotation test/camera rotation %d %d");
		ImageWriter imageWriter = new ImageWriter("", 1000, 1000);

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150) //
				.setVPDistance(1000) //
				.setImageWriter(imageWriter)
				.setRayTracer(new RayTracerBasic(scene));

		for (int i = 0; i < 360; i += 10) {
			camera.spin(10);
			camera.spinRightLeft(12d/36);
			camera.spinUpDown(12d/36);
				
			camera.renderImage();
			imageWriter.setImageName(String.format(imageName, 1, i));
			camera.writeToImage();
		}
		for (int i = 0; i < 360; i += 10) {
			camera.spin(-10);
			camera.spinUpDown(12d/36);
			camera.spinRightLeft(12d/36);
				
			camera.renderImage();
			imageWriter.setImageName(String.format(imageName, 2, i));
			camera.writeToImage();
		}
	}
}
