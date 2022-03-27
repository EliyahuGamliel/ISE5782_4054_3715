package parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SceneXMLHandler extends DefaultHandler {
    SceneDescriptor sceneDescriptor;

    public SceneDescriptor getSceneDescriptor() {
        return sceneDescriptor;
    }

    @Override
    public void startDocument() {
        sceneDescriptor = new SceneDescriptor();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("scene")) {
            sceneDescriptor.sceneAttributes = attributesToMap(attributes);
        } else if (qName.equalsIgnoreCase("ambient-light")) {
            sceneDescriptor.ambientLightAttributes = attributesToMap(attributes);
        } else if (qName.equalsIgnoreCase("geometries")) {
            sceneDescriptor.spheres = new LinkedList<Map<String,String>>();
            sceneDescriptor.triangles = new LinkedList<Map<String,String>>();
        } else if (qName.equalsIgnoreCase("sphere")) {
            Map<String, String> sphere = attributesToMap(attributes);
            sceneDescriptor.spheres.add(sphere);
        } else if (qName.equalsIgnoreCase("triangle")) {
            Map<String, String> triangle = attributesToMap(attributes);
            sceneDescriptor.triangles.add(triangle);
        }
    }
    
    Map<String, String> attributesToMap(Attributes attributes) {
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i <  attributes.getLength(); i++) {
            map.put(attributes.getQName(i),attributes.getValue(i));
        }

        return map;
    }
    
}
