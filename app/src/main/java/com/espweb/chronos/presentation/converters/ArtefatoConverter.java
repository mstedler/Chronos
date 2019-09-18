package com.espweb.chronos.presentation.converters;

import com.espweb.chronos.presentation.model.Artefato;

import org.parceler.Parcels;
import org.parceler.converter.ArrayListParcelConverter;

public class ArtefatoConverter extends ArrayListParcelConverter<Artefato> {

    public ArtefatoConverter() {

    }

    @Override
    public void itemToParcel(Artefato input, android.os.Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Artefato itemFromParcel(android.os.Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Artefato.class.getClassLoader()));
    }
}