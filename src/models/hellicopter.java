package models;

import static geometries.Utils.createRectangleYRotate;
import static geometries.Utils.createRectangleZRotate;

import geometries.Cylinder;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class hellicopter extends Geometries {
    private Point center;
    private double size;

    private Geometries body;

    private Color emission = new Color(75, 83, 32);
    private Material material = new Material().setkD(0.15).setkS(0.5).setShininess(300);

    private Color emissionWings = new Color(71,66, 42);
    private Material materialWings = new Material().setkD(0.1).setkS(0.5).setShininess(300);

    private Color emissionTailTop = new Color(75, 83, 32);
    private Color emissionTail = new Color(75, 83, 32);
    private Material materialTail = new Material().setkD(0.1).setkS(0.5).setShininess(300);

    public hellicopter(Point center, double size) {
        super();
        this.center = center;
        this.size = size;
        this.body = createBody();

        this.add(body);
        this.add(createRotor(0));
    }
    
    private hellicopter(Intersectable... geometries) {
        super(geometries);
    }

    public hellicopter rotatHellicopter(double angle) {
        hellicopter res = new hellicopter(body);
        res.add(createRotor(angle));
        return res;
    }

    private Geometries createBody() {
        Geometries body = new Geometries();
        // Sphere cocpit;
        body.add(new Sphere(center, size * 5)
            .setEmission(emission)
            .setMaterial(material));
        // Cylinder mainrotorHandle;
        body.add(new Cylinder(new Ray(center.add(new Vector(0, size * 5, 0)), new Vector(0, 1, 0)),
                            size / 2,
                            size)
            .setEmission(emission)
            .setMaterial(material));
        // Triangle tail left;
        body.add(new Triangle(center.add(new Vector(size * 5, size * 5, size).normalize().scale(size * 5)),
                            center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            center.add(new Vector(size * 5, size * -3, size).normalize().scale(size * 5)))
                .setEmission(emissionTail)
                .setMaterial(materialTail));
        // Triangle tail right;
        body.add(new Triangle(center.add(new Vector(size * 5, size * 5, -size).normalize().scale(size * 5)),
                            center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            center.add(new Vector(size * 5, size * -3, -size).normalize().scale(size * 5)))
                    .setEmission(emissionTail)
                    .setMaterial(materialTail));
        // Triangle tail top;
        body.add(new Triangle(center.add(new Vector(size * 5, size * 5, -size).normalize().scale(size * 5)),
                            center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            center.add(new Vector(size * 5, size * 5, size).normalize().scale(size * 5)))
                    .setEmission(emissionTailTop)
                    .setMaterial(materialTail));
        // Cylinder tailrotorHandle;
        body.add(new Cylinder(new Ray(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            new Vector(0, 0, 1)),
                            size / 2,
                            size)
                    .setEmission(emissionTail)
                    .setMaterial(materialTail));
        //right leg head
        body.add(new Cylinder(new Ray(center, new Vector(-7, -14, -8)),
                size / 4,
                size*8)
                .setEmission(emission)
                .setMaterial(material));

        //right leg tail
        body.add(new Cylinder(new Ray(center, new Vector(7 , -14, -8)),
                size / 4,
                size * 8)
                .setEmission(emission)
                .setMaterial(material));

        //right leg
        body.add(new Cylinder(new Ray(new Point( -0.5 * 8 * size, -0.83 * size * 8, -0.47 * size * 8), new Vector(14*size, 0, 0)),
                size / 2,
                size * 8)
                .setEmission(emission)
                .setMaterial(material));

        //right leg head
        body.add(new Cylinder(new Ray(center, new Vector(-7, -14, 8)),
                size / 4,
                size*8)
                .setEmission(emission)
                .setMaterial(material));

        //right leg tail
        body.add(new Cylinder(new Ray(center, new Vector(7 , -14, 8)),
                size / 4,
                size * 8)
                .setEmission(emission)
                .setMaterial(material));

        //right leg
        body.add(new Cylinder(new Ray(new Point( -0.5 * 8 * size, -0.83 * size * 8, 0.47 * size * 8), new Vector(14*size, 0, 0)),
                size / 2,
                size * 8)
                .setEmission(emission)
                .setMaterial(material));

        return body;
    }

    private Geometries createRotor(double angle) {
        Geometries rotor = new Geometries();
        // Polygon mainRotorWingX;
        rotor.add(createRectangleYRotate(center.add(new Vector(0, size * 5 + size, 0)), size * 20, size, angle)
            .setEmission(emissionWings)
            .setMaterial(materialWings));
        // Polygon mainRotorWingY;
        rotor.add(createRectangleYRotate(center.add(new Vector(0, size * 5 + size, 0)), size, size * 20, angle)
            .setEmission(emissionWings)
            .setMaterial(materialWings));
        
        // Polygon tailRotorWingX;
        rotor.add(createRectangleZRotate(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size * 5, size, angle)
            .setEmission(emissionWings)
            .setMaterial(materialWings));
        // Polygon tailRotorWingY;
        rotor.add(createRectangleZRotate(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size, size * 5, angle)
            .setEmission(emissionWings)
            .setMaterial(materialWings));
        return rotor;
    }

    // method to retrn random emission color
    Color getRandomEmission() {
        return new Color(
            (int)(Math.random() * 255),
            (int)(Math.random() * 255),
            (int)(Math.random() * 255)
        );
    }
}
