package org.example.BetProcess;

public class BettingAccount {

    private double balance;
    private String name;
    private double stake;
    private double proportion;

    public BettingAccount(double balance, String name, double proportion) {
        this.balance = balance;
        this.proportion = proportion;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void subtractBalance(double amount) {
        balance -= amount;
    }

    public double payForBetProportion() {
        stake = balance * proportion;
        return stake;
    }

}
