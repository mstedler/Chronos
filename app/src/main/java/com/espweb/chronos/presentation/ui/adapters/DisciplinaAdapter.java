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
import com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider;
import com.espweb.chronos.presentation.ui.adapters.providers.base.GroupItemProvider;
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

import static com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider.NOT_PINNED;
import static com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider.PINNED_LEFT;
import static com.espweb.chronos.presentation.ui.adapters.providers.DisciplinaProvider.PINNED_RIGHT;

public class DisciplinaAdapter extends AbstractExpandableItemAdapter<DisciplinaViewHolder, AssuntoViewHolder>
                            implements ExpandableSwipeableItemAdapter<DisciplinaViewHolder, AssuntoViewHolder> {

    public interface AssuntoListListener {
        void onAssuntoClicked(com.espweb.chronos.domain.model.Assunto assunto);
    }

    public interface DisciplinaListListener {
        void onEditDisciplinaClicked(com.espweb.chronos.domain.model.Disciplina disciplina);
        void onDeleteDisciplinaClicked(long id);
        void onCreateAssuntoClicked(com.espweb.chronos.domain.model.Disciplina disciplina);
    }

    private DisciplinaProvider disciplinaProvider;
    private final LayoutInflater layoutInflater;
    private AssuntoListListener assuntoListListener;
    private DisciplinaListListener disciplinaListListener;

    private RecyclerViewExpandableItemManager expandableItemManager;

    private int lastActionPosition;

    public DisciplinaAdapter(Context context, RecyclerViewExpandableItemManager expandableItemManager, DisciplinaProvider disciplinaProvider) {
        this.disciplinaProvider = disciplinaProvider;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.expandableItemManager = expandableItemManager;

        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return disciplinaProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return disciplinaProvider.getItemCount(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return disciplinaProvider.getGroup(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return disciplinaProvider.getItem(groupPosition, childPosition).getId();
    }


    private DisciplinaViewHolder.DisciplinaViewHolderListener disciplinaViewHolderListener =
            new DisciplinaViewHolder.DisciplinaViewHolderListener() {
                @Override
                public void notifyGroupItemChanged(int position) {
                    expandableItemManager.notifyGroupItemChanged(position);
                }

                @Override
                public void createAssuntoClicked(Disciplina disciplina, int adapterPosition) {
                    disciplinaListListener.onCreateAssuntoClicked(disciplina);
                    lastActionPosition = adapterPosition;
                }

                @Override
                public void deleteDisciplinaClicked(long disciplinaId, int position) {
                    disciplinaListListener.onDeleteDisciplinaClicked(disciplinaId);
                    lastActionPosition = position;
                }

                @Override
                public void editDisciplinaClicked(Disciplina disciplina, int position) {
                    disciplinaListListener.onEditDisciplinaClicked(disciplina);
                    lastActionPosition = position;
                }
            };

    @NonNull
    @Override
    public DisciplinaViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_disciplina, parent, false);
        return new DisciplinaViewHolder(view, disciplinaViewHolderListener);
    }

    private AssuntoViewHolder.AssuntoViewHolderListener assuntoViewHolderListener = new AssuntoViewHolder.AssuntoViewHolderListener() {
        @Override
        public void onAssuntoClicked(Assunto assunto) {
            assuntoListListener.onAssuntoClicked(assunto);
        }
    };

    @NonNull
    @Override
    public AssuntoViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.row_assunto, parent, false);
        return new AssuntoViewHolder(view, assuntoViewHolderListener);
    }

    @Override
    public void onBindGroupViewHolder(@NonNull DisciplinaViewHolder disciplinaViewHolder, int groupPosition, int viewType) {
        final DisciplinaProvider.GroupDisciplina groupDisciplina = disciplinaProvider.getGroup(groupPosition);
        disciplinaViewHolder.bind(groupDisciplina);
    }

    @Override
    public void onBindChildViewHolder(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int viewType) {
        final DisciplinaProvider.ItemAssunto itemAssunto = disciplinaProvider.getItem(groupPosition, childPosition);
        holder.bind(itemAssunto);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull DisciplinaViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        GroupItemProvider.Pinnable data = disciplinaProvider.getGroup(groupPosition);
        return !data.isPinned();
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
        GroupItemProvider.Pinnable pinnable = disciplinaProvider.getGroup(groupPosition);
        switch (result) {
            case SwipeableItemConstants.RESULT_SWIPED_LEFT:
                return new PinResultAction(pinnable, groupPosition, PINNED_LEFT);
            case SwipeableItemConstants.RESULT_SWIPED_RIGHT:
                return new PinResultAction(pinnable, groupPosition, PINNED_RIGHT);
            case SwipeableItemConstants.RESULT_CANCELED:
            default:
                if (groupPosition != RecyclerView.NO_POSITION) {
                    return new UnpinResultAction(pinnable, groupPosition);
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
            holder.hideBehindViews();
        } else {
            holder.showBehindViews();
        }
    }

    @Override
    public void onSetChildItemSwipeBackground(@NonNull AssuntoViewHolder holder, int groupPosition, int childPosition, int type) {

    }

    class PinResultAction extends SwipeResultActionMoveToSwipedDirection {
        private GroupItemProvider.Pinnable pinnable;
        private int position;
        private int pinDirection;

        PinResultAction(GroupItemProvider.Pinnable pinnable, int position, int pinDirection) {
            this.position = position;
            this.pinDirection = pinDirection;
            this.pinnable = pinnable;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(!pinnable.isPinned()) {
                pinnable.setPinDirection(pinDirection);
            } else if(pinnable.getPinDirection() != pinDirection) {
                pinnable.setPinDirection(NOT_PINNED);
            }
            expandableItemManager.notifyGroupItemChanged(position);
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
        }
    }

    class UnpinResultAction extends SwipeResultActionDefault {
        private GroupItemProvider.Pinnable pinnable;
        private int position;

        UnpinResultAction(GroupItemProvider.Pinnable pinnable, int position) {
            this.pinnable = pinnable;
            this.position = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();
            if(pinnable.isPinned()) {
                pinnable.setPinDirection(NOT_PINNED);
                expandableItemManager.notifyGroupItemChanged(position);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
        }
    }

    public void addAssunto(Assunto assunto) {
        int groupPosition = getGroupPosition(lastActionPosition);
        int childPosition = disciplinaProvider.getItemCount(groupPosition);
        disciplinaProvider.addItem(assunto, groupPosition, childPosition);
        expandableItemManager.notifyChildItemInserted(groupPosition, childPosition);
        expandableItemManager.expandGroup(groupPosition);
    }

    private int getGroupPosition(int flatPosition) {
        long expandablePosition = expandableItemManager.getExpandablePosition(flatPosition);
        return RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
    }

    public void removeDisciplina() {
        int groupPosition = getGroupPosition(lastActionPosition);
        disciplinaProvider.removeGroup(groupPosition);
        expandableItemManager.notifyGroupItemRemoved(groupPosition);
    }

    public void addDisciplina(com.espweb.chronos.domain.model.Disciplina disciplina) {
        int groupPosition = disciplinaProvider.getGroupCount();
        disciplinaProvider.addGroup(disciplina, groupPosition);
        expandableItemManager.notifyGroupItemInserted(groupPosition);
    }

    public void updateDisciplina(Disciplina disciplina) {
        int groupPosition = getGroupPosition(lastActionPosition);
        disciplinaProvider.updateGroup(disciplina, groupPosition);
        expandableItemManager.notifyGroupItemChanged(groupPosition);
    }

    public void setDisciplinaListListener(DisciplinaListListener disciplinaListListener) {
        this.disciplinaListListener = disciplinaListListener;
    }

    public void setAssuntoListListener(AssuntoListListener assuntoListListener) {
        this.assuntoListListener = assuntoListListener;
    }

    public void setDisciplinas(List<com.espweb.chronos.domain.model.Disciplina> disciplinas) {
        disciplinaProvider.setGroups(disciplinas);
        notifyDataSetChanged();
    }
}
