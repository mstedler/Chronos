package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisciplinaViewHolder extends AbstractSwipeableItemViewHolder implements ExpandableItemViewHolder {
    @BindView(R.id.tv_nome_disciplina)
    public TextView tvNome;

    @BindView(R.id.container)
    public FrameLayout container;

    @BindView(R.id.behind_views)
    public LinearLayout behindViews;

    @BindView(R.id.assunto_add)
    public LinearLayout llAssuntoAdd;

    @BindView(R.id.disciplina_delete)
    public LinearLayout llDisciplinaDelete;

    @BindView(R.id.disciplina_edit)
    public LinearLayout llDisciplinaEdit;



    private final ExpandableItemState expandableItemState = new ExpandableItemState();

    public DisciplinaViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @NonNull
    @Override
    public View getSwipeableContainerView() {
        return container;
    }

    @Override
    public void setExpandStateFlags(int flags) {
        expandableItemState.setFlags(flags);
    }

    @Override
    public int getExpandStateFlags() {
        return expandableItemState.getFlags();
    }

    @NonNull
    @Override
    public ExpandableItemState getExpandState() {
        return expandableItemState;
    }
}
