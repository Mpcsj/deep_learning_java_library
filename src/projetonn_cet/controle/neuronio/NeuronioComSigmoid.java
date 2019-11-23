/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.neuronio;

import java.io.Serializable;
import projetonn_cet.modelo.FuncaoAtivacao;

/**
 *
 * @author mpcsj
 */
public class NeuronioComSigmoid extends AbstractNeuronio implements Serializable{

    public NeuronioComSigmoid(double taxaAprendizagem) {
        super(taxaAprendizagem,FuncaoAtivacao.sigmoid);
    }

    public NeuronioComSigmoid() {
        super(FuncaoAtivacao.sigmoid);
    }

    @Override
    public FuncaoAtivacao getFuncaoAtivacao() {
        return FuncaoAtivacao.sigmoid;
    }

    @Override
    public double getSaidaFuncao(double entrada) {
        return 1 / (1 + Math.exp(-entrada));
    }

    @Override
    public double getDerivadaFuncao(double entrada) {
        return entrada * (1 - entrada);
    }

    

}
