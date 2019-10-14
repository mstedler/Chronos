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
import com.espweb.chronos.presentation.ui.adapters.viewholders.CronogramaViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;
import com.facebook.stetho.common.StringUtil;

import org.greenrobot.essentials.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CronogramaAdapter extends RecyclerView.Adapter<CronogramaViewHolder> implements CronogramaViewHolder.CronogramaViewHolderListener {

    public void addCronograma(Cronograma cronograma) {
        cronogramas.add(cronograma);
        int position = cronogramas.size();
        notifyItemInserted(position);
    }

    public void clear() {
        cronogramas.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onCronogramaClicked(Cronograma cronograma) {
        cronogramaListListener.onCronogramaClicked(cronograma);
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
        return new CronogramaViewHolder(view, this);
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

    public void setCronogramaListListener(CronogramaListListener cronogramaListListener) {
        this.cronogramaListListener = cronogramaListListener;
    }
}
