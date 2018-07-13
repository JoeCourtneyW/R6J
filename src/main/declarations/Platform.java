package main.declarations;

public enum Platform {
    UPLAY("uplay", "OSBOR_PC_LNCH_A", "5172a557-50b5-4665-b7db-e3f2e8c5041d"),
    PS4("psn", "OSBOR_PS4_LNCH_A", "05bfb3f7-6c21-4c42-be1f-97a33fb5cf66"),
    XBOX("xbl", "OSBOR_XBOXONE_LNCH_A", "98a601e5-ca91-4440-b1c5-753f601a2c90");

    private String name;
    private String url;
    private String spaceId;

    Platform(String name, String url, String spaceId) {
        this.name = name;
        this.url = url;
        this.spaceId = spaceId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public static Platform getByName(String name) {
        for(Platform platform : values()){
            if(platform.getName().equalsIgnoreCase(name))
                return platform;
        }
        return null;
    }
}
