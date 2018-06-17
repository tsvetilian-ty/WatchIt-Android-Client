package yankov.tsvetilian.watchit.Models.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import yankov.tsvetilian.watchit.Models.WatchModel;

@Dao
public interface WatchDao {

    @Insert
    void insert(WatchModel watch);

    @Update()
    void update(WatchModel watch);

    @Query("SELECT * FROM watch WHERE id= :id")
    LiveData<WatchModel> getWatchById(int id);

    @Query("SELECT * FROM watch")
    LiveData<List<WatchModel>> getAllFromWatch();

    @Query("SELECT * FROM watch WHERE watched=1 ORDER BY id DESC")
    LiveData<List<WatchModel>> getWatched();

    @Query("SELECT * FROM watch WHERE watch_later=1 AND watched=0 ORDER BY id DESC")
    LiveData<List<WatchModel>> getWatchLater();

    @Query("DELETE FROM watch")
    void deleteAll();

    @Query("DELETE FROM watch WHERE watched=1")
    void deleteAllWatched();

    @Query("DELETE FROM watch WHERE watch_later=1 AND watched=0")
    void deleteAllWatchLater();
}
