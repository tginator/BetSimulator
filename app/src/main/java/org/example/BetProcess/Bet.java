package org.example.BetProcess;

import org.example.Game;
import org.example.Outcome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bet {

    private List<Outcome> legs;
    private double stake;
    private double totalOdds;
    private double totalPayoff;
    private boolean isWinner;
    private boolean isCompleted = false;

    public Bet(double stake) {
        this.stake = stake;
        legs = new ArrayList<>();
    }

    public void addLeg(Outcome leg) {
        legs.add(leg);
    }

    public double calculateTotalPayoff() {
      this.totalPayoff = stake * totalOdds;
      return totalPayoff;
    }

    public double calculateTotalOdds() {
        totalOdds = 1;
        for (Outcome leg : legs) {
            totalOdds = leg.getPrice() * totalOdds;
        }

        return totalOdds;
    }

    public void setOdds(double previousOdds) {
        totalOdds = previousOdds;
    }

    public boolean checkWinner(List<Game> games) {

        for (Outcome leg : legs) {
            if (!(leg.isWinner())){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "\n" + legs.toString() + "\nTotal payoff: " + totalPayoff + "\nTotal odds: " + totalOdds;
    }


    public List<Outcome> getLegs() {
        return legs;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted() {
        isCompleted = true;
    }
}
