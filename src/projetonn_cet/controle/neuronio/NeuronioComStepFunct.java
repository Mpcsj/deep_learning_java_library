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
public class NeuronioComStepFunct extends AbstractNeuronio{
    // atributos
    private double threshold;

    
    // construtores
    public NeuronioComStepFunct(double threshold){
        super(FuncaoAtivacao.step);
        this.threshold = threshold;
    }
    public NeuronioComStepFunct(){
        super(FuncaoAtivacao.step);
        this.threshold = 0.5;
    }
    
    // getters e setters
    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
    @Override
    public FuncaoAtivacao getFuncaoAtivacao() {
        return FuncaoAtivacao.step;
    }

    @Override
    public double getSaidaFuncao(double entrada) {
        return (entrada>=threshold)?1:0;
    }

    @Override
    public double getDerivadaFuncao(double entrada) {
        throw new UnsupportedOperationException("Função não derivável !"); //To change body of generated methods, choose Tools | Templates.
    }
    // outras funcoes
}
