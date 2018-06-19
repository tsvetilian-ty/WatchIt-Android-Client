package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.WatchDao;
import yankov.tsvetilian.watchit.Models.WatchModel;

public class UpdateWatchTask extends AsyncTask<WatchModel, Void, Void> {

    private final WatchDao watch;

    public UpdateWatchTask(WatchDao watchDao) {
        watch = watchDao;
    }

    @Override
    protected Void doInBackground(WatchModel... watches) {
        watch.update(watches[0]);
        return null;
    }
}
