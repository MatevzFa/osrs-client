package com.matevzfa.osrsclient.config;

import com.matevzfa.osrsclient.rsloader.Loader;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WindowConfig {

    private static final String PROPERTIES_FILE = Loader.CLIENT_FILES_DIR + "properties";

    public static void storeWindowSize(JFrame window) {
        Properties properties = getProperties();

        properties.setProperty("window.width", String.valueOf(window.getWidth()));
        properties.setProperty("window.height", String.valueOf(window.getHeight()));

        storeProperties(properties);
    }

    public static void loadWindowSize(JFrame window) {
        Properties properties = getProperties();

        if (properties.getProperty("window.width") != null && properties.getProperty("window.height") != null) {
            int width = Integer.valueOf(properties.getProperty("window.width"));
            int height = Integer.valueOf(properties.getProperty("window.height"));
            window.setSize(width, height);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        try {
            File file = new File(PROPERTIES_FILE);
            file.createNewFile();
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return properties;
    }

    private static void storeProperties(Properties properties) {
        try {
            properties.store(new FileOutputStream(PROPERTIES_FILE), null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
