package yankov.tsvetilian.watchit.NetworkContracts;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yankov.tsvetilian.watchit.Models.Network.Requests.LoginRequest;
import yankov.tsvetilian.watchit.Models.Network.Requests.SignUpRequest;
import yankov.tsvetilian.watchit.Models.Network.Responses.AuthResponse;

public interface AuthContract {

    @POST("/api/v1/users/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("/api/v1/users/signup")
    Call<AuthResponse> signup(@Body SignUpRequest request);
}
