/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.util;

import projetonn_cet.controle.redeNeural.NN_Classifier;

/**
 *
 * @author mpcsj
 */
public class UtilObjects {

    static class Dado {

        int inicio, fim;
        String dado;
    }

    public static NN_Classifier remontaClassifier(String obj) {
        Dado dado = obtemAttr("regressionModel", obj);
        int teste = 10;
        return null;
    }

    private static Dado obtemAttr(String attr, String dado) {
        Dado res = new Dado();
        res.inicio = res.fim = -1;
        int qtd = 0;
        for (int i = dado.indexOf(attr) + attr.length(); i < dado.length(); i++) {
            char atual = dado.charAt(i);
            if (dado.charAt(i) == '{') {
                if (qtd == 0) {
                    res.inicio = i;
                }
                qtd++;
            } else if (dado.charAt(i) == '}') {
                qtd--;
                if (qtd == 0) {
                    res.fim = i;
                    break;
                }
            }
        }
        String aux = dado.substring(res.inicio, res.fim+1);
        res.dado = aux;
        return res;
    }
}
