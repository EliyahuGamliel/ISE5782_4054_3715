package OBJParser;
import java.util.LinkedList;
import java.util.List;

import geometries.Polygon;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

public class parserModel {
    final private Point start ; 
    final private List<List<Vector>> lst  ;
    final double EPS = 0.000000001;
    final Vector VEPS = new Vector(EPS,EPS,EPS); 
    parserModel(List<List<Vector>> lst){
        this.lst = lst ; 
        start = new Point(0,0,0); 
    }
    parserModel(List<List<Vector>> lst, Point start ){
        this.lst = lst ; 
        this.start = start; 
    }
    public parserModel scale(double scaleSize){
        parserModel grounded = this.changeStartingPoint(new Point(0,0,0));
        parserModel scaled = new parserModel(grounded.lst.stream().map((i)->i.stream().map((j)->j.scale(scaleSize)).toList()
        ).toList());
        return scaled.changeStartingPoint(start);
    }
    public parserModel rotate(double angle, Vector axsis){
        parserModel grounded = this.changeStartingPoint(new Point(0,0,0));
        parserModel rotated = new parserModel(grounded.lst.stream().map((i)->i.stream()
            .map((j)->j.Roatate(angle,axsis)).toList()).toList());
        return rotated.changeStartingPoint(start);
    }
    public parserModel changeStartingPoint(Point newStart){
        return new parserModel(lst.stream().map((i)->i.stream().map((j)-> j.add(new Vector(EPS + start.getX(),EPS + start.getY(),EPS + start.getZ()).add(VEPS).scale(-1)).add(new Vector(EPS + newStart.getX(),EPS  + newStart.getY(),EPS + newStart.getZ()))).toList()).toList(),newStart);
    }
    public List<Polygon> getShapes(){
        List<Polygon> newLst = new LinkedList<Polygon>();
        for(var i :lst){
            try{
                if(i.size() < 3){
                    throw new IllegalArgumentException("cant get lines or single points");
                }
                if(i.size() == 3 ){
                    newLst.add(new Triangle(i.get(0),i.get(1),i.get(2)));
                }
                else{
                    newLst.add(new Polygon(i.toArray(Point[]::new)));
                }

            }
            catch(Exception e) {
                //I dont realy care about that
            }

        }
        return newLst ;
    }
}
