package special;

import OBJParser.parser;
import geometries.Intersectable;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import per.mokiat.data.front.error.WFException;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.io.IOException;


public class Parser {
    private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
    private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light

    @Test
    public void TestParser() throws WFException, IOException {
        // Open a stream to your OBJ resource
        Scene scene = new Scene("Test scene");
        scene.setBackground(new Color(135, 206, 235));
        ImageWriter imageWriter = new ImageWriter("Ship", 5000, 5000);
        parser modelParser = new parser("scenes/ship.obj") ;
        scene.geometries.add(modelParser.getFaces().scale(2).rotate(0,new Vector(0,1,0)).getTriangles().stream().map((e)->(Intersectable)e.setEmission(new Color(75,83,32)) //
                .setMaterial(new Material().setkD(0.15).setkS(0.5).setShininess(300))).toArray(Intersectable[]::new));
//new Color(75,83,32)
        //
        scene.lights.add(new DirectionalLight(trCL, trDL));
        scene.lights.add(new DirectionalLight(trCL, new Vector(2,2,2)));

        Camera camera =  new Camera(new Point(1000, 0, -1000), new Vector(-1, 0, 1), new Vector(0, 1,0 )) //
                .setVPSize(150, 150) //
                .setVPDistance(15000)
                .spinRightLeft(0.0001)
                .setImageWriter(imageWriter);

        camera.setRayTracer(new RayTracerBasic(scene));
        camera.renderImage();
        camera.writeToImage();

    }
}
