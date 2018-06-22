package yankov.tsvetilian.watchit.NetworkContracts;

import android.support.annotation.NonNull;

public interface NetworkRequestContract {
    void onSuccess(@NonNull String statusMessage);

    void onError(@NonNull String statusMessage);
}
