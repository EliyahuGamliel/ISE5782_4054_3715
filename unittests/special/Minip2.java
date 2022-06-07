package special;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lighting.*;
import org.junit.jupiter.api.Test;

import OBJParser.parser;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import models.hellicopter;
import primitives.*;
import renderer.Camera;
import renderer.GridScatter;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class Minip2 {
    @Test
    void pictureTest() throws IOException {
        long time = System.currentTimeMillis();

        Vector axisY = new Vector(0, 1, 0);

        Geometries constGeometries = new Geometries();

        List<LightSource> constLights = new ArrayList<>();

        Geometries redLightsSurfaces = new Geometries();

        //sea
        constGeometries.add(new Plane(new Point(0, 0, 0), axisY)
                .setEmission(new Color(0, 105, 148))
                .setMaterial(new Material().setkT(0.2).setkR(0.2)));

        //island
        constGeometries.add(new Sphere(new Point(0, -70, 0), 100)
                .setEmission(new Color(194,178,128))
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(100)));

        //region palm tree

        //stem of Palm tree
        parser modelParser = new parser("scenes/stem.obj") ;
        constGeometries.add(modelParser.getFaces().scale(30).changeStartingPoint(new Point(20,25,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        //leaves of Palm tree
        modelParser = new parser("scenes/leaves.obj") ;
        constGeometries.add(modelParser.getFaces().scale(30).rotate(30, new Vector(1,0,0)).changeStartingPoint(new Point(58,22,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(58, 95, 11)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        //coconut of Palm tree
        modelParser = new parser("scenes/coconut.obj") ;
        constGeometries.add(modelParser.getFaces().scale(20).changeStartingPoint(new Point(37,37,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(150, 90, 62)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
        //endregion

        //sun
        constGeometries.add(new Sphere(new Point(-50, 70, -10), 10)
                .setEmission(new Color(253, 184, 19))
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constLights.add(new SpotLight(new Color(500, 500, 500), new Point(0, 50-0.001,1000), new Vector(0, -50, -1000)).setNarrowBeam(0.5).setkL(4E-5).setkQ(2E-7));

        //light sun
        constLights.add(new CirclesLight(new Color(100, 100, 100), new Point(-50, 70, -9), new Vector(8, -48, 9), 100).setNumOfShadowRays(10).setkL(4E-5).setkQ(2E-7));

        //helicopter
        constGeometries.add(new hellicopter(new Point(32, 75, -50), 1.5).rotatHellicopter(10));

        //region "S"
        constGeometries.add(new Sphere(new Point(-29, 18, 39), 1).setEmission(new Color(132, 132, 132)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-27, 20, 36), 1).setEmission(new Color(132, 132, 132)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-23, 20, 38), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-20, 18, 44), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(-28, 15.25, 45), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-25, 14, 49), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-22, 12.75, 52), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(-30, 10, 53), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-27, 8, 57), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-23, 8, 59), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-21, 10, 57), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        //endregion

        //region "O"
        constGeometries.add(new Sphere(new Point(-15.5, 12, 56), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-15.5, 15, 51), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(-15, 18, 47), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-12, 20, 44), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-8, 20, 44.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-5, 18, 49), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(-4.5, 12, 58), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-4.5, 15, 53), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(-15, 9, 60), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-12, 7, 63.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-8, 7, 64), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-5, 9, 62), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        //endregion

        //region "S"
        constGeometries.add(new Sphere(new Point(0, 18, 48.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(2, 20, 45), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(6, 20, 44), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(9, 18, 47.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(1, 15.25, 53.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(4, 14, 55), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(7, 12.75, 56.5), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constGeometries.add(new Sphere(new Point(0, 10, 61), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(3, 8, 63), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(7, 8, 63), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(9, 10, 60), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
//endregion

        //monkey
        modelParser = new parser("scenes/monkey.obj") ;
        constGeometries.add(modelParser.getFaces().scale(0.3).changeStartingPoint(new Point(0,30,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                 .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        //shark
        modelParser = new parser("scenes/shark.obj") ;
        constGeometries.add(modelParser.getFaces().scale(2).changeStartingPoint(new Point(-10,0,700)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(52, 54, 58)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        //ship
        modelParser = new parser("scenes/ship.obj") ;
        constGeometries.add(modelParser.getFaces().scale(3).changeStartingPoint(new Point(-75,0,-1500)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(0,0,128)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        //region flag
        Geometries flag = new Geometries();
        //flag_pole
        modelParser = new parser("scenes/flag_pole.obj") ;
        flag.add(modelParser.getFaces().scale(9).changeStartingPoint(new Point(-20,40,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
        //flag_white
        modelParser = new parser("scenes/flag_white.obj") ;
        flag.add(modelParser.getFaces().scale(9).changeStartingPoint(new Point(-20,40,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(WHITE)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
        //flag_blue
        modelParser = new parser("scenes/flag_blue.obj") ;
        flag.add(modelParser.getFaces().scale(9).changeStartingPoint(new Point(-20,40,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(BLUE)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
        //flag_red
        modelParser = new parser("scenes/flag_red.obj") ;
        flag.add(modelParser.getFaces().scale(9).changeStartingPoint(new Point(-20,40,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(RED)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
        constGeometries.add(flag);
//endregion

        ImageWriter imageWriter = new ImageWriter("The Final Picture", 100, 100);
        Camera camera = new Camera(new Point(0, 20, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .setScatterer(new GridScatter(2, 2))
                .setImageWriter(imageWriter);

        Scene scene = new Scene("Test scene");
        scene.geometries.add(constGeometries);
        scene.lights.addAll(constLights);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        scene.geometries.add(redLightsSurfaces);
        scene.setBackground(new Color(162,237,255));

        camera.setRayTracer(new RayTracerBasic(scene));

        System.out.format("build scene in %f sec\n", (System.currentTimeMillis()-time)/1000d);

        camera.renderImage();
        camera.writeToImage();
    }
}
