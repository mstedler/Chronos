package com.espweb.chronos.domain.interactors;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.cronograma.DeleteCronogramaInteractor;
import com.espweb.chronos.domain.interactors.cronograma.impl.DeleteCronogramaInteractorImpl;
import com.espweb.chronos.domain.model.Cronograma;
import com.espweb.chronos.domain.repository.CronogramaRepository;
import com.espweb.chronos.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.mockito.Mockito.when;

public class DeleteCronogramaTest {
    private MainThread mainThread;

    @Mock
    private CronogramaRepository cronogramaRepository;
    @Mock private Executor executor;
    @Mock private DeleteCronogramaInteractor.Callback callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }


    @Test
    public void testCreateCronograma() throws Exception{
        Cronograma cronograma = new Cronograma("Cronograma I", "81uf81jf81jf-fu1hfu1hf1hf-uhf18fh18fj1",
                "Esse é um cronograma Teste",
                new Date("01/01/2019"),
                new Date("01/02/2019"));
        cronograma.setId(1);

        when(cronogramaRepository.delete(cronograma.getId())).thenReturn(true);

        DeleteCronogramaInteractorImpl createCronogramaInteractor = new DeleteCronogramaInteractorImpl(
                executor, mainThread, callback, cronogramaRepository, cronograma.getId()
        );

        createCronogramaInteractor.run();

        Mockito.verify(cronogramaRepository).delete(cronograma.getId());
        Mockito.verifyNoMoreInteractions(cronogramaRepository);
        Mockito.verify(callback).onCronogramaDeleted();
    }
}
