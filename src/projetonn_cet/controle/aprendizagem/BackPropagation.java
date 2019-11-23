/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.aprendizagem;

import java.util.List;
import java.util.ListIterator;
import projetonn_cet.controle.camada.camadaMLP.ICamada;
import projetonn_cet.controle.neuronio.INeuronio;
import projetonn_cet.modelo.enumerados.LossMetrics;

/**
 *
 * @author mpcsj
 */
public class BackPropagation implements ILearning {

    private final ListIterator<ICamada<Double>> itListaCamadas; // lista de camadas da minha rede
    private List<ICamada<Double>> listaCamadas;
    private final List<double[]> listaSaidasObtidas; // 
    private final int numCamadas;
    private LossMetrics metrics;

    public BackPropagation(List<ICamada<Double>> listaCamadas, List<double[]> saidasObtidas, LossMetrics metrics) {
        // ja comeco na ultima saida
        this.listaCamadas = listaCamadas;
        this.itListaCamadas = listaCamadas.listIterator(listaCamadas.size());
        // nao consigo comecar na saida aqui devido ao fato que nesse momento, a lista de saidas ainda não foi preenchida
        this.listaSaidasObtidas = saidasObtidas;
        numCamadas = listaCamadas.size();
        this.metrics = metrics;
    }

    @Override
    public double[] corrigePesos(double[] saidaObtida, double[] saidaEsperada) {
        assert (saidaObtida.length == saidaEsperada.length);

        double[] funcaoPerdaCamSaida = new double[saidaEsperada.length];
        // indo para a ultima saida na listaSaidasObtidas
//        while(listaSaidasObtidas.hasNext()){
//            listaSaidasObtidas.next();
//        }

        // a variavel abaixo aponta para os deltas da camada anterior, comecando pela camada de saida, ate chegar na primeira cada da rede
        double[] deltaCamSaida_anterior = new double[saidaEsperada.length]; // ja que os tamanhos sao iguais, poderia ser qualquer um dos vetores

        // calculando os deltas dos neuronios da camada de saida
        for (int i = 0; i < deltaCamSaida_anterior.length; i++) {// analise da camada de saida
            switch (this.metrics) {
                case ModuleDif:
                    funcaoPerdaCamSaida[i] = FuncoesPerda.diferencaEmModulo(saidaEsperada[i], saidaObtida[i]); // funcao de perda
                    break;
                case SimpleDif:
                    funcaoPerdaCamSaida[i] = FuncoesPerda.diferencaSimples(saidaEsperada[i], saidaObtida[i]); // funcao de perda
                    break;
                case SquaredDif:
                    funcaoPerdaCamSaida[i] = FuncoesPerda.diferencaQuadrado(saidaEsperada[i], saidaObtida[i]); // funcao de perda
                    break;
            }
//            deltaCamSaida_anterior[i] = (saidaObtida[i] * (1 - saidaObtida[i])) * funcaoPerdaCamSaida[i];// derivada Saida * erro da saida(backup code)
            deltaCamSaida_anterior[i] = (((INeuronio) listaCamadas.get(listaCamadas.size()-1).
                    getListaNeuronios().get(i)).getDerivadaFuncao(saidaObtida[i]))* funcaoPerdaCamSaida[i];// derivada Saida * erro da saida
        }
        // com os deltas da camada de saida calculados
        // vou iterando da ultima camada ate a camada de entrada para calcular os deltas de cada neuronio em cada camada para ir atualizando os pesos
        for (int prox = 0; itListaCamadas.hasPrevious(); prox++) {// enquanto eu tiver camadas para voltar
            ICamada camadaEmQuestao = itListaCamadas.previous();
            double[] listaSaidasCamadaAtual = listaSaidasObtidas.get(numCamadas - prox - 1);
            double[] deltaNeuroniosCamadaEmquestao = new double[camadaEmQuestao.getPesos().length];

            for (int i = 0; i < deltaNeuroniosCamadaEmquestao.length; i++) {
                deltaNeuroniosCamadaEmquestao[i] = listaSaidasCamadaAtual[i] * (1 - listaSaidasCamadaAtual[i]);
                double resSomatorio = 0;
                for (int j = 0; j < deltaCamSaida_anterior.length; j++) {
                    resSomatorio += deltaCamSaida_anterior[j] * camadaEmQuestao.getPesos()[i][j];
                }
                deltaNeuroniosCamadaEmquestao[i] *= resSomatorio;

            }
            // atualizando os pesos
            for (int i = 0; i < camadaEmQuestao.getPesos().length; i++) {
                for (int j = 0; j < camadaEmQuestao.getPesos()[0].length; j++) {
                    camadaEmQuestao.getPesos()[i][j] += ((INeuronio) camadaEmQuestao.getListaNeuronios().get(j)).getTaxaAprendizagem()
                            * deltaCamSaida_anterior[j] * listaSaidasCamadaAtual[i];
                }
            }
            // aponto a variavel de vetor de deltas da camada anterior para a camada atual para continuar a retropropagacao do erro
            deltaCamSaida_anterior = deltaNeuroniosCamadaEmquestao;
//            camadaEmQuestao = listaCamadas.previous(); // pego uma camada para estudo
        }

        // volto o iterador de camadas para a ultima camada(para as proximas execucoes)
        while (itListaCamadas.hasNext()) {
            itListaCamadas.next();
        }
        return funcaoPerdaCamSaida;
    }

}
