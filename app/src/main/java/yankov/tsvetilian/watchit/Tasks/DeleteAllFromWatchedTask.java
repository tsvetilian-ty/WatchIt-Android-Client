package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.WatchDao;

public class DeleteAllFromWatchedTask extends AsyncTask<Void, Void, Void> {
    private final WatchDao watchDao;

    public DeleteAllFromWatchedTask(WatchDao watchDao) {
        this.watchDao = watchDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        watchDao.deleteAllWatched();
        return null;
    }
}
