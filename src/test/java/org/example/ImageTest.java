package org.example;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class ImageTest {
    @Test
    public void test()  {
        try {

//            BufferedImage image1 = ImageIO.read(ImageTest.class.
//                    getClassLoader().getResourceAsStream("images/bulletD.png"));
//            System.out.println(image1);


            BufferedImage image2 = ImageIO.read(ImageTest.class.
                    getClassLoader().getResourceAsStream("src/images/b.jpg"));

            System.out.println(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
