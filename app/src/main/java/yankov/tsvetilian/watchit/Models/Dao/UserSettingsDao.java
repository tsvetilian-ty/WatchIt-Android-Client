package yankov.tsvetilian.watchit.Models.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import yankov.tsvetilian.watchit.Models.UserModel;

@Dao
public interface UserSettingsDao {

    @Insert
    void insert(UserModel userModel);

    @Query("DELETE FROM user_settings")
    void deleteAll();

    @Query("SELECT * FROM user_settings")
    LiveData<List<UserModel>> getAllSettings();
}
