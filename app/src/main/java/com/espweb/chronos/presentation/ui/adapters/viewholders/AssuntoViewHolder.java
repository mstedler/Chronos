package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.presentation.ui.activities.CronogramaActivity;
import com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssuntoViewHolder extends AbstractExpandableItemViewHolder implements View.OnClickListener {

    public interface AssuntoViewHolderListener {
        void onAssuntoClicked(Assunto assunto);
    }

    @BindView(R.id.tv_descricao_assunto)
    public TextView tvDescricao;
    private Assunto assunto;
    private AssuntoViewHolderListener assuntoViewHolderListener;

    public AssuntoViewHolder(@NonNull View itemView, AssuntoViewHolderListener assuntoViewHolderListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        this.assuntoViewHolderListener = assuntoViewHolderListener;
    }

    public void bind(DisciplinaProvider.ItemAssunto itemAssunto) {
        assunto = itemAssunto.get();
        tvDescricao.setText(assunto.getDescricao());
    }

    @Override
    public void onClick(View v) {
        assuntoViewHolderListener.onAssuntoClicked(assunto);
    }
}
