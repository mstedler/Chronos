package com.espweb.chronos.presentation.converters;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.model.User;

public class PresentationToDomainConverter {
    public static Artefato convert(com.espweb.chronos.presentation.model.Artefato artefato) {
        if(artefato instanceof com.espweb.chronos.presentation.model.Exercicio) {
            return convert((com.espweb.chronos.presentation.model.Exercicio) artefato);
        } else if(artefato instanceof com.espweb.chronos.presentation.model.Material) {
            return convert((com.espweb.chronos.presentation.model.Material) artefato);
        } else if(artefato instanceof com.espweb.chronos.presentation.model.Revisao) {
            return convert((com.espweb.chronos.presentation.model.Revisao) artefato);
        }
        return null;
    }

    public static Exercicio convert(com.espweb.chronos.presentation.model.Exercicio exercicio) {
        Exercicio dExercicio = new Exercicio();
        dExercicio.setId(exercicio.getId());
        dExercicio.setAcertos(exercicio.getAcertos());
        dExercicio.setQuantidade(exercicio.getQuantidade());
        dExercicio.setData(exercicio.getData());
        dExercicio.setDescricao(exercicio.getDescricao());
        dExercicio.setIdAssunto(exercicio.getIdAssunto());
        dExercicio.setUuid(exercicio.getUuid());
        return dExercicio;
    }

    public static Material convert(com.espweb.chronos.presentation.model.Material material) {
        Material dMaterial = new Material();
        dMaterial.setId(material.getId());
        dMaterial.setMinutos(material.getMinutos());
        dMaterial.setData(material.getData());
        dMaterial.setDescricao(material.getDescricao());
        dMaterial.setIdAssunto(material.getIdAssunto());
        dMaterial.setEscopo(Material.Escopo.fromInt(material.getEscopo().getIntValue()));
        dMaterial.setUuid(material.getUuid());
        dMaterial.setComecarAgora(material.isComecarAgora());
        return dMaterial;
    }

    public static Revisao convert(com.espweb.chronos.presentation.model.Revisao revisao) {
        Revisao dRevisao = new Revisao();
        dRevisao.setId(revisao.getId());
        dRevisao.setEscopo(Revisao.Escopo.fromInt(revisao.getEscopo().getIntValue()));
        dRevisao.setData(revisao.getData());
        dRevisao.setDescricao(revisao.getDescricao());
        dRevisao.setIdAssunto(revisao.getIdAssunto());
        dRevisao.setUuid(revisao.getUuid());
        return dRevisao;
    }

    public static Cronograma convert(com.espweb.chronos.presentation.model.Cronograma cronograma) {
        Cronograma dCronograma = new Cronograma();
        dCronograma.setId(cronograma.getId());
        dCronograma.setTitulo(cronograma.getTitulo());
        dCronograma.setDescricao(cronograma.getDescricao());
        dCronograma.setIdUser(cronograma.getUserId());
        dCronograma.setInicio(cronograma.getInicio());
        dCronograma.setFim(cronograma.getFim());
        dCronograma.setUuid(cronograma.getUuid());
        return dCronograma;
    }

    public static Assunto convert(com.espweb.chronos.presentation.model.Assunto assunto) {
        Assunto dAssunto = new Assunto();
        dAssunto.setId(assunto.getId());
        dAssunto.setDescricao(assunto.getDescricao());
        dAssunto.setUuid(assunto.getUuid());
        dAssunto.setIdDisciplina(assunto.getIdDisciplina());
        return dAssunto;
    }

    public static Disciplina convert(com.espweb.chronos.presentation.model.Disciplina disciplina) {
        Disciplina dDisciplina = new Disciplina();
        dDisciplina.setId(disciplina.getId());
        dDisciplina.setNome(disciplina.getNome());
        dDisciplina.setDescricao(disciplina.getDescricao());
        dDisciplina.setUuid(disciplina.getUuid());
        dDisciplina.setIdCronograma(disciplina.getIdCronograma());
        return dDisciplina;
    }

    public static User convert(com.espweb.chronos.presentation.model.User pUser) {
        User dUser = new User();
        dUser.setId(pUser.getId());
        dUser.setName(pUser.getName());
        dUser.setEmail(pUser.getEmail());
        dUser.setCreatedAt(pUser.getCreatedAt());
        dUser.setUpdatedAt(pUser.getUpdatedAt());
        dUser.setUuid(pUser.getUuid());
        return dUser;
    }
}
