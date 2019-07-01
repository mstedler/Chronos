package com.espweb.chronos.storage.converters;

import com.espweb.chronos.storage.model.Artefato;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Revisao;

public class DomainToStorageConverter {

    public static Cronograma convert(com.espweb.chronos.domain.model.Cronograma cronograma) {
        Cronograma sCronograma = new Cronograma();
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setUuid(cronograma.getUuid());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        return sCronograma;
    }

    public static Revisao convert(com.espweb.chronos.domain.model.Revisao revisao) {
        Revisao sRevisao = new Revisao();
        sRevisao.setUuid(revisao.getUuid());
        sRevisao.setData(revisao.getData());
        sRevisao.setEscopo(revisao.getEscopo().getIntValue());
        sRevisao.setId(revisao.getId());
        return sRevisao;
    }
}
