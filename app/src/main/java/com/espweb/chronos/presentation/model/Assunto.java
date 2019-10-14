package com.espweb.chronos.presentation.model;

import com.espweb.chronos.presentation.converters.ArtefatoConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;
import org.parceler.Parcels;
import org.parceler.TypeRangeParcelConverter;
import org.parceler.converter.ArrayListParcelConverter;

import java.util.Collections;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Assunto {
    private long id;
    private String uuid;
    private String descricao;

    @ParcelPropertyConverter(ArtefatoConverter.class)
    private List<Artefato> artefatos;

    private long idDisciplina;

    public Assunto() {
    }

    public Assunto(long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public List<Artefato> getArtefatos() {
        return artefatos;
    }

    public int getArtefatosSize() {
        return artefatos.size();
    }

    public long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public boolean isNew() {
        return id == 0;
    }

    public boolean isValid() {
        return descricao.trim().length() > 3;
    }

    @ParcelPropertyConverter(ArtefatoConverter.class)
    public void setArtefatos(List<Artefato> artefatos) {
        this.artefatos = artefatos;
    }
}
