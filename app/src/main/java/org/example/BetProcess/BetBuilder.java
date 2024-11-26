package org.example.BetProcess;

import org.example.Game;
import org.example.Outcome;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BetBuilder {

    private double stake;
    private List<Bet> bets;
    private double maxOdds;
    private double maxOddsPerLeg;
    private double paid;
    private double payout;

    public BetBuilder(Double amount, double maxOdds, double maxOddsPerLeg) {
        this.stake = amount;
        this.maxOdds = maxOdds;
        this.maxOddsPerLeg = maxOddsPerLeg;
        this.bets = new ArrayList<>();
        this.paid = 0;
        this.payout = 0;
    }

    public void addBet(List<Game> games) {

        Bet bet = new Bet(stake);

        double currentOdds = 1;

        for (Game game : games) {

            if (bet.isCompleted()) {
                bet = new Bet(stake);
            }
            if ((!(game.getOutcomeHome() == null)) && ((game.getOutcomeHome().getPrice() - 0.13) < maxOddsPerLeg) && (currentOdds * game.getOutcomeHome().getPrice() < maxOdds)) {
                bet.addLeg(game.getOutcomeHome());
                currentOdds = bet.calculateTotalOdds();
            } else if (!(game.getOutcomeAway() == null) && ((game.getOutcomeAway().getPrice() + 0.05) < maxOddsPerLeg) && currentOdds * game.getOutcomeAway().getPrice() < maxOdds) {
                bet.addLeg(game.getOutcomeAway());
                currentOdds = bet.calculateTotalOdds();
            } else {
                if (bet.getLegs().size() > 0) {
                    bet.setCompleted();
                    currentOdds = 1;
                    bet.calculateTotalPayoff();
                    this.bets.add(bet);
                    paid += stake;
                    if (bet.checkWinner(games)) {
                        payout += bet.calculateTotalPayoff();
                    }
            }

            }

        }
    }

    public void homeBet(List<Game> games) {
        Bet bet = new Bet(stake);

        double currentOdds = 1;

        for (Game game : games) {

            if (bet.isCompleted()) {
                bet = new Bet(stake);
            }
            if ((!(game.getOutcomeHome() == null)) && ((game.getOutcomeHome().getPrice() - 0.10) < maxOddsPerLeg) && (currentOdds * game.getOutcomeHome().getPrice() < maxOdds)) {
                bet.addLeg(game.getOutcomeHome());
                currentOdds = bet.calculateTotalOdds();
            } else if (!(game.getOutcomeAway() == null) && (game.getOutcomeAway().getPrice() < maxOddsPerLeg) && currentOdds * game.getOutcomeAway().getPrice() < maxOdds) {
                bet.addLeg(game.getOutcomeAway());
                currentOdds = bet.calculateTotalOdds();
            } else {
                if (bet.getLegs().size() > 0) {
                    bet.setCompleted();
                    currentOdds = 1;
                    bet.calculateTotalPayoff();
                    this.bets.add(bet);
                    paid += stake;
                    if (bet.checkWinner(games)) {
                        payout += bet.calculateTotalPayoff();
                    }
                }

            }

        }
    }

    public double getPayout() {
        return payout;
    }

    public double getPaid() {
        return paid;
    }

    public List<Bet> getBets() {
        System.out.println("Total paid today: " + paid + "\n");
        System.out.println("Total won: " + payout + "\n");

        writeBetsToFile("bets_output.txt");

        return bets;
    }

    private void writeBetsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Total paid today: " + paid + "\n");
            writer.write("Total won: " + payout + "\n\n");


            for (Bet bet : bets) {
                writer.write(bet.toString() + "\n");
            }
            System.out.println("Bet information written to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
