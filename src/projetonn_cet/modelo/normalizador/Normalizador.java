/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo.normalizador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mpcsj
 */
public class Normalizador {

    public double[] maiorElementoDeCadaColuna;
    public double[] menorElementoDeCadaColuna;

    public void imprimeListaMaioresEMenoresElementos() {
        System.out.println("Lista de maiores elementos de cada coluna:");
        for (int i = 0; i < maiorElementoDeCadaColuna.length; i++) {
            System.out.print(maiorElementoDeCadaColuna[i] + "\t");
        }
        System.out.println("\nLista de menores elementos de cada coluna:");
        for (int i = 0; i < menorElementoDeCadaColuna.length; i++) {
            System.out.print(menorElementoDeCadaColuna[i] + "\t");
        }
    }

    public double[][] retornaListas() {
        double res[][] = new double[2][maiorElementoDeCadaColuna.length];
        for (int i = 0; i < maiorElementoDeCadaColuna.length; i++) {
            res[0][i] = maiorElementoDeCadaColuna[i];
        }
        for (int i = 0; i < menorElementoDeCadaColuna.length; i++) {
            res[1][i] = menorElementoDeCadaColuna[i];
        }
        return res;
    }

    public static double[][] normalizaEntradas(double[][] entradaOriginal) { // nao sei se será necessário
        // o processo é realizado coluna a coluna
        double[][] entradaNormalizada = new double[entradaOriginal.length][entradaOriginal[0].length];
        for (int i = 0; i < entradaOriginal[0].length; i++) {
            double maiorValorDaColuna = entradaOriginal[0][i];
            // a primeira busca encontra o menor valor da coluna, enquanto que o segundo faz a operação de fato
            for (double[] entrada : entradaOriginal) {
                if (entrada[i] > maiorValorDaColuna) {
                    maiorValorDaColuna = entrada[i];
                }
            }
            // o segundo for faz a operacao de normalizacao
            for (int j = 0; j < entradaOriginal.length; j++) {
                entradaNormalizada[i][j] = entradaOriginal[i][j] / maiorValorDaColuna;
            }

        }
        return entradaNormalizada;
    }

    /**
     * Recebe uma lista de vetores, os quais são as entradas da rede, e as
     * normaliza por "coluna" Pegando elemento por elemento de cada lista, como
     * se fosse uma matriz É importante que cada vetor da lista de vetores tenha
     * o mesmo tamanho
     *
     * @param entradaOriginal
     * @param atualizaListaMaioresMenores
     * @return
     */
    public List<double[]> normalizaBaseDados(List<double[]> entradaOriginal, boolean atualizaListaMaioresMenores) {

        List<double[]> resultado = new ArrayList<>(entradaOriginal.size());

        for (int i = 0; i < entradaOriginal.size(); i++) {// loop para criar a lista de vetores
            resultado.add(new double[entradaOriginal.get(0).length]);
        }
        if (atualizaListaMaioresMenores) {
            atualizaListaDeMaioresMenores(entradaOriginal);
        }
        for (int i = 0; i < entradaOriginal.get(0).length; i++) {// para cada "coluna"
            for (int j = 0; j < resultado.size(); j++) {// normalizando
                resultado.get(j)[i] = (entradaOriginal.get(j)[i] - menorElementoDeCadaColuna[i]) / (maiorElementoDeCadaColuna[i] - menorElementoDeCadaColuna[i]);
            }

        }

        return resultado;
    }

    public void atualizaListaDeMaioresMenores(List<double[]> entradaOriginal) {
        maiorElementoDeCadaColuna = new double[entradaOriginal.get(0).length];
        menorElementoDeCadaColuna = new double[entradaOriginal.get(0).length];
        for (int i = 0; i < entradaOriginal.get(0).length; i++) {// para cada "coluna"
            maiorElementoDeCadaColuna[i] = entradaOriginal.get(0)[i];
            menorElementoDeCadaColuna[i] = entradaOriginal.get(0)[i];
            for (double[] ds : entradaOriginal) {// procurando o maior elemento 
                if (ds[i] > maiorElementoDeCadaColuna[i]) {
                    maiorElementoDeCadaColuna[i] = ds[i];
                }
                if (ds[i] < menorElementoDeCadaColuna[i]) {
                    menorElementoDeCadaColuna[i] = ds[i];
                }
            }
        }
    }

    public List<double[]> desnormalizaBaseDados(List<double[]> baseDDados) {
        List<double[]> res = new ArrayList<>(baseDDados.size());
        for (double[] baseDDado : baseDDados) {
            double baseConvertida[] = new double[baseDDado.length];
            for (int i = 0; i < baseDDado.length; i++) {
                baseConvertida[i] = baseDDado[i] * (maiorElementoDeCadaColuna[i] - menorElementoDeCadaColuna[i]) + menorElementoDeCadaColuna[i];
            }
            res.add(baseConvertida);
        }
        return res;
    }
}
