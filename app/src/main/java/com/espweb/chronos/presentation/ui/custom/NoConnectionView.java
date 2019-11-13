package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.espweb.chronos.R;

import butterknife.ButterKnife;

public class NoConnectionView extends LinearLayout {
    public NoConnectionView(Context context) {
        super(context);
        init();
    }

    public NoConnectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoConnectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.no_connection, this);
        ButterKnife.bind(this, view);
    }

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }
}
