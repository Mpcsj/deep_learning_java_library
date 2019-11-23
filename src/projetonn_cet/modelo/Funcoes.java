/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo;

/**
 *
 * @author mpcsj
 */
public class Funcoes {

    public static double[][] multiplicaMatrizes(double[][] mat1, double[][] mat2) {
        /*Multiplica a matriz mat1 , com n1 linhas e m1 colunas  pela matriz mat2,com n2 linhas e m2 colunas */
        double[][] res = new double[mat1.length][mat2[0].length];
        int i, j, k;
        int aux, proxLinha, proxColuna;
        proxLinha = proxColuna = 0;
        for (i = 0; i < mat1.length; i++) {// varrendo por linhas da primeira
            for (k = 0; k < mat2[0].length; k++) {
                aux = 0;
                for (j = 0; j < mat2.length; j++) {// varrendo por colunas da segunda
                    aux += mat1[i][j] * mat2[j][k];
                }
                if (proxColuna == mat2[0].length) {
                    proxColuna = 0;
                    proxLinha++;
                }
                res[proxLinha][proxColuna] = aux;
                proxColuna++;
            }
        }
        return res;
    }
    public static double[][] transpoeMatriz(double matriz[][]){
        double res[][] = new double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                res[j][i] = matriz[i][j];
            }
        }
        return res;
    }
    public static double[][] transpoeMatriz(double matriz[]){
        double res[][] = new double[matriz.length][1];
        for (int i = 0; i < matriz.length; i++) {
            res[i][0] = matriz[i];
        }
        return res;
    }
    public static double[][] transformaVetorEmMatriz(double[] vetor){
        double res[][] = new double[1][vetor.length];
        for (int i = 0; i < vetor.length; i++) {
            res[0][i] = vetor[i];
        }
        return res;
    }
}
