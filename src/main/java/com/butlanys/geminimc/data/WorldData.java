package com.butlanys.geminimc.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class WorldData extends SavedData {

    public String apiKey = "";
    public String apiBaseUrl = "https://generativelanguage.googleapis.com/v1beta/models/";
    public String modelName = "gemini-pro";

    public WorldData() {
    }

    public WorldData(CompoundTag tag) {
        apiKey = tag.getString("apiKey");
        apiBaseUrl = tag.getString("apiBaseUrl");
        modelName = tag.getString("modelName");
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putString("apiKey", apiKey);
        compoundTag.putString("apiBaseUrl", apiBaseUrl);
        compoundTag.putString("modelName", modelName);
        return compoundTag;
    }

    public static WorldData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(WorldData::new, WorldData::new, "geminimc_world_data");
    }
}