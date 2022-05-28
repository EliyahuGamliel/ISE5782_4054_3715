package special;

import OBJParser.parser;
import geometries.*;
import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;
import models.hellicopter;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.GridScatter;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Minip2 {
    @Test
    void pictureTest() throws IOException {
        Vector axisY = new Vector(0, 1, 0);

        Geometries constGeometries = new Geometries();
        List<LightSource> constLights = new ArrayList<>();

        Geometries redLightsSurfaces = new Geometries();

        List<LightSource> redLights = new ArrayList<>();

        //sea
        constGeometries.add(new Plane(new Point(0, 0, 0), axisY)
                .setEmission(new Color(0, 105, 148))
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setkR(0.6).setkT(0.4).setShininess(150)));

        //island
        constGeometries.add(new Sphere(new Point(0, -70, 0), 100)
                .setEmission(new Color(194,178,128))
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(100)));

        //region palm tree

        //stem of Palm tree
        parser modelParser = new parser("scenes/stem.obj") ;
        constGeometries.add(modelParser.getFaces().scale(30).changeStartingPoint(new Point(20,25,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

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
        constGeometries.add(new Sphere(new Point(-50, 70, 0), 10)
                .setEmission(new Color(253, 184, 19))
                .setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));

        constLights.add(new SpotLight(new Color(900, 400, 400), new Point(0, 20+0.001,1000), new Vector(-50, 50, -900)).setNarrowBeam(0.6).setkL(4E-5).setkQ(2E-7));

        //light sun
        constLights.add(new DirectionalLight(new Color(0, 102, 204), new Vector(1, -5, -11)));
/*
        //helicopter
        constGeometries.add(new hellicopter(new Point(32, 75, -50), 1.5));

        //region "S"
        constGeometries.add(new Sphere(new Point(-29, 18, 39), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
        constGeometries.add(new Sphere(new Point(-27, 20, 36), 1).setEmission(new Color(128, 132, 135)).setMaterial(new Material().setkD(0.2).setkS(0.5).setShininess(300)));
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
        constGeometries.add(modelParser.getFaces().scale(2).changeStartingPoint(new Point(-10,0,700)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));


        //ship
        modelParser = new parser("scenes/ship.obj") ;
        constGeometries.add(modelParser.getFaces().scale(3).changeStartingPoint(new Point(-75,0,-1500)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
*/

        //flag
        modelParser = new parser("scenes/flag.obj") ;
        constGeometries.add(modelParser.getFaces().scale(9).changeStartingPoint(new Point(-20,40,0)).getShapes().stream().map((e)->(Intersectable)e.setEmission(new Color(103,95,75)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));

        ImageWriter imageWriter = new ImageWriter("The Final Picture", 1000, 1000);
        Camera camera = new Camera(new Point(0, 20, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000)
                .setImageWriter(imageWriter);

        Scene scene = new Scene("Test scene");
        scene.geometries.add(constGeometries);
        scene.lights.addAll(constLights);

        scene.lights.addAll(redLights);
        scene.geometries.add(redLightsSurfaces);
        scene.setBackground(new Color(162,237,255));

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();
    }
}
