package com.espweb.chronos.presentation.ui.adapters.providers.base;


import java.util.List;

public abstract class GroupItemProvider<G, I> {


    public abstract class Group {
        public abstract void set(G group);
        public abstract G get();
        public abstract int getId();
    }

    public abstract class Item {
        public abstract void set(I item);
        public abstract I get();
        public abstract int getId();
    }

    public abstract int getGroupCount();
    public abstract int getItemCount(int groupPosition);

    public abstract void addGroup(G group, int position);
    public abstract void addItem(I item, int groupPosition, int itemPosition);

    public abstract void updateGroup(G group, int position);

    public abstract Group getGroup(int position);
    public abstract Item getItem(int groupPosition, int itemPosition);

    public abstract void removeGroup(int position);
    public abstract void removeItem(int groupPosition, int itemPosition);
}
