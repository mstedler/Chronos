package com.espweb.chronos.presentation.ui.adapters.data;
import androidx.core.util.Pair;

import com.espweb.chronos.domain.model.Assunto;
import com.espweb.chronos.domain.model.Disciplina;
import com.espweb.chronos.presentation.ui.adapters.data.base.AbstractGroupProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DisciplinaAssuntoProvider extends AbstractGroupProvider<Disciplina, Assunto> {

    public static final int NOT_PINNED = 0;
    public static final int PINNED_LEFT = 1;
    public static final int PINNED_RIGHT = 2;


    private List<Pair<DisciplinaData, List<AssuntoData>>> data;

    public DisciplinaAssuntoProvider() {
        data = new LinkedList<>();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return data.get(groupPosition).second.size();
    }

    @Override
    public void setGroups(List<Disciplina> groups) {
        for (Disciplina disciplina: groups) {
            DisciplinaData disciplinaData = new DisciplinaData(disciplina);
            final List<AssuntoData> assuntos = new ArrayList<>();

            for (Assunto assunto: disciplina.getAssuntos()) {
                assuntos.add(new AssuntoData(assunto));
            }
            data.add(new Pair<>(disciplinaData, assuntos));
        }
    }

    @Override
    public void updateGroupItem(Disciplina disciplina, int lastActionGroupPosition) {
        DisciplinaData disciplinaData = data.get(lastActionGroupPosition).first;
        Disciplina disciplina1 = disciplinaData.getItem();
        disciplina1.setNome(disciplina.getNome());
        disciplinaData.setPinDirection(NOT_PINNED);
    }

    @Override
    public void addGroupItem(Disciplina group, int position) {
        data.add(position, new Pair<>(new DisciplinaData(group), new ArrayList<>()));
    }

    @Override
    public void addChilItem(Assunto child, int groupPosition, int childPosition) {
        data.get(groupPosition).second.add(childPosition, new AssuntoData(child));
    }

    @Override
    public GroupData getGroupItem(int index) {
        return data.get(index).first;
    }

    @Override
    public ChildData getChildItem(int groupIndex, int position) {
        return data.get(groupIndex).second.get(position);
    }

    @Override
    public void removeGroupItem(int position) {
        data.remove(position);
    }

    public class DisciplinaData extends GroupData<Disciplina> {
        private Disciplina disciplina;
        private int pinDirection;

        public DisciplinaData(Disciplina disciplina){
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
        public Disciplina getItem() {
            return disciplina;
        }

        @Override
        public int getGroupId() {
            return (int)disciplina.getId();
        }
    }

    public class AssuntoData extends ChildData<Assunto> {
        private Assunto assunto;
        private int pinDirection;

        public AssuntoData(Assunto assunto) {
            this.assunto = assunto;
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
        public Assunto getItem() {
            return assunto;
        }

        @Override
        public int getChildId() {
            return (int)assunto.getId();
        }
    }
}
