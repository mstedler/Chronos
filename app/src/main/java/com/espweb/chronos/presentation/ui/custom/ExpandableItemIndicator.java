package com.espweb.chronos.presentation.ui.custom;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.espweb.chronos.R;

public class ExpandableItemIndicator extends FrameLayout {

    private AppCompatImageView imageView;

    public ExpandableItemIndicator(Context context) {
        super(context);
        onInit(context, null, 0);
    }

    public ExpandableItemIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit(context, attrs, 0);
    }

    public ExpandableItemIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit(context, attrs, defStyleAttr);
    }
    protected void onInit(Context context, AttributeSet attrs, int defStyleAttr) {
        View v = LayoutInflater.from(context).inflate(R.layout.expandable_item_indicator, this, true);
        imageView = v.findViewById(R.id.image_view);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    public void setExpandedState(boolean isExpanded, boolean animate) {
        @DrawableRes int resId = isExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more;
        imageView.setImageResource(resId);
    }
}