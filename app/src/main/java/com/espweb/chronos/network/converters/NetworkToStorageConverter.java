package com.espweb.chronos.network.converters;

import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.storage.model.Sessao;
import com.espweb.chronos.storage.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NetworkToStorageConverter {

    public static Sessao convert(com.espweb.chronos.network.model.Sessao nSessao) {
        Sessao sessao = new Sessao();
        User sUser = new User();
        sUser.setName(nSessao.getUser().getName());
        sUser.setEmail(nSessao.getUser().getEmail());
        sUser.setUpdatedAt(nSessao.getUser().getUpdatedAt());
        sUser.setCreatedAt(nSessao.getUser().getCreatedAt());
        sUser.setUuid(nSessao.getUser().getUuid());
        sessao.setToken(nSessao.getToken());
        sessao.setActive(true);
        sessao.getUser().setTarget(sUser);
        return sessao;
    }

    public static List<Cronograma> convertCronogramas(List<com.espweb.chronos.network.model.Cronograma> cronogramas) {
        List<Cronograma> sCronograma = new ArrayList<>();
        for (com.espweb.chronos.network.model.Cronograma cronograma : cronogramas) {
            sCronograma.add(convert(cronograma));
        }
        return sCronograma;
    }
    private static Cronograma convert(com.espweb.chronos.network.model.Cronograma cronograma) {
        Cronograma sCronograma = new Cronograma();
        sCronograma.setUuid(cronograma.getUuid());
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        sCronograma.getDisciplinas().addAll(convertDisciplinas(cronograma.getDisciplinas()));
        return sCronograma;
    }

    public static List<Disciplina> convertDisciplinas(List<com.espweb.chronos.network.model.Disciplina> disciplinas) {
        List<Disciplina> sDisciplinas = new ArrayList<>();
        for (com.espweb.chronos.network.model.Disciplina disciplina : disciplinas) {
            sDisciplinas.add(convert(disciplina));
        }
        return sDisciplinas;
    }

    private static Disciplina convert(com.espweb.chronos.network.model.Disciplina disciplina) {
        Disciplina sDisciplina = new Disciplina();
        sDisciplina.setNome(disciplina.getNome());
        sDisciplina.setUuid(disciplina.getUuid());
        sDisciplina.setDescricao(disciplina.getDescricao());
        sDisciplina.getAssuntos().addAll(convertAssuntos(disciplina.getAssuntos()));
        return sDisciplina;
    }

    private static List<Assunto> convertAssuntos(List<com.espweb.chronos.network.model.Assunto> assuntos) {
        List<Assunto> sAssuntos = new ArrayList<>();
        for (com.espweb.chronos.network.model.Assunto assunto : assuntos) {
            sAssuntos.add(convert(assunto));
        }
        return sAssuntos;
    }

    private static Assunto convert(com.espweb.chronos.network.model.Assunto assunto) {
        Assunto sAssunto = new Assunto();
        sAssunto.setDescricao(assunto.getDescricao());
        sAssunto.setUuid(assunto.getUuid());
        sAssunto.getExercicios().addAll(convertExercicios(assunto.getExercicios()));
        sAssunto.getMateriais().addAll(convertMateriais(assunto.getMateriais()));
        sAssunto.getRevisoes().addAll(convertRevisoes(assunto.getRevisoes()));
        return sAssunto;
    }

    private static List<Revisao> convertRevisoes(List<com.espweb.chronos.network.model.Revisao> revisoes) {
        List<Revisao> sRevisoes = new ArrayList<>();
        for (com.espweb.chronos.network.model.Revisao revisao : revisoes) {
            sRevisoes.add(convert(revisao));
        }
        return sRevisoes;
    }

    private static Revisao convert(com.espweb.chronos.network.model.Revisao revisao) {
        Revisao sRevisao = new Revisao();
        sRevisao.setEscopo(revisao.getEscopo().getValue());
        sRevisao.setUuid(revisao.getUuid());
        sRevisao.setData(revisao.getData());
        return sRevisao;
    }

    private static List<Material> convertMateriais(List<com.espweb.chronos.network.model.Material> materiais) {
        List<Material> sMateriais = new ArrayList<>();
        for (com.espweb.chronos.network.model.Material material : materiais) {
            sMateriais.add(convert(material));
        }
        return sMateriais;
    }

    private static Material convert(com.espweb.chronos.network.model.Material material) {
        Material sMaterial = new Material();
        sMaterial.setDescricao(material.getDescricao());
        sMaterial.setMinutos(material.getTime());
        sMaterial.setUuid(material.getUuid());
        sMaterial.setEscopo(material.getEscopo().getValue());
        sMaterial.setData(material.getData());
        return sMaterial;
    }

    private static List<Exercicio> convertExercicios(List<com.espweb.chronos.network.model.Exercicio> exercicios) {
        List<Exercicio> sExercicios = new ArrayList<>();
        for (com.espweb.chronos.network.model.Exercicio exercicio : exercicios) {
            sExercicios.add(convert(exercicio));
        }
        return sExercicios;
    }

    private static Exercicio convert(com.espweb.chronos.network.model.Exercicio exercicio) {
        Exercicio sExercicio = new Exercicio();
        sExercicio.setAcertos(exercicio.getAcertos());
        sExercicio.setDescricao(exercicio.getDescricao());
        sExercicio.setQuantidade(exercicio.getQuantidade());
        sExercicio.setUuid(exercicio.getUuid());
        sExercicio.setData(exercicio.getData());
        return sExercicio;
    }

}
