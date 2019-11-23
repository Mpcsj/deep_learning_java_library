/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.redeNeural;

import java.util.List;
import projetonn_cet.controle.neuronio.INeuronio;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.history.IHistory;

/**
 *
 * @author mpcsj
 */
public interface IRedeNeural {

    /**
     * Treina a rede neural para que a mesma aprenda, independentemente se a
     * mesma se trata de uma rede simples, uma rede convolucional, rede
     * recorrente aprendizado supervisionado, nao supervisionado ou por reforco
     *
     * @param entrada
     * @param saida
     * @param numEpocas
     * @param verbose o quanto de informacao que seja printado ao longo do
     * treinamento
     * @param lossMetrics metrica de correcao na camada de saida
     * @return historico de treinamento da rede
     */
    public IHistory treinaRede(List entrada, List saida, int numEpocas, int verbose, LossMetrics lossMetrics);// posso passar um verbose tambem por parametro
    // que indica o quanto de informacao eu quero que seja printado a cada epoca
    // posso passar por parametro tambem um boolean que indica se quero que no final do processo
    // seja salvo o melhor modelo em treino

    /**
     * Treina a rede neural
     *
     * @param entradaTreino
     * @param saidaTreino
     * @param entradasTeste
     * @param saidasTeste
     * @param numEpocas
     * @param verbose
     * @param lossMetrics
     * @return um vetor com dois historicos: o historico do treinamento e do
     * teste
     */
    public IHistory[] treinaRede(List entradaTreino, List saidaTreino,
            List entradasTeste, List saidasTeste, int numEpocas, int verbose, LossMetrics lossMetrics);

    /**
     * Adiciona camada uma camada de neuronios na rede neural, recebendo como
     * parâmetro o numero de neuronios e um vetor de Strings, como a funcao de
     * ativacao a ser usada em cada neuronio. E importante ressaltar que a
     * camada de entrada não estara na lista de camadas pois a mesma não possui
     * conexão com camadas anteriores
     *
     * @param numNeuronios
     * @param funcoesAtivacaoDCadaNeuronio
     */
    public void addCamada(int numNeuronios, INeuronio[] funcoesAtivacaoDCadaNeuronio);

    /**
     * Adiciona camada uma camada de neuronios na rede neural, recebendo como
     * parâmetro o numero de neuronios e uma String, como a funcao de ativacao a
     * ser usada em todos os neuronios. E importante ressaltar que a camada de
     * entrada não estara na lista de camadas pois a mesma não possui conexão
     * com camadas anteriores
     *
     * @param numNeuronios
     * @param funcoesAtivacaoDCadaNeuronio
     */
    public void addCamada(int numNeuronios, INeuronio funcoesAtivacaoDCadaNeuronio);

    /**
     * executa a rede neural, fazendo uma regressão ou classificacao, dependendo
     * do que a mesma deve fazer
     *
     * @param entradasPExecucao
     * @return
     */
    public List executaRede(List entradasPExecucao);

    public double[] executaRede(double[] entradaPExecucao);

    /**
     * Retorna a lista de pesos da rede, onde cada lista representa os pesos de
     * cada camada da rede
     *
     * @return
     */
    public List retornaPesosDaRede();
    
    /**
    * Salva objeto no arquivo especificado
     * @param nomeArq a ser salvo
     * @return se o arquivo foi corretamente salvo
    */
    public boolean saveObject(String nomeArq);
    /**
     * Le objeto
     * @param nomeArq de onde o objeto sera lido
     * @return o objeto lido
     */
    public IRedeNeural loadObject(String nomeArq);
    
    /**
     * Obtem o objeto em repr JSON
     * @return 
    */
    public String getJSONObject();
}
