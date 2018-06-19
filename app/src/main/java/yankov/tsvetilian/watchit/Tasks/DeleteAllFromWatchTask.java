package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.WatchDao;

public class DeleteAllFromWatchTask extends AsyncTask<Void, Void, Void> {

    private final WatchDao mWatchDao;

    public DeleteAllFromWatchTask(WatchDao watchDao) {
        mWatchDao = watchDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mWatchDao.deleteAll();
        return null;
    }
}
