package com.haloapi;

import javax.net.ssl.HttpsURLConnection;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Scanner;
import java.net.URL;
import org.json.simple.JSONObject;
import java.io.IOException;
import org.json.simple.JSONArray;
import java.io.FileWriter;

public class Stats {

    public void getPlayerMatchHistory(Object playerAppearance, String spartanImageUrl, String playerEmblemImageUrl, String apiBaseUrl,
            String apiCategory, String gameName, String apiSubCategory,
            String gamerTag,
            String apiResourceName, String apiParam, String subKey)

            throws IOException {

        // String getUrl = "https://www.haloapi.com/stats/h5/players/" + gamerTag +
        // "/matches[?modes][&start][&count][&include-times]";
        // String getUrl = "https://www.haloapi.com/stats/h5/players/" + gamerTag +
        // "/matches/?count=1";
        String getUrl = apiBaseUrl + apiCategory + gameName + apiSubCategory + gamerTag + apiResourceName + apiParam;
        URL httpUrl = new URL(getUrl);
        HttpsURLConnection request = (HttpsURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", subKey);
        request.connect();

        int responseCode = request.getResponseCode();
        String responseMessage = request.getResponseMessage();
        System.out.println("Response code = " + responseCode);
        System.out.println("Response message = " + responseMessage);

        StringBuilder response = new StringBuilder();

        // String string = "";
        Scanner scanner = new Scanner(request.getInputStream());
        while (scanner.hasNextLine()) {
            response.append(scanner.next());
        }
        scanner.close();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray results = (JSONArray) jsonResponse.get("Results");
            JSONObject match = (JSONObject) results.get(0);
            String gameBaseVariantId = (String) match.get("GameBaseVariantId");
            String matchDuration = (String) match.get("MatchDuration");
            JSONObject matchCompletedDate = (JSONObject) match.get("MatchCompletedDate");
            String iso8601Date = (String) matchCompletedDate.get("ISO8601Date");
            JSONObject id = (JSONObject) match.get("Id");
            String matchId = (String) id.get("MatchId");
            JSONArray players = (JSONArray) match.get("Players");

            System.out.println("Game Base Variant ID: " + gameBaseVariantId);
            System.out.println("Match Duration: " + matchDuration);
            System.out.println("Match Completed Date: " + iso8601Date);
            System.out.println("Match ID: " + matchId);
            System.out.println("Players: " + players.toJSONString());
            String newLine = System.lineSeparator();
            System.out.println(newLine);

            // Access player details
            if (players != null && !players.isEmpty()) {
                JSONObject player = (JSONObject) players.get(0);
                JSONObject playerDetails = (JSONObject) player.get("Player");
                String gamertag = (String) playerDetails.get("Gamertag");
                long totalKills = (Long) player.get("TotalKills");
                long totalAssists = (Long) player.get("TotalAssists");
                long totalDeaths = (Long) player.get("TotalDeaths");
                long rank = (Long) player.get("Rank");
                long result = (Long) player.get("Result");

               /*  System.out.println("Gamertag: " + gamertag);
                System.out.println("Total Kills: " + totalKills);
                System.out.println("Total Assists: " + totalAssists);
                System.out.println("Total Deaths: " + totalDeaths);
                System.out.println("Rank: " + rank);
                System.out.println("Result: " + result); */

                // Generate HTML content
                StringBuilder htmlContent = new StringBuilder();
                htmlContent.append("<html><head><title>Match History</title>");
                htmlContent.append("<style>");
                // htmlContent.append("p{");
                htmlContent.append("body {");
                htmlContent.append(
                        "background-image: url('file:///C:/devl/HaloAPI/HaloMaven/haloapi/target/GeneratedFiles/halobackground.jpeg');");
                htmlContent.append("background-image: url('").append(spartanImageUrl).append("');");
                htmlContent.append("background-size: cover;");
                htmlContent.append("background-repeat: no-repeat;");
                htmlContent.append("background-attachment: fixed;");
                htmlContent.append("}");
                htmlContent.append(".image-container {");
                htmlContent.append("display: flex;");
                htmlContent.append("justify-content: space-around;");
                htmlContent.append("margin-top: 50px;");
                htmlContent.append("}");
                htmlContent.append(".image-container img {");
                htmlContent.append("width: 200px;");
                htmlContent.append("height: auto;");
                htmlContent.append("}");
                // htmlContent.append("}");
                htmlContent.append("</style>");
                htmlContent.append("</head><body>");
                htmlContent.append("<h1>Match Details</h1>");
                htmlContent.append("<p>Game Base Variant ID: ").append(gameBaseVariantId).append("</p>");
                htmlContent.append("<p>Match Duration: ").append(matchDuration).append("</p>");
                htmlContent.append("<p>Match Completed Date: ").append(iso8601Date).append("</p>");
                htmlContent.append("<p>Match ID: ").append(matchId).append("</p>");
                htmlContent.append("<h2>Players</h2>");
                htmlContent.append("<ul>");

                for (Object playerObj : players) {
                    JSONObject playa = (JSONObject) playerObj;
                    JSONObject playaDetails = (JSONObject) playa.get("Player");
                    String gamatag = (String) playaDetails.get("Gamertag");
                    long totalKillz = (Long) playa.get("TotalKills");
                    long totalAssistz = (Long) playa.get("TotalAssists");
                    long totalDeathz = (Long) playa.get("TotalDeaths");
                    long ranc = (Long) playa.get("Rank");
                    long rezult = (Long) playa.get("Result");
                    



                    htmlContent.append("<li>");
                    htmlContent.append("<p>Gamertag: ").append(gamatag).append("</p>");
                    htmlContent.append("<p>Total Kills: ").append(totalKillz).append("</p>");
                    htmlContent.append("<p>Total Assists: ").append(totalAssistz).append("</p>");
                    htmlContent.append("<p>Total Deaths: ").append(totalDeathz).append("</p>");
                    htmlContent.append("<p>Rank: ").append(ranc).append("</p>");
                    htmlContent.append("<p>Result: ").append(rezult).append("</p>");
                    htmlContent.append("</li>");
                }

                htmlContent.append("</ul>");
                htmlContent.append("<div class='image-container'>");
                htmlContent.append("<img src='").append(playerEmblemImageUrl).append("' alt='Image 1'>");
                htmlContent.append("</div>");
                htmlContent.append("</body></html>");

                // Write HTML content to file
                try (FileWriter fileWriter = new FileWriter(
                        "C:\\devl\\HaloAPI\\HaloMaven\\haloapi\\target\\GeneratedFiles\\match_history.html")) {
                    fileWriter.write(htmlContent.toString());
                }

                System.out.println("HTML file generated successfully.");

            }

            else {
                System.out.println("No matches found.");
            }
            ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}