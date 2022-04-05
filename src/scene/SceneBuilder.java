package scene;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import geometries.*;
import lighting.*;
import parser.*;
import primitives.*;

import static parser.Utils.*;

public class SceneBuilder {
    SceneDescriptor sceneDesc;
    Scene scene;
    String filePath;

    public void loadSceneFromFile(File file) throws SAXException, IOException, ParserConfigurationException {
        filePath = file.getAbsolutePath();
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        SceneXMLHandler handler = new SceneXMLHandler();
        saxParser.parse(file, handler);

        sceneDesc = handler.getSceneDescriptor();

        scene = new Scene(filePath);
        
        Color backgroundColor = makeColorFromString(sceneDesc.sceneAttributes.get("background-color"));
		scene.setBackground(backgroundColor);

		Color ambientLightColor = makeColorFromString(sceneDesc.ambientLightAttributes.get("color"));
		AmbientLight ambientLight = new AmbientLight(ambientLightColor, new Double3(1,1,1));
		scene.setAmbientLight(ambientLight);

        Geometries geometries = new Geometries();
        for (Map<String, String> sphereString : sceneDesc.spheres) {
            Sphere sphere = makeSphere(sphereString);
			geometries.add(sphere);
        }
        for (Map<String, String> triangleString : sceneDesc.triangles) {
            Triangle triangle = makeTriangle(triangleString);
			geometries.add(triangle);
        }
		scene.setGeometries(geometries);
    }

    public Scene getScene() {
        return scene;
    }
}
