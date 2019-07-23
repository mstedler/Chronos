package com.espweb.chronos.network.converters;


import com.espweb.chronos.domain.model.Sessao;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.storage.model.User;

import java.util.ArrayList;
import java.util.List;

public class RESTModelConverter {
    public static com.espweb.chronos.domain.model.User convertUserToDomainModel(com.espweb.chronos.network.model.User nUser) {
        com.espweb.chronos.domain.model.User user = new com.espweb.chronos.domain.model.User();
        user.setUuid(nUser.getUuid());
        user.setName(nUser.getName());
        user.setEmail(nUser.getEmail());
        user.setPassword(nUser.getPassword());
        user.setCreatedAt(nUser.getCreatedAt());
        user.setUpdatedAt(nUser.getUpdatedAt());
        return user;
    }

    public static User convertUserToStorageModel(com.espweb.chronos.network.model.User user) {
        User sUser = new User();
        sUser.setUuid(user.getUuid());
        sUser.setName(user.getName());
        sUser.setEmail(user.getEmail());
        sUser.setPassword(user.getPassword());
        sUser.setCreatedAt(user.getCreatedAt());
        sUser.setUpdatedAt(user.getUpdatedAt());
        return sUser;
    }

    public static List<Cronograma> convertCronogramasToStorageModel(List<com.espweb.chronos.network.model.Cronograma> cronogramas) {
        List<Cronograma> sCronograma = new ArrayList<>();
        for (com.espweb.chronos.network.model.Cronograma cronograma : cronogramas) {
            sCronograma.add(convertToStorageModel(cronograma));
        }
        return sCronograma;
    }

    private static Cronograma convertToStorageModel(com.espweb.chronos.network.model.Cronograma cronograma) {
        Cronograma sCronograma = new Cronograma();
        sCronograma.setUuid(cronograma.getUuid());
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        addDisciplinas(sCronograma, cronograma.getDisciplinas());
        return sCronograma;
    }

    private static void addDisciplinas(Cronograma sCronograma, List<com.espweb.chronos.network.model.Disciplina> disciplinas) {
        if (disciplinas == null) return;
        for (com.espweb.chronos.network.model.Disciplina disciplina : disciplinas) {
            Disciplina sDisciplina = new Disciplina();
            sDisciplina.getCronograma().setTarget(sCronograma);
            sDisciplina.setNome(disciplina.getNome());
            sDisciplina.setUuid(disciplina.getUuid());
            sDisciplina.setDescricao(disciplina.getDescricao());
            addAssuntos(sDisciplina, disciplina.getAssuntos());
            sCronograma.getDisciplinas().add(sDisciplina);
        }
    }

    private static void addAssuntos(Disciplina sDisciplina, List<com.espweb.chronos.network.model.Assunto> assuntos) {
        if (assuntos == null) return;
        for (com.espweb.chronos.network.model.Assunto assunto : assuntos) {
            Assunto sAssunto = new Assunto();
            sAssunto.setDescricao(assunto.getDescricao());
            sAssunto.setUuid(assunto.getUuid());
            addExercicios(sAssunto, assunto.getExercicios());
            addMateriais(sAssunto, assunto.getMateriais());
            addRevisoes(sAssunto, assunto.getRevisoes());
            sDisciplina.getAssuntos().add(sAssunto);
        }
    }

    private static void addRevisoes(Assunto sAssunto, List<com.espweb.chronos.network.model.Revisao> revisoes) {
        if (revisoes == null) return;
        for (com.espweb.chronos.network.model.Revisao revisao : revisoes) {
            Revisao sRevisao = new Revisao();
            sRevisao.setEscopo(revisao.getEscopo().getValue());
            sRevisao.setUuid(revisao.getUuid());
            sRevisao.setData(revisao.getData());
            sAssunto.getRevisoes().add(sRevisao);
        }
    }

    private static void addMateriais(Assunto sAssunto, List<com.espweb.chronos.network.model.Material> materiais) {
        if (materiais == null) return;
        for (com.espweb.chronos.network.model.Material material : materiais) {
            Material sMaterial = new Material();
            sMaterial.setDescricao(material.getDescricao());
            sMaterial.setMinutos(material.getMinutos());
            sMaterial.setUuid(material.getUuid());
            sMaterial.setData(material.getData());
            sAssunto.getMateriais().add(sMaterial);
        }
    }


    private static void addExercicios(Assunto dAssunto, List<com.espweb.chronos.network.model.Exercicio> exercicios) {
        if(exercicios == null) return;
        for (com.espweb.chronos.network.model.Exercicio exercicio : exercicios) {
            Exercicio dExercicio = new Exercicio();
            dExercicio.setAcertos(exercicio.getAcertos());
            dExercicio.setDescricao(exercicio.getDescricao());
            dExercicio.setQuantidade(exercicio.getQuantidade());
            dExercicio.setUuid(exercicio.getUuid());
            dExercicio.setData(exercicio.getData());
            dAssunto.getExercicios().add(dExercicio);
        }
    }

    public static Sessao convertSessaoToDomainModel(com.espweb.chronos.network.model.Sessao sessao) {
        Sessao dSessao = new Sessao();
        dSessao.setToken(sessao.getToken());
        dSessao.setUser(convertUserToDomainModel(sessao.getUser()));
        return dSessao;
    }
}
