package yankov.tsvetilian.watchit.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.ViewModels.WatchViewModel;

public class PlayerActivity extends AppCompatActivity {

    private WatchViewModel watchViewModel;
    private SimpleExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        watchViewModel = ViewModelProviders.of(this).get(WatchViewModel.class);
        playerView = findViewById(R.id.player);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationManagerCompat.from(this).cancel(110);

        Intent playerData = getIntent();
        if (playerData.getExtras() != null && playerData.hasExtra("id")) {
            watchViewModel.getWatchById(playerData.getExtras().getInt("id")).observe(this, new Observer<WatchModel>() {
                @Override
                public void onChanged(@Nullable WatchModel watch) {
                    if (watch != null) {
                        watch.setWatched(true);
                        watchViewModel.update(watch);
                        playInit(watch);
                    }
                }
            });
        } else if (playerData.getExtras() != null && playerData.hasExtra("watch")) {
            WatchModel watch = playerData.getExtras().getParcelable("watch");
            watch.setWatched(true);
            watchViewModel.insert(watch);
            playInit(watch);
        } else if (playerData.getExtras() != null && playerData.hasExtra("later")) {
            WatchModel watch = playerData.getExtras().getParcelable("later");
            watch.setWatchLater(true);
            watchViewModel.insert(watch);
            finish();
        }
    }

    private void playInit(WatchModel watch) {
        player = ExoPlayerFactory.newSimpleInstance(PlayerActivity.this, new DefaultTrackSelector());
        TextView videoTitle = playerView.findViewById(R.id.play_title);
        videoTitle.setText(watch.getPresentableName());

        TextView videoFrom = playerView.findViewById(R.id.play_from);
        videoFrom.setText(String.format("Stream from: %s", watch.getSourceName()));

        TextView videoSeason = playerView.findViewById(R.id.play_season_episode);

        if (watch.getSeason() != null) {
            videoSeason.setText(watch.getSeason());
        } else {
            videoSeason.setVisibility(View.GONE);
        }

        TextView videoDescription = playerView.findViewById(R.id.play_description);
        videoDescription.setText(watch.getDescription());

        Picasso.get().load(watch.getPoster()).fit().into(((ImageView) playerView.findViewById(R.id.play_poster)));

        playerView.setPlayer(player);

        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(PlayerActivity.this, "WatchIT"), null);
        dataSourceFactory.createDataSource().setRequestProperty("Authorization", String.format("Bearer %s", watch.getAuthKey()));

        Uri url = Uri.parse(String.format("http://%s:3090/watch%s/%s", watch.getSourceIp(), watch.getPath(), watch.getFileName()));

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(url);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
