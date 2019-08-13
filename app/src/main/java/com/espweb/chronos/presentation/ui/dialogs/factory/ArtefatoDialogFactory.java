package com.espweb.chronos.presentation.ui.dialogs.factory;

import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.dialogs.ExercicioDialog;
import com.espweb.chronos.presentation.ui.dialogs.MaterialDialog;
import com.espweb.chronos.presentation.ui.dialogs.RevisaoDialog;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;

public class ArtefatoDialogFactory {
    public static ArtefatoDialog createFor(Artefato artefato) {
        if(artefato instanceof Exercicio) {
            return ExercicioDialog.newInstance((Exercicio) artefato);
        } else if(artefato instanceof Material) {
            return MaterialDialog.newInstance((Material) artefato);
        } else if(artefato instanceof Revisao) {
            return RevisaoDialog.newInstance((Revisao) artefato);
        }
        return null;
    }
}
