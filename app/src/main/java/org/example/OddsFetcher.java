package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.example.BetProcess.Bet;
import org.example.BetProcess.BetBuilder;
import org.example.BetProcess.BettingAccount;
import org.example.BetProcess.DailyBetSummary;

import java.net.URL;
import java.util.*;

public class OddsFetcher {

    private static final String API_URL = "https://api.the-odds-api.com/v4/historical/sports/basketball_nba/odds";
    private static final String API_KEY = "baf40af73f1b9be32a27eead69908666";
    private static final String REGIONS = "au";
    private static final String MARKETS = "h2h";


    public static void main(String[] args) {

        int day = 1;

       String DATE = "2024-11-" + day; // Game commence time
         String DATELINK = "2024-11-" + day + "T00:00:00Z";


        double totalpayout = 0;
        double totalpaid = 0;

        Scanner scanner = new Scanner(System.in);
        List<Outcome> usedLegs = new ArrayList<>();

        System.out.println("New account balance?");
        Double balance = scanner.nextDouble();

        BettingAccount myAcc = new BettingAccount(balance, "tyler", 0.10);


        System.out.println("Enter max odds per leg: ");
        Double maxOddsPerLeg = scanner.nextDouble();

        System.out.println("Enter max odds per bet: ");
        Double maxOdds = scanner.nextDouble();



        for (day = 29; day <= 31; day++) {
            BetBuilder todaysBets = new BetBuilder(myAcc.payForBetProportion(), maxOdds, maxOddsPerLeg);
            if (myAcc.getBalance() > 0) {
                DATE = "2024-10-" + day;
                DATELINK = "2024-10-" + day + "T00:00:00Z";
                todaysBets.addBet(loadGameData(DATE, DATELINK));
                todaysBets.getBets();
                totalpaid += todaysBets.getPaid();
                totalpayout += todaysBets.getPayout();
                myAcc.subtractBalance(todaysBets.getPaid());
                myAcc.addBalance(todaysBets.getPayout());
            }
        }



        // November
        for (day = 1; day <= 24; day++) {
            BetBuilder todaysBets = new BetBuilder(myAcc.payForBetProportion(), maxOdds, maxOddsPerLeg);
            if (myAcc.getBalance() > 0) {
                if (day < 10) {
                    DATE = "2024-11-0" + day;
                    DATELINK = "2024-11-0" + day + "T00:00:00Z";
                } else {
                    DATE = "2024-11-" + day;
                    DATELINK = "2024-11-" + day + "T00:00:00Z";
                }
                todaysBets.addBet(loadGameData(DATE, DATELINK));
                todaysBets.getBets();
                totalpaid += todaysBets.getPaid();
                totalpayout += todaysBets.getPayout();
                myAcc.subtractBalance(todaysBets.getPaid());
                myAcc.addBalance(todaysBets.getPayout());
            }
        }

        System.out.println("\n Total paid: " + totalpaid);
        System.out.println("Total payout: " + totalpayout);
        System.out.println("Final account balance: $" + myAcc.getBalance());
    }

    private static List<Game> loadGameData(String DATE, String DATELINK) {

        try {
            String requestUrl = String.format("%s?apiKey=%s&regions=%s&markets=%s&date=%s",
                    API_URL, API_KEY, REGIONS, MARKETS, DATELINK);

            ObjectMapper mapper = new ObjectMapper();

            // Fetch JSON response as a String
            URL url = new URL(requestUrl);
            ResponseData responseData = mapper.readValue(url, ResponseData.class);

            // CSV information of  games up to 23/11/2024
            List<GameResult> gameResults = CSVProcessor.loadGameResults("Updated_Nba_Data.csv");

            List<Game> games = responseData.getData();

            // For every game, match the game result and put it in the game object)
            for (Game game : games) {
                for (GameResult gameResult : gameResults) {
                    if (DATE.equals(gameResult.getDate()) && game.getHome_team().equals(gameResult.getHomeTeam())) {
                        gameResult.setWinner();
                        game.setGame_result(gameResult);
                    }
                }
            }

            for (Game game : games) {
                for (Bookmaker bookmaker : game.getBookmakers()) {
                    if (bookmaker.getKey().equals("pointsbetau")) {
                        for (Market market : bookmaker.getMarkets()) {
                            if (market.getKey().equals("h2h")) {
                                List<Outcome> outcomes = market.getOutcomes();
                                // Store outcomes in your object

                                if (game.getGame_result() != null) {
                                    //set bet winner inside of game object
                                    game.setOutcome(outcomes);
                                }
                            }
                        }
                    }
                }
            }

            return games;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return List.of();
    }

    private static void betBuilder(List<Game> games) {

        Scanner scanner = new Scanner(System.in);
        List<Outcome> usedLegs = new ArrayList<>();

        System.out.println("Enter max odds per leg: ");
        Double maxOddsPerLeg = scanner.nextDouble();

        System.out.println("Enter max odds per bet: ");
        Double maxOdds = scanner.nextDouble();

        boolean dayGoing = true;

        BetBuilder todaysBets = new BetBuilder(5.0, maxOdds, maxOddsPerLeg);

        todaysBets.addBet(games);

        System.out.println(todaysBets.getBets());

    }
}