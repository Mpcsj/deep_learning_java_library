/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo.history;

/**
 *
 * @author mpcsj
 */
public class Erro {
    int valor_eixo_x;
    double saida_real,saida_obtida;

    public Erro(int valor_eixo_x, double saida_real, double saida_obtida) {
        this.valor_eixo_x = valor_eixo_x;
        this.saida_real = saida_real;
        this.saida_obtida = saida_obtida;
    }

    
    public int getValor_eixo_x() {
        return valor_eixo_x;
    }

    public void setValor_eixo_x(int valor_eixo_x) {
        this.valor_eixo_x = valor_eixo_x;
    }

    public double getSaida_real() {
        return saida_real;
    }

    public void setSaida_real(double saida_real) {
        this.saida_real = saida_real;
    }

    public double getSaida_obtida() {
        return saida_obtida;
    }

    public void setSaida_obtida(double saida_obtida) {
        this.saida_obtida = saida_obtida;
    }
    
    
}
