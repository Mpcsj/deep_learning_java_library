/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.view;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author mpcsj
 */
public class ProcessaImagens {

    public static int[][][] getPixels(BufferedImage image) {
//        int[][][] result = new int[image.getWidth()][image.getHeight()][4];
        int[][][] result = new int[3][image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y), true);
                result[0][x][y] = c.getRed();
                result[1][x][y] = c.getGreen();
                result[2][x][y] = c.getBlue();
//                result[x][y][3] = c.getAlpha();
            }
        }
        return result;
    }
}
