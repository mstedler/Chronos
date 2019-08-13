package com.espweb.chronos.presentation.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.EnumTipo;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.factory.ArtefatoViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

public class ArtefatoAdapter extends RecyclerView.Adapter<ArtefatoViewHolder> implements ArtefatoViewHolder.ArtefatoListListener {

    public interface ArtefatoListListener {
        void onArtefatoClicked(Artefato artefato);
    }

    private ArtefatoListListener artefatoListListener;

    private int lastActionPosition;
    private List<Artefato> artefatos;

    public ArtefatoAdapter() {
        artefatos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArtefatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArtefatoViewHolder artefatoViewHolder = ArtefatoViewHolderFactory.createViewHolder(parent, EnumTipo.fromInt(viewType));
        artefatoViewHolder.setArtefatoListListener(this);
        return artefatoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtefatoViewHolder holder, int position) {
        Artefato artefato = artefatos.get(position);
        holder.bind(artefato);
    }

    @Override
    public int getItemViewType(int position) {
       return artefatos.get(position).getTipo().getIntValue();
    }

    @Override
    public int getItemCount() {
        return artefatos.size();
    }

    public void setArtefatos(List<Artefato> artefatos) {
        if(artefatos != null) {
            this.artefatos = artefatos;
            notifyDataSetChanged();
        }
    }

    public void addArtefato(Artefato artefato) {
        artefatos.add(artefato);
        notifyItemInserted(artefatos.size());
    }

    public void updateArtefato(Artefato artefato) {
        artefatos.set(lastActionPosition, artefato);
        notifyItemChanged(lastActionPosition);
    }

    public void setArtefatoListListener(ArtefatoListListener artefatoListListener) {
        this.artefatoListListener = artefatoListListener;
    }

    @Override
    public void onArtefatoClicked(int row, Artefato artefato) {
        lastActionPosition = row;
        artefatoListListener.onArtefatoClicked(artefato);
    }
}
