package yankov.tsvetilian.watchit.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.ViewModels.WatchViewModel;

public class PlayerActivity extends AppCompatActivity {

    private WatchViewModel watchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        watchViewModel = ViewModelProviders.of(this).get(WatchViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent playerData = getIntent();
        if (playerData.getExtras() != null && playerData.hasExtra("id")) {
            watchViewModel.getWatchById(playerData.getExtras().getInt("id")).observe(this, new Observer<WatchModel>() {
                @Override
                public void onChanged(@Nullable WatchModel watch) {
                    if (watch != null) {
                        watch.setWatched(true);
                        watchViewModel.update(watch);
                        Log.d("WATCHIT", "FROM ACTIVITY");
                    }
                }
            });
        } else if (playerData.getExtras() != null && playerData.hasExtra("watch")) {
            WatchModel watch = playerData.getExtras().getParcelable("watch");
            watch.setWatched(true);
            watchViewModel.insert(watch);
            Log.d("WATCHIT", "FROM SERVICE");
        } else if (playerData.getExtras() != null && playerData.hasExtra("later")) {
            WatchModel watch = playerData.getExtras().getParcelable("later");
            watch.setWatchLater(true);
            watchViewModel.insert(watch);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
