package com.strong.blooddashboard.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class ApiRequest {

    public static JsonArray makeAPIGet(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return JsonParser.parseString(response.toString()).getAsJsonArray();

            }
        } else {
            String responseBody = readResponseBody(connection);
            throw new IOException("Unexpected response code: " + responseCode + ", Response body: " + responseBody);
        }
    }

    public static String makeAPIPost(String apiUrl, JsonObject jsonObject) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true); // Indicates that this request will have a body

        try (OutputStream outputStream = connection.getOutputStream()) {
            // Convert the JsonObject to a byte array and write it to the request body
            byte[] input = jsonObject.toString().getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Process the response if needed
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Handle the response as needed
            return response.toString();
        } else {
            return readResponseBody(connection);
            // throw new IOException("Unexpected response code: " + responseCode + ",
            // Response body: " + responseBody);
        }
    }

    private static String readResponseBody(HttpURLConnection connection) throws IOException {
        InputStream errorStream = connection.getErrorStream();

        if (errorStream == null) {
            return "Created Sucessfully";
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(errorStream))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return extractMessageFromJson(response.toString());
        }
    }

    private static String extractMessageFromJson(String jsonResponse) {
        try {
            JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return json.get("message").getAsString();
        } catch (JsonParseException | NullPointerException e) {
            return "Unexpected JSON format: " + jsonResponse;
        }
    }

}
