package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Outcome {
    private String name;
    private double price;
    private boolean isWinner = false;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner() {
        isWinner = true;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}