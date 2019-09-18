package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Cronograma;
import com.espweb.chronos.storage.model.Cronograma_;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;

import java.util.List;

import io.objectbox.Box;

public class CronogramaBox {

    private static Box<Cronograma> getBox() {
        return ObjectBox.get().boxFor(Cronograma.class);
    }

    public Cronograma get(long id) throws NotFoundException {
        Cronograma cronograma = getBox().get(id);
        if (cronograma == null) throw new NotFoundException("Cronograma nao encontrado : " + id);
        return cronograma;
    }

    public long put(Cronograma cronograma) {
        return getBox().put(cronograma);
    }

    public void remove(long id) {
        getBox().remove(id);
    }

    public List<Cronograma> getAll(long userId) {
        return getBox().query().equal(Cronograma_.userId, userId).and().isNull(Cronograma_.deletedAt).build().find();
    }

    public void put(List<Cronograma> cronogramas) {
        getBox().put(cronogramas);
    }

    public void deleteCascade(List<Cronograma> sCronogramas) {
        //ObjectBox.get().runInTx(() -> {
            Box<Material> materialBox = ObjectBox.get().boxFor(Material.class);
            Box<Exercicio> exercicioBox = ObjectBox.get().boxFor(Exercicio.class);
            Box<Revisao> revisaoBox = ObjectBox.get().boxFor(Revisao.class);
            Box<Assunto> assuntoBox = ObjectBox.get().boxFor(Assunto.class);
            Box<Disciplina> disciplinaBox = ObjectBox.get().boxFor(Disciplina.class);
            for (com.espweb.chronos.storage.model.Cronograma cronograma : sCronogramas) {
                List<Disciplina> disciplinas = cronograma.getDisciplinas();
                for (Disciplina disciplina : disciplinas) {
                    List<Assunto> assuntos = disciplina.getAssuntos();
                    for (Assunto assunto : assuntos) {
                        materialBox.remove(assunto.getMateriais());
                        exercicioBox.remove(assunto.getExercicios());
                        revisaoBox.remove(assunto.getRevisoes());
                    }
                    assuntoBox.remove(assuntos);
                }
                disciplinaBox.remove(disciplinas);
            }
            getBox().remove(sCronogramas);
        //});
    }
}
