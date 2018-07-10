package main;

import declarations.Operator;
import declarations.Platform;

public class R6J {

    public static final String VERSION = "V1.0";
    private Auth authenticator;

    public R6J(Auth authPackage) {
        this.authenticator = authPackage;
        loadOperatorDefs();
    }


    public R6Player getPlayer(String playerName, Platform platform){
        return R6Player.getPlayer(this, playerName, platform);
    }

    Auth getAuthenticator(){
        return authenticator;
    }

    private void loadOperatorDefs() {
        Operator.OPERATOR_DEFS = getAuthenticator().get("https://ubistatic-a.akamaihd.net/0058/prod/assets/data/operators.3a2655c8.json");
    }
}
