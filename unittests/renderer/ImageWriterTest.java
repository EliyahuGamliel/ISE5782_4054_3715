package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void Test1() {
        Color yellow = new Color(255,255,0);
        ImageWriter imageWriter = new ImageWriter("Test1", 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if ((i % 50 == 0) || (j % 50 == 0))
                    imageWriter.writePixel(i, j, Color.BLACK);
                else imageWriter.writePixel(i, j, yellow);
            }
        imageWriter.writeToImage();
    }
}