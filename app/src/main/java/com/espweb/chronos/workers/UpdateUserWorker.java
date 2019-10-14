package com.espweb.chronos.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.espweb.chronos.domain.exceptions.NotFoundException;
import com.espweb.chronos.network.RestClient;
import com.espweb.chronos.network.services.UserService;
import com.espweb.chronos.storage.boxes.UserBox;
import com.espweb.chronos.storage.model.User;
import com.espweb.chronos.workers.base.WebRequestWorker;

import java.io.IOException;

public class UpdateUserWorker extends WebRequestWorker {
    public static final String ID_USER = "USER_ID";
    public UpdateUserWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void work() throws IOException, NullPointerException, NotFoundException {
        long userId = getInputData().getLong(ID_USER, 0);

        UserBox userBox = new UserBox();

        User user = userBox.get(userId);

        UserService userService = RestClient.createService(UserService.class);

        userService.updateUser(user.getName(), user.getUuid()).execute();
    }
}
