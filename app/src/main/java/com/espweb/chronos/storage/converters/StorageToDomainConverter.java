package com.espweb.chronos.storage.converters;

import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class StorageToDomainConverter {

    public static Sessao convert(com.espweb.chronos.storage.model.Sessao sessao) {
        Sessao dSessao = new Sessao();
        dSessao.setId(sessao.getId());
        dSessao.setUser(convert(sessao.getUser().getTarget()));
        dSessao.setToken(sessao.getToken());
        dSessao.setAtivo(sessao.isActive());
        return dSessao;
    }

    public static User convert(com.espweb.chronos.storage.model.User sUser) {
        User user = new User();
        user.setId(sUser.getId());
        user.setName(sUser.getName());
        user.setEmail(sUser.getEmail());
        user.setCreatedAt(sUser.getCreatedAt());
        user.setUpdatedAt(sUser.getUpdatedAt());
        user.setUuid(sUser.getUuid());
        //user.setCronogramas(convertCronogramas(sUser.getCronogramas()));
        return user;
    }

    public static List<Cronograma> convertCronogramas(List<com.espweb.chronos.storage.model.Cronograma> cronogramas) {
        List<Cronograma> dCronogramas = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Cronograma cronograma: cronogramas) {
            dCronogramas.add(convert(cronograma));
        }
        return dCronogramas;
    }

    public static Cronograma convert(com.espweb.chronos.storage.model.Cronograma sCronograma) {
        Cronograma cronograma = new Cronograma();
        cronograma.setId(sCronograma.getId());
        cronograma.setTitulo(sCronograma.getTitulo());
        cronograma.setUuid(sCronograma.getUuid());
        cronograma.setDescricao(sCronograma.getDescricao());
        cronograma.setInicio(sCronograma.getInicio());
        cronograma.setFim(sCronograma.getFim());
        cronograma.setIdUser(sCronograma.getUser().getTargetId());
        cronograma.setDisciplinas(convertDisciplinas(sCronograma.getDisciplinas()));
        return cronograma;
    }

    public static List<Disciplina> convertDisciplinas(List<com.espweb.chronos.storage.model.Disciplina> disciplinas) {
        List<Disciplina> dDisciplinas = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Disciplina disciplina: disciplinas) {
            dDisciplinas.add(convert(disciplina));
        }
        return dDisciplinas;
    }

    public static Disciplina convert(com.espweb.chronos.storage.model.Disciplina sDisciplina) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(sDisciplina.getId());
        disciplina.setNome(sDisciplina.getNome());
        disciplina.setUuid(sDisciplina.getUuid());
        disciplina.setDescricao(sDisciplina.getDescricao());
        disciplina.setIdCronograma(sDisciplina.getCronograma().getTargetId());
        disciplina.setAssuntos(convertAssuntos(sDisciplina.getAssuntos()));
        return disciplina;
    }

    public static List<Assunto> convertAssuntos(List<com.espweb.chronos.storage.model.Assunto> assuntos) {
        List<Assunto> dAssuntos = new ArrayList<>();
        for(com.espweb.chronos.storage.model.Assunto assunto: assuntos) {
            dAssuntos.add(convert(assunto));
        }
        return dAssuntos;
    }

    public static Assunto convert(com.espweb.chronos.storage.model.Assunto assunto) {
        Assunto dAssunto = new Assunto();
        dAssunto.setId(assunto.getId());
        dAssunto.setDescricao(assunto.getDescricao());
        dAssunto.setUuid(assunto.getUuid());
        dAssunto.setIdDisciplina(assunto.getDisciplina().getTargetId());
        List<Artefato> artefatos = new ArrayList<>();
        artefatos.addAll(convertMateriais(assunto.getMateriais()));
        artefatos.addAll(convertExercicios(assunto.getExercicios()));
        artefatos.addAll(convertRevisoes(assunto.getRevisoes()));
        dAssunto.setArtefatos(artefatos);
        return dAssunto;
    }


    public static List<Exercicio> convertExercicios(List<com.espweb.chronos.storage.model.Exercicio> exercicios) {
        List<Exercicio> dExercicios = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Exercicio exercicio: exercicios) {
            dExercicios.add(convert(exercicio));
        }
        return dExercicios;
    }

    public static Exercicio convert(com.espweb.chronos.storage.model.Exercicio exercicio) {
        Exercicio dExercicio = new Exercicio();
        dExercicio.setId(exercicio.getId());
        dExercicio.setDescricao(exercicio.getDescricao());
        dExercicio.setQuantidade(exercicio.getQuantidade());
        dExercicio.setAcertos(exercicio.getAcertos());
        dExercicio.setData(exercicio.getData());
        dExercicio.setUuid(exercicio.getUuid());
        dExercicio.setIdAssunto(exercicio.getAssunto().getTargetId());
        return dExercicio;
    }



    public static List<Revisao> convertRevisoes(List<com.espweb.chronos.storage.model.Revisao> revisoes) {
        List<Revisao> dRevisoes = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Revisao revisao: revisoes) {
            dRevisoes.add(convert(revisao));
        }
        return dRevisoes;
    }

    public static Revisao convert(com.espweb.chronos.storage.model.Revisao revisao) {
        Revisao dRevisao = new Revisao();
        dRevisao.setId(revisao.getId());
        dRevisao.setEscopo(Revisao.Escopo.fromInt(revisao.getEscopo()));
        dRevisao.setDescricao(revisao.getDescricao());
        dRevisao.setData(revisao.getData());
        dRevisao.setIdAssunto(revisao.getAssunto().getTargetId());
        dRevisao.setUuid(revisao.getUuid());
        return dRevisao;
    }

    public static List<Material> convertMateriais(List<com.espweb.chronos.storage.model.Material> materiais) {
        List<Material> dMateriais = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Material material: materiais) {
            dMateriais.add(convert(material));
        }
        return dMateriais;
    }

    public static Material convert(com.espweb.chronos.storage.model.Material material) {
        Material dMaterial = new Material();
        dMaterial.setId(material.getId());
        dMaterial.setDescricao(material.getDescricao());
        dMaterial.setMinutos(material.getMinutos());
        dMaterial.setData(material.getData());
        dMaterial.setUuid(material.getUuid());
        dMaterial.setEscopo(Material.Escopo.fromInt(material.getEscopo()));
        dMaterial.setIdAssunto(material.getAssunto().getTargetId());
        return dMaterial;
    }
}
