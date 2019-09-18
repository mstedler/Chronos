package com.espweb.chronos.presentation.ui.adapters.providers;

import android.util.Pair;

import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.ui.adapters.providers.base.GroupItemProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArtefatoProvider extends GroupItemProvider<EnumTipo, Artefato> {
    private List<Pair<GroupTipo, List<ItemArtefato>>> data;

    public ArtefatoProvider() {
        data = new ArrayList<>();
    }

    public void setArtefatos(List<Artefato> artefatos) {
        Map<EnumTipo, List<Artefato>> map = artefatos.stream().collect(Collectors.groupingBy(Artefato::getTipo));

        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((enumTipo) -> {
            GroupTipo groupTipo = new GroupTipo(enumTipo.getKey());
            List<ItemArtefato> artefatoItens = new ArrayList<>();
            enumTipo.getValue().stream().sorted(Comparator.comparing(Artefato::getData).reversed()).forEach(artefato -> artefatoItens.add(new ItemArtefato(artefato)));
            data.add(new Pair<>(groupTipo, artefatoItens));
        });
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
    public void addGroup(EnumTipo group, int position) {

    }

    @Override
    public void addItem(Artefato item, int groupPosition, int itemPosition) {

    }

    @Override
    public void updateGroup(EnumTipo group, int position) {

    }

    @Override
    public GroupTipo getGroup(int position) {
        return data.get(position).first;
    }

    @Override
    public ItemArtefato getItem(int groupPosition, int itemPosition) {
        return data.get(groupPosition).second.get(itemPosition);
    }

    @Override
    public void removeGroup(int position) {

    }

    @Override
    public void removeItem(int groupPosition, int itemPosition) {
        data.get(groupPosition).second.remove(itemPosition);
//        if(data.get(groupPosition).second.isEmpty()) {
//            data.remove(groupPosition);
//        }
    }

    public void clear() {
        data.clear();
    }

    public class GroupTipo extends Group {

        private EnumTipo enumTipo;

        GroupTipo(EnumTipo enumTipo) {
            this.enumTipo = enumTipo;
        }

        @Override
        public void set(EnumTipo enumTipo) {
            this.enumTipo = enumTipo;
        }

        @Override
        public EnumTipo get() {
            return enumTipo;
        }

        @Override
        public int getId() {
            return enumTipo.getIntValue() + 1000;
        }

        @Override
        public String toString() {
            return enumTipo.toString();
        }
    }

    public class ItemArtefato extends Item {

        private Artefato artefato;

        ItemArtefato(Artefato artefato) {
            this.artefato = artefato;
        }

        @Override
        public void set(Artefato artefato) {
            this.artefato = artefato;
        }

        @Override
        public Artefato get() {
            return artefato;
        }

        @Override
        public int getId() {
            return (int)artefato.getId();
        }
    }
}
