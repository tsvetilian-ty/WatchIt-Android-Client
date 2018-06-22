package yankov.tsvetilian.watchit.Activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import yankov.tsvetilian.watchit.Fragments.DashboardFragment;
import yankov.tsvetilian.watchit.Fragments.LoginFragment;
import yankov.tsvetilian.watchit.Fragments.SignUpFragment;
import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.Utilities.Cache;
import yankov.tsvetilian.watchit.Utilities.FragmentEvent;
import yankov.tsvetilian.watchit.Utilities.FragmentEventListener;
import yankov.tsvetilian.watchit.ViewModels.UserViewModel;

public class MainActivity extends AppCompatActivity implements FragmentEventListener {

    private UserViewModel userViewModel;
    private Fragment currentFragmentHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        init();
    }

    private void init() {
        userViewModel.getAllSettings().observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable List<UserModel> userSettings) {
                if (userSettings != null && !userSettings.isEmpty()) {
                    new Cache.CacheBuilder()
                            .setAuthToken(userSettings.get(0).getAuthToken())
                            .setDeviceId(userSettings.get(0).getDeviceServerId())
                            .setServerUrl(userSettings.get(0).getServiceURL())
                            .build();
                    currentFragmentHolder = new DashboardFragment();
                    fragmentLoader(currentFragmentHolder);
                } else {
                    currentFragmentHolder = new LoginFragment();
                    fragmentLoader(currentFragmentHolder);
                }
            }
        });
    }

    private void fragmentLoader(Fragment fragmentToLoad) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, fragmentToLoad)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void onFragmentEvent(FragmentEvent event) {
        switch (event) {
            case SIGN_UP_CLICK:
                currentFragmentHolder = new SignUpFragment();
                fragmentLoader(currentFragmentHolder);
                break;
            case SIGN_IN_CLICK:
                currentFragmentHolder = new LoginFragment();
                fragmentLoader(currentFragmentHolder);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentFragmentHolder = null;
    }
}
