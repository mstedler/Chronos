package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CronogramaViewHolder extends RecyclerView.ViewHolder {

    public interface CronogramaViewHolderListener {
        void onCronogramaClicked(Cronograma cronograma);
    }

    @BindView(R.id.tv_cronograma_title)
    TextView tvCronogramaTitle;

    @BindView(R.id.tv_cronograma_description)
    TextView tvCronogramaDescription;

    @BindView(R.id.tv_data_inicio)
    TextView tvDataInicio;

    @BindView(R.id.tv_data_fim)
    TextView tvDataFim;

    private CronogramaViewHolderListener cronogramaViewHolderListener;

    public CronogramaViewHolder(@NonNull View itemView, CronogramaViewHolderListener cronogramaViewHolderListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.cronogramaViewHolderListener = cronogramaViewHolderListener;
    }

    public void bind(Cronograma cronograma) {
        tvCronogramaTitle.setText(cronograma.getTitulo());
        if(cronograma.getDescricao() != null && !cronograma.getDescricao().isEmpty()) {
            tvCronogramaDescription.setVisibility(View.VISIBLE);
            tvCronogramaDescription.setText(cronograma.getDescricao());
        }
        tvDataInicio.setText(DateUtils.formatDate(cronograma.getInicio()));
        tvDataFim.setText(DateUtils.formatDate(cronograma.getFim()));
        itemView.setOnClickListener(v -> cronogramaViewHolderListener.onCronogramaClicked(cronograma));
    }
}