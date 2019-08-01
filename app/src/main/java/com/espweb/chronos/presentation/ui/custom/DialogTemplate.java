package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogTemplate extends ConstraintLayout {

    //@BindView(R.id.ll_content)
    protected LinearLayout llContent;

    public DialogTemplate(Context context) {
        super(context);
        init(context);
    }

    public DialogTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DialogTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_template, this);
        llContent = view.findViewById(R.id.ll_content);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(llContent == null) {
            super.addView(child, index, params);
        } else {
            llContent.addView(child, index, params);
        }
    }
}
