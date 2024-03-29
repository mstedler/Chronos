package com.espweb.chronos.storage.converters;

import com.espweb.chronos.domain.model.User;

public class DomainToStorageConverter {

    public static com.espweb.chronos.storage.model.Cronograma convert(com.espweb.chronos.domain.model.Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = new com.espweb.chronos.storage.model.Cronograma();
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setUuid(cronograma.getUuid());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        sCronograma.getUser().setTargetId(cronograma.getIdUser());
        return sCronograma;
    }

    public static com.espweb.chronos.storage.model.Revisao convert(com.espweb.chronos.domain.model.Revisao revisao) {
        com.espweb.chronos.storage.model.Revisao sRevisao = new com.espweb.chronos.storage.model.Revisao();
        sRevisao.setUuid(revisao.getUuid());
        sRevisao.setData(revisao.getData());
        sRevisao.setEscopo(revisao.getEscopo().getIntValue());
        sRevisao.setId(revisao.getId());
        sRevisao.getAssunto().setTargetId(revisao.getIdAssunto());
        return sRevisao;
    }

    public static com.espweb.chronos.storage.model.User convert(User user) {
        com.espweb.chronos.storage.model.User sUser = new com.espweb.chronos.storage.model.User();
        sUser.setId(user.getId());
        sUser.setName(user.getName());
        sUser.setEmail(user.getEmail());
        sUser.setCreatedAt(user.getCreatedAt());
        sUser.setUpdatedAt(user.getUpdatedAt());
        sUser.setUuid(user.getUuid());
        return sUser;
    }

}
