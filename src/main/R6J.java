package main;

import com.fasterxml.jackson.databind.JsonNode;
import main.declarations.Platform;
import main.declarations.Region;

public class R6J {


    private Auth authenticator;

    static JsonNode OPERATOR_DEFS;

    public R6J(Auth authPackage) {
        this.authenticator = authPackage;
        loadOperatorDefs();
    }


    //TODO: Write some documentation for this class, as it is the main api class
    public R6Player getPlayerByName(String playerName, Platform platform){
        return getPlayerByName(playerName, platform, Region.NA);
    }

    public R6Player getPlayerByName(String playerName, Platform platform, Region region){
        return R6Player.getPlayer(this, playerName, platform, region);
    }

    public R6Player getPlayerById(String playerId, Platform platform){
        return getPlayerById(playerId, platform, Region.NA);
    }

    public R6Player getPlayerById(String playerId, Platform platform, Region region){
        return R6Player.getPlayerById(this, playerId, platform);
    }

    public boolean playerExists(String playerName, Platform platform){
        try {
            R6Player.getPlayer(this, playerName, platform);
        } catch(Exception e){
            return false;
        }
        return true;
    }

    Auth getAuthenticator(){
        return authenticator;
    }

    private void loadOperatorDefs() {
        OPERATOR_DEFS = getAuthenticator().get("https://ubistatic-a.akamaihd.net/0058/prod/assets/data/operators.3a2655c8.json");
    }

    public static final String[] OPERATOR_NAMES = {
            "ASH", "ALIBI", "BANDIT", "BUCK", "BLACKBEARD",
            "BLITZ", "CASTLE", "CÃPITAO", "CAVEIRA",
            "DOC", "DOKKAEBI", "ECHO", "ELA", "FINKA",
            "FROST", "FUZE", "GLAZ", "HIBANA", "IQ",
            "JACKAL", "JÄGER", "KAPKAN", "LESION",
            "LION", "MAESTRO", "MIRA", "MONTAGNE",
            "MUTE", "PULSE", "ROOK", "SLEDGE", "SMOKE",
            "TACHANKA", "THATCHER", "THERMITE", "TWITCH",
            "VALKYRIE", "VIGIL", "YING", "ZOFIA"
    };

}
