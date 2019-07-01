package com.espweb.chronos.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.ui.adapters.data.DisciplinaAssuntoProvider;
import com.espweb.chronos.presentation.ui.adapters.data.base.AbstractGroupProvider;
import com.espweb.chronos.presentation.ui.adapters.viewholders.AssuntoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.DisciplinaViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableSwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;

import java.util.List;

import static com.espweb.chronos.presentation.ui.adapters.data.DisciplinaAssuntoProvider.NOT_PINNED;
import static com.espweb.chronos.presentation.ui.adapters.data.DisciplinaAssuntoProvider.PINNED_LEFT;
import static com.espweb.chronos.presentation.ui.adapters.data.DisciplinaAssuntoProvider.PINNED_RIGHT;

public class DisciplinaAdapter extends AbstractExpandableItemAdapter<DisciplinaViewHolder, AssuntoViewHolder>
                            implements ExpandableSwipeableItemAdapter<DisciplinaViewHolder, AssuntoViewHolder> {

    public void addAssunto(Assunto assunto, int groupPosition) {
        int childPosition = disciplinaAssuntoProvider.getChildCount(groupPosition);
        disciplinaAssuntoProvider.addChilItem(assunto, groupPosition, childPosition);
        expandableItemManager.notifyChildItemInserted(groupPosition, childPosition);
    }

    public void removeDisciplina(int lastActionGroupPosition) {
        disciplinaAssuntoProvider.removeGroupItem(lastActionGroupPosition);
        expandableItemManager.notifyGroupItemRemoved(lastActionGroupPosition);
    }

    public void addDisciplina(Disciplina disciplina) {
        int groupPosition = disciplinaAssuntoProvider.getGroupCount();
        disciplinaAssuntoProvider.addGroupItem(disciplina, groupPosition);
        expandableItemManager.notifyGroupItemInserted(groupPosition);
    }

    public void updateDisciplina(Disciplina disciplina, int lastActionGroupPosition) {
        disciplinaAssuntoProvider.updateGroupItem(disciplina, lastActionGroupPosition);
        expandableItemManager.notifyGroupItemChanged(lastActionGroupPosition);

    }

    public interface AssuntoListListener {
        void onAssuntoClicked(Assunto assunto);
    }

    public interface DisciplinaListListener {
        void onEditClicked(Disciplina disciplina, int groupPosition);
        void onDeleteClicked(long id, int groupPosition);
        void onAddAssuntoClicked(Disciplina disciplina, int groupPosition);
    }

    private DisciplinaAssuntoProvider disciplinaAssuntoProvider;
    private final LayoutInflater layoutInflater;
    private AssuntoListListener assuntoListListener;
    private DisciplinaListListener disciplinaListListener;

    private RecyclerViewExpandableItemManager expandableItemManager;

    public void setDisciplinaListListener(DisciplinaListListener disciplinaListListener) {
        this.disciplinaListListener = disciplinaListListener;
    }

    public void setAssuntoListListener(AssuntoListListener assuntoListListener) {
        this.assuntoListListener = assuntoListListener;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        disciplinaAssuntoProvider.setGroups(disciplinas);
        notifyDataSetChanged();
    }

    public DisciplinaAdapter(Context context,
                             RecyclerViewExpandableItemManager expandableItemManager,
                             DisciplinaAssuntoProvider disciplinaAssuntoProvider) {
        this.disciplinaAssuntoProvider = disciplinaAssuntoProvider;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.expandableItemManager = expandableItemManager;

        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return disciplinaAssuntoProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return disciplinaAssuntoProvider.getChildCount(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return disciplinaAssuntoProvider.getGroupItem(groupPosition).getGroupId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return disciplinaAssuntoProvider.getChildItem(groupPosition, childPosition).getChildId();
    }

    @NonNull
    @Override
    public DisciplinaViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_disciplina, parent, false);
        return new DisciplinaViewHolder(view);
    }

    @NonNull
    @Override
    public AssuntoViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_assunto, parent, false);
        return new AssuntoViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(@NonNull DisciplinaViewHolder holder, int groupPosition, int viewType) {
        final DisciplinaAssuntoProvider.DisciplinaData data = (DisciplinaAssuntoProvider.DisciplinaData) disciplinaAssuntoProvider.getGroupItem(groupPosition);
        final Disciplina disciplina = data.getItem();
        holder.setMaxLeftSwipeAmount(-0.2f);
        holder.setMaxRightSwipeAmount(0.5f);

        switch (data.getPinDirection()) {
            case PINNED_LEFT:
                holder.setSwipeItemHorizontalSlideAmount(-0.2f);
                break;
            case PINNED_RIGHT:
                holder.setSwipeItemHorizontalSlideAmount(0.5f);
                break;
            default:
                holder.setSwipeItemHorizontalSlideAmount(0);
                break;
        }

        holder.container.setOnClickListener(v -> {
            if(data.getPinDirection() != NOT_PINNED) {
                data.setPinDirection(NOT_PINNED);
                expandableItemManager.notifyGroupItemChanged(groupPosition);
            }
        });

        holder.llAssuntoAdd.setOnClickListener(v -> {
            if(disciplinaListListener != null) {
                disciplinaListListener.onAddAssuntoClicked(disciplina, groupPosition);
            }
        });

        holder.llDisciplinaDelete.setOnClickListener(v -> {
            if(disciplinaListListener != null) {
                disciplinaListListener.onDeleteClicked(disciplina.getId(), groupPosition);
            }
        });

        holder.llDisciplinaEdit.setOnClickListener( v -> {
            if(disciplinaListListener != null) {
                disciplinaListListener.onEditClicked(disciplina, groupPosition);
            }
        });

        holder.tvNome.setText(disciplina.getNome());
    }

    @Override
    public void onBindChildViewHolder(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int viewType) {
        final DisciplinaAssuntoProvider.AssuntoData assuntoData = (DisciplinaAssuntoProvider.AssuntoData)disciplinaAssuntoProvider.getChildItem(groupPosition, childPosition);
        final Assunto assunto = assuntoData.getItem();
        holder.tvDescricao.setText(assunto.getDescricao());
        holder.itemView.setOnClickListener(v -> assuntoListListener.onAssuntoClicked(assuntoData.getItem()));
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull DisciplinaViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        DisciplinaAssuntoProvider.DisciplinaData data = (DisciplinaAssuntoProvider.DisciplinaData) disciplinaAssuntoProvider.getGroupItem(groupPosition);
        return data.getPinDirection() == NOT_PINNED;
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public SwipeResultAction onSwipeGroupItem(@NonNull DisciplinaViewHolder holder, int groupPosition, int result) {
        switch (result) {
            case SwipeableItemConstants.RESULT_SWIPED_LEFT:
                return new PinResultAction(this, groupPosition, PINNED_LEFT);
            case SwipeableItemConstants.RESULT_SWIPED_RIGHT:
                return new PinResultAction(this, groupPosition, PINNED_RIGHT);
            case SwipeableItemConstants.RESULT_CANCELED:
            default:
                if (groupPosition != RecyclerView.NO_POSITION) {
                    return new UnpinResultAction(this, groupPosition);
                } else {
                    return null;
                }
        }
    }

    @Override
    public SwipeResultAction onSwipeChildItem(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int result) {
        return null;
    }

    @Override
    public int onGetGroupItemSwipeReactionType(@NonNull DisciplinaViewHolder holder, int groupPosition, int x, int y) {
        return SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public int onGetChildItemSwipeReactionType(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int x, int y) {
        return SwipeableItemConstants.REACTION_CAN_NOT_SWIPE_ANY;
    }

    @Override
    public void onSwipeGroupItemStarted(@NonNull DisciplinaViewHolder holder, int groupPosition) {
        expandableItemManager.notifyGroupItemChanged(groupPosition);
    }

    @Override
    public void onSwipeChildItemStarted(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition) {
        expandableItemManager.notifyGroupItemChanged(groupPosition, childPosition);
    }

    @Override
    public void onSetGroupItemSwipeBackground(@NonNull DisciplinaViewHolder holder, int groupPosition, int type) {
        if(type == SwipeableItemConstants.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND) {
            holder.behindViews.setVisibility(View.GONE);
        } else {
            holder.behindViews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSetChildItemSwipeBackground(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int type) {

    }

    class PinResultAction extends SwipeResultActionMoveToSwipedDirection {
        private DisciplinaAdapter disciplinaAdapter;
        private int position;
        private int pinDirection;

        PinResultAction(DisciplinaAdapter disciplinaAdapter, int position, int pinDirection) {
            this.disciplinaAdapter = disciplinaAdapter;
            this.position = position;
            this.pinDirection = pinDirection;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            AbstractGroupProvider.BaseData data = disciplinaAdapter.disciplinaAssuntoProvider.getGroupItem(position);

            if(data.getPinDirection() == NOT_PINNED) {
                data.setPinDirection(pinDirection);
            } else if(data.getPinDirection() != pinDirection) {
                data.setPinDirection(NOT_PINNED);
            }
            disciplinaAdapter.notifyItemChanged(position);
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            disciplinaAdapter = null;
        }
    }

    class UnpinResultAction extends SwipeResultActionDefault {
        private DisciplinaAdapter disciplinaAdapter;
        private int position;

        UnpinResultAction(DisciplinaAdapter disciplinaAdapter, int position) {
            this.disciplinaAdapter = disciplinaAdapter;
            this.position = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            AbstractGroupProvider.BaseData data = disciplinaAdapter.disciplinaAssuntoProvider.getGroupItem(position);

            if(data.getPinDirection() != NOT_PINNED) {
                data.setPinDirection(NOT_PINNED);
                disciplinaAdapter.notifyItemChanged(position);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            disciplinaAdapter = null;
        }
    }
}
