package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyView extends ConstraintLayout {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EmptyView,
                0, 0);

        try {
            CharSequence title = a.getText(R.styleable.EmptyView_ev_title);
            tvTitle.setText(title);

            CharSequence subtitle = a.getText(R.styleable.EmptyView_ev_subtitle);
            tvSubtitle.setText(subtitle);

            Drawable icon = a.getDrawable(R.styleable.EmptyView_ev_icon);
            ivIcon.setImageDrawable(icon);
        } finally {
            a.recycle();
        }

    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_empty, this);
        ButterKnife.bind(this, view);
    }
}
