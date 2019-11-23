/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.controle.aprendizagem;

/**
 *
 * @author mpcsj
 */
public class FuncoesPerda {
    public static double diferencaSimples(double a, double b){
        return a-b;
    }
    public static double diferencaEmModulo(double a, double b){
        return Math.abs(a-b);
    }
    public static double diferencaQuadrado(double a,double b){
        return (a-b)*(a-b);
    }
    
}
