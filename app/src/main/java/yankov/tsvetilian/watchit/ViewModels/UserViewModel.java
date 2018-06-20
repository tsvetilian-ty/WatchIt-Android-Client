package yankov.tsvetilian.watchit.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.Repository;

public class UserViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<UserModel>> mAllSettings;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllSettings = mRepository.getAllSettings();
    }

    public LiveData<List<UserModel>> getAllSettings() {
        return mAllSettings;
    }

    public void insert(UserModel userModel) {
        mRepository.insertToSettings(userModel);
    }
}
