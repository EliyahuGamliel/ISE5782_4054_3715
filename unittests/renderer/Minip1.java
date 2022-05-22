package renderer;

import OBJParser.parser;
import geometries.*;
import lighting.*;
import models.hellicopter;
import org.junit.jupiter.api.Test;
import per.mokiat.data.front.error.WFException;
import primitives.*;
import scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static geometries.Utils.createRectangleY;

public class Minip1 {

    @Test
    void coolGIF() {
        Vector axisY = new Vector(0, 1, 0);

        Geometries constGeometries = new Geometries();
        List<LightSource> constLights = new ArrayList<>();

        Geometries redLightsSurfaces = new Geometries();

        List<LightSource> redLights = new ArrayList<>();

        hellicopter choper = new hellicopter(new Point(0, 0, 0), 8);
        //add a plane to geometries as a background
        constGeometries.add(new Plane(new Point(0, -100, 0), axisY)
                .setEmission(new Color(155, 118, 83))
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

        ImageWriter imageWriter = new ImageWriter("pic1", 1000, 1000);

        Camera camera =  new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1,0 )) //
                .setVPSize(150, 150) //
                .setVPDistance(700)
                .spinRightLeft(0.0001)
                .setImageWriter(imageWriter)
                .setScatterer(new GridScatter(3, 3));

        ImageWriter imageWriter2 = new ImageWriter("pic2", 1000, 1000);

        Camera camera2 = new Camera(new Point(1000, 0, 0), new Vector(-1, 0, 0), new Vector(0, 1,0 )) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .spinRightLeft(0.0001)
                .setImageWriter(imageWriter2);



        Scene scene = new Scene("Test scene");
        scene.geometries.add(constGeometries);
        scene.geometries.add(choper.rotatHellicopter(0));
        scene.setBackground(new Color(135, 206, 235));
        scene.lights.addAll(constLights);


        scene.lights.addAll(redLights);
        scene.geometries.add(redLightsSurfaces);

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();
        camera2.setRayTracer(new RayTracerBasic(scene));
        camera2.renderImage();
        camera2.writeToImage();
    }
    private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
    private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light

    @Test
    public void TestParser() throws WFException, IOException {
        // Open a stream to your OBJ resource
        Scene scene = new Scene("Test scene");
        scene.setBackground(new Color(135, 206, 235));
        ImageWriter imageWriter = new ImageWriter("Ship", 5000, 5000);
        parser modelParser = new parser("scenes/ship2.obj") ;
        scene.geometries.add(modelParser.getFaces().scale(2).rotate(0,new Vector(0,1,0)).getTriangles().stream().map((e)->(Intersectable)e.setEmission(new Color(75,83,32)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        scene.lights.add(new DirectionalLight(trCL, trDL));
        scene.lights.add(new DirectionalLight(trCL, new Vector(2,2,2)));

        Camera camera =  new Camera(new Point(1000, 0, -1000), new Vector(-1, 0, 1), new Vector(0, 1,0 )) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .spinRightLeft(0.0001)
                .setImageWriter(imageWriter);

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();

    }

    @Test
    void antiAlisingTest() {
        Vector axisY = new Vector(0, 1, 0);

        Geometries constGeometries = new Geometries();
        List<LightSource> constLights = new ArrayList<>();

        Geometries redLightsSurfaces = new Geometries();

        List<LightSource> redLights = new ArrayList<>();

        hellicopter choper = new hellicopter(new Point(0, 0, 0), 8);
        //add a plane to geometries as a background
        constGeometries.add(new Plane(new Point(0, -100, -200), axisY)
                .setEmission(new Color(155, 118, 83))
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


            ImageWriter imageWriter = new ImageWriter("AntiAlisingTest", 1000, 1000);
        Camera camera = new Camera(new Point(1000, 1000, 1000), new Vector(-1, -1, -1), new Vector(-1, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .spin(30)
                .spinRightLeft(0.0001)
                .setScatterer(new GridScatter(3,3))
                .setImageWriter(imageWriter);

                Scene scene = new Scene("Test scene");
                scene.geometries.add(constGeometries);
                scene.geometries.add(choper.rotatHellicopter(0));
                scene.lights.addAll(constLights);

                scene.lights.addAll(redLights);
                scene.geometries.add(redLightsSurfaces);

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();
    }

    @Test
    void withoutAntiAlisingTest() {
        Vector axisY = new Vector(0, 1, 0);

        Geometries constGeometries = new Geometries();
        List<LightSource> constLights = new ArrayList<>();

        Geometries redLightsSurfaces = new Geometries();

        List<LightSource> redLights = new ArrayList<>();

        hellicopter choper = new hellicopter(new Point(0, 0, 0), 8);
        //add a plane to geometries as a background
        constGeometries.add(new Plane(new Point(0, -100, -200), axisY)
                .setEmission(new Color(155, 118, 83))
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


        ImageWriter imageWriter = new ImageWriter("withoutAntiAlisingTest", 1000, 1000);
        Camera camera = new Camera(new Point(1000, 1000, 1000), new Vector(-1, -1, -1), new Vector(-1, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .spin(30)
                .spinRightLeft(0.0001)
                .setImageWriter(imageWriter);

        Scene scene = new Scene("Test scene");
        scene.geometries.add(constGeometries);
        scene.geometries.add(choper.rotatHellicopter(0));
        scene.lights.addAll(constLights);

        scene.lights.addAll(redLights);
        scene.geometries.add(redLightsSurfaces);

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();
    }

    @Test
    void softShadowTest() {
        Scene scene = new Scene("the soft shadow test");

        scene.geometries.add(new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(155, 118, 83))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
        scene.geometries.add(new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 20, 60)
                .setEmission(new Color(0, 100, 100))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));

        scene.lights.add(new CirclesLight(new Color(100,100,100), new Point(300, 200, 0), new Vector(0, -1, 0), 50)
                .setNumOfShadowRays(50)
                .setkL(0.0000003).setkQ(0.0000001));

        Camera camera = new Camera(new Point(-200, 200, 200), new Vector(1, -1, -1), new Vector(1, 0, 1))
                .setVPSize(100, 100)
                .setVPDistance(150)
                .setImageWriter(new ImageWriter("SoftShadow", 700, 700))
                .setRayTracer(new RayTracerBasic(scene))
                .setScatterer(new GridScatter(3, 3))
                .spin(-90);

        camera.renderImage();
        camera.writeToImage();
    }

    @Test
    void withoutSoftShadowTest() {
        Scene scene = new Scene("the soft shadow test");

        scene.geometries.add(new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(155, 118, 83))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));
        scene.geometries.add(new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)), 20, 60)
                .setEmission(new Color(0, 100, 100))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(300)));


        scene.lights.add(new PointLight(new Color(100,100,100), new Point(300, 200, 0))
                 .setkL(0.0000003).setkQ(0.0000001));

        Camera camera = new Camera(new Point(-200, 200, 200), new Vector(1, -1, -1), new Vector(1, 0, 1))
                .setVPSize(100, 100)
                .setVPDistance(150)
                .setImageWriter(new ImageWriter("withoutSoftShadow", 700, 700))
                .setRayTracer(new RayTracerBasic(scene))
                .setScatterer(new GridScatter(3, 3))
                .spin(-90);

        camera.renderImage();
        camera.writeToImage();
    }

}
