package com.espweb.chronos.presentation.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.espweb.chronos.R;
import com.espweb.chronos.events.SessionHasExpiredEvent;
import com.espweb.chronos.network.utils.ConnectionTimer;
import com.espweb.chronos.presentation.ui.custom.NoConnectionView;
import com.espweb.chronos.threading.MainThreadImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewModelStoreOwner, ConnectionTimer.ConnectionTimerListener {

    @BindView(R.id.main_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.no_connection)
    NoConnectionView noConnectionView;

    private NavController navController;
    private ConnectionTimer connectionTimer;

    NavController.OnDestinationChangedListener onDestinationChangedListener = (controller, destination, arguments) -> {
        int id = destination.getId();

        if (hasBottomNav(id)) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    };

    private boolean hasBottomNav(int id) {
        return id == R.id.main_dest || id == R.id.profile_dest || id == R.id.andamento_dest;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Chronos);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navController = Navigation.findNavController(this, R.id.nav_host_Fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);

        connectionTimer = new ConnectionTimer(this, MainThreadImpl.getInstance());

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectionTimer.startChecking();
    }

    @Override
    protected void onStop() {
        super.onStop();
        connectionTimer.stopChecking();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sessionHasExpired(SessionHasExpiredEvent expiredEvent) {
        //Toast.makeText(this, R.string.sessao_expirou, Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.signin_dest);
    }

    @Override
    public void isConnected() {
        noConnectionView.setVisibility(View.GONE);
    }

    @Override
    public void isDisconnected() {
        noConnectionView.setVisibility(View.VISIBLE);
    }
}
