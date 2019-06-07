package com.espweb.chronos.storage.converters;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.storage.model.Cronograma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.objectbox.relation.ToMany;

public class StorageModelConverter {
    public static Cronograma ConvertToStorageModel(com.espweb.chronos.domain.model.Cronograma cronograma) {
        Cronograma sCronograma = new Cronograma();
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setUid(cronograma.getUid());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        return sCronograma;
    }

    public static com.espweb.chronos.domain.model.Cronograma ConvertToDomainModel(Cronograma sCronograma) {
        com.espweb.chronos.domain.model.Cronograma cronograma = new com.espweb.chronos.domain.model.Cronograma();
        cronograma.setId(sCronograma.getId());
        cronograma.setTitulo(sCronograma.getTitulo());
        cronograma.setUid(sCronograma.getUid());
        cronograma.setDescricao(sCronograma.getDescricao());
        cronograma.setInicio(sCronograma.getInicio());
        cronograma.setFim(sCronograma.getFim());
        return cronograma;
    }

    public static Disciplina ConvertToDomainModel(com.espweb.chronos.storage.model.Disciplina sDisciplina) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(sDisciplina.getId());
        disciplina.setNome(sDisciplina.getNome());
        return disciplina;
    }

    public static List<Disciplina> ConvertDisciplinasToDomainModel(List<com.espweb.chronos.storage.model.Disciplina> disciplinas) {
        List<Disciplina> dDisciplinas = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Disciplina disciplina: disciplinas) {
            dDisciplinas.add(ConvertToDomainModel(disciplina));
        }
        return dDisciplinas;
    }

    public static Assunto ConvertToDomainModel(com.espweb.chronos.storage.model.Assunto assunto) {
        Assunto dAssunto = new Assunto();
        dAssunto.setId(assunto.getId());
        dAssunto.setDescricao(assunto.getDescricao());
        dAssunto.setAnotacao(assunto.getAnotacao());
        return dAssunto;
    }

    public static List<Assunto> ConvertAssuntosToDomainModel(List<com.espweb.chronos.storage.model.Assunto> assuntos) {
        List<Assunto> dAssuntos = new ArrayList<>();
        for(com.espweb.chronos.storage.model.Assunto assunto: assuntos) {
            dAssuntos.add(ConvertToDomainModel(assunto));
        }
        return dAssuntos;
    }

    public static Exercicio ConvertToDomainModel(com.espweb.chronos.storage.model.Exercicio exercicio) {
        Exercicio dExercicio = new Exercicio();
        dExercicio.setId(exercicio.getId());
        dExercicio.setDescricao(exercicio.getDescricao());
        dExercicio.setQuantidade(exercicio.getQuantidade());
        dExercicio.setAcertos(exercicio.getAcertos());
        return dExercicio;
    }

    public static List<Exercicio> ConvertExerciciosToDomainModel(List<com.espweb.chronos.storage.model.Exercicio> exercicios) {
        List<Exercicio> dExercicios = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Exercicio exercicio: exercicios) {
            dExercicios.add(ConvertToDomainModel(exercicio));
        }
        return dExercicios;
    }

    public static Revisao ConvertToDomainModel(com.espweb.chronos.storage.model.Revisao revisao) {
        Revisao dRevisao = new Revisao();
        dRevisao.setId(revisao.getId());
        dRevisao.setQuantidade(revisao.getQuantidade());
        dRevisao.setEscopo(Revisao.Escopo.fromInt(revisao.getEscopo()));
        return dRevisao;
    }

    public static List<Revisao> ConvertRevisoesToDomainModel(List<com.espweb.chronos.storage.model.Revisao> revisoes) {
        List<Revisao> dRevisoes = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Revisao revisao: revisoes) {
            dRevisoes.add(ConvertToDomainModel(revisao));
        }
        return dRevisoes;
    }

    public static Material ConvertToDomainModel(com.espweb.chronos.storage.model.Material material) {
        Material dMaterial = new Material();
        dMaterial.setId(material.getId());
        dMaterial.setDescricao(material.getDescricao());
        dMaterial.setPorcentagem(material.getPorcentagem());
        return dMaterial;
    }

    public static List<Material> ConvertMateriaisToDomainModel(List<com.espweb.chronos.storage.model.Material> materiais) {
        List<Material> dMateriais = new ArrayList<>();
        for (com.espweb.chronos.storage.model.Material material: materiais) {
            dMateriais.add(ConvertToDomainModel(material));
        }
        return dMateriais;
    }

    public static List<com.espweb.chronos.domain.model.Cronograma> ConvertCronogramasToDomainModel(List<Cronograma> cronogramas) {
        List<com.espweb.chronos.domain.model.Cronograma> dCronogramas = new ArrayList<>();
        for (Cronograma cronograma: cronogramas) {
            dCronogramas.add(ConvertToDomainModel(cronograma));
        }
        return dCronogramas;
    }
}
