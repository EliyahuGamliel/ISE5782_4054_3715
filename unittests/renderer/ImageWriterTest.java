package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void Test1() {
        Color yellow = new Color(255,255,0);
        ImageWriter imageTest = new ImageWriter("Test1", 16, 10);
        for (int i = 0; i < imageTest.getNx(); i++) {
            for (int j = 0; j < imageTest.getNy(); j++) {
                imageTest.writePixel(i, j, yellow);
            }
        }
        imageTest.writeToImage();
    }
}