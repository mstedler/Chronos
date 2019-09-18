package com.espweb.chronos.data.factory;

import android.content.Context;

import com.espweb.chronos.data.ExercicioRepositoryImpl;
import com.espweb.chronos.data.MaterialRepositoryImpl;
import com.espweb.chronos.data.RevisaoRepositoryImpl;
import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.EnumTipo;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.ArtefatoRepository;

public class ArtefatoRepositoryFactory {

    public static ArtefatoRepository createRepository(EnumTipo tipo, Context context) throws InvalidArtefatoException {
        switch (tipo) {
            case REVISAO: return new RevisaoRepositoryImpl(context);
            case MATERIAL: return new MaterialRepositoryImpl(context);
            case EXERCICIO: return new ExercicioRepositoryImpl(context);
            default: throw new InvalidArtefatoException("");
        }
    }
}
