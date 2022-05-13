package renderer;

import org.junit.jupiter.api.Test;

import static geometries.Utils.*;

import geometries.*;
import lighting.*;
import models.hellicopter;
import primitives.*;
import scene.Scene;
import scene.SceneBuilder;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

		camera.renderImage();
		camera.printGrid(100, new Color(YELLOW));
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

	// @Test
	void basicCameraRotationTest() {
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
		
		String imageName = new String("basic rotation test/camera rotation %d %d");
		ImageWriter imageWriter = new ImageWriter("", 1000, 1000);
																													
		Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(imageWriter)				
				.setRayTracer(new RayTracerBasic(scene));
			
		for (int i = 0; i < 360; i += 15) {
			camera.spinRightLeft(15);
			// camera.spinUpDown(12d/36);
				
			camera.renderImage();
			camera.printGrid(100, new Color(CYAN));
			imageWriter.setImageName(String.format(imageName, 1, i));
			camera.writeToImage();
		}
	}


	// @Test
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

	@Test
	void coolImageTest() {
		Vector axisY = new Vector(0, 1, 0);

		Geometries constGeometries = new Geometries();
		List<LightSource> constLights = new ArrayList<>();

		Geometries redLightsSurfaces = new Geometries();
		Geometries greenLightsSurfaces = new Geometries();
		Geometries blueLightsSurfaces = new Geometries();
		
		List<LightSource> redLights = new ArrayList<>();
		List<LightSource> greenLights = new ArrayList<>();
		List<LightSource> blueLights = new ArrayList<>();

		hellicopter choper = new hellicopter(new Point(0, 0, 0), 8);
		//add a plane to geometries as a background
		constGeometries.add(new Plane(new Point(0, -100, -200), axisY)
									.setEmission(new Color(155, 118, 83))
									.setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(100)));

		constGeometries.add(new Plane(new Point(-150, 0, 0), new Vector(1, 0, 1))
									.setEmission(new Color(135, 206, 235))
									.setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(100)));

		//draw an H at the floor background
		constGeometries.add(createRectangleY(new Point(0, -100+0.09, 25), 80, 18)
									.setEmission(new Color(58, 58, 58))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
		constGeometries.add(createRectangleY(new Point(0, -100+0.09, -25), 80, 18)
									.setEmission(new Color(58, 58, 58))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
		constGeometries.add(createRectangleY(new Point(0, -100+0.09, 0), 20, 50)
									.setEmission(new Color(58, 58, 58))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		constGeometries.add(createRectangleY(new Point(0, -100+0.1, 25), 74, 12)
									.setEmission(new Color(150, 150, 150))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
		constGeometries.add(createRectangleY(new Point(0, -100+0.1, -25), 74, 12)
									.setEmission(new Color(150, 150, 150))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
		constGeometries.add(createRectangleY(new Point(0, -100+0.1, 0), 14, 50)
									.setEmission(new Color(150, 150, 150))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		//draw an circel around the H at the floor background				
		constGeometries.add(new Circle(new Point(0, -100+0.05, 0), axisY, 81)
									.setEmission(new Color(58, 58, 58))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		constGeometries.add(new Circle(new Point(0, -100+0.06, 0), axisY, 78)
									.setEmission(new Color(255, 211, 25))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		constGeometries.add(new Circle(new Point(0, -100+0.07, 0), axisY, 68)
									.setEmission(new Color(58, 58, 58))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

		constGeometries.add(new Circle(new Point(0, -100+0.08, 0), axisY, 65)
									.setEmission(new Color(93, 93, 93))
									.setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
		
		constLights.add(new DirectionalLight(new Color(200, 200, 200), new Vector(-1, -5, -7)));
		constLights.add(new PointLight(new Color(100, 100, 100), new Point(0, 110, 50))
									.setkL(0.0000003).setkQ(0.0000001));

		Vector lightsRadius = new Vector(60, 0, 0);
		double lightsSize = 3;

		for (int i = 0; i < 5; i++) {
			redLightsSurfaces.add(new Circle(new Point(0, -100+0.2,0).add(lightsRadius.Roatate(72*i, axisY)),
										axisY, lightsSize)
											.setMaterial(new Material().setkS(0.0002).setkT(0.99)));
		
			redLights.add(new SpotLight(new Color(900, 400, 400),
										new Point(0, -100+lightsSize/2,0).add(lightsRadius.Roatate(72*i, axisY)),
										new Vector(0, -1, 0)) //
										.setNarrowBeam(0.6).setkL(4E-5).setkQ(2E-7));
		}
		for (int i = 0; i < 5; i++) {
			greenLightsSurfaces.add(new Circle(new Point(0, -100+0.2,0).add(lightsRadius.Roatate(24+72*i, axisY)),
										axisY, lightsSize)
											.setMaterial(new Material().setkS(0.0002).setkT(0.99)));
		
			greenLights.add(new SpotLight(new Color(400, 900, 400),
										new Point(0, -100+lightsSize/2,0).add(lightsRadius.Roatate(24+72*i, axisY)),
										new Vector(0, -1, 0)) //
										.setNarrowBeam(0.6).setkL(4E-5).setkQ(2E-7));
		}
		for (int i = 0; i < 5; i++) {
			blueLightsSurfaces.add(new Circle(new Point(0, -100+0.2,0).add(lightsRadius.Roatate(48+72*i, axisY)),
										axisY, lightsSize)
											.setMaterial(new Material().setkS(0.0002).setkT(0.99)));
		
			blueLights.add(new SpotLight(new Color(400, 400, 900),
										new Point(0, -100+lightsSize/2,0).add(lightsRadius.Roatate(48+72*i, axisY)),
										new Vector(0, -1, 0)) //
										.setNarrowBeam(0.6).setkL(4E-5).setkQ(2E-7));
		}

		String imageName = new String("cool image/cool image %d");
		ImageWriter imageWriter = new ImageWriter("", 1000, 1000);

		Camera camera = new Camera(new Point(1000, 1000, 1000), new Vector(-1, -1, -1), new Vector(-1, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000)
			.spin(30)
			.spinRightLeft(0.0001)
			.setImageWriter(imageWriter);

		for (int i = 0; i < 180; i += 15) {
			Scene scene = new Scene("Test scene");
			scene.geometries.add(constGeometries);
			scene.geometries.add(choper.rotatHellicopter(i));

			scene.lights.addAll(constLights);
			
			switch ((i/45)%3) {
				case 0:	
					scene.lights.addAll(redLights);
					scene.geometries.add(redLightsSurfaces);
					break;
				case 1:
					scene.lights.addAll(greenLights);
					scene.geometries.add(greenLightsSurfaces);
					break;
				case 2:
					scene.lights.addAll(blueLights);
					scene.geometries.add(blueLightsSurfaces);
					break;
			}

			// camera.spinRightLeft(0.001);

			camera.setRayTracer(new RayTracerBasic(scene));
			camera.renderImage();
			imageWriter.setImageName(String.format(imageName, i));
			camera.writeToImage();
		}
	}
}
