/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.util;

/**
 *
 * @author mpcsj
 */
public class FuncoesPVetor {

    public int maiorEl(int[] vet) {
        int maiorEl = vet[0];
        for (int i = 1; i < vet.length; i++) {
            if (vet[i] > maiorEl) {
                maiorEl = vet[i];
            }
        }
        return maiorEl;
    }

    public int mediaEl(int[] vet) {
        int soma = 0;
        for (int i = 0; i < vet.length; i++) {
            soma += vet[i];
        }
        return soma / vet.length;
    }
}
