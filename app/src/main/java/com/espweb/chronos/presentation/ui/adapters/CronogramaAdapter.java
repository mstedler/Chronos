package com.espweb.chronos.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Cronograma;
import com.espweb.chronos.presentation.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CronogramaAdapter extends RecyclerView.Adapter<CronogramaAdapter.CronogramaViewHolder> {
    public void addCronograma(Cronograma cronograma) {
        cronogramas.add(cronograma);
        notifyItemInserted(cronogramas.size());
    }

    public interface CronogramaListListener {
        void onCronogramaClicked(final Cronograma cronograma);
    }

    private CronogramaListListener cronogramaListListener;
    private List<Cronograma> cronogramas;
    private final LayoutInflater layoutInflater;

    public CronogramaAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cronogramas = new ArrayList<>();
    }

    public void setCronogramas(List<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CronogramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_cronograma, parent, false);
        return new CronogramaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CronogramaViewHolder holder, int position) {
        final Cronograma cronograma = cronogramas.get(position);
        holder.bind(cronograma);
    }

    @Override
    public int getItemCount() {
        return (cronogramas != null) ? cronogramas.size() : 0;
    }

    class CronogramaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cronograma_title)
        TextView tvCronogramaTitle;

        @BindView(R.id.tv_cronograma_description)
        TextView tvCronogramaDescription;

        @BindView(R.id.tv_data_inicio)
        TextView tvDataInicio;

        @BindView(R.id.tv_data_fim)
        TextView tvDataFim;

        CronogramaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Cronograma cronograma) {
            tvCronogramaTitle.setText(cronograma.getTitulo());
            tvCronogramaDescription.setText(cronograma.getDescricao());
            tvDataInicio.setText(DateUtils.formatDate(cronograma.getInicio()));
            tvDataFim.setText(DateUtils.formatDate(cronograma.getFim()));
            itemView.setOnClickListener(v -> cronogramaListListener.onCronogramaClicked(cronograma));
        }
    }

    public void setCronogramaListListener(CronogramaListListener cronogramaListListener) {
        if (cronogramaListListener != null) {
            this.cronogramaListListener = cronogramaListListener;
        }
    }
}
