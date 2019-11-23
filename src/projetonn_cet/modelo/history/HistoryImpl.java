/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo.history;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mpcsj
 */
public class HistoryImpl implements IHistory{
    private final String infoExtraDados;
    private List<Double>erroEpocas;
    public HistoryImpl(String info){
        erroEpocas = new ArrayList();
        // trata info
        String[]res = info.split(" ");
        StringBuilder strBuilder = new StringBuilder();
        for(String aux:res){
            strBuilder.append(aux);
            strBuilder.append("_");
        }
        this.infoExtraDados =strBuilder.toString();
    }
    

    @Override
    public void salvaDadosNumArq(String nomeArq) {
       String nome=infoExtraDados+"_";
       if(nomeArq==null){
           nome += new Date().toString()+"";
       }else{
           nome += nomeArq;
       }
       File file = new File(nome);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("num de epocas: "+erroEpocas.size()+"\n");
            fw.write("Erro epocas:\n");
            for (int i = 0; i < erroEpocas.size(); i++) {
                fw.write(erroEpocas.get(i)+"\n");
            }
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(HistoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public List<Double> getErroEpocas() {
        return this.erroEpocas;
    }

    @Override
    public void setErroEpocas(List<Double> dados) {
        this.erroEpocas = dados;
    }
    
}
