package org.example;

public class GameResult {
    private String date;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private String winner;

    // Constructor
    public GameResult(String date, String homeTeam, String awayTeam, int homeScore, int awayScore) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    // Determine the winner
    public void setWinner() {
        if (homeScore > awayScore) {
            this.winner = homeTeam;
        } else if (homeScore < awayScore) {
            this.winner = this.awayTeam;
        }
    }

    // Getters
    public String getDate() {
        if (date != null && date.length() >= 10) {
            // Extract the "YYYY-MM-DD" part of the date string
            return date.substring(0, 10);
        }
        return null; // or handle the case when 'date' is null or too short
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getWinner() {
        return winner;
    }
}

