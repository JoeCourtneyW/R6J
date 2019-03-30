package main.com.github.joecourtneyw;

import main.com.github.joecourtneyw.declarations.Platform;
import main.com.github.joecourtneyw.declarations.Region;
import org.pmw.tinylog.Configuration;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class R6J {


    private Auth authenticator;


    public R6J(Auth authPackage) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        Configurator.defaultConfig()
                .writer(new ConsoleWriter(), Level.DEBUG, "[{date:HH:mm:ss}] {class_name}.{method}() [{level}]: {message}")
                .addWriter(new FileWriter("logs" + File.separator + dateFormat.format(date) + ".txt"), Level.INFO, "[{date:HH:mm:ss}] {class_name}.{method}() [{level}]: {message}")
                .activate();
        this.authenticator = authPackage;

    }


    /**
     * Creates an R6Player object based on the given user information, defaulting to NA region
     *
     * @param playerName The user's current name
     * @param platform   The user's platform
     * @return An R6Player object containing all the data on the player
     */
    public R6Player getPlayerByName(String playerName, Platform platform) {
        return getPlayerByName(playerName, platform, Region.NA);
    }

    /**
     * Creates an R6Player object based on the given user information
     *
     * @param playerName The user's current name
     * @param platform   The user's platform
     * @param region     The user's primary region
     * @return An R6Player object containing all the data on the player
     */
    public R6Player getPlayerByName(String playerName, Platform platform, Region region) {
        return R6Player.getPlayer(this, playerName, platform, region);
    }

    /**
     * Creates an R6Player object based on the given user information, defaulting to NA region
     *
     * @param playerId The user's profile_id
     * @param platform The user's platform
     * @return An R6Player object containing all the data on the player
     */
    public R6Player getPlayerById(String playerId, Platform platform) {
        return getPlayerById(playerId, platform, Region.NA);
    }

    /**
     * Creates an R6Player object based on the given user information
     *
     * @param playerId The user's profile_id
     * @param platform The user's platform
     * @param region   The user's primary region
     * @return An R6Player object containing all the data on the player
     */
    public R6Player getPlayerById(String playerId, Platform platform, Region region) {
        return R6Player.getPlayerById(this, playerId, platform);
    }

    /**
     * Determines if the player exists on Ubisoft servers
     *
     * @param playerName The user's current name
     * @param platform   The user's platform
     * @return Does the player exist?
     */
    public boolean playerExists(String playerName, Platform platform) {
        return R6Player.playerExists(this, playerName, platform);
    }

    /**
     * Determines if the player exists on Ubisoft servers
     *
     * @param playerId The user's profile_id
     * @param platform The user's platform
     * @return Does the player exist?
     */
    public boolean playerIdExists(String playerId, Platform platform) {
        return R6Player.playerExists(this, playerId, platform);
    }

    /**
     * @return The Auth object used to create the R6J object
     */
    Auth getAuthenticator() {
        return authenticator;
    }

}
