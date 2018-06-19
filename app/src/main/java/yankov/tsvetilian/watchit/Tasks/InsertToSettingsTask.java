package yankov.tsvetilian.watchit.Tasks;

import android.os.AsyncTask;

import yankov.tsvetilian.watchit.Models.Dao.UserSettingsDao;
import yankov.tsvetilian.watchit.Models.UserModel;

public class InsertToSettingsTask extends AsyncTask<UserModel, Void, Void> {

    private UserSettingsDao mUserSettings;

    public InsertToSettingsTask(UserSettingsDao userSettingsDao) {
        this.mUserSettings = userSettingsDao;
    }

    @Override
    protected Void doInBackground(UserModel... settings) {
        mUserSettings.insert(settings[0]);
        return null;
    }

}
