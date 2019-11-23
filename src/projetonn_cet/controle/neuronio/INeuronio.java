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
public interface INeuronio {

    public FuncaoAtivacao getFuncaoAtivacao();

    public double getSaidaFuncao(double entrada);

    public double getDerivadaFuncao(double entrada);

    public double getTaxaAprendizagem();

    public void setTaxaAprendizagem(double taxaAprendizagem);

    public FuncaoAtivacao getTipoNeuronio();
}
