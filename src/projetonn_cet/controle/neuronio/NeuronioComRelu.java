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
public class NeuronioComRelu extends AbstractNeuronio implements Serializable{

    public NeuronioComRelu(){
        super(FuncaoAtivacao.relu);
    }
    public static double funcaoReluPDouble(double entrada) {
        return Math.max(entrada, 0);
    }

    public static int funcaoReluPInt(int entrada) {
        return Math.max(entrada, 0);
    }

    @Override
    public FuncaoAtivacao getFuncaoAtivacao() {
        return FuncaoAtivacao.relu;
    }

    @Override
    public double getSaidaFuncao(double entrada) {
        return funcaoReluPDouble(entrada);
    }

    @Override
    public double getDerivadaFuncao(double entrada) {
        return (entrada > 0) ? 1 : 0;
    }

}
