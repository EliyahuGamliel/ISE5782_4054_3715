package parser;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SceneDescriptor {
    public Map <String, String> sceneAttributes;
    public Map <String, String> ambientLightAttributes;
    public List <Map <String, String>> spheres;
    public List <Map <String, String>> triangles;
}
