package com.espweb.chronos.workers.base;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;

import java.util.concurrent.TimeUnit;

public class ApiWorkRequest {
    private OneTimeWorkRequest.Builder oneTimeWorkRequestBuilder;
    private Constraints.Builder constraintsBuilder;

    public ApiWorkRequest(Data data, Class<? extends ApiWorker> c) {
        constraintsBuilder = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED);
        oneTimeWorkRequestBuilder = new OneTimeWorkRequest.Builder(c).setInputData(data);
    }

    public ApiWorkRequest setInitialDelay(int delayInSeconds) {
        oneTimeWorkRequestBuilder.setInitialDelay(delayInSeconds, TimeUnit.SECONDS);
        return this;
    }

    public OneTimeWorkRequest build() {
        return oneTimeWorkRequestBuilder.setConstraints(constraintsBuilder.build()).build();
    }
}
