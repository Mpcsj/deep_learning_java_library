/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.camada.camadaConvolucao;

import java.util.List;
import projetonn_cet.controle.neuronio.NeuronioComRelu;
import projetonn_cet.modelo.ModeloPooling;

/**
 *
 * @author mpcsj
 */
public class CamadaConvolucao {

    /**
     * Retorna um cubo de tamanho [dimA do resultado da convolucao][dimB do
     * resultado da convolucao][numero de filtros]
     *
     * @param entrada cubo de entrada (na camada de entrada se trata da imagem
     * de entrada)
     * @param listaFiltros filtros que quero aplicar (cada filtro tem que ter o
     * mesmo numero de canais que a entrada e os filtros precisam ser do mesmo
     * tamanho)
     * @param tamSalto tamanho do salto que quero dar em cada multiplicacao
     * @return cubo processado pelos filtros
     */
    public static int[][][] convolucao(int[][][] entrada, List<int[][][]> listaFiltros, int tamSalto) {
        int[][][] res = new int[listaFiltros.size()][(entrada[0].length - listaFiltros.get(0)[0].length) / tamSalto][(entrada[0][0].length - listaFiltros.get(0)[0][0].length) / tamSalto];
        for (int k = 0; k < listaFiltros.size(); k++) { // para cada filtro da lista

        }
        return res;
    }

    public static int[][][] pooling(ModeloPooling metodoPooling, int[][][] in, int larguraBox, int alturaBox) {
        int res[][][] = new int[in.length][(in[0].length / larguraBox) + 1 * ((in[0].length % larguraBox == 0) ? 0 : 1)][in[0][0].length / alturaBox + 1 * ((in[0][0].length % alturaBox == 0) ? 0 : 1)];
        int atual[] = new int[larguraBox + alturaBox];
        int idxAtual = 0;
        
        if (metodoPooling == ModeloPooling.maxPooling) {
            // a cada cubo de dimensoes larguraBox e alturaBox, seleciono o maior elemento
        } else if (metodoPooling == ModeloPooling.averagePooling) {
            // a cada cubo de dimensoes larguraBox e alturaBox, pego a media

        }
        return res;
    }

    /**
     * Aplica a funcao relu em cada elemento da matriz original
     *
     * @param in cubo de entrada
     */
    public static void reluLayer(int in[][][]) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                for (int k = 0; k < in[0][0].length; k++) {
                    in[i][j][k] = NeuronioComRelu.funcaoReluPInt(in[i][j][k]);
                }
            }
        }
    }

    /**
     * Aplica a funcao relu em cada elemento de uma copia da matriz original sem
     * alterar a funcao original
     *
     * @param in cubo de entrada
     * @return copia processada criada
     */
    public static int[][][] reluLayerCopia(int in[][][]) {
        int res[][][] = new int[in.length][in[0].length][in[0][0].length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                for (int k = 0; k < in[0][0].length; k++) {
                    res[i][j][k] = NeuronioComRelu.funcaoReluPInt(in[i][j][k]);
                }
            }
        }
        return res;
    }
}
