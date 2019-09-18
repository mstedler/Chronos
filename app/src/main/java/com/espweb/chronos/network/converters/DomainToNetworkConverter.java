package com.espweb.chronos.network.converters;

import com.espweb.chronos.network.model.Cronograma;

public class DomainToNetworkConverter {

    public static Cronograma convert(com.espweb.chronos.domain.model.Cronograma cronograma) {
        Cronograma nCronograma = new Cronograma();
        nCronograma.setUuid(cronograma.getUuid());
        nCronograma.setTitulo(cronograma.getTitulo());
        nCronograma.setDescricao(cronograma.getDescricao());
        nCronograma.setInicio(cronograma.getInicio());
        nCronograma.setFim(cronograma.getFim());
        return nCronograma;
    }
}
