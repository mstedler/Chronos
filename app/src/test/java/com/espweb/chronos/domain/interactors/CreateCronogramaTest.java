package com.espweb.chronos.domain.interactors;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.CreateCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.CreateCronogramaInteractorImpl;
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

public class CreateCronogramaTest {
    private MainThread mainThread;

    @Mock private CronogramaRepository cronogramaRepository;
    @Mock private Executor executor;
    @Mock private CreateCronogramaInteractor.Callback callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }

    @Test
    public void testCreateCronograma() throws Exception{
        long userId = 1;
        Cronograma cronograma = new Cronograma(0, UUID.randomUUID().toString(), "CronogramaActivity I",
                "Esse é um cronograma Teste",
                new Date("01/01/2019"),
                new Date("01/02/2019"));

        when(cronogramaRepository.insert(cronograma)).thenReturn(Long.valueOf(1));


        CreateCronogramaInteractorImpl createCronogramaInteractor = new CreateCronogramaInteractorImpl(
                executor, mainThread, callback, cronogramaRepository, cronograma);

        createCronogramaInteractor.run();

        Mockito.verify(cronogramaRepository).insert(cronograma);
        Mockito.verifyNoMoreInteractions(cronogramaRepository);
        Mockito.verify(callback).onCronogramaCreated(cronograma);
    }
}
