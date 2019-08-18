package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Assunto_;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Cronograma_;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;

public class CronogramaBox {



    private static Box<Cronograma> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class);
    }

    public static Cronograma get(long id) throws NotFoundException {
        Cronograma cronograma = getBox().get(id);
        if(cronograma == null) throw new NotFoundException("Cronograma nao encontrado : " + id);
        return cronograma;
    }

    public static long put(Cronograma cronograma) {
        return getBox().put(cronograma);
    }

    public static void remove(long id) {
       getBox().remove(id);
    }

    public static List<Cronograma> getAll(long userId) {
        return getBox().query().equal(Cronograma_.userId, userId).and().isNull(Cronograma_.deletedAt).build().find();
    }

    public static void put(List<Cronograma> cronogramas) {
        getBox().put(cronogramas);
    }

    public static void deleteCascade(List<Cronograma> sCronogramas) {
        for (com.espweb.chronos.storage.model.Cronograma cronograma : sCronogramas) {
            List<Disciplina> disciplinas = cronograma.getDisciplinas();
            for (Disciplina disciplina : disciplinas) {
                List<Assunto> assuntos = disciplina.getAssuntos();
                for (Assunto assunto : assuntos) {
                    ObjectBox.get().boxFor(Material.class).remove(assunto.getMateriais());
                    ObjectBox.get().boxFor(Exercicio.class).remove(assunto.getExercicios());
                    ObjectBox.get().boxFor(Revisao.class).remove(assunto.getRevisoes());
                }
                ObjectBox.get().boxFor(Assunto.class).remove(assuntos);
            }
            ObjectBox.get().boxFor(Disciplina.class).remove(disciplinas);
        }
        getBox().remove(sCronogramas);
    }
}
