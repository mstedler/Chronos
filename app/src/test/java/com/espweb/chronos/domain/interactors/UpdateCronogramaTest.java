package com.espweb.chronos.domain.interactors;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.UpdateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.UpdateCronogramaInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class UpdateCronogramaTest {
    private MainThread mainThread;

    @Mock private CronogramaRepository cronogramaRepository;
    @Mock private Executor executor;
    @Mock private UpdateCronogramaInteractor.Callback callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }

    @Test
    public void testCreateCronograma() throws Exception{
        Cronograma cronograma = new Cronograma(UUID.randomUUID().toString(), "CronogramaActivity I",
                "Esse é um cronograma Teste",
                new Date("01/01/2019"),
                new Date("01/02/2019"));

        when(cronogramaRepository.update(cronograma)).thenReturn(true);

        UpdateCronogramaInteractorImpl createCronogramaInteractor = new UpdateCronogramaInteractorImpl(
                executor, mainThread, callback, cronogramaRepository, cronograma
        );

        createCronogramaInteractor.run();

        Mockito.verify(cronogramaRepository).update(cronograma);
        Mockito.verifyNoMoreInteractions(cronogramaRepository);
        Mockito.verify(callback).onCronogramaUpdated();
    }
}
