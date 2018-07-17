package main.stats;


import main.declarations.Operator;

public class OperatorStats implements Comparable<OperatorStats> {


    private Operator operator;
    private int statistic_count;

    private int kills;
    private int deaths;
    private int wins;
    private int losses;
    private int melees;
    private int dbnos;
    private int xp;
    private long timeplayed;

    public OperatorStats(Operator operator, int kills, int death, int roundwon, int roundlost,
                         int meleekills, int dbno, int totalxp, long timeplayed,
                         int statistic_count) {
        this.operator = operator;
        this.kills = kills;
        this.deaths = death;
        this.wins = roundwon;
        this.losses = roundlost;
        this.melees = meleekills;
        this.dbnos = dbno;
        this.xp = totalxp;
        this.timeplayed = timeplayed;
        this.statistic_count = statistic_count;
    }

    public Operator getOperator() {
        return operator;
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

    public int compareTo(OperatorStats otherOperator) {
        return otherOperator.getKills() - getKills();
    }
}
