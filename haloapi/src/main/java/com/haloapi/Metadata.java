package com.haloapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class Metadata {

    public String getMetaCampaign(String apiBaseUrl,
            String apiCategory, String gameName, String apiSubCategory,
            String apiResourceName, String subKey) throws IOException, ParseException {

        // String getUrl =
        // "https://www.haloapi.com/metadata/h5/metadata/campaign-missions";
        // String getUrl =
        // "https://www.haloapi.com/metadata/h4/metadata/campaign-missions";
        String getUrl = apiBaseUrl + apiCategory + gameName + apiSubCategory + apiResourceName;
        java.net.URL httpUrl = new java.net.URL(getUrl);
        HttpURLConnection request = (HttpURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", subKey);
        request.connect();
        request.getContent();
        int responseCode = request.getResponseCode();
        String responseMessage = request.getResponseMessage();
        System.out.println("Response code = " + responseCode);
        System.out.println("Response message = " + responseMessage);

        String string = "";
        Scanner scanner = new Scanner(request.getInputStream());
        while (scanner.hasNextLine()) {
            string += scanner.nextLine();
        }
        scanner.close();
        System.out.println(string);
        return string;
    }
}