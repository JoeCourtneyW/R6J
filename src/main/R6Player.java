package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.stats.OperatorStats;
import main.declarations.Platform;
import main.declarations.Rank;
import main.declarations.Region;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

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

    private HashMap<String, OperatorStats> operators;
    private OperatorStats[] sortedOperators;

    private int kills;
    private int deaths;
    private int wins;
    private int losses;
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

    private R6Player(R6J api, String profileId, String userId, String idOnPlatform, String nameOnPlatform, Platform platform, Region region){
        this.api = api;
        this.profileId = profileId;
        this.userId = userId;
        this.idOnPlatform = idOnPlatform;
        this.nameOnPlatform = nameOnPlatform;
        this.platform = platform;
        this.region = region;
    }


    /**
     * Loads the player's level, amount of xp, and they're lootbox probability
     */
    private void loadLevelXpAndLootbox(){
        JsonNode response = api.getAuthenticator().authorizedGet("https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                + platform.getUrl() + "/r6playerprofile/playerprofile/progressions",
                "profile_ids", profileId);

        JsonNode player = response.get("player_profiles").get(0);

        this.level = player.get("level").asInt();
        this.xp = player.get("xp").asInt();
        this.lootbox_probability = player.get("lootbox_probability").asInt();
    }

    /**
     * Load's the player's stats involved with their rank: Abandons, Ranked Wins,
     * Ranked Losses, Rank, Max Rank, Skill, and Skill Standard Deviation
     *
     * Use only if you wish to load a season other than the current season
     *
     * @param season The season that you want the player's main.stats from, use -1 if not sure
     */
    public void loadRankedStats(int season) {
        JsonNode response = api.getAuthenticator().authorizedGet("https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                + platform.getUrl() + "/r6karma/players",
                "board_id", "pvp_ranked",
                "profile_ids", "pvp_ranked",
                "region_id", region.getName(),
                "season_id", season + "");
        JsonNode player = response.get("players").get(profileId);
        this.abandons = player.get("abandons").asInt();
        this.ranked_wins = player.get("wins").asInt();
        this.ranked_losses = player.get("losses").asInt();
        this.rank = player.get("rank").asInt();
        this.max_rank = player.get("max_rank").asInt();
        this.mmr = player.get("mmr").asDouble();
        this.max_mmr = player.get("max_mmr").asDouble();
        this.skill = player.get("skill_mean").asDouble();
        this.skill_stdev = player.get("skill_stdev").asDouble();

    }

    /**
     * Load's the player's stats involved with their rank: Abandons, Ranked Wins,
     * Ranked Losses, Rank, Max Rank, Skill, and Skill Standard Deviation
     *
     */
    private void loadRankedStats(){
        loadRankedStats(-1);
    }

    /**
     * Loads the player's general stats
     */
    private void loadGeneralStats(JsonNode stats) {
        String stat = "generalpvp_";
        this.deaths = stats.get(stat + "death" + ":infinite").asInt();
        this.kills = stats.get(stat + "kills" + ":infinite").asInt();
        this.wins = stats.get(stat + "matchwon" + ":infinite").asInt();
        this.losses = stats.get(stat + "matchlost" + ":infinite").asInt();
        this.penetration_kills = stats.get(stat + "penetrationkills" + ":infinite").asInt();
        this.melee_kills = stats.get(stat + "meleekills" + ":infinite").asInt();
        //this.bullets_fired = main.stats.get(stat + "bulletfired" + ":infinite").asInt(); //Stat does not exist on ubi site
        this.bullets_hit = stats.get(stat + "bullethit" + ":infinite").asInt();
        this.matches_played = stats.get(stat + "matchplayed" + ":infinite").asInt();
        this.assists = stats.get(stat + "killassists" + ":infinite").asInt();
        this.time_played = stats.get(stat + "timeplayed" + ":infinite").asInt();
        this.revives = stats.get(stat + "revive" + ":infinite").asInt();
        this.headshots = stats.get(stat + "headshot" + ":infinite").asInt();
        this.dbno_assists = stats.get(stat + "dbnoassists" + ":infinite").asInt();
        this.suicides = stats.get(stat + "suicide" + ":infinite").asInt();
        this.barricades_deployed = stats.get(stat + "barricadedeployed" + ":infinite").asInt();
        this.reinforcements_deployed = stats.get(stat + "reinforcementdeploy" + ":infinite").asInt();
        this.xp = stats.get(stat + "totalxp" + ":infinite").asInt();
        this.rappel_breaches = stats.get(stat + "rappelbreach" + ":infinite").asInt();
        this.distance_travelled = stats.get(stat + "distancetravelled" + ":infinite").asInt();
        //this.revives_denied = main.stats.get(stat + "revivedenied" + ":infinite").asInt(); //Stat does not exist on ubi site
        this.dbnos = stats.get(stat + "dbno" + ":infinite").asInt();
        this.gadgets_destroyed = stats.get(stat + "gadgetdestroy" + ":infinite").asInt();
        this.blind_kills = stats.get(stat + "blindkills" + ":infinite").asInt();
    }

    private static String capitalize(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    /**
     * Loads the player's operator statistics, including kills, deaths,
     * gadget kills, and other operator specific statistics
     */
    private void loadOperatorStats(JsonNode generalStats){
        StringJoiner operatorsStringJoiner = new StringJoiner(",");

        JsonNode defs = R6J.OPERATOR_DEFS;
        for(JsonNode operatorDef : defs){
            operatorsStringJoiner.add(operatorDef.get("uniqueStatistic").get("pvp").get("statisticId").asText().split(":")[0]);
        }
        String uniqueStatisticsString = operatorsStringJoiner.toString();
        JsonNode uniqueStats = fetchStatistics(uniqueStatisticsString);

        operators = new HashMap<>();
        for(JsonNode operator : defs){
            if(generalStats.get("operatorpvp_timeplayed:" + operator.get("index").asText() + ":infinite") != null) { //If the player has time on the operator
                operators.put(operator.get("id").asText(),
                        new OperatorStats(capitalize(operator.get("id").asText()),
                                getPvpNode(generalStats, operator, "kills").asInt(),
                                getPvpNode(generalStats, operator, "kills").asInt(),
                                getPvpNode(generalStats, operator, "roundwon").asInt(),
                                getPvpNode(generalStats, operator, "roundlost").asInt(),
                                getPvpNode(generalStats, operator, "headshot").asInt(),
                                getPvpNode(generalStats, operator, "meleekills").asInt(),
                                getPvpNode(generalStats, operator, "dbno").asInt(),
                                getPvpNode(generalStats, operator, "totalxp").asInt(),
                                getPvpNode(generalStats, operator, "timeplayed").asLong(),
                                operator.get("uniqueStatistic").get("pvp").get("statisticId").asText(),
                                getPvpNode(uniqueStats, operator, operator.get("uniqueStatistic").get("pvp").get("statisticId").asText() + ":infinite").asInt(),
                                operator.get("category").asText()));
            }
        }
    }

    private JsonNode getPvpNode(JsonNode responseContent, JsonNode operator, String stat){
        JsonNode node = responseContent.get("operatorpvp_"+ stat + ":" + operator.get("index").asText() + ":" + "infinite");
        if(node == null) {
            try {
                return new ObjectMapper().readTree("0");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return node;
        }
    }

    /**
     * Loads the player's match statistics based on gamemode:
     * Matches Won, Matches Lost, Matches Played, Unique Gamemode Stat, Time Played
     */
    private void loadGamemodeStats(JsonNode stats){
        System.out.println(stats.toString());
    }

    private JsonNode fetchStatistics(String... statistics){
        StringJoiner concat = new StringJoiner(",");
        for(String stat : statistics){
            concat.add(stat);
        }
        return api.getAuthenticator().authorizedGet("https://public-ubiservices.ubi.com/v1/spaces/" + platform.getSpaceId() + "/sandboxes/"
                + platform.getUrl() + "/playerstats2/statistics",
                "populations", profileId,
                "statistics", concat.toString())
                .get("results").get(profileId);
    }

    /**
     *
     * @return A Map containing the player's operator statistics; Key = Operator Name in lowercase
     */
    public Map<String, OperatorStats> getOperators() {
        return operators;
    }

    /**
     *
     * @return A sorted array of the player's operator statistics based on kills
     */
    public OperatorStats[] getSortedOperators() {
        if(sortedOperators == null){
            OperatorStats[] sortedOps = getOperators().values().toArray(new OperatorStats[0]);
            Arrays.sort(sortedOps);
            sortedOperators = sortedOps;
        }
        return sortedOperators;
    }

    /**
     *
     * @param side Which side you wish to grab the top operator from: "atk" or "def"
     *
     * @return The operator statistic wrapper for the top operator based on kills
     */
    public OperatorStats getTopOperator(String side) {
        for(OperatorStats operator : getSortedOperators()) {
            if(operator.getSide().equalsIgnoreCase(side)){
                return operator;
            }
        }
        return null;
    }

    public double getRankedWinLossRatio() {
        return (ranked_wins*1.0) / (ranked_losses*1.0);
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

    public Region getRegion() { return region; }

    public int getLevel() {
        return level;
    }

    public int getLootboxProbability() {
        return lootbox_probability;
    }

    public int getAbandons() {
        return abandons;
    }

    public int getRankedWins() {
        return ranked_wins;
    }

    public int getRankedLosses() {
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



    /**
     * Loads all players stats other than level and ranked stats
     */
    private void loadStats() {
        loadLevelXpAndLootbox();
        loadRankedStats();

        JsonNode stats = fetchStatistics(STATS); //Centralize the server request so we only make one and have it contain the majority of the data

        loadGeneralStats(stats);
        loadOperatorStats(stats);
        loadGamemodeStats(stats);

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
                "generalpvp_bulletfired",
                "generalpvp_bullethit",
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
                "operatorpvp_roundwon",
                "operatorpvp_roundlost",
                "operatorpvp_timeplayed",

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
     * Queries the NA ubisoft servers for the player based on their name
     *
     * @param api Your R6J object
     * @param nameOnPlatform The current name of the user on the platform
     * @param platform The platform the user is on
     *
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayer(R6J api, String nameOnPlatform, Platform platform){
        return getPlayer(api, nameOnPlatform, platform, Region.NA);
    }

    /**
     * GQueries the ubisoft servers for the player based on their name
     *
     * @param api Your R6J object
     * @param nameOnPlatform The current name of the user on the platform
     * @param platform The platform the user is on
     * @param region The primary region the user plays in
     *
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayer(R6J api, String nameOnPlatform, Platform platform, Region region){
        JsonNode response = api.getAuthenticator().authorizedGet("https://connect.ubi.com/ubiservices/v2/profiles",
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
     * Queries the NA ubisoft servers for the player based on their ID
     *
     * @param api Your R6J object
     * @param idOnPlatform The Id of the user on the platform
     * @param platform The platform the user is on
     *
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayerById(R6J api, String idOnPlatform, Platform platform){
        return getPlayerById(api, idOnPlatform, platform, Region.NA);
    }

    /**
     * Queries the ubisoft servers for the player based on their ID
     *
     * @param api Your R6J object
     * @param idOnPlatform The Id of the user on the platform
     * @param platform The platform the user is on
     * @param region The primary region the user plays in
     *
     * @return An R6Player object containing all the data on the player
     */
    static R6Player getPlayerById(R6J api, String idOnPlatform, Platform platform, Region region){
        JsonNode response = api.getAuthenticator().authorizedGet("https://connect.ubi.com/ubiservices/v2/profiles",
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
}
