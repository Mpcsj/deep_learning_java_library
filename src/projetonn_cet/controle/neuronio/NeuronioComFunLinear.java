/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.neuronio;

import projetonn_cet.modelo.FuncaoAtivacao;

/**
 *
 * @author mpcsj
 */
public class NeuronioComFunLinear extends AbstractNeuronio{
    public NeuronioComFunLinear(){
        super(FuncaoAtivacao.linear);
    }
    @Override
    public FuncaoAtivacao getFuncaoAtivacao() {
        return FuncaoAtivacao.linear;
    }

    @Override
    public double getSaidaFuncao(double entrada) {
        return entrada;
    }

    @Override
    public double getDerivadaFuncao(double entrada) {
        return 1;
    }
    
}
