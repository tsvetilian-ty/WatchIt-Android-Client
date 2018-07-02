package yankov.tsvetilian.watchit.Utilities;

public interface DeleteDeviceContract {
    void onDeleted(String message);

    void onFail(String message);
}
