package com.espweb.chronos.presentation.ui.adapters.viewholders;

import androidx.annotation.NonNull;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.espweb.chronos.R;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.adapters.viewholders.base.ArtefatoViewHolder;
import com.espweb.chronos.presentation.utils.DateUtils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevisaoViewHolder extends ArtefatoViewHolder {

    private Revisao revisao;

    @BindView(R.id.tv_escopo)
    TextView tvEscopo;

    @BindView(R.id.tv_descricao)
    TextView tvDescricao;

    @BindView(R.id.tv_data)
    TextView tvData;

    private Context context;
    public RevisaoViewHolder(@NonNull View itemView, ArtefatoAdapter.ArtefatoListListener artefatoListListener, Context context) {
        super(itemView, artefatoListListener);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    @Override
    public void bind(Artefato artefato) {
        this.revisao = (Revisao) artefato;
        String[] escopos = context.getResources().getStringArray(R.array.escopos);
        tvEscopo.setText(escopos[revisao.getEscopo().getIntValue()]);
        tvDescricao.setText(revisao.getDescricao());
        tvData.setText(DateUtils.formatDate(revisao.getData()));
    }

    @Override
    public void onClick(View v) {
        artefatoListListener.onArtefatoClicked(revisao);
    }
}
