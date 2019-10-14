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
    private List<Pair<GroupTipo, List<ItemArtefato>>> dados;

    public ArtefatoProvider() {
        dados = new ArrayList<>();
    }

    public void setArtefatos(List<Artefato> artefatos) {
        Map<EnumTipo, List<Artefato>> map = artefatos.stream().collect(Collectors.groupingBy(Artefato::getTipo));

        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach((enumTipo) -> {
            GroupTipo groupTipo = new GroupTipo(enumTipo.getKey());
            List<ItemArtefato> artefatoItens = new ArrayList<>();
            enumTipo.getValue().stream().sorted(Comparator.comparing(Artefato::getData).reversed()).forEach(artefato -> artefatoItens.add(new ItemArtefato(artefato)));
            dados.add(new Pair<>(groupTipo, artefatoItens));
        });
    }

    @Override
    public int getGroupCount() {
        return dados.size();
    }

    @Override
    public int getItemCount(int groupPosition) {
        return dados.get(groupPosition).second.size();
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
        return dados.get(position).first;
    }

    @Override
    public ItemArtefato getItem(int groupPosition, int itemPosition) {
        return dados.get(groupPosition).second.get(itemPosition);
    }

    @Override
    public void removeGroup(int position) {

    }

    @Override
    public void removeItem(int groupPosition, int itemPosition) {
        dados.get(groupPosition).second.remove(itemPosition);
//        if(dados.get(groupPosition).second.isEmpty()) {
//            dados.remove(groupPosition);
//        }
    }

    public void clear() {
        dados.clear();
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
