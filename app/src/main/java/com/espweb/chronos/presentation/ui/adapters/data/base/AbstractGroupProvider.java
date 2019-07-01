package com.espweb.chronos.presentation.data.base;


import androidx.core.util.Pair;

import java.util.List;

public abstract class AbstractGroupProvider<G, C> {
    public abstract class BaseData {
        public abstract void setPinDirection(int direction);
        public abstract int getPinDirection();
    }

    public abstract class GroupData<G> extends BaseData {
        public abstract G getItem();
        public abstract int getGroupId();
    }

    public abstract class ChildData<C> extends BaseData {
        public abstract C getItem();
        public abstract int getChildId();
    }

    public abstract int getGroupCount();
    public abstract int getChildCount(int groupPosition);

    public abstract void setGroups(List<G> groups);

    public abstract void addGroupItem(G group, int position);
    public abstract void addChilItem(C child, int groupPosition, int childPosition);

    public abstract GroupData getGroupItem(int index);
    public abstract ChildData getChildItem(int groupIndex, int position);

    public abstract void removeGroupItem(int position);

}
