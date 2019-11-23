/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.redeNeural;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import projetonn_cet.controle.camada.camadaMLP.ICamada;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetonn_cet.controle.aprendizagem.BackPropagation;
import projetonn_cet.controle.aprendizagem.ILearning;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.history.HistoryImpl;
import projetonn_cet.modelo.history.IHistory;
import projetonn_cet.util.FileUtils;
import projetonn_cet.util.UtilLists;

/**
 *
 * @author mpcsj
 */
public class NN_RegressionLearning extends AbstractRedeNeural implements Serializable {

//    public static LinkedList<Double> listaErrosPorEpoca = new LinkedList<>();// gambiarra...
    public NN_RegressionLearning(int numAtributosCamadaEntrada) {
        super(numAtributosCamadaEntrada);
    }

    @Override
    public List executaRede(List entradasPExecucao) {
        List saidas = new ArrayList(entradasPExecucao.size());
        for (Object object : entradasPExecucao) { // para cada tupla de entrada
            saidas.add(executaRede((double[]) object));
        }
        return saidas;
    }

    @Override
    public double[] executaRede(double[] entradaPExecucao) {
        for (ICamada<Double> camada : listaCamadas) {// para cada camada da rede neural , faco feedfoward
            entradaPExecucao = camada.feedforward(entradaPExecucao);
        }
        return entradaPExecucao;
    }

    @Override
    public IHistory treinaRede(List entradas, List saidas, int numEpocas, int verbose, LossMetrics lossMetrics) { // por questoes de desempenho, é melhor usar um ArrayList ou VectorList
//        List<double[][]> listaPesos = UtilLists.retornaCopiaPesosDaRede(listaCamadas);
//        double menorErro = Double.MAX_VALUE;

        IHistory historicoAprendizado = new HistoryImpl("base de treino");
        List<double[]> listaSaidasObtidasAoLongoDoProcesso = new ArrayList<>(listaCamadas.size());
        ILearning aprendizado = new BackPropagation(listaCamadas, listaSaidasObtidasAoLongoDoProcesso, lossMetrics); // vou usar o metodo de backpropagation para o aprendizado da rede(por enquanto)
        for (int epoca = 1; epoca <= numEpocas; epoca++) {
            // para cada tupla da lista de entradas
            double erroEpoca = executaTreino1Epoca(entradas, saidas, listaSaidasObtidasAoLongoDoProcesso, aprendizado);
//            if (erroEpoca < menorErro) {
//                listaPesos = UtilLists.retornaCopiaPesosDaRede(listaCamadas);
//                menorErro = erroEpoca;
//            }
            if (verbose > 0) {
                System.out.println("Erro epoca(treino): " + erroEpoca);
            }
            historicoAprendizado.getErroEpocas().add(erroEpoca);
        }
        // coloco a melhor configuracao na rede ao fim
//        atualizaPesosRede(listaPesos);
        return historicoAprendizado;
    }

    public double executaTreino1Epoca(List entradas, List saidas, List<double[]> listaSaidasObtidasAoLongoDoProcesso, ILearning aprendizado) {
        double erroEpoca = 0;
        for (int i = 0; i < entradas.size(); i++) { // para cada entrada
            double res[] = (double[]) entradas.get(i);// pego a entrada para ser passado para a primeira camada
            listaSaidasObtidasAoLongoDoProcesso.add(res);
            for (ICamada<Double> camada : listaCamadas) {// para cada camada da rede neural , faco feedfoward
                res = camada.feedforward(res);
                listaSaidasObtidasAoLongoDoProcesso.add(res);
            }
            // e então, faco o backpropagation para a entrada atual
//                System.out.println("Loss: ");
            double somaPerdaSaidasAtual = 0;
            for (double loss : aprendizado.corrigePesos(res, (double[]) saidas.get(i))) {
//                    System.out.print(loss+" -|-");
                somaPerdaSaidasAtual += loss;// erro de uma unica entrada(somo os erros de cada neuronio de saida)
            }
//                System.out.println("");
//                System.out.println("Erro entrada atual: " + somaPerda);

            // limpo a lista de saidas  da amostra atual
            listaSaidasObtidasAoLongoDoProcesso.clear();
            erroEpoca += somaPerdaSaidasAtual;
        }
        return erroEpoca;
    }

    @Override
    public List retornaPesosDaRede() {
        List<double[][]> resultList = new ArrayList<>();
        // para cada camada da rede:
        for (ICamada<Double> camada : listaCamadas) {
            resultList.add(camada.getPesos());
        }
        return resultList;
    }

    @Override
    public IHistory[] treinaRede(List entradaTreino, List saidaTreino,
            List entradasTeste, List saidasTeste, int numEpocas, int verbose, LossMetrics lossMetrics) {
        List saidasObtidas = null;
        IHistory[] historicosTreinoeTeste = new IHistory[2];
        historicosTreinoeTeste[0] = new HistoryImpl("base de treino");
        historicosTreinoeTeste[1] = new HistoryImpl("base de teste");
        //IHistory historicoAprendizado = new HistoryImpl("base de treino");
        List<double[]> listaSaidasObtidasAoLongoDoProcesso = new ArrayList<>(listaCamadas.size());
        ILearning aprendizado = new BackPropagation(listaCamadas, listaSaidasObtidasAoLongoDoProcesso, lossMetrics); // vou usar o metodo de backpropagation para o aprendizado da rede(por enquanto)
        for (int epoca = 1; epoca <= numEpocas; epoca++) {
            // para cada tupla da lista de entradas de treino
            double erroEpoca = executaTreino1Epoca(entradaTreino, saidaTreino, listaSaidasObtidasAoLongoDoProcesso, aprendizado);
            // apos isso, executo a base de teste para averiguar o desempenho
            saidasObtidas = executaRede(entradasTeste);// preciso comparar agora
            double erroEpocaTesteAtual = UtilLists.getErroAcumulado(saidasObtidas, saidasTeste, lossMetrics);
            if (verbose > 0) {
                if (verbose == 1 && epoca % 100 == 0) {
                    System.out.println("Epoca:" + epoca + "--> Erro treino atual: " + erroEpoca + "\tErro teste atual: " + erroEpocaTesteAtual);
                } else if (verbose > 1) {
                    System.out.println("Epoca:" + epoca + "--> Erro treino atual: " + erroEpoca + "\tErro teste atual: " + erroEpocaTesteAtual);

                }
            }
//            if (erroEpoca < menorErro) {
//                listaPesos = UtilLists.retornaCopiaPesosDaRede(listaCamadas);
//                menorErro = erroEpoca;
//            }
            historicosTreinoeTeste[0].getErroEpocas().add(erroEpoca);
            historicosTreinoeTeste[1].getErroEpocas().add(erroEpocaTesteAtual);
        }
        return historicosTreinoeTeste;
    }

    @Override
    public boolean saveObject(String nomeArq) {
//        return FileUtils.saveObject("models/", "model_regression", this);
        String jsonObj = getJSONObject();
        // salvo obj
        return true;
    }

    @Override
    public IRedeNeural loadObject(String nomeArq) {
        Object obj = FileUtils.openObject("models/", "model_regression");
        return (IRedeNeural) obj;
    }

    @Override
    public String getJSONObject() {
        Gson gson = new Gson();
        String template = gson.toJson(this);
        StringBuilder strBuider = new StringBuilder();
        strBuider.append("[");
        for (ICamada camada : listaCamadas) {
            strBuider.append(camada.getJSON());
            strBuider.append(",");
        }
        strBuider.deleteCharAt(strBuider.lastIndexOf(","));
        strBuider.append("]");
        String a = template.substring(0, template.indexOf("["));
        String b = template.substring(template.indexOf("]"));
        String res = a + strBuider.toString() + b;
        return res;
    }

}
