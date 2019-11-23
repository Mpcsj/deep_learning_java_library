/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author mpcsj
 */
public class ExternaDadosRede implements NNView {

    public static int NumArquivo = 1;

    private static void externaDadosNormalizacao(FileWriter fw, double vetor[], String abre, String fecha) throws IOException {
        fw.write(abre);
        for (int i = 0; i < vetor.length - 1; i++) {
            fw.write(vetor[i] + ",");
        }
        fw.write(vetor[vetor.length - 1] + fecha + ";\n");
    }

    private static void escreveMatriz(FileWriter fw, double matriz[][], String abre, String fecha) throws IOException {
        fw.write(abre);
        for (int i = 0; i < matriz.length - 1; i++) {
            fw.write(abre);
            for (int j = 0; j < matriz[0].length - 1; j++) {
                fw.write(matriz[i][j] + ",");
            }
            fw.write(matriz[i][matriz[0].length - 1] + fecha + ",");
        }
        fw.write(abre);
        for (int j = 0; j < matriz[0].length - 1; j++) {
            fw.write(matriz[matriz.length - 1][j] + ",");
        }
        fw.write(matriz[matriz.length - 1][matriz[0].length - 1] + fecha + fecha + ";\n");
    }

    public void externaListaPesosRedeEDadosNormalizacao(double listaNormalizacaoEntrada[][], double listaNormalizacaoSaida[][], List<double[][]> pesosRede) {
        try {
            File file = new File("DadosDaRede_" + (NumArquivo++));
            FileWriter fw = new FileWriter(file);

            fw.write("Lista da entrada:\n");
            fw.write("//Lista de maiores e menores elementos de cada coluna:\n");
            fw.write("//Tam das listas: " + listaNormalizacaoEntrada[0].length + "\n");
            for (int i = 0; i < listaNormalizacaoEntrada.length; i++) {
                fw.write("double [] maiores =");
                externaDadosNormalizacao(fw, listaNormalizacaoEntrada[i], "{", "}");
                fw.write("const maiores =");
                externaDadosNormalizacao(fw, listaNormalizacaoEntrada[i], "[", "]");
            }
            fw.write("Lista da saida:\n");
            fw.write("//Lista de maiores e menores elementos de cada coluna:\n");
            fw.write("//Tam das listas: " + listaNormalizacaoEntrada[0].length + "\n");
            for (int i = 0; i < listaNormalizacaoSaida.length; i++) {
                fw.write("double []menores = ");
                externaDadosNormalizacao(fw, listaNormalizacaoSaida[i], "{", "}");
                fw.write("const menores = ");
                externaDadosNormalizacao(fw, listaNormalizacaoSaida[i], "[", "]");
            }
            //--------------------------------------------------------------------------//
            //--------------------------------------------------------------------------//
//            fw.write("\n\n Lista de Pesos Por camada: \n\n");
//            int i = 0;
//            String nomeVar[] = {"a", "b"};
//            for (double[][] ds : pesosRede) {
//                fw.write("\n");
//                fw.write("double [][]" + nomeVar[i] + " =");
//                escreveMatriz(fw, ds, "{", "}");
//                fw.write("const " + nomeVar[i] + " = ");
//                escreveMatriz(fw, ds, "[", "]");
//                i++;
//            }
            fw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
