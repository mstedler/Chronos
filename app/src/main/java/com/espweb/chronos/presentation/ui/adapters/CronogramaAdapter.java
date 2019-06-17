package com.espweb.chronos.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.Cronograma;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CronogramaAdapter extends RecyclerView.Adapter<CronogramaAdapter.CronogramaViewHolder> {
    public interface CronogramaListListener {
        void onCronogramaClicked(final Cronograma cronograma);
    }

    private CronogramaListListener cronogramaListListener;
    private List<Cronograma> cronogramas;
    private final LayoutInflater inflater;

    public CronogramaAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cronogramas = Collections.emptyList();
    }

    public void setCronogramasList(List<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CronogramaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.row_cronograma, parent, false);
        return new CronogramaViewHolder(view, cronogramaListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CronogramaViewHolder holder, int position) {
        final Cronograma cronograma = this.cronogramas.get(position);
        holder.bind(cronograma);
    }

    @Override
    public int getItemCount() {
        return (cronogramas != null) ? cronogramas.size() : 0;
    }

    class CronogramaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  CronogramaListListener cronogramaListListener;

        @BindView(R.id.tv_cronograma_title)
        TextView tvCronogramaTitle;

        @BindView(R.id.tv_cronograma_description)
        TextView tvCronogramaDescription;

        CronogramaViewHolder(@NonNull View itemView, CronogramaListListener cronogramaListListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.cronogramaListListener = cronogramaListListener;
        }

        void bind(Cronograma cronograma) {
            tvCronogramaTitle.setText(cronograma.getTitulo());
            tvCronogramaDescription.setText(cronograma.getDescricao());
        }

        @Override
        public void onClick(View v) {
            cronogramaListListener.onCronogramaClicked(cronogramas.get(getAdapterPosition()));
        }
    }


    public void setCronogramaListListener(CronogramaListListener cronogramaListListener) {
        if(cronogramaListListener != null) {
            this.cronogramaListListener = cronogramaListListener;
        }
    }
}
