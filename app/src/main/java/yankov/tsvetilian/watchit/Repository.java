package yankov.tsvetilian.watchit;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import yankov.tsvetilian.watchit.Models.Dao.UserSettingsDao;
import yankov.tsvetilian.watchit.Models.Dao.WatchDao;
import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.NetworkContracts.NetworkRequestContract;
import yankov.tsvetilian.watchit.Tasks.DeleteAllFromUsersTask;
import yankov.tsvetilian.watchit.Tasks.DeleteAllFromWatchLaterTask;
import yankov.tsvetilian.watchit.Tasks.DeleteAllFromWatchTask;
import yankov.tsvetilian.watchit.Tasks.DeleteAllFromWatchedTask;
import yankov.tsvetilian.watchit.Tasks.InsertToSettingsTask;
import yankov.tsvetilian.watchit.Tasks.InsertToWatchTask;
import yankov.tsvetilian.watchit.Tasks.UpdateWatchTask;
import yankov.tsvetilian.watchit.Utilities.Cache;
import yankov.tsvetilian.watchit.Utilities.DeleteDeviceContract;
import yankov.tsvetilian.watchit.Utilities.SignOutContract;

public class Repository {

    private final UserSettingsDao mUserSettings;
    private final LiveData<List<UserModel>> mAllSettings;
    private final WatchDao mWatch;
    private final LiveData<List<WatchModel>> mAllWatch;
    private final LiveData<List<WatchModel>> mWatchLater;
    private final LiveData<List<WatchModel>> mWatched;
    private final NetworkHandler networkHandler;
    private final Cache cache;

    public Repository(Application application) {
        WatchItDatabase db = WatchItDatabase.getDatabaseInstance(application);
        mUserSettings = db.userSettings();
        mWatch = db.watch();

        mAllSettings = mUserSettings.getAllSettings();
        mAllWatch = mWatch.getAllFromWatch();
        mWatchLater = mWatch.getWatchLater();
        mWatched = mWatch.getWatched();

        cache = Cache.getCache();
        networkHandler = NetworkHandler.getNetworkHandler(this);
    }

    public void insertToSettings(UserModel settings) {
        cache.setAuthToken(settings.getAuthToken());
        cache.setServerUrl(settings.getServiceURL());
        new InsertToSettingsTask(mUserSettings).execute(settings);
    }

    public void insertToWatch(WatchModel watch) {
        new InsertToWatchTask(mWatch).execute(watch);
    }

    public void updateWatch(WatchModel watch) {
        new UpdateWatchTask(mWatch).execute(watch);
    }

    public void logout(final SignOutContract callback) {
        networkHandler.deleteDeviceFromServer(new DeleteDeviceContract() {
            @Override
            public void onDeleted(String message) {
                deleteAllUsers();
                deleteAllFromWatch();
                cache.clearCache();
                callback.onSignOut("You sign out!");
            }

            @Override
            public void onFail(String message) {
                Log.d("WATCHIT_NETWORK", message);
                callback.onSignOut("Something went wrong!");
            }
        });
    }

    public LiveData<WatchModel> getWatchById(int id) {
        return mWatch.getWatchById(id);
    }

    public LiveData<List<WatchModel>> getAllFromWatch() {
        return mAllWatch;
    }

    public LiveData<List<UserModel>> getAllSettings() {
        return mAllSettings;
    }

    public LiveData<List<WatchModel>> getWatchLater() {
        return mWatchLater;
    }

    public LiveData<List<WatchModel>> getWatched() {
        return mWatched;
    }

    public void deleteAllWatchLater() {
        new DeleteAllFromWatchLaterTask(mWatch).execute();
    }

    public void deleteAllWatched() {
        new DeleteAllFromWatchedTask(mWatch).execute();
    }

    private void deleteAllUsers() {
        new DeleteAllFromUsersTask(mUserSettings).execute();
    }

    private void deleteAllFromWatch() {
        new DeleteAllFromWatchTask(mWatch).execute();
    }


    // Network calls

    public void signIn(CharSequence watchItServer, CharSequence email, CharSequence password, final NetworkRequestContract callback) {
        networkHandler.signIn(watchItServer, email, password, callback);
    }

    public void signUp(CharSequence watchItServer, CharSequence username, CharSequence email, CharSequence password, final NetworkRequestContract callback) {
        networkHandler.signUp(watchItServer, username, email, password, callback);
    }

}
