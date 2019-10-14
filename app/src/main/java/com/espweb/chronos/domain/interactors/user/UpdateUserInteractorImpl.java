package com.espweb.chronos.domain.interactors.user;

import com.espweb.chronos.domain.executor.Executor;
import com.espweb.chronos.domain.executor.MainThread;
import com.espweb.chronos.domain.interactors.base.AbstractInteractor;
import com.espweb.chronos.domain.model.User;
import com.espweb.chronos.domain.repository.Repository;

public class UpdateUserInteractorImpl extends AbstractInteractor implements UpdateUserInteractor {

    private Callback callback;
    private Repository<User> userRepository;
    private User user;

    public UpdateUserInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, Repository<User> userRepository, User user) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepository = userRepository;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            userRepository.update(user);
            mainThread.post(() -> callback.onUserUpdated());
        } catch (Exception e) {
            mainThread.post(() -> callback.onError(e.getMessage()));
        }
    }
}
