package com.espweb.chronos.presentation.ui.adapters.providers;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Disciplina;
import com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter;
import com.espweb.chronos.presentation.ui.adapters.providers.base.GroupItemProvider;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.espweb.chronos.presentation.ui.adapters.DisciplinaAdapter.NOT_PINNED;

public class DisciplinaProvider extends GroupItemProvider<Disciplina, Assunto> {
    private List<Pair<GroupDisciplina, List<ItemAssunto>>> data;

    public DisciplinaProvider() {
        data = new ArrayList<>();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getItemCount(int groupPosition) {
        return data.get(groupPosition).second.size();
    }

    public void setData(List<Disciplina> disciplinas){
        data.clear();
        for (Disciplina disciplina: disciplinas) {
            GroupDisciplina groupDisciplina = new GroupDisciplina(disciplina);
            final List<ItemAssunto> assuntos = new ArrayList<>();

            for (Assunto assunto: disciplina.getAssuntos()) {
                assuntos.add(new ItemAssunto(assunto));
            }
            data.add(new Pair<>(groupDisciplina, assuntos));
        }
    }

    @Override
    public void updateGroup(Disciplina disciplina, int position) {
        GroupDisciplina groupDisciplina = data.get(position).first;
        groupDisciplina.set(disciplina);
    }

    @Override
    public void addGroup(Disciplina disciplina, int position) {
        data.add(position, new Pair<>(new GroupDisciplina(disciplina), new ArrayList<>()));
    }

    @Override
    public void addItem(Assunto assunto, int groupPosition, int childPosition) {
        data.get(groupPosition).second.add(childPosition, new ItemAssunto(assunto));
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
    public void removeGroup(int position)    {
        data.remove(position);
    }

    public class GroupDisciplina extends Group implements DisciplinaAdapter.Pinnable {
        private Disciplina disciplina;
        private int pinDirection;

        GroupDisciplina(Disciplina disciplina){
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
        public void set(Disciplina disciplina) {
            this.disciplina = disciplina;
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

    public class ItemAssunto extends Item {
        private Assunto assunto;

        ItemAssunto(Assunto assunto) {
            this.assunto = assunto;
        }

        @Override
        public void set(Assunto assunto) {
            this.assunto = assunto;
        }

        @Override
        public Assunto get() {
            return assunto;
        }

        @Override
        public int getId() {
            return (int)assunto.getId();
        }
    }
}
