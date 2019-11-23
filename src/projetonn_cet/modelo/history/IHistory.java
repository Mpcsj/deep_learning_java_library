/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetonn_cet.modelo.history;

import java.util.List;

/**
 *
 * @author mpcsj
 */
public interface IHistory {

    public List<Double> getErroEpocas();
    public void setErroEpocas(List<Double> dados);
    public void salvaDadosNumArq(String nomeArq);
}
