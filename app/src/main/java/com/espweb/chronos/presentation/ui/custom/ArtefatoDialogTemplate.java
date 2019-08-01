package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;

public class ArtefatoDialogTemplate extends ConstraintLayout {

    private LinearLayout llArtefatoContent;

    public ArtefatoDialogTemplate(Context context) {
        super(context);
        init(context);
    }

    public ArtefatoDialogTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArtefatoDialogTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_artefato_template, this);
        llArtefatoContent = view.findViewById(R.id.ll_artefato_content);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(llArtefatoContent != null) {
            llArtefatoContent.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }
}
