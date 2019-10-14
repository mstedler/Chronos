package com.espweb.chronos.storage.boxes;

import com.espweb.chronos.storage.database.ObjectBox;
import com.espweb.chronos.storage.model.User;

import io.objectbox.Box;

public class UserBox {

    public static Box<User> getBox() {
        return ObjectBox.get().boxFor(User.class);
    }

    public long insert(User user) {
        return getBox().put(user);
    }

    public void update(User user) {
        getBox().put(user);
    }

    public void delete(long id) {
        getBox().remove(id);
    }

    public User get(long id) {
        return getBox().get(id);
    }
}
