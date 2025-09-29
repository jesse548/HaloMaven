package com.haloapi;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Base64;

public class DocuSignApi {
    private static final String AUTH_HEADER = "Basic " + Base64.getEncoder().encodeToString("YOUR_ACCOUNT_ID:YOUR_API_KEY".getBytes());
    private static final String DOCUSIGN_API_URL = "https://demo.docusign.net/restapi/v2.1/accounts/YOUR_ACCOUNT_ID/envelopes";
    private static final String FAKE_API_ENDPOINT = "https://fakeapi.com/endpoint";

    public static void main(String[] args) {
        try {
            // Connect to DocuSign
            URL url = new URL(DOCUSIGN_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", AUTH_HEADER);

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Forward to fake API endpoint
            URL fakeUrl = new URL(FAKE_API_ENDPOINT);
            HttpURLConnection fakeConn = (HttpURLConnection) fakeUrl.openConnection();
            fakeConn.setRequestMethod("POST");
            fakeConn.setRequestProperty("Content-Type", "application/json");
            fakeConn.setDoOutput(true);

            OutputStream os = fakeConn.getOutputStream();
            os.write(content.toString().getBytes("UTF-8"));
            os.close();

            // Check response from fake API
            int responseCode = fakeConn.getResponseCode();
            System.out.println("Response Code from fake API: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
