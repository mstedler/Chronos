package com.espweb.chronos.data;

import android.content.Context;

import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.storage.converters.DomainToStorageConverter;
import com.espweb.chronos.storage.converters.StorageToDomainConverter;
import com.espweb.chronos.storage.database.ObjectBox;

import java.util.List;

import io.objectbox.Box;

public class UserRepositoryImpl implements Repository<User> {
    public UserRepositoryImpl() {
    }

    public static Box<com.espweb.chronos.storage.model.User> getBox() {
        return ObjectBox.get().boxFor(com.espweb.chronos.storage.model.User.class);
    }

    @Override
    public long insert(User model) {
        return getBox().put(DomainToStorageConverter.convert(model));
    }

    @Override
    public void update(User model) {
    }

    @Override
    public void delete(long id) {
        getBox().remove(id);
    }

    @Override
    public User get(long id) {
        com.espweb.chronos.storage.model.User user;
        user = getBox().get(id);
        return StorageToDomainConverter.convert(user);
    }

    @Override
    public List<User> getAll(long parentId) {
        return null;
    }
}
