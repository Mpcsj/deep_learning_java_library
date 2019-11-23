/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.redeNeural;

import projetonn_cet.controle.camada.camadaMLP.ICamada;
import projetonn_cet.controle.camada.camadaMLP.Camada;
import java.util.LinkedList;
import java.util.List;
import projetonn_cet.controle.neuronio.INeuronio;
import projetonn_cet.controle.neuronio.NeuronioComATanh;
import projetonn_cet.controle.neuronio.NeuronioComFunLinear;
import projetonn_cet.controle.neuronio.NeuronioComRelu;
import projetonn_cet.controle.neuronio.NeuronioComSigmoid;
import projetonn_cet.controle.neuronio.NeuronioComStepFunct;

/**
 *
 * @author mpcsj
 */
public abstract class AbstractRedeNeural implements IRedeNeural {

    protected List<ICamada<Double>> listaCamadas;
    private final int numAtributosDaCamadaEntrada;

    public AbstractRedeNeural(int numAtributosPrimeiraCamada) {
        listaCamadas = new LinkedList<>();
        this.numAtributosDaCamadaEntrada = numAtributosPrimeiraCamada;
    }

    @Override
    public String getJSONObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public void addCamada(int numNeuronios, INeuronio[] funcoesAtivacaoDCadaNeuronio) {
        if (listaCamadas.isEmpty()) {
            // caso seja a primeira
            listaCamadas.add(new Camada(numAtributosDaCamadaEntrada, numNeuronios, funcoesAtivacaoDCadaNeuronio));
        } else {
            listaCamadas.add(new Camada(listaCamadas.get(listaCamadas.size() - 1).getNumNeuronios(),
                    numNeuronios, funcoesAtivacaoDCadaNeuronio));
        }
    }

    @Override
    public void addCamada(int numNeuronios, INeuronio funcoesAtivacaoDCadaNeuronio) {
        INeuronio neuronios[] = new INeuronio[numNeuronios];
        switch (funcoesAtivacaoDCadaNeuronio.getFuncaoAtivacao()) {
            case tanh:
                for (int i = 0; i < numNeuronios; i++) {
                    neuronios[i] = new NeuronioComATanh();
                }
                break;
            case linear:
                for (int i = 0; i < numNeuronios; i++) {
                    neuronios[i] = new NeuronioComFunLinear();
                }
                break;
            case relu:
                for (int i = 0; i < numNeuronios; i++) {
                    neuronios[i] = new NeuronioComRelu();
                }
                break;
            case sigmoid:
                for (int i = 0; i < numNeuronios; i++) {
                    neuronios[i] = new NeuronioComSigmoid(funcoesAtivacaoDCadaNeuronio.getTaxaAprendizagem());
                }
                break;
            case step:
                for (int i = 0; i < numNeuronios; i++) {
                    neuronios[i] = new NeuronioComStepFunct();
                }
                break;
        }
        addCamada(numNeuronios, neuronios);
    }


}
