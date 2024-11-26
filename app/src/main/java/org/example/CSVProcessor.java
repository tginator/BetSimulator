package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVProcessor {

    public static List<GameResult> loadGameResults(String filePath) {
        List<GameResult> results = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            reader.readNext(); // Skip header row
            while ((line = reader.readNext()) != null) {
                String date = line[2];
                String homeTeam = line[4];
                String awayTeam = line[5];
                String[] scores = line[6].split("-");
                if (scores.length == 2 ) {
                    int homeScore = Integer.parseInt(scores[0].trim());
                    int awayScore = Integer.parseInt(scores[1].trim());
                    results.add(new GameResult(date, homeTeam, awayTeam, homeScore, awayScore));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
