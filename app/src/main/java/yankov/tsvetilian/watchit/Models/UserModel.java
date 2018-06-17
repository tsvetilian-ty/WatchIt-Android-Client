package yankov.tsvetilian.watchit.Models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "user_settings")
public class UserModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String email;

    @ColumnInfo(name = "service_name")
    private String serviceURL;

    @ColumnInfo(name = "auth_token")
    private String authToken;

    @ColumnInfo(name = "device_server_id")
    @Nullable
    private String deviceServerId;


    public UserModel(String serviceURL, String name, String email, String authToken, String deviceServerId) {
        this.serviceURL = serviceURL;
        this.name = name;
        this.email = email;
        this.authToken = authToken;
        this.deviceServerId = deviceServerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getId() {
        return id;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public String getName() {
        return name;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getEmail() {
        return email;
    }

    public String getDeviceServerId() {
        return deviceServerId;
    }

    public void setDeviceServerId(String deviceServerId) {
        this.deviceServerId = deviceServerId;
    }
}
