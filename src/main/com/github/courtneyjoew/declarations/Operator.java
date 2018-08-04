package main.com.github.courtneyjoew.declarations;

public enum Operator {
    SMOKE("Smoke", "def", "2:1", "poisongaskill"),
    MUTE("Mute", "def", "3:1", "gadgetjammed"),
    SLEDGE("Sledge", "atk", "4:1", "hammerhole"),
    THATCHER("Thatcher", "atk", "5:1", "gadgetdestroywithemp"),

    CASTLE("Castle", "def", "2:2", "kevlarbarricadedeployed"),
    ASH("Ash", "atk", "3:2", "bonfirewallbreached"),
    PULSE("Pulse", "def", "4:2", "heartbeatspot"),
    THERMITE("Thermite", "atk", "5:2", "reinforcementbreached"),

    DOC("Doc", "def", "2:3", "teammaterevive"),
    ROOK("Rook", "def", "3:3", "armortakenteammate"),
    TWITCH("Twitch", "atk", "4:3", "gadgetdestroybyshockdrone"),
    MONTAGNE("Montagne", "atk", "5:3", "shieldblockdamage"),

    GLAZ("Glaz", "atk", "2:4", "sniperkill"),
    FUZE("Fuze", "atk", "3:4", "clusterchargekill"),
    KAPKAN("Kapkan", "def", "4:4", "boobytrapkill"),
    TACHANKA("Tachanka", "def", "5:4", "turretkill"),

    BLITZ("Blitz", "atk", "2:5", "flashedenemy"),
    IQ("IQ", "atk", "3:5", "gadgetspotbyef"),
    JAGER("Jäger", "def", "4:5", "gadgetdestroybycatcher"),
    BANDIT("Bandit", "def", "5:5", "batterykill"),

    BUCK("Buck", "atk", "2:6", "buck_kill"),
    FROST("Frost", "def", "3:6", "frost_dbno"),

    BLACKBEARD("Blackbeard", "atk", "2:7", "gunshieldblockdamage"),
    VALKYRIE("Valkyrie", "def", "3:7", "camdeployed"),

    CAPITAO("Capitão", "atk", "2:8", "lethaldartkills"),
    CAVEIRA("Caveira", "def", "3:8", "interrogations"),

    HIBANA("Hibana", "atk", "2:9", "detonate_projectile"),
    ECHO("Echo", "def", "3:9", "enemy_sonicburst_affected"),

    JACKAL("Jackal", "atk", "2:A", "cazador_assist_kill"),
    MIRA("Mira", "def", "3:A", "black_mirror_gadget_deployed"),

    YING("Ying", "atk", "2:B", "dazzler_gadget_detonate"),
    LESION("Lesion", "def", "3:B", "caltrop_enemy_affected"),

    ELA("Ela", "def", "2:C", "concussionmine_detonate"),
    ZOFIA("Zofia", "atk", "3:C", "concussiongrenade_detonate"),

    VIGIL("Vigil", "def", "2:D", "attackerdrone_diminishedrealitymode"),
    DOKKAEBI("Dokkaebi", "atk", "3:D", "phoneshacked"),

    LION("Lion", "atk", "3:E", "tagger_tagdevice_spot"),
    FINKA("Finka", "atk", "4:E", "rush_adrenalinerush"),

    MAESTRO("Maestro", "def", "2:F", "killswithturret"),
    ALIBI("Alibi", "def", "3:F", "revealedattackers");

    private String id;
    private String displayName;
    private String category;
    private String index;
    private String gadget;

    Operator(String displayName, String category, String index, String gadget) {
        this.id = name().toLowerCase();
        this.displayName = displayName;
        this.category = category;
        this.index = index;
        this.gadget = gadget;
    }


    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCategory() {
        return category;
    }

    public String getIndex() {
        return index;
    }

    public String getGadget() {
        return gadget;
    }

    public String getGadgetStatisticQuery() {
        return "operatorpvp_" + id + "_" + gadget + ":" + index; //operatorpvp_smoke_poisongaskill:2:1
    }

    public String getStatisticName(String statistic) {
        return "operatorpvp_" + statistic + ":" + index + ":infinite";
    }
}
