package declarations;

public enum Gamemode {
    SECURE_AREA("securearea", "Secure Area"),
    HOSTAGE("rescuehostage", "Hostage Rescue"),
    BOMB("plantbomb", "Bomb");

    private String ubiName;
    private String displayName;

    Gamemode(String ubiName, String displayName){
        this.ubiName = ubiName;
        this.displayName = displayName;
    }

    public String getUbiName() {
        return ubiName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static Gamemode getByUbiName(String ubiName){
        for(Gamemode mode : values()){
            if(mode.getUbiName().equalsIgnoreCase(ubiName))
                return mode;
        }
        return null;
    }
}
