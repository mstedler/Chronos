package com.espweb.chronos.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.espweb.chronos.R;
import com.espweb.chronos.events.SessionHasExpiredEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewModelStoreOwner {

    @BindView(R.id.main_navigation_view)
    BottomNavigationView bottomNavigationView;

    private NavController navController;

    NavController.OnDestinationChangedListener onDestinationChangedListener = (controller, destination, arguments) -> {
        int id = destination.getId();

        if (id == R.id.main_dest || id == R.id.profile_dest || id == R.id.graphs_dest) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Chronos);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavBehavior());

        navController = Navigation.findNavController(this, R.id.nav_host_Fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.addOnDestinationChangedListener(onDestinationChangedListener);

        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sessionHasExpired(SessionHasExpiredEvent expiredEvent) {
        Toast.makeText(this, R.string.sessao_expirou, Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.signin_dest);
    }
}
