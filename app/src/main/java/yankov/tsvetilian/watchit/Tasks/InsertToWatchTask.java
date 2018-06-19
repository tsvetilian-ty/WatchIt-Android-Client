package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.WatchDao;
import yankov.tsvetilian.watchit.Models.WatchModel;

public class InsertToWatchTask extends AsyncTask<WatchModel, Void, Void> {

    private final WatchDao watchDao;

    public InsertToWatchTask(WatchDao watchDao) {
        this.watchDao = watchDao;
    }

    @Override
    protected Void doInBackground(WatchModel... watches) {
        watchDao.insert(watches[0]);
        return null;
    }
}
