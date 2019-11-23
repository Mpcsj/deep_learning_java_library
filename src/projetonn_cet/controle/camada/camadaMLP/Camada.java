/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.camada.camadaMLP;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import projetonn_cet.modelo.enumerados.Distribution;
import projetonn_cet.controle.neuronio.INeuronio;

/**
 *
 * @author mpcsj
 */
public class Camada implements ICamada, Serializable {
// atributos:

    static Random random = new Random(System.currentTimeMillis());
    protected int numNeuronios;
    private final Distribution modoDistribuicaoPesos = Distribution.NORMALIZADO;
    List<INeuronio> listaNeuronios;
    private double[][] pesos;
//---------------------------------------------------------------------------------------------------------------------//
    // construtores:

    public Camada(int numNeuroniosCamAnterior, int numNeuroniosCamAtual, INeuronio[] funcoesAtivacao) { // passo uma lista de neuronios de uma vez
        // pq caso o neuronio tenha algum parametro, ele já vem para a camada definido de uma vez
        assert (funcoesAtivacao.length == numNeuroniosCamAtual); // conferir...

        this.numNeuronios = numNeuroniosCamAtual;
        listaNeuronios = new ArrayList<>(numNeuroniosCamAtual);
        for (int i = 0; i < numNeuroniosCamAtual; i++) {
            listaNeuronios.add(funcoesAtivacao[i]);
        }
        criaMatrizPesos(numNeuroniosCamAnterior, numNeuroniosCamAtual);
    }

//---------------------------------------------------------------------------------------------------------------------//
    // getters e setters:
    @Override
    public int getNumNeuronios() {
        return this.numNeuronios;
    }

    @Override
    public List<INeuronio> getListaNeuronios() {
        return this.listaNeuronios;
    }

    @Override
    public double[][] getPesos() {
        return this.pesos;
    }
//---------------------------------------------------------------------------------------------------------------------//
    // outras funcoes:

    private void criaMatrizPesos(int numLinhas, int numColunas) {
        pesos = new double[numLinhas][numColunas];
        switch (modoDistribuicaoPesos) {
            case DISTRIBUIDO:
                for (int i = 0; i < numLinhas; i++) {
                    for (int j = 0; j < numColunas; j++) {
                        pesos[i][j] = random.nextGaussian();
                    }
                }
                break;
            case NORMALIZADO:
                for (int i = 0; i < numLinhas; i++) {
                    for (int j = 0; j < numColunas; j++) {
                        pesos[i][j] = random.nextDouble();
                    }
                }
                break;
        }
    }

    @Override
    public double[] feedforward(double[] entrada) { // recebe apenas uma tupla de entrada
        // primeiro faz o produto escalar entre a matriz de peso e a matriz de entrada,
        // depois, usa a funcao de ativacao apropriada para o neuronio
        // o resultado de cada operacao, será mandado para cada neuronio da lista de neuronios da camada
        // onde, com base no que foi decidido no construtor, cada neuronio usará a função de ativação previamente selecionada

        double[] saida = new double[numNeuronios]; // Java já cria um vetor com zero em todas as posicoes
        for (int j = 0; j < saida.length; j++) {
            for (int i = 0; i < entrada.length; i++) {
                saida[j] += entrada[i] * pesos[i][j]; // conferir
            }
            saida[j] = listaNeuronios.get(j).getSaidaFuncao(saida[j]);
        }

        return saida;
    }

    @Override
    public boolean setPesos(double[][] pesos) {
        if (this.pesos.length == pesos.length && pesos[0].length == this.pesos[0].length) {// verifico as dimensoes da matriz
            this.pesos = pesos;
            return true;
        }
        return false;
    }

    @Override
    public String getJSON() {
        String teste = new Gson().toJson(this);
        return teste;
    }

}
