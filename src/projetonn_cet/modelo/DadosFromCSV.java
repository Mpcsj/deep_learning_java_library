/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projetonn_cet.modelo.enumerados.PrintSintax;
import projetonn_cet.util.UtilLists;

/**
 *
 * @author mpcsj
 */
public class DadosFromCSV implements IDados {

    private List<double[]> entradas;
    private List<double[]> saidas;
    private List<double[]> entradasTeste, saidasTeste, entradasTreino, saidasTreino;
    private double porcBaseTeste = 0.7;

    public DadosFromCSV() {
        entradas = new ArrayList<>();
        saidas = new ArrayList<>();
    }

    public DadosFromCSV(String arquivo, int[] idxColunasDTeste) {
        this();
        leDados(arquivo, idxColunasDTeste);
    }

    
    public DadosFromCSV(double porcBaseTeste, String arquivo, int[] idxColunasDTeste) {
        this();
        assert (0 <= porcBaseTeste && porcBaseTeste <= 1);
        leDados(arquivo, idxColunasDTeste);
        this.porcBaseTeste = porcBaseTeste;
        List<List<double[]>> bases = UtilLists.trainTestSplit(porcBaseTeste, 2319, entradas, saidas, false);
        entradasTreino = bases.get(0);
        entradasTeste = bases.get(1);
        saidasTreino = bases.get(2);
        saidasTeste = bases.get(3);
    }

    @Override
    public List<double[]> getBaseEntradaTreino() {
        return entradasTreino;
    }

    @Override
    public List<double[]> getBaseSaidaTreino() {
        return saidasTreino;
    }

    @Override
    public List<double[]> getBaseEntradaTeste() {
        return entradasTeste;
    }

    @Override
    public List<double[]> getBaseSaidaTeste() {
        return saidasTeste;
    }

    private boolean leDados(String arquivo, int[] idxColunasDTeste) {
        File file;
        file = new File(arquivo);

        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while (br.ready()) {
                List<Double> lista = new ArrayList<>();
                String[] linha = br.readLine().split(",");
                for (int i = 0; i < linha.length; i++) {// lendo linha
                    lista.add(Double.parseDouble(linha[i]));// todos os elementos
                }
                double[] entradaAtual = new double[lista.size() - idxColunasDTeste.length];// apenas entradas
                double[] saidaAtual = new double[idxColunasDTeste.length];
                for (int i = 0; i < idxColunasDTeste.length; i++) {// pego as colunas de saida
                    saidaAtual[i] = lista.get(idxColunasDTeste[i]);
                    lista.set(idxColunasDTeste[i], null);
                }
                int idxAtual = 0;
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i) != null) {
                        entradaAtual[idxAtual++] = lista.get(i);
                    }
                }
                entradas.add(entradaAtual);
                saidas.add(saidaAtual);
            }
            br.close();
            fr.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DadosFromCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DadosFromCSV.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public List<double[]> getDadosEntrada() {
        return entradas;
    }

    @Override
    public List<double[]> getDadosSaida() {
        return saidas;
    }

    @Override
    public double getPorcBaseTreino() {
        return porcBaseTeste;
    }

    @Override
    public void setPorcBaseTreino(double novaPorc) {
        assert (0 <= novaPorc && novaPorc <= 1);
        this.porcBaseTeste = novaPorc;
    }

    @Override
    public void imprimeDados(PrintSintax printSintax) {
        String open, close;
        switch (printSintax) {
            case cLike:
                open = "{";
                close = "}";
                break;
            case pythonArray:
                open = "[";
                close = "]";
                break;
            default:
                open = "{";
                close = "}";
        }
        System.out.println("Entradas: ");
        System.out.print(open);
        for (int i = 0; i < entradas.size(); i++) {
            System.out.print(open);
            for (int j = 0; j < entradas.get(i).length - 1; j++) {
                System.out.print(entradas.get(i)[j]);
                System.out.print(",");
            }
            System.out.print(entradas.get(i)[entradas.get(i).length - 1]);
            System.out.print(close);
            if (i < entradas.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print(close);
        System.out.println("\nSaidas:");
        //
        System.out.print(open);
        for (int i = 0; i < saidas.size(); i++) {
            System.out.print(open);
            for (int j = 0; j < saidas.get(i).length - 1; j++) {
                System.out.print(saidas.get(i)[j]);
                System.out.print(",");
            }
            System.out.print(saidas.get(i)[saidas.get(i).length - 1]);
            System.out.print(close);
            if (i < saidas.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print(close);
    }

}
