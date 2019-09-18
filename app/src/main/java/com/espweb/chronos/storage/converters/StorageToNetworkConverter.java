package com.espweb.chronos.storage.converters;

import com.espweb.chronos.network.model.Exercicio;
import com.espweb.chronos.network.model.Material;
import com.espweb.chronos.network.model.Revisao;

public class StorageToNetworkConverter {
    public static Exercicio convert(com.espweb.chronos.storage.model.Exercicio sExercicio) {
        Exercicio exercicio = new Exercicio();
        exercicio.setAcertos(sExercicio.getAcertos());
        exercicio.setQuantidade(sExercicio.getQuantidade());
        exercicio.setAssuntoUuid(sExercicio.getAssunto().getTarget().getUuid());
        exercicio.setData(sExercicio.getData());
        exercicio.setDescricao(sExercicio.getDescricao());
        return exercicio;
    }

    public static Material convert(com.espweb.chronos.storage.model.Material sMaterial) {
        Material material = new Material();
        material.setDescricao(sMaterial.getDescricao());
        material.setTime(sMaterial.getMinutos());
        material.setData(sMaterial.getData());
        material.setUuid(material.getUuid());
        material.setEscopo(Material.Escopo.fromInt(sMaterial.getEscopo()));
        material.setAssuntoUuid(sMaterial.getAssunto().getTarget().getUuid());
        return material;
    }

    public static Revisao convert(com.espweb.chronos.storage.model.Revisao sRevisao) {
        Revisao revisao = new Revisao();
        revisao.setAssuntoUuid(sRevisao.getAssunto().getTarget().getUuid());
        revisao.setEscopo(Revisao.Escopo.fromInt(sRevisao.getEscopo()));
        revisao.setData(sRevisao.getData());
        revisao.setDescricao(sRevisao.getDescricao());
        return revisao;
    }
}
