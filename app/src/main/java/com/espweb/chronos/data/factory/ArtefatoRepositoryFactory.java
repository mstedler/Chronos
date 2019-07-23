package com.espweb.chronos.data.artefatos;

import com.espweb.chronos.data.MaterialRepositoryImpl;
import com.espweb.chronos.domain.model.Artefato;
import com.espweb.chronos.domain.model.Material;
import com.espweb.chronos.domain.repository.Repository;

public class ArtefatoRepositoryFactory {

    public static Repository repository(Artefato artefato) {
        if (artefato instanceof Material) {
            return new MaterialRepositoryImpl();
        }

    }
}
