package yankov.tsvetilian.watchit.Utilities;

public class Cache {

    private static Cache INSTANCE;
    private String deviceId;
    private String serverUrl;
    private String authToken;

    private Cache() {

    }

    public static Cache getCache() {
        if (INSTANCE == null) {
            synchronized (Cache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Cache();
                }
            }
        }
        return INSTANCE;
    }

    public static class CacheBuilder {
        private String id;
        private String url;
        private String token;

        public CacheBuilder setDeviceId(String id) {
            this.id = id;
            return this;
        }

        public CacheBuilder setServerUrl(String url) {
            this.url = url;
            return this;
        }

        public CacheBuilder setAuthToken(String token) {
            this.token = token;
            return this;
        }

        public void build() {
            INSTANCE.setDeviceId(this.id);
            INSTANCE.setServerUrl(this.url);
            INSTANCE.setAuthToken(this.token);
        }

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void clearCache() {
        setAuthToken(null);
        setDeviceId(null);
        setServerUrl(null);
    }
}
