package yankov.tsvetilian.watchit.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import yankov.tsvetilian.watchit.Activities.PlayerActivity;
import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.NetworkHandler;
import yankov.tsvetilian.watchit.R;

public class PlayMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        createWatchObject(remoteMessage.getData());
    }

    private void createWatchObject(Map<String, String> remoteMessage) {
        String seasonCheck = null;

        if (!remoteMessage.get("season").equals("null")) {
            seasonCheck = remoteMessage.get("season");
        }

        WatchModel watch = new WatchModel(
                remoteMessage.get("name"),
                remoteMessage.get("play"),
                remoteMessage.get("playFrom"),
                remoteMessage.get("sourceName"),
                remoteMessage.get("key"),
                remoteMessage.get("path"),
                remoteMessage.get("poster"),
                remoteMessage.get("description"),
                remoteMessage.get("time"),
                false,
                false,
                seasonCheck
        );

        createChannel(watch);
    }

    private void createChannel(WatchModel watch) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("WATCHIT", "WatchIt Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        buildNotification(watch);
    }

    private void buildNotification(WatchModel watch) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Intent startPlayer = new Intent(getApplicationContext(), PlayerActivity.class);
        startPlayer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startPlayer.putExtra("watch", watch);
        PendingIntent play = PendingIntent.getActivity(this, 90, startPlayer, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent watchLater = new Intent(getApplicationContext(), PlayerActivity.class);
        watchLater.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        watchLater.putExtra("later", watch);
        PendingIntent later = PendingIntent.getActivity(this, 100, watchLater, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "WATCHIT")
                .setSmallIcon(R.drawable.ic_play)
                .setContentTitle(watch.getPresentableName())
                .setContentText(watch.getDescription())
                .setContentIntent(play)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(watch.getPresentableName())
                        .bigText(watch.getDescription())
                        .setSummaryText(watch.getSourceName()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_play, "Play", play)
                .addAction(R.drawable.ic_later, "Later", later)
                .setAutoCancel(true);

        notificationManager.notify(110, notification.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        refreshToken(s);
    }

    private void refreshToken(String s) {
        NetworkHandler networkHandler = NetworkHandler.getStaticNetworkRunner();
        if (networkHandler != null) {
            networkHandler.refreshToken(s);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
