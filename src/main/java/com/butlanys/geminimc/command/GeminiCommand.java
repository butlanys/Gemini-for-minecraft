package com.butlanys.geminimc.command;

import com.butlanys.geminimc.data.WorldData;
import com.butlanys.geminimc.service.GeminiService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class GeminiCommand {

    private static final GeminiService geminiService = new GeminiService();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gemini")
                .then(Commands.literal("help")
                        .executes(context -> {
                            context.getSource().sendSuccess(() -> Component.literal("--- GeminiMC Help ---"), false);
                            context.getSource().sendSuccess(() -> Component.literal("/gemini <prompt> - Send a prompt to Gemini"), false);
                            context.getSource().sendSuccess(() -> Component.literal("/gemini setkey <key> - Set your API Key"), false);
                            context.getSource().sendSuccess(() -> Component.literal("/gemini setbaseurl <url> - Set the API Base URL"), false);
                            context.getSource().sendSuccess(() -> Component.literal("/gemini setmodel <model> - Set the model name"), false);
                            return 1;
                        })
                )
                .then(Commands.literal("setkey")
                        .then(Commands.argument("key", StringArgumentType.greedyString())
                                .executes(context -> {
                                    ServerLevel level = context.getSource().getLevel();
                                    WorldData worldData = WorldData.get(level);
                                    worldData.apiKey = StringArgumentType.getString(context, "key");
                                    worldData.setDirty();
                                    context.getSource().sendSuccess(() -> Component.literal("API Key set successfully!"), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("setbaseurl")
                        .then(Commands.argument("url", StringArgumentType.greedyString())
                                .executes(context -> {
                                    ServerLevel level = context.getSource().getLevel();
                                    WorldData worldData = WorldData.get(level);
                                    worldData.apiBaseUrl = StringArgumentType.getString(context, "url");
                                    worldData.setDirty();
                                    context.getSource().sendSuccess(() -> Component.literal("API Base URL set successfully!"), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("setmodel")
                        .then(Commands.argument("model", StringArgumentType.greedyString())
                                .executes(context -> {
                                    ServerLevel level = context.getSource().getLevel();
                                    WorldData worldData = WorldData.get(level);
                                    worldData.modelName = StringArgumentType.getString(context, "model");
                                    worldData.setDirty();
                                    context.getSource().sendSuccess(() -> Component.literal("Model Name set successfully!"), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.argument("prompt", StringArgumentType.greedyString())
                        .executes(context -> {
                            String prompt = StringArgumentType.getString(context, "prompt");
                            ServerLevel level = context.getSource().getLevel();
                            WorldData worldData = WorldData.get(level);
                            context.getSource().sendSuccess(() -> Component.literal("Sending to Gemini: " + prompt), true);

                            geminiService.getResponse(prompt, worldData).thenAccept(response -> {
                                context.getSource().getServer().execute(() -> {
                                    try {
                                        // Clean up the response to extract only the JSON part
                                        String cleanedResponse = response;
                                        int firstBrace = cleanedResponse.indexOf('{');
                                        int lastBrace = cleanedResponse.lastIndexOf('}');
                                        if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
                                            cleanedResponse = cleanedResponse.substring(firstBrace, lastBrace + 1);
                                        }
                                        
                                        JsonObject jsonObject = JsonParser.parseString(cleanedResponse).getAsJsonObject();
                                        String command = jsonObject.get("command").getAsString();
                                        String chat = jsonObject.get("chat").getAsString();

                                        if (command != null && !command.isEmpty()) {
                                            // Use performPrefixedCommand which expects the leading '/'
                                            context.getSource().getServer().getCommands().performPrefixedCommand(context.getSource(), command.trim());
                                        }

                                        if (chat != null && !chat.isEmpty()) {
                                            context.getSource().sendSuccess(() -> Component.literal(chat), false);
                                        }
                                    } catch (Exception e) {
                                        context.getSource().sendFailure(Component.literal("Error processing Gemini response: " + e.getMessage()));
                                    }
                                });
                            });

                            return 1;
                        })
                )
        );
    }
}