package com.loucaskreger.searchablecontainers.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.loucaskreger.searchablecontainers.SearchableContainers;

import java.io.*;

public class Config {

    private static final File configFolder = new File("config");
    private static File configFile;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static ConfigInstance INSTANCE;

    public static void init() {
        load();
        genFile();
        read();
        write();
    }

    private static void load() {
        INSTANCE = new ConfigInstance();

    }

    private static void genFile() {
        if (!configFolder.exists()) {
            configFolder.mkdir();
        }
        if (configFolder.isDirectory()) {
            configFile = new File(configFolder, SearchableContainers.MOD_ID + ".json");
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();
                    load();
                    String config = gson.toJson(INSTANCE);
                    FileWriter writer = new FileWriter(configFile);
                    writer.write(config);
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalStateException("Can't create config file", e);
                }
            } else if (configFile.isDirectory()) {
                throw new IllegalStateException(
                        "'" + SearchableContainers.MOD_ID + ".json' must be a file not a directory.");
            } else {
            }
        } else {
            throw new IllegalStateException("'config' must be a folder!");
        }

    }

    private static void read() {
        try {
            INSTANCE = gson.fromJson(new FileReader(configFile), ConfigInstance.class);
            if (INSTANCE == null) {
                throw new IllegalStateException("Null configuration");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("JSON error, invalid syntax.");
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        }
    }

    private static void write() {
        try {
            String json = gson.toJson(INSTANCE);
            FileWriter writer = new FileWriter(configFile, false);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new IllegalStateException("Can't update config file", e);
        }

    }

}
