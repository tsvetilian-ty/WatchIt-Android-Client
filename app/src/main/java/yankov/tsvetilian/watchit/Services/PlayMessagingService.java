package yankov.tsvetilian.watchit.Services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import yankov.tsvetilian.watchit.NetworkHandler;

public class PlayMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO
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
