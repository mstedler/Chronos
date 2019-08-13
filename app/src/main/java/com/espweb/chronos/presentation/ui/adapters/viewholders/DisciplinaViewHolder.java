package com.espweb.chronos.presentation.ui.adapters.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter.NOT_PINNED;
import static com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter.PINNED_LEFT;
import static com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter.PINNED_RIGHT;

public class DisciplinaViewHolder extends AbstractSwipeableItemViewHolder implements ExpandableItemViewHolder {
    public interface DisciplinaViewHolderListener {
        void notifyGroupItemChanged(int position);
        void createAssuntoClicked(Disciplina disciplina, int adapterPosition);
        void deleteDisciplinaClicked(Disciplina disciplina, int position);
        void editDisciplinaClicked(Disciplina disciplina, int position);
    }

    private DisciplinaViewHolderListener disciplinaViewHolderListener;

    @BindView(R.id.tv_nome_disciplina)
    public TextView tvNome;

    @BindView(R.id.tv_descricao_disciplina)
    public TextView tvDescricao;

    @BindView(R.id.fl_container)
    public ConstraintLayout container;

    @BindView(R.id.ll_behindviews)
    public LinearLayout behindViews;

    @BindView(R.id.ll_add_assunto)
    public LinearLayout llAddAssunto;

    @BindView(R.id.ll_delete_disciplina)
    public LinearLayout llDeleteDisciplina;

    @BindView(R.id.ll_edit_disciplina)
    public LinearLayout llEditDisciplina;

    private final ExpandableItemState expandableItemState = new ExpandableItemState();
    private DisciplinaProvider.GroupDisciplina groupDisciplina;
    private Disciplina disciplina;


    public DisciplinaViewHolder(@NonNull View itemView, DisciplinaViewHolderListener disciplinaViewHolderListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.disciplinaViewHolderListener = disciplinaViewHolderListener;
    }

    public void hideBehindViews() {
        behindViews.setVisibility(View.GONE);
    }

    public void showBehindViews() {
        behindViews.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.fl_container)
    void onContainerClick() {
        if(groupDisciplina.isPinned()) {
            groupDisciplina.setPinDirection(NOT_PINNED);
            disciplinaViewHolderListener.notifyGroupItemChanged(getAdapterPosition());
        }
    }

    @OnClick(R.id.ll_add_assunto)
    void onAddAssuntoClick() {
        disciplinaViewHolderListener.createAssuntoClicked(disciplina, getAdapterPosition());
    }

    @OnClick(R.id.ll_delete_disciplina)
    void onDeleteDisciplinaClick() {
        disciplinaViewHolderListener.deleteDisciplinaClicked(disciplina, getAdapterPosition());
    }

    @OnClick(R.id.ll_edit_disciplina)
    void onEditDisciplinaClick() {
        disciplinaViewHolderListener.editDisciplinaClicked(disciplina, getAdapterPosition());
    }

    public void bind(DisciplinaProvider.GroupDisciplina groupDisciplina) {
        this.groupDisciplina = groupDisciplina;
        this.disciplina = groupDisciplina.get();
        adjustSwipe();
        tvNome.setText(disciplina.getNome());
        tvDescricao.setText(disciplina.getDescricao());
    }

    private void adjustSwipe() {
        float LEFT_AMOUNT = -0.2f;
        float RIGHT_AMOUT = 0.5f;

        setMaxLeftSwipeAmount(LEFT_AMOUNT);
        setMaxRightSwipeAmount(RIGHT_AMOUT);

        switch (groupDisciplina.getPinDirection()) {
            case PINNED_LEFT:
                setSwipeItemHorizontalSlideAmount(LEFT_AMOUNT);
                break;
            case PINNED_RIGHT:
                setSwipeItemHorizontalSlideAmount(RIGHT_AMOUT);
                break;
            default:
                setSwipeItemHorizontalSlideAmount(0);
                break;
        }
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
