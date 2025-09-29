package com.haloapi;

import org.json.JSONObject;

// Main class to call other classes that make the calls to the Halo API and return results

//json parser
import org.json.simple.parser.ParseException;

//IO exception handling
import java.io.IOException;

//Want to create a form/interface for a user to choose which item to return with a prompt to enter users name

public class Main {

        public static void main(String[] args) throws IOException, ParseException {

                // Set properties, will be moved to an app.properties or secret eventually

                String gamerTag = "/Jeh%20Sea%20548";
                String subKey = "d53f21e540a3419ba28d208d631b6501";
                String apiBaseUrl = "https://www.haloapi.com";
                String apiCategory = "";
                String apiSubCategory = "";
                String apiResourceName = "";
                String apiParam = "";
                String gameName = "";

                // create Profile object and call the 3 api's under this category
                Profile profile = new Profile();

                // player emblem
                // "https://www.haloapi.com/profile/h5/profiles/" + gamerTag + "/emblem";
                apiCategory = "/profile";
                apiSubCategory = "/profiles";
                apiResourceName = "/emblem";
                gameName = "/h5";
                //String playerEmblemImageUrl = profile.getPlayerEmblem(apiBaseUrl, apiCategory, gameName, apiSubCategory,
                  //              gamerTag, apiResourceName, subKey);

                // player appearance
                apiResourceName = "/appearance";
                // String playerAppearance = profile.getPlayerAppearance(apiBaseUrl,
                // apiCategory, gameName, apiSubCategory,
                // gamerTag, apiResourceName, subKey);

                JSONObject playerAppearance = new JSONObject(
                               profile.getPlayerAppearance(apiBaseUrl, apiCategory, gameName, apiSubCategory,
                                                gamerTag, apiResourceName, subKey));

                //System.out.println(playerAppearance);

                // spartan image
                apiResourceName = "/spartan";
                //String spartanImageUrl = profile.getSpartanEmblem(apiBaseUrl, apiCategory, gameName, apiSubCategory,
                            //    gamerTag, apiResourceName, subKey);

                // create metadataH5 object and call 21 api's
                // Metadata metadata = new Metadata();
                // metadata.getMetaCampaign();

                // create metadataHW2 object and call 16 api's
                // Metadata metadata = new Metadata();
                // metadata.getMetaCampaign();

                // create statsH5 object and call 16 api's
                Metadata metadata = new Metadata();
                apiCategory = "/metadata";
                apiSubCategory = "/metadata";
                apiParam = "/?count=1";
                apiResourceName = "/campaign-missions";
                gameName = "/h5";  
                //metadata.getMetaCampaign(apiBaseUrl,apiCategory, gameName,apiSubCategory, apiResourceName, apiParam, subKey);
                JSONObject metadataCampaign = 
                //needs to be a json array i think
                new JSONObject(metadata.getMetaCampaign(apiBaseUrl, apiCategory, gameName, apiSubCategory,
                                               apiResourceName, subKey));

                
                //System.out.println(metadataCampaign);
                // create statsHW2 object and call 9 api's
                Stats stat = new Stats(); 
                apiCategory = "/stats";
                apiSubCategory = "/players";
                apiParam = "/?count=1";
                apiResourceName = "/matches";
                gameName = "/h5";
                //stat.getPlayerMatchHistory(playerAppearance, playerEmblemImageUrl, spartanImageUrl, apiBaseUrl,
                             //   apiCategory, gameName,
                             //   apiSubCategory, gamerTag, apiResourceName, apiParam, subKey);

        }
}