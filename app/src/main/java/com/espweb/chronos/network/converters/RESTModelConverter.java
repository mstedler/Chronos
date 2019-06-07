package com.espweb.chronos.network.converters;


import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.relation.ToMany;

public class RESTModelConverter {

    public static List<Cronograma> convertCronogramasToStorageModel(List<com.espweb.chronos.network.model.Cronograma> cronogramas) {
        List<Cronograma> dCronogramas = new ArrayList<>();
        for (com.espweb.chronos.network.model.Cronograma cronograma: cronogramas) {
            dCronogramas.add(convertToStorageModel(cronograma));
        }
        return dCronogramas;
    }

    private static Cronograma convertToStorageModel(com.espweb.chronos.network.model.Cronograma cronograma) {
        Cronograma dCronograma = new Cronograma();
        dCronograma.setUid(cronograma.getUid());
        dCronograma.setTitulo(cronograma.getTitulo());
        dCronograma.setDescricao(cronograma.getDescricao());
        dCronograma.setInicio(cronograma.getInicio());
        dCronograma.setFim(cronograma.getFim());
        dCronograma.setSynced(true);
        addDisciplinas(dCronograma, cronograma.getDisciplinas());
        return dCronograma;
    }

    private static void addDisciplinas(Cronograma dCronograma, List<com.espweb.chronos.network.model.Disciplina> disciplinas) {
        for (com.espweb.chronos.network.model.Disciplina disciplina: disciplinas) {
            Disciplina dDisciplina = new Disciplina();
            dDisciplina.getCronograma().setTarget(dCronograma);
            dDisciplina.setNome(disciplina.getNome());
            addAssuntos(dDisciplina, disciplina.getAssuntos());
            dCronograma.getDisciplinas().add(dDisciplina);
        }
    }

    private static void addAssuntos(Disciplina dDisciplina, List<com.espweb.chronos.network.model.Assunto> assuntos) {
        for (com.espweb.chronos.network.model.Assunto assunto: assuntos) {
            Assunto dAssunto = new Assunto();
            dAssunto.setAnotacao(assunto.getAnotacao());
            dAssunto.setDescricao(assunto.getDescricao());
            addExercicios(dAssunto, assunto.getExercicios());
            addMateriais(dAssunto, assunto.getMateriais());
            addRevisoes(dAssunto, assunto.getRevisoes());
            dDisciplina.getAssuntos().add(dAssunto);
        }
    }

    private static void addRevisoes(Assunto dAssunto, List<com.espweb.chronos.network.model.Revisao> revisoes) {
        for (com.espweb.chronos.network.model.Revisao revisao: revisoes) {
            Revisao dRevisao = new Revisao();
            dRevisao.setEscopo(revisao.getEscopo().getValue());
            dRevisao.setQuantidade(revisao.getQuantidade());
            dAssunto.getRevisoes().add(dRevisao);
        }
    }

    private static void addMateriais(Assunto dAssunto, List<com.espweb.chronos.network.model.Material> materiais) {
       for (com.espweb.chronos.network.model.Material material: materiais) {
           Material dMaterial = new Material();
           dMaterial.setDescricao(material.getDescricao());
           dMaterial.setPorcentagem(material.getPorcentagem());
           dAssunto.getMateriais().add(dMaterial);
        }
    }


    private static void addExercicios(Assunto dAssunto, List<com.espweb.chronos.network.model.Exercicio> exercicios) {
        for (com.espweb.chronos.network.model.Exercicio exercicio: exercicios) {
            Exercicio dExercicio = new Exercicio();
            dExercicio.setAcertos(exercicio.getAcertos());
            dExercicio.setDescricao(exercicio.getDescricao());
            dExercicio.setQuantidade(exercicio.getQuantidade());
            dAssunto.getExercicios().add(dExercicio);
        }
    }
}
