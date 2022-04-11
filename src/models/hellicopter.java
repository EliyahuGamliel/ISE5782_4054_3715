package models;

import java.util.List;


import geometries.*;
import primitives.*;

public class hellicopter extends Geometries {

    private Color emission = new Color(0, 0, 100);
    private Material material = new Material().setkD(0.5).setkS(0.5).setShininess(300);

    public hellicopter(Point center, double size) {
        super();
        // Sphere cocpit;
        this.add(new Sphere(center, size * 5)
            .setEmission(emission)
            .setMaterial(material));
        // Cylinder mainRoterHandle;
        this.add(new Cylinder(new Ray(center.add(new Vector(0, size * 5, 0)), new Vector(0, 1, 0)),
                            size / 2,
                            size)
            .setEmission(emission)
            .setMaterial(material));
        // Polygon mainRotorWingX;
        this.add(createRectangleY(center.add(new Vector(0, size * 5 + size, 0)), size * 20, size)
            .setEmission(emission)
            .setMaterial(material));
        // Polygon mainRotorWingY;
        this.add(createRectangleY(center.add(new Vector(0, size * 5 + size, 0)), size, size * 20)
            .setEmission(emission)
            .setMaterial(material));
        // Triangle tail;
        this.add(new Triangle(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)),
                            center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                            center.add(new Vector(size * 5, size * -3, 0).normalize().scale(size * 5)))
            .setEmission(emission)
            .setMaterial(material));
        // Cylinder tailRoterHandle;
        this.add(new Cylinder(new Ray(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)),
                                        new Vector(0, 0, 1)),
                            size / 2,
                            size)
            .setEmission(emission)
            .setMaterial(material));
        // Polygon tailRotorWingX;
        this.add(createRectangleZ(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size * 5, size)
            .setEmission(emission)
            .setMaterial(material));
        // Polygon tailRotorWingY;
        this.add(createRectangleZ(center.add(new Vector(size * 5, size * 5, 0).normalize().scale(size * 5)).add(new Vector(size * 15, -size, 0)).add(new Vector(0, 0, size)),
                                size, size * 5)
            .setEmission(emission)
            .setMaterial(material));
    }

    Polygon createRectangleY(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, 0, height/2)),
            canter.add(new Vector(width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, height/2)),
        });
    }
    Polygon createRectangleZ(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, height/2, 0)),
            canter.add(new Vector(width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, height/2, 0)),
        });
    }
}
