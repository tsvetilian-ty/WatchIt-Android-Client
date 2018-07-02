package yankov.tsvetilian.watchit;

import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yankov.tsvetilian.watchit.Models.Network.Requests.DeviceRegistrationRequest;
import yankov.tsvetilian.watchit.Models.Network.Requests.LoginRequest;
import yankov.tsvetilian.watchit.Models.Network.Requests.SignUpRequest;
import yankov.tsvetilian.watchit.Models.Network.Requests.UpdateBroadcastRequest;
import yankov.tsvetilian.watchit.Models.Network.Responses.AuthResponse;
import yankov.tsvetilian.watchit.Models.Network.Responses.DeviceRegistrationResponse;
import yankov.tsvetilian.watchit.Models.UserModel;
import yankov.tsvetilian.watchit.NetworkContracts.AuthContract;
import yankov.tsvetilian.watchit.NetworkContracts.DeviceContract;
import yankov.tsvetilian.watchit.NetworkContracts.NetworkRequestContract;
import yankov.tsvetilian.watchit.Utilities.Cache;
import yankov.tsvetilian.watchit.Utilities.DeleteDeviceContract;
import yankov.tsvetilian.watchit.Utilities.RetrofitClient;

public class NetworkHandler {

    private static NetworkHandler INSTANCE;
    private final Cache cache;
    private Repository repository;

    public static NetworkHandler getStaticNetworkRunner() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        return null;
    }

    public static NetworkHandler getNetworkHandler(Repository repo) {
        if (INSTANCE == null) {
            synchronized (NetworkHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetworkHandler(repo);
                }
            }
        }
        return INSTANCE;
    }

    private NetworkHandler(Repository repo) {
        repository = repo;
        cache = Cache.getCache();
    }

    public void signIn(CharSequence watchItServer, CharSequence email, CharSequence password, final NetworkRequestContract callback) {
        final String requestServer = watchItServer.toString();
        String requestEmail = email.toString();
        String requestPassword = password.toString();

        LoginRequest request = new LoginRequest(requestEmail, requestPassword);

        AuthContract retrofitClient = RetrofitClient.getClient(requestServer).create(AuthContract.class);

        Call<AuthResponse> response = retrofitClient.login(request);

        response.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, final Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    final UserModel newUser = new UserModel(
                            requestServer,
                            response.body().getUsername(),
                            response.body().getEmail(),
                            response.body().getToken(),
                            null);

                    registerDevice(requestServer, response.body().getToken(), new NetworkRequestContract() {
                        @Override
                        public void onSuccess(@NonNull String statusMessage) {
                            newUser.setDeviceServerId(statusMessage);
                            repository.insertToSettings(newUser);
                            callback.onSuccess(String.format("Welcome back %s", response.body().getUsername()));
                        }

                        @Override
                        public void onError(@NonNull String statusMessage) {
                            callback.onError("Unsuccessful login check your input!");
                        }
                    });
                } else {
                    callback.onError("Unsuccessful login check your input!");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Unsuccessful login!");
            }
        });
    }

    public void signUp(CharSequence watchItServer, CharSequence username, CharSequence email, CharSequence password, final NetworkRequestContract callback) {
        final String requestServer = watchItServer.toString();
        String requestUserName = username.toString();
        String requestEmail = email.toString();
        String requestPassword = password.toString();

        SignUpRequest request = new SignUpRequest(requestUserName, requestEmail, requestPassword);

        AuthContract retrofitClient = RetrofitClient.getClient(requestServer).create(AuthContract.class);

        Call<AuthResponse> response = retrofitClient.signup(request);

        response.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, final Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    final UserModel newUser = new UserModel(
                            requestServer,
                            response.body().getUsername(),
                            response.body().getEmail(),
                            response.body().getToken(),
                            null);

                    registerDevice(requestServer, response.body().getToken(), new NetworkRequestContract() {
                        @Override
                        public void onSuccess(@NonNull String statusMessage) {
                            newUser.setDeviceServerId(statusMessage);
                            repository.insertToSettings(newUser);
                            callback.onSuccess(String.format("Welcome %s", response.body().getUsername()));
                        }

                        @Override
                        public void onError(@NonNull String statusMessage) {
                            callback.onError("Unsuccessful registration check your input!");
                        }
                    });
                } else {
                    callback.onError("Unsuccessful registration check your input!");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError("Unsuccessful registration!");
            }
        });

    }

    private void registerDevice(final String server, final String authToken, final NetworkRequestContract callback) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String deviceBroadCastToken = task.getResult().getToken();

                        registerDevice(server, deviceBroadCastToken, authToken, callback);
                    }
                });
    }

    private void registerDevice(String server, String broadcastToken, String authToken, final NetworkRequestContract callback) {
        String deviceName = String.format("%s %s", Build.MANUFACTURER, Build.MODEL);

        DeviceRegistrationRequest request = new DeviceRegistrationRequest(deviceName, "Android " + Build.VERSION.SDK_INT, broadcastToken);

        DeviceContract retrofitClient = RetrofitClient.getClient(server).create(DeviceContract.class);

        Call<DeviceRegistrationResponse> response = retrofitClient.registerDevice("Bearer " + authToken, request);

        cache.setServerUrl(server);
        cache.setAuthToken(authToken);

        response.enqueue(new Callback<DeviceRegistrationResponse>() {
            @Override
            public void onResponse(Call<DeviceRegistrationResponse> call, Response<DeviceRegistrationResponse> response) {
                if (response.isSuccessful()) {
                    cache.setDeviceId(response.body().getId());
                    callback.onSuccess(response.body().getId());
                } else {
                    callback.onError(null);
                }
            }

            @Override
            public void onFailure(Call<DeviceRegistrationResponse> call, Throwable t) {
                callback.onError(null);
            }
        });
    }

    public void deleteDeviceFromServer(final DeleteDeviceContract callback) {
        if (cache.getDeviceId() != null && cache.getAuthToken() != null && cache.getServerUrl() != null) {
            DeviceContract retrofitClient = RetrofitClient.getClient(cache.getServerUrl()).create(DeviceContract.class);

            Call<ResponseBody> response = retrofitClient.deleteDevice("Bearer " + cache.getAuthToken(), cache.getDeviceId());

            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        callback.onDeleted(null);
                    }
                    callback.onFail(null);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onFail(null);
                }
            });
        }
    }
}
