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
public abstract class AbstractNeuronio implements INeuronio, Serializable {

    // ---------------------------------------------------------------------------------------------------------//
    //variaveis
    double taxaAprendizagem;
    protected FuncaoAtivacao funcaoAtivacao;

    // ---------------------------------------------------------------------------------------------------------//
    // construtores
    public AbstractNeuronio(double taxaAprendizagem,FuncaoAtivacao funcaoAtivacao) {
        this.taxaAprendizagem = taxaAprendizagem;
        this.funcaoAtivacao = funcaoAtivacao;
    }

    public AbstractNeuronio(FuncaoAtivacao funcaoAtivacao) {
        this.taxaAprendizagem = 0.1;
        this.funcaoAtivacao = funcaoAtivacao;
    }

    // ---------------------------------------------------------------------------------------------------------//
    // getters e setters
    @Override
    public double getTaxaAprendizagem() {
        return taxaAprendizagem;
    }

    @Override
    public void setTaxaAprendizagem(double taxaAprendizagem) {
        this.taxaAprendizagem = taxaAprendizagem;
    }

    @Override
    public FuncaoAtivacao getTipoNeuronio() {
        return funcaoAtivacao;
    }

    
    
}
