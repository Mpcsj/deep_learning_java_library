/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.redeNeural;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetonn_cet.controle.aprendizagem.BackPropagation;
import projetonn_cet.controle.aprendizagem.ILearning;
import projetonn_cet.controle.classifier.IThreshold;
import projetonn_cet.controle.neuronio.INeuronio;
import projetonn_cet.modelo.enumerados.LossMetrics;
import projetonn_cet.modelo.history.HistoryImpl;
import projetonn_cet.modelo.history.IHistory;
import projetonn_cet.util.FileUtils;
import projetonn_cet.util.UtilLists;
import projetonn_cet.util.UtilObjects;

/**
 *
 * @author mpcsj
 */
public class NN_Classifier extends AbstractRedeNeural implements Serializable {

    // todo: testar...
    // uso um regressor por dentro, a diferenca é que as saidas do modelo 
    // sao classificadas
    private NN_RegressionLearning regressionModel;// uso um regressor internamente
    private IThreshold threshold;

    public NN_Classifier(int numAtributosPrimeiraCamada, IThreshold threshold) {
        super(numAtributosPrimeiraCamada);
        regressionModel = new NN_RegressionLearning(numAtributosPrimeiraCamada);
        this.threshold = threshold;
    }

    @Override
    public void addCamada(int numNeuronios, INeuronio funcoesAtivacaoDCadaNeuronio) {
        regressionModel.addCamada(numNeuronios, funcoesAtivacaoDCadaNeuronio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCamada(int numNeuronios, INeuronio[] funcoesAtivacaoDCadaNeuronio) {
        regressionModel.addCamada(numNeuronios, funcoesAtivacaoDCadaNeuronio); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List executaRede(List entradasPExecucao) {
        // executa o feedforward e retorno uma lista de saidas classificadas
        List<double[]> res = new ArrayList<>();
        for (Object obj : regressionModel.executaRede(entradasPExecucao)) {// para cada tupla de entrada
            double[] currentRow = (double[]) obj;// saida em regressao
            res.add(threshold.classify(currentRow));
        }
        return res;
    }

    @Override
    public IHistory treinaRede(List entradas, List saidas, int numEpocas, int verbose, LossMetrics lm) {
        return regressionModel.treinaRede(entradas, saidas, numEpocas, verbose, lm);
    }

    @Override
    public double[] executaRede(double[] entradaPExecucao) {
        double[] res = regressionModel.executaRede(entradaPExecucao);
        res = threshold.classify(res);
//        for (int i = 0; i < res.length; i++) {
//            res[i] = threshold.classify(res[i]);
//        }
        return res;
    }

    @Override
    public List retornaPesosDaRede() {
        return regressionModel.retornaPesosDaRede();
    }

    @Override
    public IHistory[] treinaRede(List entradaTreino, List saidaTreino, List entradasTeste, List saidasTeste, int numEpocas, int verbose, LossMetrics lm) {
        IHistory[] historicosTreinoeTeste = new IHistory[2];
        historicosTreinoeTeste[0] = new HistoryImpl("base de treino");
        historicosTreinoeTeste[1] = new HistoryImpl("base de teste");
        //IHistory historicoAprendizado = new HistoryImpl("base de treino");
        List<double[]> listaSaidasObtidasAoLongoDoProcesso = new ArrayList<>(regressionModel.listaCamadas.size());
        ILearning aprendizado = new BackPropagation(regressionModel.listaCamadas, listaSaidasObtidasAoLongoDoProcesso, lm); // vou usar o metodo de backpropagation para o aprendizado da rede(por enquanto)
        for (int epoca = 1; epoca <= numEpocas; epoca++) {
            // para cada tupla da lista de entradas de treino
            double erroEpoca = regressionModel.executaTreino1Epoca(entradaTreino, saidaTreino, listaSaidasObtidasAoLongoDoProcesso, aprendizado);
            // apos isso, executo a base de teste para averiguar o desempenho
            List saidasObtidas = executaRede(entradasTeste);// preciso comparar agora
            double erroEpocaTesteAtual = UtilLists.getTaxaDeErroEmClassificacao(saidasObtidas, saidasTeste, verbose > 1);
            if (verbose > 0) {
                if (verbose == 1 && epoca % 100 == 0) {
                    System.out.println("Epoca:" + epoca + "--> Erro treino atual: " + erroEpoca + "\tErro teste atual(classificacao): " + erroEpocaTesteAtual);
                } else if (verbose > 1) {
                    System.out.println("Epoca:" + epoca + "--> Erro treino atual: " + erroEpoca + "\tErro teste atual(classificacao): " + erroEpocaTesteAtual);
                }
            }
//            if (erroEpoca < menorErro) {// por enquanto nao usado
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
        String obj = getJSONObject();
        File file = new File(nomeArq);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            fw.write(obj);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(NN_Classifier.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public IRedeNeural loadObject(String nomeArq) {
        return null;
    }

    public static IRedeNeural read(String nomeArq) {
        File f = new File(nomeArq);
        NN_Classifier classifier = null;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String obj = br.readLine();// preciso montar manualmente
            classifier = UtilObjects.remontaClassifier(obj);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NN_Classifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NN_Classifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        return classifier;
    }

    @Override
    public String getJSONObject() {
        Gson gson = new Gson();
        String template = gson.toJson(this);
        int qtd = 0;
        int inicio = -1;
        int fim = -1;
        for (int i = 1; i < template.length(); i++) {
            if (template.charAt(i) == '{') {
                if (qtd == 0) {
                    inicio = i;
                }
                qtd++;
            } else if (template.charAt(i) == '}') {
                qtd--;
                if (qtd == 0) {
                    fim = i;
                    break;
                }
            }
        }
        String a = template.substring(0, inicio);
        String b = template.substring(fim + 1);
        // preciso remover o objeto regressionModel acima pelo de baixo
        String templateRegression = regressionModel.getJSONObject();
        String res = a + templateRegression + b;
        return res;
    }

}
