package com.espweb.chronos.data;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;

import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.boxes.UserBox;
import com.espweb.chronos.storage.converters.DomainToStorageConverter;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.workers.api.UpdateUserWorker;
import com.espweb.chronos.workers.api.base.ApiWorkEnqueuer;
import com.espweb.chronos.workers.api.base.ApiWorkRequest;

import java.util.List;

public class UserRepositoryImpl implements Repository<User> {

    private UserBox box;
    private Context context;

    public UserRepositoryImpl(Context context) {
        box = new UserBox();
        this.context = context;
    }

    @Override
    public long insert(User user) {
        return box.insert(DomainToStorageConverter.convert(user));
    }

    @Override
    public void update(User user) {
        box.update(DomainToStorageConverter.convert(user));
        Data data = new Data.Builder().putLong(UpdateUserWorker.ID_USER, user.getId()).build();
        OneTimeWorkRequest workRequest = new ApiWorkRequest(data, UpdateUserWorker.class).build();
        ApiWorkEnqueuer.enqueueUnique(context, workRequest);
    }

    @Override
    public void delete(long id) {
        box.delete(id);
    }

    @Override
    public User get(long id) {
        com.espweb.chronos.storage.model.User user = box.get(id);
        return StorageToDomainConverter.convert(user);
    }

    @Override
    public List<User> getAll(long parentId) {
        return null;
    }
}
