/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.outros;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetonn_cet.Application;
import projetonn_cet.controle.neuronio.NeuronioComSigmoid;
import projetonn_cet.controle.redeNeural.IRedeNeural;
import projetonn_cet.controle.redeNeural.NN_RegressionLearning;
import projetonn_cet.modelo.DadosFromCSV;
import projetonn_cet.modelo.IDados;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.history.IHistory;
import projetonn_cet.modelo.normalizador.Normalizador;
import projetonn_cet.util.Agregado;
import projetonn_cet.util.FeedForwardEstruturado;
import projetonn_cet.util.UtilLists;
import projetonn_cet.view.ExternaDadosRede;
import projetonn_cet.view.NNView;

/**
 *
 * @author mpcsj
 */
public class ProjetoCeT {
    public static LinkedList<Double> listaErrosSaida1 = new LinkedList<>();
    public static LinkedList<Double> listaErrosSaida2 = new LinkedList<>();
    public static LinkedList<Double> listaErrosSaida3 = new LinkedList<>();
    public static LinkedList<Double> listaErrosSaida4 = new LinkedList<>();

    public static void testaRedeEstruturada(IDados dadosPRede, List<double[]> entradas, List<double[]> saidas) {
        for (double[] entrada : entradas) {
            String resist = entrada[0] + "";
            Agregado agregado = new Agregado(entrada[1], entrada[2], entrada[3], entrada[4], entrada[5], entrada[6], entrada[7], entrada[8]);
            String cimento;
            if (entrada[9] == 1 && entrada[10] == 0 && entrada[11] == 0) {
                cimento = "CPII";
            } else if (entrada[9] == 0 && entrada[10] == 1 && entrada[11] == 0) {
                cimento = "CPIII";
            } else {
                cimento = "CPIV";
            }
            String solucao = entrada[12] + "";
            FeedForwardEstruturado.calcula(resist, agregado, cimento, solucao, (int) entrada[13]);
        }
    }

    public static void treinaRede(IRedeNeural rede, IDados dados, int qtdEpocasTreino, int colunaBuscada, boolean normaliza) {
        IHistory history = rede.treinaRede(dados.getBaseEntradaTreino(),
                UtilLists.pegaApenasColunaEspecificaDasSaidas(colunaBuscada, dados.getBaseSaidaTreino()),
                qtdEpocasTreino, 0, LossMetrics.SimpleDif);

        double erroTotal = 0;
        for (double ds : history.getErroEpocas()) {
            System.out.println(ds);
            listaErrosSaida1.add(ds);
            erroTotal += ds;
        }
        System.out.println("Erro total da rede: " + erroTotal);
    }

    public static IRedeNeural criaeTreinaRede1(FileWriter fr, List<double[]> entradas, List<double[]> saidas, int qtdEpocasTreino) throws IOException {
        System.out.println("Rede 1:");
        IRedeNeural redeNeural1 = new NN_RegressionLearning(entradas.get(0).length);

        // adiciono uma camada oculta e uma camada de saida
        redeNeural1.addCamada(8, new NeuronioComSigmoid());
        redeNeural1.addCamada(1, new NeuronioComSigmoid());
        // num neuronios,func ativacao d cada neuronio
        IHistory history = redeNeural1.treinaRede(entradas, UtilLists.pegaApenasColunaEspecificaDasSaidas(0, saidas), qtdEpocasTreino, 0, LossMetrics.SimpleDif);

        System.out.println("Erros ao longo das epocas:(UPV)");
        fr.write("Erros ao longo das epocas:(UPV)\n");
        double erroTotal = 0;
        for (double ds : history.getErroEpocas()) {
            System.out.println(ds);
            listaErrosSaida1.add(ds);
            erroTotal += ds;
        }
        fr.write(erroTotal / history.getErroEpocas().size() + "\n");
        return redeNeural1;
    }

    public static IRedeNeural criaeTreinaRede2(FileWriter fr, List<double[]> entradas, List<double[]> saidas, int qtdEpocasTreino) throws IOException {
        System.out.println("Rede 2:");
        IRedeNeural redeNeural2 = new NN_RegressionLearning(entradas.get(0).length);

        // adiciono uma camada oculta e uma camada de saida
        redeNeural2.addCamada(8, new NeuronioComSigmoid());
        redeNeural2.addCamada(1, new NeuronioComSigmoid());
        // num neuronios,func ativacao d cada neuronio
        IHistory history = redeNeural2.treinaRede(entradas, UtilLists.pegaApenasColunaEspecificaDasSaidas(1, saidas), qtdEpocasTreino, 0, LossMetrics.SimpleDif);

        System.out.println("Erros ao longo das epocas:(FC)\n");
        double erroTotal = 0;
        for (double ds : history.getErroEpocas()) {
            System.out.println(ds);
            listaErrosSaida2.add(ds);
            erroTotal += ds;
        }
        fr.write(erroTotal / history.getErroEpocas().size() + "\n");
        return redeNeural2;
    }

    public static IRedeNeural criaeTreinaRede3(FileWriter fr, List<double[]> entradas, List<double[]> saidas, int qtdEpocasTreino) throws IOException {
        // rede para trabalhar com a terceira saida
        // ...
        System.out.println("Rede 3:");
        IRedeNeural redeNeural3 = new NN_RegressionLearning(entradas.get(0).length);

        // adiciono uma camada oculta e uma camada de saida
        redeNeural3.addCamada(8, new NeuronioComSigmoid());
        redeNeural3.addCamada(1, new NeuronioComSigmoid());
        // num neuronios,func ativacao d cada neuronio
        IHistory history = redeNeural3.treinaRede(entradas, UtilLists.pegaApenasColunaEspecificaDasSaidas(2, saidas), qtdEpocasTreino, 0, LossMetrics.SimpleDif);
        double erroTotal = 0;
        System.out.println("Erros ao longo das epocas:(PM)");
        fr.write("Erros ao longo das epocas:(PM)");
        for (double ds : history.getErroEpocas()) {
            System.out.println(ds);
            listaErrosSaida3.add(ds);
            erroTotal += ds;
        }
        fr.write(erroTotal / history.getErroEpocas().size() + "\n");
        return redeNeural3;
    }

    public static void projCeT() throws IOException {
        File file = new File("ErrosDasRedes");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        IDados dadosPRede = new DadosFromCSV(0.33, null, new int[]{11, 12, 13});// tres ultimas colunas
        // ------------------------------------------------------------------------------------------------------------------------------------//
        // ------------------------------------------------------------------------------------------------------------------------------------//
        // ainda tenho que dividir a base de dados entre parte de treinamento e parte de testes

        Normalizador normA = new Normalizador();
        Normalizador normB = new Normalizador();

        normA.atualizaListaDeMaioresMenores(dadosPRede.getDadosEntrada());// para atualizar a lista de pesos com todos os dados da rede
        normB.atualizaListaDeMaioresMenores(dadosPRede.getDadosSaida()); // idem

        List<double[]> entradas = UtilLists.normalizaOuNao(normA, dadosPRede.getBaseEntradaTreino(), true);
        List<double[]> saidas = UtilLists.normalizaOuNao(normB, dadosPRede.getBaseSaidaTreino(), true);

        int qtdEpocasTreino = 10000;
        IRedeNeural redeNeural1 = criaeTreinaRede1(fr, entradas, saidas, qtdEpocasTreino);
        IRedeNeural redeNeural2 = criaeTreinaRede2(fr, entradas, saidas, qtdEpocasTreino);
        IRedeNeural redeNeural3 = criaeTreinaRede3(fr, entradas, saidas, qtdEpocasTreino);

        entradas = normA.normalizaBaseDados(dadosPRede.getBaseEntradaTeste(), true); // normalizando a base de teste
        saidas = normB.normalizaBaseDados(dadosPRede.getBaseSaidaTeste(), true); // idem

        UtilLists.escreveDadosCSVSaida(new File("SaidasRede1"), UtilLists.pegaApenasColunaEspecificaDasSaidas(0, saidas), redeNeural1.executaRede(entradas));
        UtilLists.escreveDadosCSVSaida(new File("SaidasRede2"), UtilLists.pegaApenasColunaEspecificaDasSaidas(1, saidas), redeNeural2.executaRede(entradas));
        UtilLists.escreveDadosCSVSaida(new File("SaidasRede3"), UtilLists.pegaApenasColunaEspecificaDasSaidas(2, saidas), redeNeural3.executaRede(entradas));

        NNView view = new ExternaDadosRede();
        view.externaListaPesosRedeEDadosNormalizacao(normA.retornaListas(), normB.retornaListas(), redeNeural1.retornaPesosDaRede());
        view.externaListaPesosRedeEDadosNormalizacao(normA.retornaListas(), normB.retornaListas(), redeNeural2.retornaPesosDaRede());
        view.externaListaPesosRedeEDadosNormalizacao(normA.retornaListas(), normB.retornaListas(), redeNeural3.retornaPesosDaRede());
    }
}
