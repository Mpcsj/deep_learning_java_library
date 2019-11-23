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
public class NeuronioComATanh extends AbstractNeuronio {

    public NeuronioComATanh() {
        super(FuncaoAtivacao.tanh);
    }

    // ------------------------------------------------------------------------------------------------------------------------------//
    // getters e setters
    @Override
    public FuncaoAtivacao getFuncaoAtivacao() {
        return FuncaoAtivacao.tanh;
    }

    @Override
    public double getSaidaFuncao(double entrada) {
        return (Math.exp(entrada) - Math.exp(-entrada)) / (double) (Math.exp(entrada) + Math.exp(-entrada));
    }

    @Override
    public double getDerivadaFuncao(double entrada) {
        return 1 - Math.pow(Math.exp(entrada) - Math.exp(-entrada), 2) / (double) Math.pow(Math.exp(entrada) + Math.exp(-entrada), 2);
    }

}
