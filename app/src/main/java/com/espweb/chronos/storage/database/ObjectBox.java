package com.espweb.chronos.storage.database;

import android.content.Context;

import com.espweb.chronos.storage.model.MyObjectBox;

import io.objectbox.BoxStore;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() {
        return boxStore;
    }

    public static void deleteDB() {
        if(!boxStore.isClosed())
            boxStore.close();

        boxStore.deleteAllFiles();
    }
}
