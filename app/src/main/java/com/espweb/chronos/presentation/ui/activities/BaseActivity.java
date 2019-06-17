package com.espweb.chronos.presentation.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.espweb.chronos.presentation.navigation.Navigator;

public class BaseActivity extends AppCompatActivity {

    protected Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigator = new Navigator();
    }
}
