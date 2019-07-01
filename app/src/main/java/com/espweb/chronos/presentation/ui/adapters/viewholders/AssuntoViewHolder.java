package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.espweb.chronos.R;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssuntoViewHolder extends AbstractExpandableItemViewHolder {

    @BindView(R.id.tv_descricao_assunto)
    public TextView tvDescricao;

    public AssuntoViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
