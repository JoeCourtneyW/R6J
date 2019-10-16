package main.com.github.joecourtneyw.declarations;

public enum Rank {

    UNRANKED("Unranked", "https://i.imgur.com/sB11BIz.png"),
    COPPER_5("Copper V", "https://i.imgur.com/B8NCTyX.png"),
    COPPER_4("Copper IV", "https://i.imgur.com/ehILQ3i.jpg"),
    COPPER_3("Copper III", "https://i.imgur.com/6CxJoMn.jpg"),
    COPPER_2("Copper II", "https://i.imgur.com/eI11lah.jpg"),
    COPPER_1("Copper I", "https://i.imgur.com/0J0jSWB.jpg"),
    BRONZE_5("Bronze V", "https://i.imgur.com/TIWCRyO.png"),
    BRONZE_4("Bronze IV", "https://i.imgur.com/42AC7RD.jpg"),
    BRONZE_3("Bronze III", "https://i.imgur.com/QD5LYD7.jpg"),
    BRONZE_2("Bronze II", "https://i.imgur.com/9AORiNm.jpg"),
    BRONZE_1("Bronze I", "https://i.imgur.com/hmPhPBj.jpg"),
    SILVER_5("Silver V", "https://i.imgur.com/PY2p17k.png"),
    SILVER_4("Silver IV", "https://i.imgur.com/D36ZfuR.jpg"),
    SILVER_3("Silver III", "https://i.imgur.com/m8GToyF.jpg"),
    SILVER_2("Silver II", "https://i.imgur.com/EswGcx1.jpg"),
    SILVER_1("Silver I", "https://i.imgur.com/KmFpkNc.jpg"),
    GOLD_3("Gold III", "https://i.imgur.com/B0s1o1h.jpg"),
    GOLD_2("Gold II", "https://i.imgur.com/ELbGMc7.jpg"),
    GOLD_1("Gold I", "https://i.imgur.com/ffDmiPk.jpg"),
    PLATINUM_3("Platinum III", "https://i.imgur.com/tmcWQ6I.png"),
    PLATINUM_2("Platinum II", "https://i.imgur.com/CYMO3Er.png"),
    PLATINUM_1("Platinum I", "https://i.imgur.com/qDYwmah.png"),
    DIAMOND("Diamond", "https://i.imgur.com/37tSxXm.png"),
    CHAMPIONS("Champions", "https://i.imgur.com/VlnwLGk.png");

    private String displayName;
    private String iconUrl;

    Rank(String displayName, String iconUrl) {
        this.displayName = displayName;
        this.iconUrl = iconUrl;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public static Rank from(int index) {
        return values()[index];
    }
}
