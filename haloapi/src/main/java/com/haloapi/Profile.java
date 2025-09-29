//Profile API resource has 3 endpoints
//###Player Appearance
//###Player Emplem Image
//###Player Spartan Image

package com.haloapi;

import javax.net.ssl.HttpsURLConnection;

import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;

public class Profile {

    public String getPlayerAppearance(String apiBaseUrl, String apiCategory, String gameName, String apiSubCategory,
    String gamerTag, String apiResourceName, String subKey)
    throws IOException {

        String getUrl = apiBaseUrl + apiCategory + gameName + apiSubCategory + gamerTag + apiResourceName;
        //String getUrl = "https://www.haloapi.com/profile/h5/profiles/" + gamerTag + "/appearance";
        java.net.URL httpUrl = new java.net.URL(getUrl);
        HttpsURLConnection request = (HttpsURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", "d53f21e540a3419ba28d208d631b6501");
        request.connect();
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
        return string;
    }

    public String getPlayerEmblem(String apiBaseUrl, String apiCategory, String gameName, String apiSubCategory,
            String gamerTag, String apiResourceName, String subKey)
            throws IOException {
        String getUrl = apiBaseUrl + apiCategory + gameName + apiSubCategory + gamerTag + apiResourceName;
        java.net.URL httpUrl = new java.net.URL(getUrl);
        HttpsURLConnection request = (HttpsURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", "d53f21e540a3419ba28d208d631b6501");
        request.connect();
        int responseCode = request.getResponseCode();
        String responseMessage = request.getResponseMessage();
        System.out.println("Response code = " + responseCode);
        System.out.println("Response message = " + responseMessage);
        System.out.println(request.getURL().toString());
        String emblemUrl = request.getURL().toString();
        return emblemUrl;
    }

    public String getSpartanEmblem(String apiBaseUrl, String apiCategory, String gameName, String apiSubCategory,
            String gamerTag, String apiResourceName, String subKey)
            throws IOException {
        //String getUrl = "https://www.haloapi.com/profile/h5/profiles/" + gamerTag + "/spartan";
        String getUrl = apiBaseUrl + apiCategory + gameName + apiSubCategory + gamerTag + apiResourceName;
        java.net.URL httpUrl = new java.net.URL(getUrl);
        HttpsURLConnection request = (HttpsURLConnection) httpUrl.openConnection();
        request.setRequestMethod("GET");
        request.setRequestProperty("Ocp-Apim-Subscription-Key", "d53f21e540a3419ba28d208d631b6501");
        request.connect();
        int responseCode = request.getResponseCode();
        String responseMessage = request.getResponseMessage();
        System.out.println("Response codes = " + responseCode);
        System.out.println("Response message = " + responseMessage);
        System.out.println(request.getURL().toString());
        String emblemUrl = request.getURL().toString();
        return emblemUrl;
    }

}