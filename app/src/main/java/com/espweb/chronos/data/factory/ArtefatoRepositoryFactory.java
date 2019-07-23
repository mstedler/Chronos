package com.espweb.chronos.data.factory;

import com.espweb.chronos.data.ExercicioRepositoryImpl;
import com.espweb.chronos.data.MaterialRepositoryImpl;
import com.espweb.chronos.data.RevisaoRepositoryImpl;
import com.espweb.chronos.domain.exceptions.InvalidArtefatoException;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Exercicio;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.model.Revisao;
import com.espweb.chronos.domain.repository.ArtefatoRepository;

public class ArtefatoRepositoryFactory {

    public static ArtefatoRepository repositoryFor(Artefato artefato) throws InvalidArtefatoException {
        if (artefato instanceof Material) {
            return new MaterialRepositoryImpl();
        } else if( artefato instanceof Revisao){
            return new RevisaoRepositoryImpl();
        } else if (artefato instanceof Exercicio) {
            return new ExercicioRepositoryImpl();
        }
        throw new InvalidArtefatoException("");
    }
}
