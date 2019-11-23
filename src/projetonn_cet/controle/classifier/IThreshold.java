/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.classifier;

import java.io.Serializable;

/**
 *
 * @author mpcsj
 */
public interface IThreshold extends Serializable{
    void setThreshold(double threshold);
    double getCurrentThreshold();
    
    double[] classify(double[] input);
}
