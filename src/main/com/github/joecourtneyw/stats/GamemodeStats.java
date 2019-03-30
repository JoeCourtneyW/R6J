package main.com.github.joecourtneyw.stats;

import main.com.github.joecourtneyw.declarations.Gamemode;

public class GamemodeStats implements Comparable<GamemodeStats> {

    private Gamemode gamemode;
    private int wins;
    private int losses;
    private int totalGames;
    private int bestScore;

    /**
     * Shows objectives completed for Hostage and Secure Area gamemode
     */
    private int objectives_completed;

    /**
     * Shows objectives defended for Hostage and Secure Area gamemode
     */
    private int objectives_defended;

    /**
     * Shows contested objective kills for Secure Area gamemode
     */

    private int objectives_contested;

    public GamemodeStats(Gamemode gamemode, int wins, int losses, int totalGames, int bestScore) {
        this.gamemode = gamemode;
        this.wins = wins;
        this.losses = losses;
        this.totalGames = totalGames;
        this.bestScore = bestScore;
        this.objectives_completed = 0;
        this.objectives_defended = 0;
        this.objectives_contested = 0;
    }

    public void setObjectivesCompleted(int objectivesCompleted) {
        this.objectives_completed = objectivesCompleted;
    }
    public void setObjectivesDefended(int objectivesDefended) {
        this.objectives_defended = objectivesDefended;
    }
    public void setObjectivesContested(int objectivesContested) {
        this.objectives_contested = objectivesContested;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getObjectivesCompleted() {
        return objectives_completed;
    }

    public int getObjectivesDefended() {
        return objectives_defended;
    }

    public int getObjectivesContested() {
        return objectives_contested;
    }

    public int compareTo(GamemodeStats otherGamemode) {
        return otherGamemode.getWins() - getWins();
    }
}
