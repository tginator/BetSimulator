package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    private String id;
    private String sport_key;
    private String sport_title;
    private String commence_time;
    private String home_team;
    private String away_team;
    private List<Bookmaker> bookmakers;
    private GameResult game_result;
    private Outcome outcomeWinner;
    private Outcome outcomeHome;
    private Outcome outcomeAway;
    private List<Outcome> outcomes;

    // Getters and setters
    public String getCommence_time() {
        return commence_time;
    }

    public void setCommence_time(String commence_time) {
        this.commence_time = commence_time;
    }

    public List<Bookmaker> getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(List<Bookmaker> bookmakers) {
        this.bookmakers = bookmakers;
    }

    public void setOutcome(List<Outcome> outcomes) {

        for (Outcome outcome : outcomes) {
            if (outcome.getName().equals(game_result.getHomeTeam())) {
                this.outcomeHome = outcome;
                if (outcome.getName().equals(game_result.getWinner())) {
                    this.outcomeWinner = outcome;
                    outcome.setWinner();
                }
            } else if (outcome.getName().equals(game_result.getAwayTeam())) {
                this.outcomeAway = outcome;
                if (outcome.getName().equals(game_result.getWinner())) {
                    this.outcomeWinner = outcome;
                    outcome.setWinner();
                }
            }
        }
    }

    public Outcome getOutcomeHome() {
        return outcomeHome;
    }

    public Outcome getOutcomeAway() {
        return outcomeAway;
    }

    public Outcome getOutcomeWinner() {
        return outcomeWinner;
    }


    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public String getOutcome() {
        return "The winner of " + this.home_team + " - " + this.away_team + " is " + this.outcomeWinner.getName();
    }

    public void setGame_result(GameResult game_result) {
        this.game_result = game_result;
    }

    public GameResult getGame_result() {
        return game_result;
    }
}





