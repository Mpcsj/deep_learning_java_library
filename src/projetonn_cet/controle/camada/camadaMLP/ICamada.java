/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.camada.camadaMLP;

import java.util.List;
import projetonn_cet.controle.neuronio.INeuronio;

/**
 *
 * @author mpcsj
 * @param <T>
 */
public interface ICamada <T>{
    public int getNumNeuronios();
    public List<INeuronio> getListaNeuronios();
    public double[] feedforward(double[] entrada);
    public double[][] getPesos();
    public boolean setPesos(double pesos[][]);
    public String getJSON();
}
