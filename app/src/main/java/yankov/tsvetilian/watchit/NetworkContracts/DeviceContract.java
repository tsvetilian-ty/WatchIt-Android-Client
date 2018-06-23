package yankov.tsvetilian.watchit.NetworkContracts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import yankov.tsvetilian.watchit.Models.Network.Requests.DeviceRegistrationRequest;
import yankov.tsvetilian.watchit.Models.Network.Requests.UpdateBroadcastRequest;
import yankov.tsvetilian.watchit.Models.Network.Responses.DeviceRegistrationResponse;

public interface DeviceContract {

    @POST("/api/v1/devices")
    Call<DeviceRegistrationResponse> registerDevice(@Header("Authorization") String authToken, @Body DeviceRegistrationRequest request);

    @DELETE("/api/v1/devices/{id}")
    Call<ResponseBody> deleteDevice(@Header("Authorization") String authToken, @Path("id") String deviceId);

    @PATCH("/api/v1/devices/{id}")
    Call<ResponseBody> updateBroadcaster(@Header("Authorization") String authToken, @Path("id") String deviceId, @Body UpdateBroadcastRequest request);
}
