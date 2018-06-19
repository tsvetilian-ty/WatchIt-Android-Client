package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.UserSettingsDao;

public class DeleteAllFromUsersTask extends AsyncTask<Void, Void, Void> {

    private final UserSettingsDao mUserSettingsDao;

    public DeleteAllFromUsersTask(UserSettingsDao userSettingsDao) {
        mUserSettingsDao = userSettingsDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mUserSettingsDao.deleteAll();
        return null;
    }
}
