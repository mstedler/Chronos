package com.espweb.chronos.domain.interactors;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.GetAllCronogramasInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.GetAllCronogramasInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.Repository;
import com.espweb.chronos.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class GetAllCronogramasTest {
    private MainThread mainThread;
    @Mock private Executor executor;
    @Mock private Repository<Cronograma> cronogramaRepository;
    @Mock private GetAllCronogramasInteractor.Callback callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }

    @Test
    public void testCronogramasRetrieved() throws Exception{
        List<Cronograma> cronogramas = new ArrayList<>();

        when(cronogramaRepository.getAll(1)).thenReturn(cronogramas);

        GetAllCronogramasInteractorImpl getAllCronogramasInteractor = new GetAllCronogramasInteractorImpl(executor, mainThread, callback, cronogramaRepository, 1);
        getAllCronogramasInteractor.run();

        Mockito.verify(cronogramaRepository).getAll(1);
        Mockito.verifyNoMoreInteractions(cronogramaRepository);
        Mockito.verify(callback).onCronogramasRetrieved(cronogramas);
    }
}
