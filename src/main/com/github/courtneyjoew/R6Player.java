package main.com.github.courtneyjoew;

import com.fasterxml.jackson.databind.JsonNode;
import main.com.github.courtneyjoew.declarations.*;
import main.com.github.courtneyjoew.stats.GamemodeStats;
import main.com.github.courtneyjoew.stats.OperatorStats;
import main.com.github.courtneyjoew.stats.WeaponStats;

import java.util.*;

public class R6Player {

    private R6J api;

    private String profileId;
    private String userId;
    private String idOnPlatform;
    private String nameOnPlatform;
    private Platform platform;
    private Region region;

    private int level;
    private int lootbox_probability;

    private int abandons;
    private int ranked_wins;
    private int ranked_losses;
    private int rank;
    private int max_rank;
    private double mmr;
    private double max_mmr;
    private double skill;
    private double skill_stdev;

    private HashMap<Operator, OperatorStats> operators;
    private OperatorStats[] sortedOperators;

    private HashMap<Gamemode, GamemodeStats> gamemodes;
    private GamemodeStats[] sortedGamemodes;

    private HashMap<Weapon, WeaponStats> weapons;
    private WeaponStats[] sortedWeapons;

    private int kills;
    private int deaths;
    private int wins;
    private int losses;
    private int ranked_kills;
    private int ranked_deaths;
    private int total_ranked_wins;
    private int total_ranked_losses;
    private int penetration_kills;
    private int bullets_hit;
    private int melee_kills;
    private int bullets_fired;
    private int matches_played;
    private int assists;
    private int time_played;
    private int revives;
    private int headshots;
    private int dbno_assists;
    private int suicides;
    private int barricades_deployed;
    private int reinforcements_deployed;
    private int xp;
    private int rappel_breaches;
    private int distance_travelled;
    private int revives_denied;
    private int dbnos;
    private int gadgets_destroyed;
    private int blind_kills;

    private R6Player(R6J api, String profileId, String userId, String idOnPlatform, String nameOnPlatform, Platform platform, Region region) {
        this.api = api;
        this.profileId = profileId;
        this.userId = userId;
        this.idOnPlatform = idOnPlatform;
        this.nameOnPlatform = nameOnPlatform;
        this.platform = platform;
        this.region = region;

        loadStats();
    }


    /**
     * Loads the player's level, total xp, and their lootbox probability
     */
    private void loadXpStats() {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                        + platform.getUrl() + "/r6playerprofile/playerprofile/progressions",
                "profile_ids", profileId);

        JsonNode player = response.get("player_profiles").get(0);

        this.level = player.get("level").asInt(0);
        this.xp = player.get("xp").asInt(0);
        this.lootbox_probability = player.get("lootbox_probability").asInt(0);
    }

    /**
     * Load's the player's stats involved with their rank: Abandons, Ranked Wins,
     * Ranked Losses, Rank, Max Rank, Skill, and Skill Standard Deviation
     * <p>
     * Use only if you wish to load a season other than the current season, overrides old ranked data
     *
     * @param season The season that you want the player's main.stats from, use -1 if not sure
     */
    public void loadRankedStats(int season) {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                        + platform.getUrl() + "/r6karma/players",
                "board_id", "pvp_ranked",
                "profile_ids", profileId,
                "region_id", region.getName(),
                "season_id", season + "");
        JsonNode player = response.get("players").get(profileId);
        this.abandons = player.get("abandons").asInt(0);
        this.ranked_wins = player.get("wins").asInt(0);
        this.ranked_losses = player.get("losses").asInt(0);
        this.rank = player.get("rank").asInt(0);
        this.max_rank = player.get("max_rank").asInt(0);
        this.mmr = player.get("mmr").asDouble();
        this.max_mmr = player.get("max_mmr").asDouble();
        this.skill = player.get("skill_mean").asDouble();
        this.skill_stdev = player.get("skill_stdev").asDouble();

    }

    /**
     * Load's the player's stats involved with their rank: Abandons, Ranked Wins,
     * Ranked Losses, Rank, Max Rank, Skill, and Skill Standard Deviation
     */
    private void loadRankedStats() {
        loadRankedStats(-1);
    }

    /**
     * Loads the player's general stats
     *
     * @param generalStats The global stats node fetched on creation
     */
    private void loadGeneralStats(JsonNode generalStats) {
        String stat = "generalpvp_";
        this.deaths = getStat(generalStats, stat + "death" + ":infinite");
        this.kills = getStat(generalStats, stat + "kills" + ":infinite");
        this.wins = getStat(generalStats, stat + "matchwon" + ":infinite");
        this.losses = getStat(generalStats, stat + "matchlost" + ":infinite");

        this.ranked_deaths = getStat(generalStats, "rankedpvp_death" + ":infinite");
        this.ranked_kills = getStat(generalStats, "rankedpvp_kills" + ":infinite");
        this.ranked_wins = getStat(generalStats, "rankedpvp_matchwon" + ":infinite");
        this.ranked_losses = getStat(generalStats, "rankedpvp_matchlost" + ":infinite");

        this.penetration_kills = getStat(generalStats, stat + "penetrationkills" + ":infinite");
        this.melee_kills = getStat(generalStats, stat + "meleekills" + ":infinite");
        this.bullets_fired = getStat(generalStats,
                stat + "bulletfired" + ":infinite"); //Stat does not exist on ubi site
        this.bullets_hit = getStat(generalStats, stat + "bullethit" + ":infinite");
        this.matches_played = getStat(generalStats, stat + "matchplayed" + ":infinite");
        this.assists = getStat(generalStats, stat + "killassists" + ":infinite");
        this.time_played = getStat(generalStats, stat + "timeplayed" + ":infinite");
        this.revives = getStat(generalStats, stat + "revive" + ":infinite");
        this.headshots = getStat(generalStats, stat + "headshot" + ":infinite");
        this.dbno_assists = getStat(generalStats, stat + "dbnoassists" + ":infinite");
        this.suicides = getStat(generalStats, stat + "suicide" + ":infinite");
        this.barricades_deployed = getStat(generalStats, stat + "barricadedeployed" + ":infinite");
        this.reinforcements_deployed = getStat(generalStats, stat + "reinforcementdeploy" + ":infinite");
        this.rappel_breaches = getStat(generalStats, stat + "rappelbreach" + ":infinite");
        this.distance_travelled = getStat(generalStats, stat + "distancetravelled" + ":infinite");
        this.revives_denied = getStat(generalStats,
                stat + "revivedenied" + ":infinite"); //Stat does not exist on ubi site
        this.dbnos = getStat(generalStats, stat + "dbno" + ":infinite");
        this.gadgets_destroyed = getStat(generalStats, stat + "gadgetdestroy" + ":infinite");
        this.blind_kills = getStat(generalStats, stat + "blindkills" + ":infinite");
    }

    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    /**
     * Loads the player's operator statistics into the operators map
     *
     * @param generalStats The global stats node fetched on creation
     */
    private void loadOperatorStats(JsonNode generalStats) {
        operators = new HashMap<>(); // A map with operator names as keys, and the players OperatorStats object as the value
        for (Operator operator : Operator.values()) { //Loop through every operators definition
            if (generalStats.has(
                    operator.getStatisticName("timeplayed"))) { //If the player has time on the operator
                operators.put(operator,
                        new OperatorStats(operator,
                                getStat(generalStats, operator.getStatisticName("kills")),
                                getStat(generalStats, operator.getStatisticName("death")),
                                getStat(generalStats, operator.getStatisticName("roundwon")),
                                getStat(generalStats, operator.getStatisticName("roundlost")),
                                getStat(generalStats, operator.getStatisticName("meleekills")),
                                getStat(generalStats, operator.getStatisticName("dbno")),
                                getStat(generalStats, operator.getStatisticName("totalxp")),
                                getStat(generalStats, operator.getStatisticName("timeplayed")),
                                getStat(generalStats,
                                        operator.getStatisticName(operator.getGadget()))));
            }
        }
    }

    /**
     * Loads the player's gamemode statistics into the gamemodes map
     *
     * @param generalStats The global stats node fetched on creation
     */
    private void loadGamemodeStats(JsonNode generalStats) {
        gamemodes = new HashMap<>();

        for (Gamemode gamemode : Gamemode.values()) {
            if (generalStats.has(gamemode.getStatisticName("timeplayed"))) {
                gamemodes.put(gamemode,
                        new GamemodeStats(gamemode,
                                getStat(generalStats, gamemode.getStatisticName("matchwon")),
                                getStat(generalStats, gamemode.getStatisticName("matchlost")),
                                getStat(generalStats, gamemode.getStatisticName("matchplayed")),
                                getStat(generalStats, gamemode.getStatisticName("bestscore"))));

            }
        }
    }

    /**
     * Loads the player's weapon statistics into the weapons map
     *
     * @param generalStats The global stats node fetched on creation
     */
    private void loadWeaponStats(JsonNode generalStats) {
        weapons = new HashMap<>();

        for (Weapon weapon : Weapon.values()) {
            if (generalStats.has(weapon.getStatisticName("kills"))) {
                weapons.put(weapon,
                        new WeaponStats(weapon,
                                getStat(generalStats, weapon.getStatisticName("kills")),
                                getStat(generalStats, weapon.getStatisticName("headshot")),
                                getStat(generalStats, weapon.getStatisticName("bulletfired")),
                                getStat(generalStats, weapon.getStatisticName("bullethit"))));
            }
        }
    }

    /**
     * @return A Map containing the player's operator statistics
     */
    public Map<Operator, OperatorStats> getOperatorStats() {
        return operators;
    }

    /**
     * @return A sorted array of the player's operator statistics based on kills
     */
    public OperatorStats[] getSortedOperators() {
        if (sortedOperators == null) {
            OperatorStats[] sorted = getOperatorStats().values().toArray(new OperatorStats[0]);
            Arrays.sort(sorted);
            sortedOperators = sorted;
        }
        return sortedOperators;
    }

    /**
     * @param category Which side you wish to grab the top operator from: "atk" or "def"
     * @return The operator statistic wrapper for the top operator based on kills
     */
    public OperatorStats getTopOperator(String category) {
        for (OperatorStats operator : getSortedOperators()) {
            if (operator.getOperator().getCategory().equalsIgnoreCase(category)) {
                return operator;
            }
        }
        return null;
    }

    /**
     * @return A Map containing the player's gamemode statistics
     */
    public Map<Gamemode, GamemodeStats> getGamemodeStats() {
        return gamemodes;
    }

    /**
     * @return A sorted array of the player's gamemode statistics based on wins
     */
    public GamemodeStats[] getSortedGamemodes() {
        if (sortedGamemodes == null) {
            GamemodeStats[] sorted = getGamemodeStats().values().toArray(new GamemodeStats[0]);
            Arrays.sort(sorted);
            sortedGamemodes = sorted;
        }
        return sortedGamemodes;
    }

    /**
     * @return A Map containing the player's weapon statistics
     */
    public Map<Weapon, WeaponStats> getWeaponStats() {
        return weapons;
    }

    /**
     * @return A sorted array of the player's weapon statistics based on kills
     */
    public WeaponStats[] getSortedWeapons() {
        if (sortedWeapons == null) {
            WeaponStats[] sorted = getWeaponStats().values().toArray(new WeaponStats[0]);
            Arrays.sort(sorted);
            sortedWeapons = sorted;
        }
        return sortedWeapons;
    }

    /**
     * @return A double representation of the player's Win/Loss ratio in ranked play
     */
    public double getRankedWinLossRatio() {
        return (ranked_wins * 1.0) / (ranked_losses + ranked_wins * 1.0);
    }

    public String getProfileId() {
        return profileId;
    }

    public String getName() {
        return nameOnPlatform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Region getRegion() {
        return region;
    }

    public int getLevel() {
        return level;
    }

    public int getLootboxProbability() {
        return lootbox_probability;
    }

    public int getAbandons() {
        return abandons;
    }

    public int getSeasonRankedWins() {
        return ranked_wins;
    }

    public int getSeasonRankedLosses() {
        return ranked_losses;
    }

    public Rank getRank() {
        return Rank.from(rank);
    }

    public Rank getMaxRank() {
        return Rank.from(max_rank);
    }

    public double getMmr() {
        return mmr;
    }

    public double getMaxMmr() {
        return max_mmr;
    }

    public double getSkill() {
        return skill;
    }

    public double getSkillStandardDeviation() {
        return skill_stdev;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getRankedKills() {
        return ranked_kills;
    }

    public int getRankedDeaths() {
        return ranked_deaths;
    }

    public int getTotalRankedWins() {
        return wins;
    }

    public int getTotalRankedLosses() {
        return losses;
    }

    public int getPenetrationKills() {
        return penetration_kills;
    }

    public int getBulletsHit() {
        return bullets_hit;
    }

    public int getMeleeKills() {
        return melee_kills;
    }

    public int getBulletsFired() {
        return bullets_fired;
    }

    public int getMatchesPlayed() {
        return matches_played;
    }

    public int getAssists() {
        return assists;
    }

    public int getTimePlayed() {
        return time_played;
    }

    public int getRevives() {
        return revives;
    }

    public int getHeadshots() {
        return headshots;
    }

    public int getInjureAssists() {
        return dbno_assists;
    }

    public int getSuicides() {
        return suicides;
    }

    public int getBarricadesDeployed() {
        return barricades_deployed;
    }

    public int getReinforcementsDeployed() {
        return reinforcements_deployed;
    }

    public int getXp() {
        return xp;
    }

    public int getRappelBreaches() {
        return rappel_breaches;
    }

    public int getDistanceTravelled() {
        return distance_travelled;
    }

    public int getRevivesDenied() {
        return revives_denied;
    }

    public int getInjures() {
        return dbnos;
    }

    public int getGadgetsDestroyed() {
        return gadgets_destroyed;
    }

    public int getBlindKills() {
        return blind_kills;
    }

    public String getAvatarUrl() {
        return "https://ubisoft-avatars.akamaized.net/" + profileId + "/default_146_146.png";
    }

    /**
     * Loads <b>ALL</b> players stats into memory
     */
    private void loadStats() {
        loadXpStats();
        loadRankedStats();


        List<String> statsToFetch = new ArrayList<>(Arrays.asList(STATS));

        for (Operator op : Operator.values())
            statsToFetch.add("operatorpvp_" + op.getGadget());

        JsonNode stats = fetchStatistics(
                statsToFetch); //Centralize the server request so we only make one and have it contain the majority of the data

        loadGeneralStats(stats);
        loadOperatorStats(stats);
        loadGamemodeStats(stats);

    }

    /**
     * A quick method to handle certain json keys not being returned by Ubisoft if they are 0
     *
     * @param node      The JsonNode to grab from
     * @param stat_name The stat to get
     * @return The int value of the stat, 0 if it doesn't exist
     */
    private int getStat(JsonNode node, String stat_name) {
        if(node == null)
            return 0;
        else if (node.has(stat_name))
            return node.get(stat_name).asInt();
        else
            return 0;
    }


    /**
     * Fetches all statistics in the list from the general ubisoft statistics endpoint
     *
     * @param statistics A list of all statistics to fetch
     * @return A JsonNode object with the data returned by the server
     */
    private JsonNode fetchStatistics(List<String> statistics) {
        StringJoiner concat = new StringJoiner(",");
        for (String stat : statistics) {
            concat.add(stat);
        }

        return api.getAuthenticator().authorizedGet(
                "https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                        + platform.getUrl() + "/playerstats2/statistics",
                "populations", profileId,
                "statistics", concat.toString())
                .get("results").get(profileId);
    }

    /**
     * A large list of all the possible stat requests
     */
    private static final String[] STATS =
            {
                    "casualpvp_kills",
                    "casualpvp_death",
                    "casualpvp_matchlost",
                    "casualpvp_matchplayed",
                    "casualpvp_matchwon",
                    "casualpvp_timeplayed",

                    "rankedpvp_kills",
                    "rankedpvp_death",
                    "rankedpvp_matchlost",
                    "rankedpvp_matchplayed",
                    "rankedpvp_matchwon",
                    "rankedpvp_timeplayed",

                    "secureareapvp_bestscore",
                    "secureareapvp_matchlost",
                    "secureareapvp_matchplayed",
                    "secureareapvp_matchwon",
                    "secureareapvp_timeplayed",

                    "rescuehostagepvp_bestscore",
                    "rescuehostagepvp_matchlost",
                    "rescuehostagepvp_matchplayed",
                    "rescuehostagepvp_matchwon",
                    "rescuehostagepvp_timeplayed",

                    "plantbombpvp_bestscore",
                    "plantbombpvp_matchlost",
                    "plantbombpvp_matchplayed",
                    "plantbombpvp_matchwon",
                    "plantbombpvp_timeplayed",

                    "weapontypepvp_headshot",
                    "weapontypepvp_bulletfired",
                    "weapontypepvp_bullethit",
                    "weapontypepvp_kills",

                    "operatorpvp_kills",
                    "operatorpvp_death",
                    "operatorpvp_meleekills",
                    "operatorpvp_dbno",
                    "operatorpvp_roundwon",
                    "operatorpvp_roundlost",
                    "operatorpvp_timeplayed",
                    "operatorpvp_totalxp",


                    "generalpvp_bulletfired",
                    "generalpvp_bullethit",
                    "generalpvp_barricadedeployed",
                    "generalpvp_reinforcementdeploy",
                    "generalpvp_rappelbreach",
                    "generalpvp_distancetravelled",
                    "generalpvp_headshot",
                    "generalpvp_death",
                    "generalpvp_killassists",
                    "generalpvp_kills",
                    "generalpvp_matchlost",
                    "generalpvp_matchplayed",
                    "generalpvp_matchwon",
                    "generalpvp_meleekills",
                    "generalpvp_penetrationkills",
                    "generalpvp_revive",
                    "generalpvp_timeplayed",
                    "generalpvp_blindkills",
                    "generalpvp_dbno",
                    "generalpvp_dbnoassists",
                    "generalpvp_gadgetdestroy",
                    "generalpvp_hostagedefense",
                    "generalpvp_hostagerescue",
                    "generalpvp_rappelbreach",
                    "generalpvp_revivedenied",
                    "generalpvp_serveraggression",
                    "generalpvp_serverdefender",
                    "generalpvp_servershacked",
                    "generalpvp_suicide"
            };


    /**
     * Creates an R6Player object based on the given user information, defaulting to NA region
     *
     * @param api            Your R6J object
     * @param nameOnPlatform The user's current name
     * @param platform       The user's platform
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayer(R6J api, String nameOnPlatform, Platform platform) {
        return getPlayer(api, nameOnPlatform, platform, Region.NA);
    }

    /**
     * Creates an R6Player object based on the given user information
     *
     * @param api            Your R6J object
     * @param nameOnPlatform The user's current name
     * @param platform       The user's platform
     * @param region         The user's primary region
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayer(R6J api, String nameOnPlatform, Platform platform, Region region) {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://connect.ubi.com/ubiservices/v2/profiles",
                "nameOnPlatform", nameOnPlatform,
                "platformType", platform.getName());

        JsonNode player = response.get("profiles").get(0);

        return new R6Player(api,
                player.get("profileId").asText(),
                player.get("userId").asText(),
                player.get("idOnPlatform").asText(), player.get("nameOnPlatform").asText(),
                Platform.getByName(player.get("platformType").asText()),
                region);
    }

    /**
     * Creates an R6Player object based on the given user information, defaulting to NA region
     *
     * @param api          Your R6J object
     * @param idOnPlatform The user's profile_id
     * @param platform     The user's platform
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayerById(R6J api, String idOnPlatform, Platform platform) {
        return getPlayerById(api, idOnPlatform, platform, Region.NA);
    }

    /**
     * Creates an R6Player object based on the given user information
     *
     * @param api          Your R6J object
     * @param idOnPlatform The user's profile_id
     * @param platform     The user's platform
     * @param region       The user's primary region
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayerById(R6J api, String idOnPlatform, Platform platform, Region region) {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://connect.ubi.com/ubiservices/v2/profiles",
                "idOnPlatform", idOnPlatform,
                "platformType", platform.getName());

        JsonNode player = response.get("profiles").get(0);

        return new R6Player(api,
                player.get("profileId").asText(),
                player.get("userId").asText(),
                player.get("idOnPlatform").asText(), player.get("nameOnPlatform").asText(),
                Platform.getByName(player.get("platformType").asText()),
                region);
    }

    /**
     * Determines if the player exists on Ubisoft servers
     *
     * @param api            Your R6J object
     * @param nameOnPlatform The user's current name
     * @param platform       The user's platform
     * @return Does the player exist?
     */
    static boolean playerExists(R6J api, String nameOnPlatform, Platform platform) {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://connect.ubi.com/ubiservices/v2/profiles",
                "nameOnPlatform", nameOnPlatform,
                "platformType", platform.getName());
        return response != null && response.get("profiles").size() > 0;
    }

    /**
     * Determines if the player id exists on Ubisoft servers
     *
     * @param api          Your R6J object
     * @param idOnPlatform The user's profile_id
     * @param platform     The user's platform
     * @return Does the player exist?
     */
    static boolean playerIdExists(R6J api, String idOnPlatform, Platform platform) {
        JsonNode response = api.getAuthenticator().authorizedGet(
                "https://connect.ubi.com/ubiservices/v2/profiles",
                "idOnPlatform", idOnPlatform,
                "platformType", platform.getName());
        return response != null && response.get("profiles").size() > 0;
    }
}
