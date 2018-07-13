package main.declarations;

public enum Rank {
    COPPER_4("Copper IV"),
    COPPER_3("Copper III"),
    COPPER_2("Copper II"),
    COPPER_1("Copper I"),
    BRONZE_4("Bronze IV"),
    BRONZE_3("Bronze III"),
    BRONZE_2("Bronze II"),
    BRONZE_1("Bronze I"),
    SILVER_4("Silver IV"),
    SILVER_3("Silver III"),
    SILVER_2("Silver II"),
    SILVER_1("Silver I"),
    GOLD_4("Gold IV"),
    GOLD_3("Gold III"),
    GOLD_2("Gold II"),
    GOLD_1("Gold I"),
    PLATINUM_3("Platinum III"),
    PLATINUM_2("Platinum II"),
    PLATINUM_1("Platinum I"),
    DIAMOND("Diamond");

    private String displayName;

    Rank(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public static Rank from(int index){
        return values()[index];
    }
}
