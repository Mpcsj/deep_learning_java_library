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
public class SimpleThreshold implements IThreshold,Serializable {

    private double threshold;

    public SimpleThreshold(double threshold) {
        this.threshold = threshold;
    }

    public SimpleThreshold() {
        this.threshold = 0.5;
    }

    @Override
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public double getCurrentThreshold() {
        return threshold;
    }

    @Override
    public double[] classify(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = (input[i] < threshold) ? 0 : 1;
        }
        return input;
    }

}
