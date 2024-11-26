package org.example.BetProcess;

import java.util.ArrayList;
import java.util.List;

public class DailyBetSummary {

    private List<Bet> bets;


    public DailyBetSummary() {
        this.bets = new ArrayList<>();
    }
    public void addBet(Bet bet) {
        bets.add(bet);
    }

    public void printSummary() {
        for (Bet bet : bets) {
            bet.calculateTotalPayoff();
            System.out.println(bet);
        }
    }
}
