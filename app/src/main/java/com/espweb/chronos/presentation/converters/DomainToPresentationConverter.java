package com.espweb.chronos.presentation.converters;

import com.espweb.chronos.presentation.model.User;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;

import java.util.ArrayList;
import java.util.List;

public class DomainToPresentationConverter {

    public static User convert(com.espweb.chronos.domain.model.User dUser) {
        User user = new User();
        user.setId(dUser.getId());
        user.setEmail(dUser.getEmail());
        user.setName(dUser.getName());
        user.setCreatedAt(dUser.getCreatedAt());
        user.setUpdatedAt(dUser.getUpdatedAt());
        user.setUuid(dUser.getUuid());
        //user.setDisciplinas(convertCronogramas(dUser.getDisciplinas()));
        return user;
    }

    public static List<Cronograma> convertCronogramas(List<com.espweb.chronos.domain.model.Cronograma> cronogramas) {
        List<Cronograma> pCronogramas = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Cronograma cronograma: cronogramas) {
            pCronogramas.add(convert(cronograma));
        }
        return pCronogramas;
    }

    public static Cronograma convert(com.espweb.chronos.domain.model.Cronograma dCronograma) {
        Cronograma cronograma = new Cronograma();
        cronograma.setId(dCronograma.getId());
        cronograma.setTitulo(dCronograma.getTitulo());
        cronograma.setDescricao(dCronograma.getDescricao());
        cronograma.setUserId(dCronograma.getIdUser());
        cronograma.setInicio(dCronograma.getInicio());
        cronograma.setFim(dCronograma.getFim());
        cronograma.setUuid(dCronograma.getUuid());
        cronograma.setDisciplinas(convertDisciplinas(dCronograma.getDisciplinas()));
        return cronograma;
    }

    public static List<Disciplina> convertDisciplinas(List<com.espweb.chronos.domain.model.Disciplina> dDisciplinas) {
        List<Disciplina> disciplinas = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Disciplina disciplina: dDisciplinas) {
            disciplinas.add(convert(disciplina));
        }
        return disciplinas;
    }

    public static Disciplina convert(com.espweb.chronos.domain.model.Disciplina disciplina) {
        Disciplina pDisciplina = new Disciplina();
        pDisciplina.setId(disciplina.getId());
        pDisciplina.setNome(disciplina.getNome());
        pDisciplina.setDescricao(disciplina.getDescricao());
        pDisciplina.setUuid(disciplina.getUuid());
        pDisciplina.setIdCronograma(disciplina.getIdCronograma());
        pDisciplina.setAssuntos(convertAssuntos(disciplina.getAssuntos()));
        return pDisciplina;
    }

    private static List<Assunto> convertAssuntos(List<com.espweb.chronos.domain.model.Assunto> dAssuntos) {
        List<Assunto> assuntos = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Assunto assunto: dAssuntos) {
            assuntos.add(convert(assunto));
        }
        return assuntos;
    }

    public static Assunto convert(com.espweb.chronos.domain.model.Assunto dAssunto) {
        Assunto assunto = new Assunto();
        assunto.setId(dAssunto.getId());
        assunto.setDescricao(dAssunto.getDescricao());
        assunto.setUuid(dAssunto.getUuid());
        assunto.setIdDisciplina(dAssunto.getIdDisciplina());
        assunto.setArtefatos(convertArtefatos(dAssunto.getArtefatos()));
        return assunto;
    }

    public static List<Artefato> convertArtefatos(List<com.espweb.chronos.domain.model.Artefato> dArtefatos) {
        List<Artefato> artefatos = new ArrayList<>();
        for (com.espweb.chronos.domain.model.Artefato artefato : dArtefatos) {
            artefatos.add(convert(artefato));
        }
        return artefatos;
    }

    public static Artefato convert(com.espweb.chronos.domain.model.Artefato dArtefato) {
        switch (dArtefato.getTipo()) {
            case EXERCICIO: return convert((com.espweb.chronos.domain.model.Exercicio) dArtefato);
            case MATERIAL: return convert((com.espweb.chronos.domain.model.Material) dArtefato);
            case REVISAO: return convert((com.espweb.chronos.domain.model.Revisao) dArtefato);
            default: return null;
        }
    }

    public static Exercicio convert(com.espweb.chronos.domain.model.Exercicio dExercicio) {
        Exercicio exercicio = new Exercicio();
        exercicio.setId(dExercicio.getId());
        exercicio.setAcertos(dExercicio.getAcertos());
        exercicio.setQuantidade(dExercicio.getQuantidade());
        exercicio.setDescricao(dExercicio.getDescricao());
        exercicio.setData(dExercicio.getData());
        exercicio.setUuid(dExercicio.getUuid());
        exercicio.setIdAssunto(dExercicio.getIdAssunto());
        return exercicio;
    }

    public static Material convert(com.espweb.chronos.domain.model.Material dMaterial) {
        Material material = new Material();
        material.setId(dMaterial.getId());
        material.setMinutos(dMaterial.getMinutos());
        material.setData(dMaterial.getData());
        material.setDescricao(dMaterial.getDescricao());
        material.setIdAssunto(dMaterial.getIdAssunto());
        material.setEscopo(Material.Escopo.fromInt(dMaterial.getEscopo().getIntValue()));
        material.setUuid(dMaterial.getUuid());
        return material;
    }

    public static Revisao convert(com.espweb.chronos.domain.model.Revisao dRevisao) {
        Revisao revisao = new Revisao();
        revisao.setId(dRevisao.getId());
        revisao.setEscopo(Revisao.Escopo.fromInt(dRevisao.getEscopo().getIntValue()));
        revisao.setData(dRevisao.getData());
        revisao.setDescricao(dRevisao.getDescricao());
        revisao.setIdAssunto(dRevisao.getIdAssunto());
        revisao.setUuid(dRevisao.getUuid());
        return revisao;
    }

}
