package yankov.tsvetilian.watchit.Models.Network.Requests;

public class DeviceRegistrationRequest {
    private String name;
    private String type;
    private String broadcast;

    public DeviceRegistrationRequest(String name, String type, String broadcast) {
        this.name = name;
        this.type = type;
        this.broadcast = broadcast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }
}