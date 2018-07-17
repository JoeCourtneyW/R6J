package main.stats;

import main.declarations.Gamemode;

public class GamemodeStats implements Comparable<GamemodeStats> {

    private Gamemode gamemode;
    private int wins;
    private int losses;
    private int totalGames;
    private int bestScore;

    public GamemodeStats(Gamemode gamemode, int wins, int losses, int totalGames, int bestScore) {
        this.gamemode = gamemode;
        this.wins = wins;
        this.losses = losses;
        this.totalGames = totalGames;
        this.bestScore = bestScore;
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

    public int compareTo(GamemodeStats otherGamemode) {
        return otherGamemode.getWins() - getWins();
    }
}
