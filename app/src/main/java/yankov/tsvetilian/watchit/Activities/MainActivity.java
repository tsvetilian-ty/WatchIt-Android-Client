package yankov.tsvetilian.watchit.Activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.ViewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

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
                    Log.d("WATCHIT", userSettings.get(0).getName());
                } else {
                    userSettings.add(
                            new UserModel("http", "name", "mail", "token", "server_id")
                    );
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
