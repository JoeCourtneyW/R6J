package main.com.github.joecourtneyw.declarations;

public enum Rank {

    UNRANKED("Unranked", "https://i.imgur.com/sB11BIz.png"),
    COPPER_4("Copper IV", "https://i.imgur.com/ehILQ3i.jpg"),
    COPPER_3("Copper III", "https://i.imgur.com/6CxJoMn.jpg"),
    COPPER_2("Copper II", "https://i.imgur.com/0J0jSWB.jpg"),
    COPPER_1("Copper I", "https://i.imgur.com/0J0jSWB.jpg"),
    BRONZE_4("Bronze IV", "https://i.imgur.com/42AC7RD.jpg"),
    BRONZE_3("Bronze III", "https://i.imgur.com/QD5LYD7.jpg"),
    BRONZE_2("Bronze II", "https://i.imgur.com/9AORiNm.jpg"),
    BRONZE_1("Bronze I", "https://i.imgur.com/hmPhPBj.jpg"),
    SILVER_4("Silver IV", "https://i.imgur.com/D36ZfuR.jpg"),
    SILVER_3("Silver III", "https://i.imgur.com/m8GToyF.jpg"),
    SILVER_2("Silver II", "https://i.imgur.com/EswGcx1.jpg"),
    SILVER_1("Silver I", "https://i.imgur.com/KmFpkNc.jpg"),
    GOLD_4("Gold IV", "https://i.imgur.com/6Qg6aaH.jpg"),
    GOLD_3("Gold III", "https://i.imgur.com/B0s1o1h.jpg"),
    GOLD_2("Gold II", "https://i.imgur.com/ELbGMc7.jpg"),
    GOLD_1("Gold I", "https://i.imgur.com/ffDmiPk.jpg"),
    PLATINUM_3("Platinum III", "https://i.imgur.com/Sv3PQQE.jpg"),
    PLATINUM_2("Platinum II", "https://i.imgur.com/Uq3WhzZ.jpg"),
    PLATINUM_1("Platinum I", "https://i.imgur.com/xx03Pc5.jpg"),
    DIAMOND("Diamond", "https://i.imgur.com/nODE0QI.jpg");

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
