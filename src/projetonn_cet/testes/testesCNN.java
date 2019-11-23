/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.testes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import projetonn_cet.view.ProcessaImagens;

/**
 *
 * @author mpcsj
 */
public class testesCNN {

    static void imprimeTensor(int[][][] cubo) {
        for (int k = 0; k < cubo[0][0].length; k++) {
            for (int i = 0; i < cubo.length; i++) {
                for (int j = 0; j < cubo[0].length; j++) {
                    System.out.print(cubo[i][j][k] + " ");
                }
                System.out.println("");
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedImage img = ImageIO.read(new File("imgs/imgTeste.jpg"));
//        imprimeTensor(ProcessaImagens.getPixels(img));
        int imgArray[][][] = ProcessaImagens.getPixels(img);
        int teste = 10;
    }
}
