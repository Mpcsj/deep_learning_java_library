/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetonn_cet.Application;
import projetonn_cet.controle.aprendizagem.FuncoesPerda;
import projetonn_cet.controle.camada.camadaMLP.ICamada;
import projetonn_cet.controle.redeNeural.IRedeNeural;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.normalizador.Normalizador;

/**
 *
 * @author mpcsj
 */
public class UtilLists {

    /**
     * @param testSize porcentagem da base de teste
     * @param randomState semente de aleatoriedade para o shuffle
     * @param entradas base de entrada
     * @param saidas base de saida
     * @param shuffle indica se quero que a base esteja aleatoria
     * @return entradasTreino,entradasTeste,saidasTreino,saidasTeste
     */
    public static List<List<double[]>> trainTestSplit(double testSize, long randomState, List<double[]> entradas, List<double[]> saidas, boolean shuffle) {
        List<double[]> entradasTeste = new ArrayList();
        List<double[]> saidasTeste = new ArrayList();
        List<double[]> entradasTreino = new ArrayList();
        List<double[]> saidasTreino = new ArrayList();
        List<Integer> indices = new ArrayList(entradas.size());
        for (int i = 0; i < entradas.size(); i++) {
            indices.add(i);
        }
        if (shuffle) {
            Collections.shuffle(indices, new Random(randomState));
        }
        int qtdBaseTeste = (int) (indices.size() * testSize);
        int i = 0;
        for (; i < qtdBaseTeste; i++) {
            entradasTeste.add(entradas.get(indices.get(i)));
            saidasTeste.add(saidas.get(indices.get(i)));
        }
        for (; i < indices.size(); i++) {
            entradasTreino.add(entradas.get(indices.get(i)));
            saidasTreino.add(saidas.get(indices.get(i)));
        }
        List<List<double[]>> res = new ArrayList();
        res.add(entradasTreino);
        res.add(entradasTeste);
        res.add(saidasTreino);
        res.add(saidasTeste);
        return res;
    }

    public static List retornaCopiaPesosDaRede(List<ICamada<Double>> listaCamadas) {
        List<double[][]> resultList = new ArrayList<>();
        // para cada camada da rede:
        for (ICamada<Double> camada : listaCamadas) {
            double[][] copia = new double[camada.getPesos().length][camada.getPesos()[0].length];
            for (int i = 0; i < copia.length; i++) {
                for (int j = 0; j < copia[0].length; j++) {
                    copia[i][j] = camada.getPesos()[i][j];
                }
            }
            resultList.add(copia);
        }
        return resultList;
    }

    public static void imprimeBase(List<double[]> baseDados) {
        for (double[] baseDado : baseDados) {
            for (int i = 0; i < baseDado.length; i++) {
                System.out.print(" " + baseDado[i]);
            }
            System.out.println("");
        }
    }

    public static void printList(List entrada) {
        for (int i = 0; i < entrada.size(); i++) {
            System.out.println(entrada.get(i));
        }
    }

    public static void escreveEmArquivo(File file, FileWriter fr, List<double[]> saidasReais, List<double[]> saidasObtidas, String info) {
        try {
            fr.write(info + "\n");
            for (int i = 0; i < saidasReais.size(); i++) {
                for (int j = 0; j < saidasReais.get(i).length; j++) {
                    fr.write(saidasReais.get(i)[j] + ",");
                }
                fr.write("\t");
                for (int j = 0; j < saidasReais.get(i).length; j++) {
                    fr.write(saidasObtidas.get(i)[j] + ",");
                }
                fr.write("\n");
//                fr.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void escreveDadosCSVSaida(File file, List<double[]> saidasReais, List<double[]> saidasCalculadas) {
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write("SaidaReal,SaidaCalculada\n");
            for (int i = 0; i < saidasReais.size(); i++) {
                for (int j = 0; j < saidasReais.get(i).length; j++) {
                    fw.write(saidasReais.get(i)[j] + "," + saidasCalculadas.get(i)[j] + "\n");
                }
            }

            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<double[]> pegaApenasColunaEspecificaDasSaidas(int nColuna, List<double[]> listaSaidas) {
        List<double[]> res = new ArrayList<>(listaSaidas.size());
        for (double[] amostra : listaSaidas) {
            res.add(new double[]{amostra[nColuna]});
        }
        return res;
    }

    public static List<double[]> normalizaOuNao(Normalizador norm, List<double[]> lista, boolean normaliza) {
        if (normaliza) {
            return norm.normalizaBaseDados(lista, false);
        } else {
            return lista;
        }
    }

    public static double compara(IRedeNeural rede, double[] saidaCalculada, double[] saidaReal) {
        double erro = 0;
        for (int i = 0; i < saidaCalculada.length; i++) {
            erro += Math.abs(saidaCalculada[i] - saidaReal[i]);
        }
        return erro;
    }

    public static String getVet(double[] vet) {
        String res = "[";
        for (int i = 0; i < vet.length; i++) {
            if (i > 0) {
                res += " - ";
            }
            res += vet[i];
        }
        res += "]";
        return res;
    }

    public static double[] converteListaEmVetor(List<Double> lista) {
        double res[] = new double[lista.size()];
        int i = 0;
        for (double d : lista) {
            res[i++] = d;
        }
        return res;
    }

    public static double getTaxaDeErroEmClassificacao(List<double[]> saidasObtidas, List<double[]> saidasEsperadas, boolean imprimeInfo) {
        int qtdErros = 0;// quantos eu errei
        for (int i = 0; i < saidasObtidas.size(); i++) {
            double[] saidaObtidaAtual = saidasObtidas.get(i);
            double[] saidaRealAtual = saidasEsperadas.get(i);
            for (int j = 0; j < saidaObtidaAtual.length; j++) {
                if (saidaObtidaAtual[j] != saidaRealAtual[j]) {
                    // se alguma das linhas estiver diferente,eu errei a classificacao
                    // essa foi a metrica que escolhi no momento
                    qtdErros++;
                    break;
                }
            }

        }
        if (imprimeInfo) {
            System.out.println("Erro: " + qtdErros + " em " + saidasEsperadas.size());
        }
        return qtdErros / (double) saidasObtidas.size();
    }

    public static double getErroAcumulado(List<double[]> saidasObtidas, List<double[]> saidasReais, LossMetrics metrica) {
        double res = 0;
        switch (metrica) {
            case SimpleDif:
                for (int i = 0; i < saidasObtidas.size(); i++) {
                    double atual = 0;// entrada atual
                    double[] saidaObtidaAtual = saidasObtidas.get(i);
                    double[] saidaRealAtual = saidasReais.get(i);
                    for (int j = 0; j < saidaObtidaAtual.length; j++) {
                        atual += FuncoesPerda.diferencaSimples(saidaObtidaAtual[j], saidaRealAtual[j]);
                    }
                    res += atual;
                }
                break;
            case ModuleDif:
                for (int i = 0; i < saidasObtidas.size(); i++) {
                    double atual = 0;// entrada atual
                    double[] saidaObtidaAtual = saidasObtidas.get(i);
                    double[] saidaRealAtual = saidasReais.get(i);
                    for (int j = 0; j < saidaObtidaAtual.length; j++) {
                        atual += FuncoesPerda.diferencaEmModulo(saidaObtidaAtual[j], saidaRealAtual[j]);
                    }
                    res += atual;
                }
                break;
            case SquaredDif:
                for (int i = 0; i < saidasObtidas.size(); i++) {
                    double atual = 0;// entrada atual
                    double[] saidaObtidaAtual = saidasObtidas.get(i);
                    double[] saidaRealAtual = saidasReais.get(i);
                    for (int j = 0; j < saidaObtidaAtual.length; j++) {
                        atual += FuncoesPerda.diferencaQuadrado(saidaObtidaAtual[j], saidaRealAtual[j]);
                    }
                    res += atual;
                }
                break;
        }
        return res;
    }

    public static void printLine(String string, int i) {
        for (int j = 0; j < i; j++) {
            System.out.print(string);
        }
        System.out.println("");
    }
}
