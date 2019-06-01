package com.espweb.chronos.network.converters;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;

import java.util.ArrayList;
import java.util.List;

public class RESTModelConverter {

    public static List<Cronograma> convertCronogramasToDomainModel(List<com.espweb.chronos.network.model.Cronograma> cronogramas) {
        List<Cronograma> dCronogramas = new ArrayList<>();
        for (com.espweb.chronos.network.model.Cronograma cronograma: cronogramas) {
            dCronogramas.add(convertToDomainModel(cronograma));
        }
        return dCronogramas;
    }

    private static Cronograma convertToDomainModel(com.espweb.chronos.network.model.Cronograma cronograma) {
        Cronograma dCronograma = new Cronograma();
        dCronograma.setInicio(cronograma.getInicio());
        dCronograma.setFim(cronograma.getFim());
        dCronograma.setDisciplinas(convertDisciplinasToDomainModel(cronograma.getDisciplinas()));
        return dCronograma;
    }

    private static List<Disciplina> convertDisciplinasToDomainModel(List<com.espweb.chronos.network.model.Disciplina> disciplinas) {
        List<Disciplina> dDisciplinas = new ArrayList<>();
        for (com.espweb.chronos.network.model.Disciplina disciplina: disciplinas) {
            dDisciplinas.add(convertToDomainModel(disciplina));
        }
        return dDisciplinas;
    }

    private static Disciplina convertToDomainModel(com.espweb.chronos.network.model.Disciplina disciplina) {
        Disciplina dDisciplina = new Disciplina();
        dDisciplina.setNome(disciplina.getNome());
        dDisciplina.setAssuntos(convertAssuntosToDomainModel(disciplina.getAssuntos()));
        return dDisciplina;
    }

    private static List<Assunto> convertAssuntosToDomainModel(List<com.espweb.chronos.network.model.Assunto> assuntos) {
        List<Assunto> dAssuntos = new ArrayList<>();
        for (com.espweb.chronos.network.model.Assunto assunto: assuntos) {
            dAssuntos.add(convertToDomainModel(assunto));
        }

        assuntos.clear();
        assuntos = null;

        return dAssuntos;
    }

    private static Assunto convertToDomainModel(com.espweb.chronos.network.model.Assunto assunto) {
        Assunto dAssunto = new Assunto();
        dAssunto.setAnotacao(assunto.getAnotacao());
        dAssunto.setDescricao(assunto.getDescricao());
        dAssunto.setExercicios(convertExerciciosToDomainModel(assunto.getExercicios()));
        dAssunto.setMateriais(convertMateriaisToDomainModel(assunto.getMateriais()));
        dAssunto.setRevisoes(convertRevisoesToDomainModel(assunto.getRevisoes()));
        return dAssunto;
    }

    private static List<Revisao> convertRevisoesToDomainModel(List<com.espweb.chronos.network.model.Revisao> revisoes) {
        List<Revisao> dRevisoes = new ArrayList<>();
        for (com.espweb.chronos.network.model.Revisao revisao: revisoes) {
            dRevisoes.add(convertToDomainModel(revisao));
        }
        return dRevisoes;
    }

    private static Revisao convertToDomainModel(com.espweb.chronos.network.model.Revisao revisao) {
        Revisao dRevisao = new Revisao();
        //dRevisao.setEscopo();
        dRevisao.setQuantidade(revisao.getQuantidade());
        return dRevisao;
    }

    private static List<Material> convertMateriaisToDomainModel(List<com.espweb.chronos.network.model.Material> materiais) {
        List<Material> dMateriais = new ArrayList<>();
        for (com.espweb.chronos.network.model.Material material: materiais) {
            dMateriais.add(convertToDomainModel(material));
        }
        materiais.clear();

        return dMateriais;
    }

    private static Material convertToDomainModel(com.espweb.chronos.network.model.Material material) {
        Material dMaterial = new Material();
        dMaterial.setDescricao(material.getDescricao());
        dMaterial.setPorcentagem(material.getPorcentagem());
        return dMaterial;
    }

    private static List<Exercicio> convertExerciciosToDomainModel(List<com.espweb.chronos.network.model.Exercicio> exercicios) {
        List<Exercicio> dExercicios = new ArrayList<>();
        for (com.espweb.chronos.network.model.Exercicio exercicio: exercicios) {
            dExercicios.add(convertToDomainModel(exercicio));
        }
        return dExercicios;
    }

    private static Exercicio convertToDomainModel(com.espweb.chronos.network.model.Exercicio exercicio) {
        Exercicio dExercicio = new Exercicio();
        dExercicio.setAcertos(exercicio.getAcertos());
        dExercicio.setDescricao(exercicio.getDescricao());
        dExercicio.setQuantidade(exercicio.getQuantidade());
        return dExercicio;
    }
}
