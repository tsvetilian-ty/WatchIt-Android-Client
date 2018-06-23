package yankov.tsvetilian.watchit.Models.Network.Requests;

public class UpdateBroadcastRequest {

    private String broadcast;

    public UpdateBroadcastRequest(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }
}
