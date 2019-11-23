/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.outros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import projetonn_cet.controle.classifier.SelectOneClassThreshold;
import projetonn_cet.controle.classifier.SimpleThreshold;
import projetonn_cet.controle.neuronio.NeuronioComRelu;
import projetonn_cet.controle.neuronio.NeuronioComSigmoid;
import projetonn_cet.controle.redeNeural.IRedeNeural;
import projetonn_cet.controle.redeNeural.NN_Classifier;
import projetonn_cet.controle.redeNeural.NN_RegressionLearning;
import projetonn_cet.modelo.DadosFromCSV;
import projetonn_cet.modelo.IDados;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.enumerados.PrintSintax;
import projetonn_cet.modelo.history.IHistory;
import projetonn_cet.modelo.normalizador.Normalizador;
import projetonn_cet.util.UtilLists;

/**
 *
 * @author mpcsj
 */
public class Trabs_IC {

    public static void trab1IC() {
        List<double[]> inputLogicGates = Arrays.asList(new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}});
        List<double[]> inputRobot = Arrays.asList(new double[][]{{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}});
        String modelos[] = new String[]{"OR", "AND", "XOR", "ROBOT"};
        List<List<double[]>> listaDBases = new ArrayList();
        List<List<double[]>> saidas = new ArrayList<>();
        // entradas
        listaDBases.add(inputLogicGates);// and
        listaDBases.add(inputLogicGates);// or
        listaDBases.add(inputLogicGates);//xor
        listaDBases.add(inputRobot);// robot
        // saidas
        saidas.add(Arrays.asList(new double[][]{{0}, {0}, {0}, {1}}));// output AND
        saidas.add(Arrays.asList(new double[][]{{0}, {1}, {1}, {1}}));// output OR
        saidas.add(Arrays.asList(new double[][]{{0}, {1}, {1}, {0}}));// output xor
        saidas.add(Arrays.asList(new double[][]{{1, 1}, {0, 1}, {0, 0}, {0, 1}, {1, 0}, {0, 0}, {1, 0}, {0, 0}}));// output Robot
        for (int i = 0; i < modelos.length; i++) {
            System.out.println("Teste com a base: " + modelos[i]);
            List<double[]> baseAtual = listaDBases.get(i);
            List<double[]> saidaAtual = saidas.get(i);
            IRedeNeural redeAtual = new NN_RegressionLearning(baseAtual.get(0).length);
            redeAtual.addCamada(3, new NeuronioComSigmoid());// camada oculta
            redeAtual.addCamada(saidaAtual.get(0).length, new NeuronioComSigmoid());// camada de saida
            IHistory historico = redeAtual.treinaRede(baseAtual, saidaAtual, 5000, 0, LossMetrics.SimpleDif);
            System.out.println("Predição: ");
            for (int j = 0; j < baseAtual.size(); j++) {
                System.out.println("Predição: " + UtilLists.getVet(redeAtual.executaRede(baseAtual.get(j)))
                        + " saida correta: " + UtilLists.getVet(saidaAtual.get(j)));
            }
            historico.salvaDadosNumArq("modelo_" + (i));
        }
    }

    public static void trab2IC() {
        // obs: a saida eh a primeira coluna
        // carrego a base 2,testo o modelo e imprimo os dados a serem comparados com o outro projeto feito
        IDados dados = new DadosFromCSV(0.3, "base_trab2.csv", new int[]{0});
//        Normalizador normalizadorTreino = new Normalizador();
//        List<double[]> baseNormalizadaTreino = normalizadorTreino.normalizaBaseDados(dados.getBaseEntradaTreino(), true);
//        Normalizador normalizadorTeste = new Normalizador();
//        List<double[]> baseNormalizadaTeste = normalizadorTeste.normalizaBaseDados(dados.getBaseEntradaTeste(), true);
        int nEntradas = dados.getBaseEntradaTreino().get(0).length;
//        IRedeNeural nn = new NN_RegressionLearning(nEntradas);

        IRedeNeural nn = new NN_Classifier(nEntradas, new SimpleThreshold());
//            nn.addCamada((nEntradas + dados.getBaseSaidaTreino().get(0).length) / 2, new NeuronioComSigmoid(0.0001));// hidden layer
        nn.addCamada(10, new NeuronioComRelu());
//            nn.addCamada(80, new NeuronioComSigmoid(0.0001));
        nn.addCamada(dados.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(0.0001));// output layer
        IHistory historicos[] = nn.treinaRede(dados.getBaseEntradaTeste(), dados.getBaseSaidaTreino(),
                dados.getBaseEntradaTeste(), dados.getBaseSaidaTeste(), 100000, 1, LossMetrics.ModuleDif);

        UtilLists.printLine("-", 50);
//        historicos[0].salvaDadosNumArq("");
//        historicos[1].salvaDadosNumArq("");

//        dados.imprimeDados(PrintSintax.pythonArray);
//        nn.treinaRede(dados.getBaseEntradaTeste(), dados.getBaseSaidaTreino(), 50000, 1);
    }
    public static void trab5IC(){
        IDados dados = new DadosFromCSV(0.3, "base_trab2.csv", new int[]{0});
        int nEntradas = dados.getBaseEntradaTreino().get(0).length;

//        IRedeNeural nn = new NN_Classifier(nEntradas, new SimpleThreshold());
        IRedeNeural nn= NN_Classifier.read("models/modelo_trab5");
        nn.addCamada(10, new NeuronioComRelu());
        nn.addCamada(dados.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(0.0001));// output layer
        IHistory historicos[] = nn.treinaRede(dados.getBaseEntradaTeste(), dados.getBaseSaidaTreino(),
                dados.getBaseEntradaTeste(), dados.getBaseSaidaTeste(), 10000, 1, LossMetrics.ModuleDif);

        UtilLists.printLine("-", 50);
        System.out.println("Salvando modelo");
//        nn.saveObject("models/modelo_trab5");
    }
    public static void trab5ICCompeticao(){
        int idxSaida[] = new int[]{23,24,25};
        IDados dadosTreino = new DadosFromCSV(0, "data_trab_ic/base_aumentada_treino", idxSaida);
        IDados dadosTeste = new DadosFromCSV(0, "data_trab_ic/base_aumentada_teste", idxSaida);
//        int idxSaida[] = new int[]{21,22,23};
//        IDados dadosTreino = new DadosFromCSV(0, "data_trab_ic/base_treino_c_ruido_gauss.csv", idxSaida);
//        IDados dadosTeste = new DadosFromCSV(0, "data_trab_ic/base_teste_c_ruido_gauss.csv", idxSaida);
        int nEntradas = dadosTreino.getDadosEntrada().get(0).length;
        double lr = 0.01;
        IRedeNeural nn = new NN_Classifier(nEntradas, new SelectOneClassThreshold());
//        IRedeNeural nn = new NN_RegressionLearning(nEntradas);
        nn.addCamada(idxSaida.length, new NeuronioComSigmoid(lr));
        nn.addCamada(dadosTreino.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(lr));// output layer
        IHistory historicos[] = nn.treinaRede(dadosTreino.getDadosEntrada(), dadosTreino.getDadosSaida(),
                dadosTeste.getDadosEntrada(), dadosTeste.getDadosSaida(), 1000000, 2, LossMetrics.SimpleDif);
//        IRedeNeural nn = new NN_Classifier(nEntradas, new SelectOneClassThreshold());
////        IRedeNeural nn = new NN_RegressionLearning(nEntradas);
//        nn.addCamada(30, new NeuronioComSigmoid(lr));
//        nn.addCamada(dadosTreino.getBaseSaidaTreino().get(0).length, new NeuronioComSigmoid(lr));// output layer
//        IHistory historicos[] = nn.treinaRede(dadosTreino.getDadosEntrada(), dadosTreino.getDadosSaida(),
//                dadosTeste.getDadosEntrada(), dadosTeste.getDadosSaida(), 1000000, 2, LossMetrics.SimpleDif);

        UtilLists.printLine("-", 50);
    }
    
    public static void trabFinal1(){
      double lr = 0.01;
      IDados dados = new DadosFromCSV(0.25,"data/dados1_tratados.csv",new int[]{18});
      IRedeNeural nn =new NN_Classifier(18, new SimpleThreshold());
      nn.addCamada(20, new NeuronioComSigmoid(lr));
      nn.addCamada(1,new NeuronioComSigmoid(lr));
      IHistory historico = nn.treinaRede(dados.getBaseEntradaTreino(),
              dados.getBaseSaidaTreino(),100000, 2, LossMetrics.SimpleDif);
    }
}
