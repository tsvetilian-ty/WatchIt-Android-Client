package yankov.tsvetilian.watchit;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import yankov.tsvetilian.watchit.Models.Dao.UserSettingsDao;
import yankov.tsvetilian.watchit.Models.Dao.WatchDao;
import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.Models.WatchModel;

@Database(entities = {UserModel.class, WatchModel.class}, version = 1)
public abstract class WatchItDatabase extends RoomDatabase {

    public abstract UserSettingsDao userSettings();

    public abstract WatchDao watch();

    private static WatchItDatabase INSTANCE;

    public static WatchItDatabase getInMemoryDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WatchItDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                            WatchItDatabase.class)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static WatchItDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (WatchItDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WatchItDatabase.class, "watch_it_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
