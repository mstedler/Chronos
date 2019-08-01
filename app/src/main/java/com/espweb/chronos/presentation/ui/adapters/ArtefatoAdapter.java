package com.espweb.chronos.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.ExercicioViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.MaterialViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.RevisaoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.UnknownViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ArtefatoAdapter extends RecyclerView.Adapter<ArtefatoViewHolder> {

    private static final int MATERIAL = 0;
    private static final int EXERCICIO = 1;
    private static final int REVISAO = 2;
    private static final int UNKNOWN = -1;
    private final Context context;

    private int lastActionPosition;

    public void addArtefato(Artefato artefato) {
        artefatos.add(artefato);
        notifyItemInserted(artefatos.size());
    }

    public void updateArtefato(Artefato artefato) {
        artefatos.set(lastActionPosition, artefato);
        notifyItemChanged(lastActionPosition);
    }


    public interface ArtefatoListListener {
        void onArtefatoClicked(Artefato artefato);
    }

    private List<Artefato> artefatos;
    private ArtefatoListListener artefatoListListener;
    private final LayoutInflater layoutInflater;

    public ArtefatoAdapter(Context context) {
        this.context = context;
        artefatos = new ArrayList<>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ArtefatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case MATERIAL:
                view = layoutInflater.inflate(R.layout.row_material, parent, false);
                return new MaterialViewHolder(view, artefatoListListener);
            case EXERCICIO:
                view = layoutInflater.inflate(R.layout.row_exercicio, parent, false);
                return new ExercicioViewHolder(view, artefatoListListener);
            case REVISAO:
                view = layoutInflater.inflate(R.layout.row_revisao, parent, false);
                return  new RevisaoViewHolder(view, artefatoListListener, context);
            default:
                view = layoutInflater.inflate(R.layout.row_artefato, parent, false);
                return new UnknownViewHolder(view, artefatoListListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ArtefatoViewHolder holder, int position) {
        Artefato artefato = artefatos.get(position);
        holder.bind(artefato);
    }

    @Override
    public int getItemViewType(int position) {
        final Artefato artefato = artefatos.get(position);
        if(artefato instanceof Material) {
            return MATERIAL;
        } else if(artefato instanceof Exercicio) {
            return EXERCICIO;
        } else if (artefato instanceof Revisao) {
            return REVISAO;
        } else {
            return UNKNOWN;
        }
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

    public void setArtefatoListListener(ArtefatoListListener artefatoListListener) {
        if(artefatoListListener != null) {
            this.artefatoListListener = artefatoListListener;
        }
    }
}
