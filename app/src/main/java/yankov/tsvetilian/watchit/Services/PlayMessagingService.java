package yankov.tsvetilian.watchit.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PlayMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("WATCHIT", remoteMessage.getData().get("token"));
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("WATCHIT", s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
