package com.butlanys.geminimc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GeminiMC.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    // We will store the API key in memory only for security
    public static String apiKey = "";
    public static String apiBaseUrl = "https://generativelanguage.googleapis.com/v1beta/models/";
    public static String modelName = "gemini-pro";
}
