/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.camada.camadaConvolucao;

import java.util.Random;

/**
 *
 * @author mpcsj
 */
public class Filtro {

    Random r = new Random(System.currentTimeMillis());

    public int[][] geraFiltro2DAleatorio(int valores[], int largura, int altura) {
        int filtro[][] = new int[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                filtro[i][j] = valores[r.nextInt(valores.length)];
            }
        }
        return filtro;
    }
}
