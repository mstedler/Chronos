package com.espweb.chronos.presentation.ui.adapters.providers;
import androidx.core.util.Pair;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.ui.adapters.providers.base.GroupItemProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DisciplinaProvider extends GroupItemProvider<Disciplina, Assunto> {

    public static final int NOT_PINNED = 0;
    public static final int PINNED_LEFT = 1;
    public static final int PINNED_RIGHT = 2;

    private List<Pair<GroupDisciplina, List<ItemAssunto>>> data;

    public DisciplinaProvider() {
        data = new LinkedList<>();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getItemCount(int groupPosition) {
        return data.get(groupPosition).second.size();
    }

    @Override
    public void setGroups(List<Disciplina> groups) {
        data.clear();
        for (Disciplina disciplina: groups) {
            GroupDisciplina groupDisciplina = new GroupDisciplina(disciplina);
            final List<ItemAssunto> assuntos = new ArrayList<>();

            for (com.espweb.chronos.domain.model.Assunto assunto: disciplina.getAssuntos()) {
                assuntos.add(new ItemAssunto(assunto));
            }
            data.add(new Pair<>(groupDisciplina, assuntos));
        }
    }

    @Override
    public void updateGroup(com.espweb.chronos.domain.model.Disciplina disciplina, int position) {
        GroupDisciplina groupDisciplina = data.get(position).first;
        com.espweb.chronos.domain.model.Disciplina disciplina1 = groupDisciplina.get();
        disciplina1.setNome(disciplina.getNome());
        groupDisciplina.setPinDirection(NOT_PINNED);
    }

    @Override
    public void addGroup(com.espweb.chronos.domain.model.Disciplina group, int position) {
        data.add(position, new Pair<>(new GroupDisciplina(group), new ArrayList<>()));
    }

    @Override
    public void addItem(com.espweb.chronos.domain.model.Assunto child, int groupPosition, int childPosition) {
        data.get(groupPosition).second.add(childPosition, new ItemAssunto(child));
    }

    @Override
    public GroupDisciplina getGroup(int position) {
        return data.get(position).first;
    }

    @Override
    public ItemAssunto getItem(int groupPosition, int childPosition) {
        return data.get(groupPosition).second.get(childPosition);
    }

    @Override
    public void removeGroup(int position) {
        data.remove(position);
    }

    public class GroupDisciplina extends Group<Disciplina> {
        private com.espweb.chronos.domain.model.Disciplina disciplina;
        private int pinDirection;

        public GroupDisciplina(com.espweb.chronos.domain.model.Disciplina disciplina){
            this.disciplina = disciplina;
        }

        @Override
        public void setPinDirection(int direction) {
            pinDirection = direction;
        }

        @Override
        public int getPinDirection() {
            return pinDirection;
        }

        @Override
        public boolean isPinned() {
            return pinDirection != NOT_PINNED;
        }

        @Override
        public Disciplina get() {
            return disciplina;
        }

        @Override
        public int getId() {
            return (int)disciplina.getId();
        }
    }

    public class ItemAssunto extends Item<Assunto> {
        private com.espweb.chronos.domain.model.Assunto assunto;

        public ItemAssunto(com.espweb.chronos.domain.model.Assunto assunto) {
            this.assunto = assunto;
        }

        @Override
        public com.espweb.chronos.domain.model.Assunto get() {
            return assunto;
        }

        @Override
        public int getId() {
            return (int)assunto.getId();
        }
    }
}
