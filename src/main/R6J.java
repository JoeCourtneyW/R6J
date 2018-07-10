package main;

import com.fasterxml.jackson.databind.JsonNode;
import declarations.Platform;

public class R6J {


    private Auth authenticator;

    public static JsonNode OPERATOR_DEFS;

    public R6J(Auth authPackage) {
        this.authenticator = authPackage;
        loadOperatorDefs();
    }


    public R6Player getPlayer(String playerName, Platform platform){
        return R6Player.getPlayer(this, playerName, platform);
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
