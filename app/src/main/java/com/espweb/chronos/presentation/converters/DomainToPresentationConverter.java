package com.espweb.chronos.presentation.converters;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.model.User;

import java.util.ArrayList;
import java.util.List;

public class DomainToPresentationConverter {
    public static Artefato convert(com.espweb.chronos.domain.model.Artefato artefato) {
        if (artefato instanceof com.espweb.chronos.domain.model.Exercicio) {
            return convert((com.espweb.chronos.domain.model.Exercicio) artefato);
        } else if(artefato instanceof com.espweb.chronos.domain.model.Material) {
            return convert((com.espweb.chronos.domain.model.Material) artefato);
        } else if(artefato instanceof com.espweb.chronos.domain.model.Revisao) {
            return convert((com.espweb.chronos.domain.model.Revisao) artefato);
        }
        return null;
    }

    public static Exercicio convert(com.espweb.chronos.domain.model.Exercicio exercicio) {
        Exercicio pExercicio = new Exercicio();
        pExercicio.setId(exercicio.getId());
        pExercicio.setAcertos(exercicio.getAcertos());
        pExercicio.setQuantidade(exercicio.getQuantidade());
        pExercicio.setDescricao(exercicio.getDescricao());
        pExercicio.setData(exercicio.getData());
        pExercicio.setUuid(exercicio.getUuid());
        pExercicio.setIdAssunto(exercicio.getIdAssunto());
        return pExercicio;
    }

    public static List<Artefato> convertArtefatos(List<com.espweb.chronos.domain.model.Artefato> artefatos) {
        List<Artefato> pArtefatos = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Artefato artefato : artefatos) {
            pArtefatos.add(convert(artefato));
        }
        return pArtefatos;
    }

    public static Material convert(com.espweb.chronos.domain.model.Material material) {
        Material pMaterial = new Material();
        pMaterial.setId(material.getId());
        pMaterial.setMinutos(material.getMinutos());
        pMaterial.setData(material.getData());
        pMaterial.setDescricao(material.getDescricao());
        pMaterial.setIdAssunto(material.getIdAssunto());
        pMaterial.setUuid(material.getUuid());
        return pMaterial;
    }

    public static Revisao convert(com.espweb.chronos.domain.model.Revisao revisao) {
        Revisao pRevisao = new Revisao();
        pRevisao.setId(revisao.getId());
        pRevisao.setEscopo(Revisao.Escopo.fromInt(revisao.getEscopo().getIntValue()));
        pRevisao.setData(revisao.getData());
        pRevisao.setDescricao(revisao.getDescricao());
        pRevisao.setIdAssunto(revisao.getIdAssunto());
        pRevisao.setUuid(revisao.getUuid());
        return pRevisao;
    }

    public static Cronograma convert(com.espweb.chronos.domain.model.Cronograma cronograma) {
        Cronograma pCronograma = new Cronograma();
        pCronograma.setId(cronograma.getId());
        pCronograma.setTitulo(cronograma.getTitulo());
        pCronograma.setDescricao(cronograma.getDescricao());
        pCronograma.setUserId(cronograma.getIdUser());
        pCronograma.setInicio(cronograma.getInicio());
        pCronograma.setFim(cronograma.getFim());
        pCronograma.setUuid(cronograma.getUuid());
        return pCronograma;
    }

    public static Assunto convert(com.espweb.chronos.domain.model.Assunto assunto) {
        Assunto pAssunto = new Assunto();
        pAssunto.setId(assunto.getId());
        pAssunto.setDescricao(assunto.getDescricao());
        pAssunto.setUuid(assunto.getUuid());
        pAssunto.setIdDisciplina(assunto.getIdDisciplina());
        return pAssunto;
    }

    public static List<Disciplina> convertDisciplinas(List<com.espweb.chronos.domain.model.Disciplina> disciplinas) {
        List<Disciplina> pDisciplinas = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Disciplina disciplina: disciplinas) {
            pDisciplinas.add(convert(disciplina));
        }
        return pDisciplinas;
    }

    public static Disciplina convert(com.espweb.chronos.domain.model.Disciplina disciplina) {
        Disciplina pDisciplina = new Disciplina();
        pDisciplina.setId(disciplina.getId());
        pDisciplina.setNome(disciplina.getNome());
        pDisciplina.setDescricao(disciplina.getDescricao());
        pDisciplina.setUuid(disciplina.getUuid());
        pDisciplina.setIdCronograma(disciplina.getIdCronograma());
        List<Assunto> assuntos = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Assunto assunto: disciplina.getAssuntos()) {
            assuntos.add(convert(assunto));
        }
        pDisciplina.setAssuntos(assuntos);
        return pDisciplina;
    }

    public static User convert(com.espweb.chronos.domain.model.User user) {
        User pUser = new User();
        pUser.setId(user.getId());
        pUser.setEmail(user.getEmail());
        pUser.setName(user.getName());
        pUser.setCreatedAt(user.getCreatedAt());
        pUser.setUpdatedAt(user.getUpdatedAt());
        pUser.setUuid(user.getUuid());
        return pUser;
    }

    public static List<Cronograma> convert(List<com.espweb.chronos.domain.model.Cronograma> cronogramas) {
        List<Cronograma> pCronogramas = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Cronograma cronograma: cronogramas) {
            pCronogramas.add(convert(cronograma));
        }
        return pCronogramas;
    }
}
