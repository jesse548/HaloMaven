package com.haloapi;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class GenerateHTML {

    public void getPlayerMatchHistory(String gamerTag) throws IOException {
        String getUrl = "https://www.haloapi.com/stats/h5/players/" + gamerTag + "/matches/?count=1";
        URL httpUrl = new URL(getUrl);
        HttpsURLConnection request = (HttpsURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", "d53f21e540a3419ba28d208d631b6501");
        request.connect();
        
        int responseCode = request.getResponseCode();
        String responseMessage = request.getResponseMessage();
        System.out.println("Response code = " + responseCode);
        System.out.println("Response message = " + responseMessage);

        StringBuilder response = new StringBuilder();
        Scanner scanner = new Scanner(request.getInputStream());
        while (scanner.hasNextLine()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray results = (JSONArray) jsonResponse.get("Results");
            if (results != null && !results.isEmpty()) {
                JSONObject match = (JSONObject) results.get(0);
                String gameBaseVariantId = (String) match.get("GameBaseVariantId");
                String matchDuration = (String) match.get("MatchDuration");
                JSONObject matchCompletedDate = (JSONObject) match.get("MatchCompletedDate");
                String iso8601Date = (String) matchCompletedDate.get("ISO8601Date");
                JSONObject id = (JSONObject) match.get("Id");
                String matchId = (String) id.get("MatchId");
                JSONArray players = (JSONArray) match.get("Players");

                // Generate HTML content
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append("<html><head><title>Match History</title></head><body>");
                htmlContent.append("<h1>Match Details</h1>");
                htmlContent.append("<p>Game Base Variant ID: ").append(gameBaseVariantId).append("</p>");
                htmlContent.append("<p>Match Duration: ").append(matchDuration).append("</p>");
                htmlContent.append("<p>Match Completed Date: ").append(iso8601Date).append("</p>");
                htmlContent.append("<p>Match ID: ").append(matchId).append("</p>");
                htmlContent.append("<h2>Players</h2>");
                htmlContent.append("<ul>");

                for (Object playerObj : players) {
                    JSONObject player = (JSONObject) playerObj;
                    JSONObject playerDetails = (JSONObject) player.get("Player");
                    String gamertag = (String) playerDetails.get("Gamertag");
                    long totalKills = (Long) player.get("TotalKills");
                    long totalAssists = (Long) player.get("TotalAssists");
                    long totalDeaths = (Long) player.get("TotalDeaths");
                    long rank = (Long) player.get("Rank");
                    long result = (Long) player.get("Result");

                    htmlContent.append("<li>");
                    htmlContent.append("<p>Gamertag: ").append(gamertag).append("</p>");
                    htmlContent.append("<p>Total Kills: ").append(totalKills).append("</p>");
                    htmlContent.append("<p>Total Assists: ").append(totalAssists).append("</p>");
                    htmlContent.append("<p>Total Deaths: ").append(totalDeaths).append("</p>");
                    htmlContent.append("<p>Rank: ").append(rank).append("</p>");
                    htmlContent.append("<p>Result: ").append(result).append("</p>");
                    htmlContent.append("</li>");
                }

                htmlContent.append("</ul>");
                htmlContent.append("</body></html>");

                // Write HTML content to file
                try (FileWriter fileWriter = new FileWriter("match_history.html")) {
                    fileWriter.write(htmlContent.toString());
                }

                System.out.println("HTML file generated successfully.");
            } else {
                System.out.println("No matches found.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
