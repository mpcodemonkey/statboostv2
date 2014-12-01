package com.statboost.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam Kerr
 * 7:09 PM on 11/30/2014
 */
public class MandrillUtil {
    private static final String API_KEY = "SEkTm2zp5JPbsxA7hV6DgQ";
    private static final String REST_URL = "https://mandrillapp.com/api/1.0/";


    /**
     *
     * @param templateName - Template Name or Slug Name
     * @param recipients - List of email addresses
     * @param mergeVarMap - map of email addresses to a list of merge variable pairs
     */
    public static void sendEmail(String templateName, List<String> recipients, Map<String, List<Pair>> mergeVarMap) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", API_KEY);
        jsonObject.addProperty("template_name", templateName);

        JsonArray templateContent = new JsonArray();
        jsonObject.add("template_content", templateContent);

        JsonObject message = new JsonObject();
        //compose recipients
        JsonArray toArray = new JsonArray();
        for (String recipient : recipients) {
            JsonObject to = new JsonObject();
            to.addProperty("email", recipient);
            toArray.add(to);
        }
        message.add("to", toArray);
        message.addProperty("auto_text", true);
        message.addProperty("preserve_recipients", false);

        //compose merge vars for each recipient
        JsonArray mergeVarArray = new JsonArray();
        for (String rcpt : mergeVarMap.keySet()) {
            JsonObject mergeVar = new JsonObject();
            mergeVar.addProperty("rcpt", rcpt);
            JsonArray vars = new JsonArray();
            for (Pair pair : mergeVarMap.get(rcpt)) {
                JsonObject p = new JsonObject();
                p.addProperty("name", (String)pair.getKey());
                p.addProperty("content", (String)pair.getValue());
                vars.add(p);
            }
            mergeVar.add("vars", vars);
            mergeVarArray.add(mergeVar);
        }
        message.add("merge_vars", mergeVarArray);

        jsonObject.add("message", message);

        System.out.println(jsonObject.toString());
        System.out.println(sendJsonToMandrill("messages/send-template.json", jsonObject.toString()));
    }


    public static String getTemplateList() {
        //create json payload
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", API_KEY);
        String payload = jsonObject.toString();

        return sendJsonToMandrill("templates/list.json", payload);
    }


    /**
     * This method makes a REST call to the Mandrill API.
     * @param method - The Mandrill function to be used
     * @param json - The JSON request to be sent to Mandrill
     * @return - Mandrill's JSON response
     */
    private static String sendJsonToMandrill(String method, String json) {
        String result = "";
        try {
            URL url = new URL(REST_URL + method);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            //write json to request
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            System.out.println("JSON REQUEST:\n" + json);
            writer.write(json);
            writer.close();

            //get response
            int HttpResult = connection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK){
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                result = ""+sb.toString();
            } else {
                result = connection.getResponseMessage();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
