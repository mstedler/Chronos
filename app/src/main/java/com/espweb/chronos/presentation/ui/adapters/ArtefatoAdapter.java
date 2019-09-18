package com.espweb.chronos.presentation.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.espweb.chronos.R;
import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.ui.adapters.providers.ArtefatoProvider;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.ui.adapters.viewholders.factory.ArtefatoViewHolderFactory;
import com.espweb.chronos.presentation.ui.custom.ExpandableItemIndicator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemState;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableSwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToOrigin;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionRemoveItem;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants.RESULT_SWIPED_RIGHT;

public class ArtefatoAdapter extends AbstractExpandableItemAdapter<ArtefatoAdapter.TipoViewHolder, ArtefatoViewHolder>
        implements
        ExpandableSwipeableItemAdapter<ArtefatoAdapter.TipoViewHolder, ArtefatoViewHolder>,
        ArtefatoViewHolder.ArtefatoListListener{

    private ArtefatoProvider artefatoProvider;
    private RecyclerViewExpandableItemManager expandableItemManager;

    public interface ArtefatoListListener {
        void onArtefatoClicked(Artefato artefato);
        void onArtefatoSwiped(Artefato artefato);
    }

    private ArtefatoAdapter.ArtefatoListListener artefatoListListener;

    public ArtefatoAdapter(RecyclerViewExpandableItemManager expandableItemManager){
        artefatoProvider = new ArtefatoProvider();
        setHasStableIds(true);
        this.expandableItemManager = expandableItemManager;
    }

    @Override
    public int getGroupCount() {
        return artefatoProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return artefatoProvider.getItemCount(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return artefatoProvider.getGroup(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return artefatoProvider.getItem(groupPosition, childPosition).getId();
    }

    @NonNull
    @Override
    public TipoViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.row_tipo, null);
        return new TipoViewHolder(view);
    }


    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return artefatoProvider.getItem(groupPosition, childPosition).get().getTipo().getIntValue();
    }

    @NonNull
    @Override
    public ArtefatoViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        ArtefatoViewHolder artefatoViewHolder = ArtefatoViewHolderFactory.createViewHolder(parent, EnumTipo.fromInt(viewType));
        artefatoViewHolder.setArtefatoListListener(this);
        return artefatoViewHolder;
    }

    @Override
    public void onBindGroupViewHolder(@NonNull TipoViewHolder holder, int groupPosition, int viewType) {
        holder.bind(artefatoProvider.getGroup(groupPosition));
    }

    @Override
    public void onBindChildViewHolder(@NonNull ArtefatoViewHolder holder, int groupPosition, int childPosition, int viewType) {
        holder.bind(artefatoProvider.getItem(groupPosition, childPosition).get());
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(@NonNull TipoViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return true;
    }

    @Override
    public SwipeResultAction onSwipeGroupItem(@NonNull TipoViewHolder holder, int groupPosition, int result) {
        return null;
    }

    @Override
    public SwipeResultAction onSwipeChildItem(@NonNull ArtefatoViewHolder holder, int groupPosition, int childPosition, int result) {
        if(result == RESULT_SWIPED_RIGHT) {
            return new DeleteArtefatoAction(groupPosition, childPosition);
        } else {
            return  null;
        }
    }

    @Override
    public int onGetGroupItemSwipeReactionType(@NonNull TipoViewHolder holder, int groupPosition, int x, int y) {
        return SwipeableItemConstants.REACTION_CAN_NOT_SWIPE_ANY;
    }

    @Override
    public int onGetChildItemSwipeReactionType(@NonNull ArtefatoViewHolder holder, int groupPosition, int childPosition, int x, int y) {
        return SwipeableItemConstants.REACTION_CAN_SWIPE_RIGHT;
    }

    @Override
    public void onSwipeGroupItemStarted(@NonNull TipoViewHolder holder, int groupPosition) {

    }

    @Override
    public void onSwipeChildItemStarted(@NonNull ArtefatoViewHolder holder, int groupPosition, int childPosition) {
        expandableItemManager.notifyGroupItemChanged(groupPosition, childPosition);
    }

    @Override
    public void onSetGroupItemSwipeBackground(@NonNull TipoViewHolder holder, int groupPosition, int type) {

    }

    @Override
    public void onSetChildItemSwipeBackground(@NonNull ArtefatoViewHolder holder, int groupPosition, int childPosition, int type) {
        if(type == SwipeableItemConstants.DRAWABLE_SWIPE_RIGHT_BACKGROUND) {
            holder.showBehindViews();
        } else {
            holder.hideBehindViews();
        }
    }

    @Override
    public void onArtefatoClicked(int row, Artefato artefato) {
        artefatoListListener.onArtefatoClicked(artefato);
    }

    public class TipoViewHolder extends AbstractExpandableItemViewHolder {
        @BindView(R.id.tv_tipo)
        TextView tvTipo;

        @BindView(R.id.item_indicator)
        ExpandableItemIndicator itemIndicator;

        private Context context;

        public TipoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bind(ArtefatoProvider.GroupTipo groupTipo) {
            tvTipo.setText(context.getResources().getStringArray(R.array.artefatosplural)[groupTipo.get().getIntValue()]);
            setExpandedState();
        }

        private void setExpandedState() {
            final ExpandableItemState expandState = getExpandState();

            if (expandState.isUpdated()) {
                boolean animateIndicator = expandState.hasExpandedStateChanged();
                itemIndicator.setExpandedState(expandState.isExpanded(), animateIndicator);
            }
        }

    }

    class DeleteArtefatoAction extends SwipeResultActionMoveToOrigin {
        private Artefato artefato;

        DeleteArtefatoAction(int groupPosition, int childPosition) {
            artefato = ArtefatoAdapter.this.artefatoProvider.getItem(groupPosition, childPosition).get();
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();
            artefatoListListener.onArtefatoSwiped(artefato);

        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
        }
    }

    public void setArtefatoListListener(ArtefatoAdapter.ArtefatoListListener artefatoListListener) {
        this.artefatoListListener = artefatoListListener;
    }

    public void setArtefatos(List<Artefato> artefatos) {
        artefatoProvider.clear();
        artefatoProvider.setArtefatos(artefatos);
        notifyDataSetChanged();
    }
    public void clear() {
        artefatoProvider.clear();
        notifyDataSetChanged();
    }

}
