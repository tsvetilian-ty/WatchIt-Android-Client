package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.WatchDao;

public class DeleteAllFromWatchLaterTask extends AsyncTask<Void, Void, Void> {

    private final WatchDao watchDao;

    public DeleteAllFromWatchLaterTask(WatchDao watchDao) {
        this.watchDao = watchDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        watchDao.deleteAllWatchLater();
        return null;
    }
}
