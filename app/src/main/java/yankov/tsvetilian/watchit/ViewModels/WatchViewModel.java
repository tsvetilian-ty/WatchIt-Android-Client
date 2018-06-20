package yankov.tsvetilian.watchit.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import yankov.tsvetilian.watchit.Models.WatchModel;
import yankov.tsvetilian.watchit.Repository;

public class WatchViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<WatchModel>> allFromWatch;
    private final LiveData<List<WatchModel>> watched;
    private final LiveData<List<WatchModel>> watchLater;

    public WatchViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        allFromWatch = mRepository.getAllFromWatch();
        watched = mRepository.getWatched();
        watchLater = mRepository.getWatchLater();
    }

    public void insert(WatchModel watch) {
        mRepository.insertToWatch(watch);
    }

    public void update(WatchModel watch) {
        mRepository.updateWatch(watch);
    }

    public LiveData<WatchModel> getWatchById(int id) {
        return mRepository.getWatchById(id);
    }

    public LiveData<List<WatchModel>> getAllFromWatch() {
        return allFromWatch;
    }

    public LiveData<List<WatchModel>> getWatched() {
        return watched;
    }

    public LiveData<List<WatchModel>> getWatchLater() {
        return watchLater;
    }

    public void deleteAllWatchLater() {
        mRepository.deleteAllWatchLater();
    }

    public void deleteAllWatched() {
        mRepository.deleteAllWatched();
    }
}
