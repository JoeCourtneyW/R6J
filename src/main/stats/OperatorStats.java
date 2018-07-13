package main.stats;


public class OperatorStats implements Comparable<OperatorStats>{


    private String name;
    private String statistic_name;
    private int statistic_count;
    private String side;

    private int kills;
    private int deaths;
    private int wins;
    private int losses;
    private int headshots;
    private int melees;
    private int dbnos;
    private int xp;
    private long timeplayed;

    public OperatorStats(String name, int kills, int death, int roundwon, int roundlost,
                         int headshot, int meleekills, int dbno, int totalxp, long timeplayed,
                         String __statistic_name, int statistic_count, String side){
        this.name = name;
        this.kills = kills;
        this.deaths = death;
        this.wins = roundwon;
        this.losses = roundlost;
        this.headshots = headshot;
        this.melees = meleekills;
        this.dbnos = dbno;
        this.xp = totalxp;
        this.timeplayed = timeplayed;
        this.statistic_name = __statistic_name;
        this.statistic_count = statistic_count;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public String getUniqueStatisticName() {
        return statistic_name;
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

    public int getHeadshots() {
        return headshots;
    }

    public int getMelees() {
        return melees;
    }

    public int getDbnos() {
        return dbnos;
    }

    public int getXp() {
        return xp;
    }

    public long getTimePlayed() {
        return timeplayed;
    }

    public int getUniqueStatisticCount() {
        return statistic_count;
    }

    public String getSide() { return side; }

    public int compareTo(OperatorStats otherOperator) {
        return otherOperator.getKills() - this.getKills();
    }
}
