package com.espweb.chronos.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espweb.chronos.R;
import com.espweb.chronos.data.ArtefatoRepositoryImpl;
import com.espweb.chronos.data.AssuntoRepositoryImpl;
import com.espweb.chronos.domain.executor.impl.ThreadExecutor;
import com.espweb.chronos.domain.repository.ArtefatoRepository;
import com.espweb.chronos.presentation.model.Artefato;
import com.espweb.chronos.presentation.model.Assunto;
import com.espweb.chronos.presentation.model.Exercicio;
import com.espweb.chronos.presentation.model.Material;
import com.espweb.chronos.presentation.model.Revisao;
import com.espweb.chronos.presentation.presenters.AssuntoPresenter;
import com.espweb.chronos.presentation.presenters.impl.AssuntoPresenterImpl;
import com.espweb.chronos.presentation.ui.adapters.ArtefatoAdapter;
import com.espweb.chronos.presentation.ui.dialogs.AssuntoDialog;
import com.espweb.chronos.presentation.ui.dialogs.YesNoDialog;
import com.espweb.chronos.presentation.ui.dialogs.base.ArtefatoDialog;
import com.espweb.chronos.presentation.ui.dialogs.factory.ArtefatoDialogFactory;
import com.espweb.chronos.threading.MainThreadImpl;
import com.github.clans.fab.FloatingActionMenu;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssuntoFragment extends Fragment implements AssuntoPresenter.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_artefatos)
    RecyclerView rvArtefatos;

    @BindView(R.id.cl_empty_artefatos)
    ConstraintLayout clEmptyArtefatos;

    @BindView(R.id.fa_menu)
    FloatingActionMenu faMenu;

    private AssuntoPresenter assuntoPresenter;
    private ArtefatoAdapter artefatoAdapter;

    private Assunto assunto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assunto, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long id = AssuntoFragmentArgs.fromBundle(requireArguments()).getIdAssunto();

        if(id == 0)
            navigateToCronograma();

        init();

        assuntoPresenter.getAssunto(id);
    }

    private void init() {
        initToolbar();
        initPresenter();
        initAdapter();
        initRecyclerView();
    }

    private void initToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> navigateToCronograma());

    }

    private void initPresenter() {
        ArtefatoRepository artefatoRepository = new ArtefatoRepositoryImpl(getContext());
        AssuntoRepositoryImpl assuntoRepository = new AssuntoRepositoryImpl(requireContext());
        assuntoPresenter = new AssuntoPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, artefatoRepository, assuntoRepository);
    }

    private ArtefatoAdapter.ArtefatoListListener artefatoListListener = new ArtefatoAdapter.ArtefatoListListener() {
        @Override
        public void onArtefatoClicked(Artefato artefato) {
            showDialogFor(artefato);
        }

        @Override
        public void onArtefatoSwiped(Artefato artefato) {
            showDeleteDialog(() -> assuntoPresenter.deleteArtefato(artefato));
        }
    };

    private void initAdapter() {


    }

    private void initRecyclerView() {
        RecyclerViewExpandableItemManager expandableItemManager = new RecyclerViewExpandableItemManager(null);
        RecyclerViewSwipeManager swipeManager = new RecyclerViewSwipeManager();

        artefatoAdapter = new ArtefatoAdapter(expandableItemManager);
        artefatoAdapter.setArtefatoListListener(artefatoListListener);

        expandableItemManager.setDefaultGroupsExpandedState(true);
        RecyclerView.Adapter wrappedAdapter = expandableItemManager.createWrappedAdapter(artefatoAdapter);
        wrappedAdapter = swipeManager.createWrappedAdapter(wrappedAdapter);
//        GridLayoutManager gridLayoutManager  = new GridLayoutManager(requireContext(), 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                long packedPosition = expandableItemManager.getExpandablePosition(position);
//                //int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(packedPosition);
//                int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(packedPosition);
//
//                if (childPosition == RecyclerView.NO_POSITION) {
//                    return 2;
//                } else {
//                    return 1;
//                }
//            }
//        });



        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        animator.setSupportsChangeAnimations(false);

        rvArtefatos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvArtefatos.setAdapter(wrappedAdapter);
        rvArtefatos.setItemAnimator(animator);

        swipeManager.attachRecyclerView(rvArtefatos);
        expandableItemManager.attachRecyclerView(rvArtefatos);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            showAssuntoDialog();
        } else if (item.getItemId() == R.id.item_delete) {
            showDeleteDialog(deleteAssuntoListener);
        }
        return true;
    }

    private YesNoDialog.YesNoDialogListener deleteAssuntoListener = new YesNoDialog.YesNoDialogListener() {
        @Override
        public void yesClicked() {
            assuntoPresenter.deleteAssunto(assunto);
        }
    };

    private void showDeleteDialog(YesNoDialog.YesNoDialogListener listener) {
        YesNoDialog confirmDelete = YesNoDialog.newInstance(getString(R.string.excluir), getString(R.string.tem_certeza));
        confirmDelete.setListener(listener);
        confirmDelete.show(requireFragmentManager(), "YES_NO_DIALOG");
    }

    private AssuntoDialog.AssuntoDialogListener assuntoDialogListener = new AssuntoDialog.AssuntoDialogListener() {
        @Override
        public void onAssuntoCreated(Assunto assunto) {
            //////
        }

        @Override
        public void onAssuntoUpdated(Assunto assunto) {
            setAssunto(assunto);
        }
    };

    private void showAssuntoDialog() {
        AssuntoDialog assuntoDialog = AssuntoDialog.newInstance(assunto);
        assuntoDialog.setAssuntoDialogListener(assuntoDialogListener);
        assuntoDialog.show(requireFragmentManager(), "ASSUNTO_DIALOG");
    }

    @Override
    public void showArtefatos(List<Artefato> artefatos) {
        artefatoAdapter.setArtefatos(artefatos);
        showRecyclerView();
    }

    @Override
    public void showEmptyView() {
        clEmptyArtefatos.setVisibility(View.VISIBLE);
        rvArtefatos.setVisibility(View.GONE);
    }

    @Override
    public void onArtefatoDeleted() {
        assuntoPresenter.getAllArtefatos(assunto.getId());
    }

    @Override
    public void onArtefatoNotFound() {
        artefatoAdapter.clear();
    }

    private void showRecyclerView() {
        rvArtefatos.setVisibility(View.VISIBLE);
        clEmptyArtefatos.setVisibility(View.GONE);
    }

    @Override
    public void navigateToCronograma() {
        Navigation.findNavController(requireView()).popBackStack();
    }

    @Override
    public void setAssunto(Assunto pAssunto) {
        this.assunto = pAssunto;
        toolbar.setTitle(pAssunto.getDescricao());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    private ArtefatoDialog.ArtefatoDialogListener artefatoDialogListener = new ArtefatoDialog.ArtefatoDialogListener() {
        @Override
        public void onArtefatoCreated(Artefato artefato) {
            faMenu.close(false);
            assuntoPresenter.getAllArtefatos(assunto.getId());
            //artefatoAdapter.addArtefato(artefato);
            showRecyclerView();
        }

        @Override
        public void onArtefatoUpdated(Artefato artefato) {
            assuntoPresenter.getAllArtefatos(assunto.getId());
            //artefatoAdapter.updateArtefato(artefato);
        }
    };

    @OnClick(R.id.fab_add_material)
    void onAddMaterialClick() {
        showDialogFor(new Material(assunto.getId()));
    }

    @OnClick(R.id.fab_add_exercicio)
    void onAddExercicioClick() {
        showDialogFor(new Exercicio(assunto.getId()));
    }

    @OnClick(R.id.fab_add_revisao)
    void onAddRevisaoClick() {
        showDialogFor(new Revisao(assunto.getId()));
    }

    private void showDialogFor(Artefato artefato) {
        ArtefatoDialog artefatoDialog = ArtefatoDialogFactory.createFor(artefato);
        artefatoDialog.setListener(artefatoDialogListener);
        artefatoDialog.show(requireFragmentManager(), "ARTEFATO_DIALOG");
    }
}
