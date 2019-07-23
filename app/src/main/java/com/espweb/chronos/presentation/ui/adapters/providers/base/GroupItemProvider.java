package com.espweb.chronos.presentation.ui.adapters.providers.base;


import java.util.List;

public abstract class GroupItemProvider<G, I> {

    public interface Pinnable {
       void setPinDirection(int direction);
       int getPinDirection();
       boolean isPinned();
    }

    public abstract class Group<G> implements Pinnable {
        public abstract G get();
        public abstract int getId();
    }

    public abstract class Item<I> {
        public abstract I get();
        public abstract int getId();
    }

    public abstract int getGroupCount();
    public abstract int getItemCount(int groupPosition);

    public abstract void setGroups(List<G> groups);

    public abstract void addGroup(G group, int position);
    public abstract void addItem(I item, int groupPosition, int itemPosition);

    public abstract void updateGroup(G disciplina, int position);

    public abstract Group getGroup(int position);
    public abstract Item getItem(int groupPosition, int itemPosition);

    public abstract void removeGroup(int position);

}
