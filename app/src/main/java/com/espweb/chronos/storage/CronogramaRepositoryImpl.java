package com.espweb.chronos.storage;

import android.content.Context;
import android.util.Log;

import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.storage.converters.StorageModelConverter;
import com.espweb.chronos.storage.database.ObjectBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.objectbox.Box;

public class CronogramaRepositoryImpl implements CronogramaRepository {

    private Context context;

    static {
        Box<com.espweb.chronos.storage.model.Cronograma> cronogramaBox = ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class);
        //Gerar dummy data
        List<com.espweb.chronos.storage.model.Cronograma> cronogramas = cronogramaBox.getAll();

        if(cronogramas.size() == 0) {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            calendar.add(Calendar.DATE, 30);
            Date nextMonth = calendar.getTime();

            try {
                com.espweb.chronos.storage.model.Cronograma cronograma = new com.espweb.chronos.storage.model.Cronograma();
                String uuid1 = UUID.randomUUID().toString();
                cronograma.setUuid(uuid1);
                cronograma.setTitulo("Cronograma I");
                cronograma.setDescricao("This is a Dummy Chronos-gram");
                cronograma.setInicio(today);
                cronograma.setFim(nextMonth);
                cronograma.setSynced(false);

                com.espweb.chronos.storage.model.Cronograma cronograma2 = new com.espweb.chronos.storage.model.Cronograma();
                String uuid2 = UUID.randomUUID().toString();
                cronograma2.setUuid(uuid2);
                cronograma2.setTitulo("Cronograma II");
                cronograma2.setDescricao("This is a Dummy II Chronos-gram");
                cronograma2.setInicio(today);
                cronograma2.setFim(nextMonth);
                cronograma2.setSynced(false);

                cronogramaBox.put(cronograma, cronograma2);

            } catch (Exception e) {
                Log.e("StaticCRONREP", e.getMessage());
            }
        }

    }

    @Override
    public long insert(Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = StorageModelConverter.ConvertToStorageModel(cronograma);
        sCronograma.setSynced(false);
        return getBox().put(sCronograma);
    }

    @Override
    public List<Cronograma> getUnsynced() {
        return null;
    }

    @Override
    public List<Cronograma> getAll() {
        List<Cronograma> cronogramas = new ArrayList<>();

        //GetAllCronogramasService service = RestClient.createService(GetAllCronogramasService.class);

        try {
            //Buscar todos os cronogramas da web
            //Response<GetAllCronogramas> response = service.getAllCronogramas(0).execute();
            //Converter todos os cronogramas para Storage Model
            //List<com.espweb.chronos.storage.model.Cronograma> sCronogramas = RESTModelConverter.convertCronogramasToStorageModel(response.body().getCronogramas());

            //Remover todos os cronogramas locais e adiciona-los novamente
            //getBox().removeAll();
            //getBox().put(sCronogramas);

            //Converter todos para o Domain Model
            cronogramas.addAll(StorageModelConverter.ConvertCronogramasToDomainModel(getBox().getAll()));
        } catch (Exception e){
            Log.e("GetAllCronogramasSvc", e.getMessage());
        }
        return cronogramas;
    }

    @Override
    public boolean update(Cronograma cronograma) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = getBox().get(cronograma.getId());
        sCronograma.setTitulo(cronograma.getTitulo());
        sCronograma.setDescricao(cronograma.getDescricao());
        sCronograma.setInicio(cronograma.getInicio());
        sCronograma.setFim(cronograma.getFim());
        sCronograma.setSynced(false);
        return getBox().put(sCronograma) != 0;
    }

    @Override
    public Cronograma get(long id) {
        com.espweb.chronos.storage.model.Cronograma sCronograma = getBox().get(id);
        return StorageModelConverter.ConvertToDomainModel(sCronograma);
    }

    @Override
    public boolean delete(long id) {
        getBox().remove(id);
        return true;
    }

    public CronogramaRepositoryImpl(Context context) {
        this.context = context;
    }

    public Box<com.espweb.chronos.storage.model.Cronograma> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.Cronograma.class);
    }

    @Override
    public long insert(long parentId, Cronograma cronograma) {
        return 0;
    }

    @Override
    public List<Cronograma> getAll(long parentId) {
        return null;
    }
}
