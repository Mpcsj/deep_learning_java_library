/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo;

import java.util.List;
import projetonn_cet.modelo.enumerados.PrintSintax;

/**
 *
 * @author mpcsj
 */
public interface IDados {

    public double getPorcBaseTreino();
    public void setPorcBaseTreino(double novaPorc);
    
    public List<double[]> getDadosEntrada();

    public List<double[]> getDadosSaida();

    public List<double[]> getBaseEntradaTreino();

    public List<double[]> getBaseSaidaTreino();

    public List<double[]> getBaseEntradaTeste();

    public List<double[]> getBaseSaidaTeste();
    
    public void imprimeDados(PrintSintax printSintax);
}
