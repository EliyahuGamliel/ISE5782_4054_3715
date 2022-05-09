package geometries;

import primitives.Point;
import primitives.Vector;

public class Utils {
    
    public static Polygon createRectangleY(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, 0, height/2)),
            canter.add(new Vector(width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, -height/2)),
            canter.add(new Vector(-width/2, 0, height/2)),
        });
    }

    public static Polygon createRectangleYRotate(Point canter, double width, double height,double angle) {
        Vector axsis = new Vector(0,1,0);
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, 0, height/2).Roatate(angle, axsis)),
            canter.add(new Vector(width/2, 0, -height/2).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, 0, -height/2).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, 0, height/2).Roatate(angle, axsis)),
        });
    }

    public static Polygon createRectangleZ(Point canter, double width, double height) {
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, height/2, 0)),
            canter.add(new Vector(width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, -height/2, 0)),
            canter.add(new Vector(-width/2, height/2, 0)),
        });
    }

    public static Polygon createRectangleZRotate(Point canter, double width, double height,double angle) {
        Vector axsis = new Vector(0,0,1);
        return new Polygon(new Point[] {
            canter.add(new Vector(width/2, height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(width/2, -height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, -height/2, 0).Roatate(angle, axsis)),
            canter.add(new Vector(-width/2, height/2, 0).Roatate(angle, axsis)),
        });
    }
}
