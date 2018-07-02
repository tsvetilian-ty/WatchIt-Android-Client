package yankov.tsvetilian.watchit.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.NetworkContracts.NetworkRequestContract;
import yankov.tsvetilian.watchit.Repository;
import yankov.tsvetilian.watchit.Utilities.SignOutContract;

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

    public void userSignIn(CharSequence watchItServer, CharSequence email, CharSequence password, NetworkRequestContract callback) {
        mRepository.signIn(watchItServer, email, password, callback);
    }

    public void userSignUp(CharSequence watchItServer, CharSequence username, CharSequence email, CharSequence password, NetworkRequestContract callback) {
        mRepository.signUp(watchItServer, username, email, password, callback);
    }

    public void userSignOut(SignOutContract callback) {
        mRepository.logout(callback);
    }
}
