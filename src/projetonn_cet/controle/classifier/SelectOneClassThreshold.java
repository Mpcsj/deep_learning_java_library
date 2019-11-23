/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.classifier;

/**
 *
 * @author mpcsj
 */
public class SelectOneClassThreshold implements IThreshold {

    @Override
    public void setThreshold(double threshold) {
        //useless
    }

    @Override
    public double getCurrentThreshold() {
        return 1;
    }

    @Override
    public double[] classify(double[] input) {
        int idxMaior = 0;
        double maior = input[0];
        for (int i = 1; i < input.length; i++) {
            // descubro a maior saida
            if (input[i] > maior) {
                maior = input[i];
                idxMaior = i;
            }
        }
        for (int i = 0; i < input.length; i++) {
            if (i == idxMaior) {
                input[i] = 1;
            } else {
                input[i] = 0;
            }
        }
        return input;
    }

}
