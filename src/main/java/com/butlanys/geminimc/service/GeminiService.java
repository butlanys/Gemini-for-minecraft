package com.butlanys.geminimc.service;

import com.butlanys.geminimc.data.WorldData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class GeminiService {

    private final HttpClient client = HttpClient.newHttpClient();

    public CompletableFuture<String> getResponse(String prompt, WorldData worldData) {
        if (worldData.apiKey == null || worldData.apiKey.isEmpty()) {
            return CompletableFuture.completedFuture("{\"command\":\"\", \"chat\":\"API Key not set. Use /gemini setkey <key> to set it.\"}");
        }
        String apiUrl = worldData.apiBaseUrl + worldData.modelName + ":generateContent?key=" + worldData.apiKey;
        String systemInstruction = "You are a Minecraft assistant. Your task is to translate natural language requests into a JSON object. The JSON object should have two fields: 'command' and 'chat'. 'command' must contain the full, executable Minecraft command including the leading '/'. 'chat' should contain a friendly message to the user, responding in the same language as the user's request. For example, if the user says 'give me a diamond sword', you must output: {\\\"command\\\": \\\"/give @p diamond_sword 1\\\", \\\"chat\\\": \\\"Here is your diamond sword!\\\"}";
        String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"" + systemInstruction + "\"}, {\"text\":\"" + prompt + "\"}]}]}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parseResponse);
    }

    private String parseResponse(String responseBody) {
        try {
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonElement textElement = jsonObject.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .get("content").getAsJsonObject()
                    .get("parts").getAsJsonArray()
                    .get(0).getAsJsonObject()
                    .get("text");
            return textElement.getAsString();
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }
}