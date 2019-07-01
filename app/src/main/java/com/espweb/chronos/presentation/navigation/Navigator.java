package com.espweb.chronos.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.espweb.chronos.presentation.ui.activities.AssuntoActivity;
import com.espweb.chronos.presentation.ui.activities.CronogramaActivity;

public class Navigator {

    public Navigator(){

    }

    public void navigateToCronograma(Context context, long cronogramaId) {
        if(context != null) {
            Intent intent = CronogramaActivity.getCallingIntent(context, cronogramaId);
            context.startActivity(intent);
        }
    }

    public void navigateToAssunto(Context context, long assuntoId) {
        if(context != null) {
            Intent intent = AssuntoActivity.getCallingIntent(context, assuntoId);
            context.startActivity(intent);
        }
    }
}
