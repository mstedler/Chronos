package com.espweb.chronos.presentation.ui.custom.dialogs.factory;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.custom.dialogs.ExercicioDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.MaterialDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.RevisaoDialog;
import com.espweb.chronos.presentation.ui.custom.dialogs.base.ArtefatoDialog;

public class ArtefatoDialogFactory {
    public static ArtefatoDialog createFor(Artefato artefato) {
        switch (artefato.getTipo()){
            case REVISAO: return RevisaoDialog.newInstance((Revisao) artefato);
            case MATERIAL: return MaterialDialog.newInstance((Material) artefato);
            case EXERCICIO: return ExercicioDialog.newInstance((Exercicio) artefato);
        }
        return null;
    }
}
