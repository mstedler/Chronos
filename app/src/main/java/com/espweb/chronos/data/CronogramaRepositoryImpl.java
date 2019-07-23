package com.espweb.chronos.data;

import android.content.Context;
import android.util.Log;

import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.WebService;
import com.espweb.chronos.network.converters.RESTModelConverter;
import com.espweb.chronos.network.model.Cronogramas;
import com.espweb.chronos.network.services.CronogramaService;
import com.espweb.chronos.storage.converters.DomainToStorageConverter;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.Assunto;
import com.espweb.chronos.storage.model.Cronograma_;
import com.espweb.chronos.storage.model.Disciplina;
import com.espweb.chronos.storage.model.Exercicio;
import com.espweb.chronos.storage.model.Material;
import com.espweb.chronos.storage.model.Revisao;
import com.espweb.chronos.storage.utils.ObjectBoxUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.objectbox.Box;
import retrofit2.Response;

public class CronogramaRepositoryImpl implements Repository<Cronograma> {

    private static final String TAG = "CronogramaRepository";
    private Context context;

    public CronogramaRepositoryImpl(Context context) {
        this.context = context;
    }

    public static Box<com.espweb.chronos.storage.model.Cronograma> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class);
    }

    @Override
    public void update(Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = getBox().get(cronograma.getId());
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        getBox().put(sCronograma);
    }

    @Override
    public Cronograma get(long id) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = getBox().get(id);
        return StorageToDomainConverter.convert(sCronograma);
    }

    @Override
    public void delete(Cronograma cronograma) {
        getBox().remove(cronograma.getId());
    }

    @Override
    public long insert(Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = new com.espweb.chronos.storage.model.Cronograma();
        sCronograma.setUuid(UUID.randomUUID().toString());
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setFim(cronograma.getFim());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.getUser().setTargetId(cronograma.getUserId());
        return getBox().put(sCronograma);
    }

    @Override
    public List<Cronograma> getAll(long userId) {
        List<Cronograma> cronogramas = new ArrayList<>();
        try {
            List<com.espweb.chronos.storage.model.Cronograma> sCronogramas = getBox().query().equal(Cronograma_.userId, userId).build().find();
            List<com.espweb.chronos.network.model.Cronograma> nCronogramas = WebService.getCronogramasCompletos();
            if(!nCronogramas.isEmpty()) {
                manualDeleteCascade(sCronogramas);
                sCronogramas = RESTModelConverter.convertCronogramasToStorageModel(nCronogramas);
                for (com.espweb.chronos.storage.model.Cronograma cronograma: sCronogramas) {
                    cronograma.getUser().setTargetId(userId);
                }
                getBox().put(sCronogramas);
            }
            cronogramas.addAll(StorageToDomainConverter.convertCronogramas(sCronogramas));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return cronogramas;
    }

    //Método necessario pois no ObjectBox não há DELETE CASCADE
    public static void manualDeleteCascade(List<com.espweb.chronos.storage.model.Cronograma> sCronogramas) {
        for (com.espweb.chronos.storage.model.Cronograma cronograma: sCronogramas) {
            List<Disciplina> disciplinas = cronograma.getDisciplinas();
            for(Disciplina disciplina: disciplinas) {
                List<Assunto> assuntos = disciplina.getAssuntos();
                for(Assunto assunto: assuntos) {
                    ObjectBox.get().boxFor(Material.class).remove(assunto.getMateriais());
                    ObjectBox.get().boxFor(Exercicio.class).remove(assunto.getExercicios());
                    ObjectBox.get().boxFor(Revisao.class).remove(assunto.getRevisoes());
                }
                ObjectBox.get().boxFor(Assunto.class).remove(assuntos);
            }
            ObjectBox.get().boxFor(Disciplina.class).remove(disciplinas);
        }
        ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class).remove(sCronogramas);
    }


    //
//    static {
//        Box<com.espweb.chronos.storage.model.Cronograma> cronogramaBox = ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class);
//        //Gerar dummy data
//        List<com.espweb.chronos.storage.model.Cronograma> cronogramas = cronogramaBox.getAll();
//
//        if (cronogramas.size() == 0) {
//            Calendar calendar = Calendar.getInstance();
//            Date today = calendar.getTime();
//
//            calendar.add(Calendar.DATE, 30);
//            Date nextMonth = calendar.getTime();
//
//            try {
//                com.espweb.chronos.storage.model.Cronograma cronograma = new com.espweb.chronos.storage.model.Cronograma();
//                String uuid1 = UUID.randomUUID().toString();
//                cronograma.setUuid(uuid1);
//                cronograma.setTitulo("Cronograma I");
//                cronograma.setDescricao("This is a Dummy Chronos-gram");
//                cronograma.setInicio(today);
//                cronograma.setFim(nextMonth);
//
//                Disciplina disciplina = new Disciplina();
//                disciplina.setUuid(UUID.randomUUID().toString());
//                disciplina.setNome("Disciplina I");
//
//                Disciplina disciplina2 = new Disciplina();
//                disciplina2.setUuid(UUID.randomUUID().toString());
//                disciplina2.setNome("Disciplina II");
//
//                Assunto assunto = new Assunto();
//                assunto.setDescricao("Assunto I da Disciplina I");
//                assunto.setUuid(UUID.randomUUID().toString());
//
//                com.espweb.chronos.storage.model.Exercicio exercicio = new Exercicio();
//                exercicio.setDescricao("Exercício I do Assunto I");
//                exercicio.setAcertos(7);
//                exercicio.setQuantidade(25);
//                exercicio.setData(today);
//                exercicio.setUuid(UUID.randomUUID().toString());
//
//                assunto.getExercicios().add(exercicio);
//
//                disciplina.getAssuntos().add(assunto);
//
//                cronograma.getDisciplinas().add(disciplina);
//                cronograma.getDisciplinas().add(disciplina2);
//
//                com.espweb.chronos.storage.model.Cronograma cronograma2 = new com.espweb.chronos.storage.model.Cronograma();
//                String uuid2 = UUID.randomUUID().toString();
//                cronograma2.setUuid(uuid2);
//                cronograma2.setTitulo("Cronograma II");
//                cronograma2.setDescricao("This is a Dummy II Chronos-gram");
//                cronograma2.setInicio(today);
//                cronograma2.setFim(nextMonth);
//
//                cronogramaBox.put(cronograma, cronograma2);
//
//            } catch (Exception e) {
//                Log.e("StaticCRONREP", e.getMessage());
//            }
//        }
//
//    }
}
