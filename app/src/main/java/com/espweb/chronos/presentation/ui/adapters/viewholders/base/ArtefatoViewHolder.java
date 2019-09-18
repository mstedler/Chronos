package com.espweb.chronos.presentation.ui.adapters.viewholders.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.espweb.chronos.presentation.model.Artefato;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

public abstract class ArtefatoViewHolder extends AbstractSwipeableItemViewHolder implements View.OnClickListener {

    public abstract void bind(Artefato artefato);
    public abstract void hideBehindViews();
    public abstract void showBehindViews();

    public interface ArtefatoListListener {
        void onArtefatoClicked(int row, Artefato artefato);
    }

    protected ArtefatoListListener artefatoListListener;

    protected ArtefatoViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    public void setArtefatoListListener(ArtefatoListListener artefatoListListener) {
        this.artefatoListListener = artefatoListListener;
    }
}
