package com.matevzfa.osrsclient.config;

import com.matevzfa.osrsclient.rsloader.Loader;

import javax.swing.*;
import java.awt.*;
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


    public static void storeWindowPosition(JFrame window) {
        Properties properties = getProperties();

        // game window location relative to (0,0)
        Point windowLocation = window.getLocation();

        GraphicsDevice screen = window.getGraphicsConfiguration()
                                      .getDevice();

        Rectangle screenBounds = screen.getDefaultConfiguration()
                                       .getBounds();

        // store window location relative to window.display's (0,0)
        properties.setProperty("window.display", String.valueOf(screen.getIDstring()));
        properties.setProperty("window.x", String.valueOf(windowLocation.x - screenBounds.x));
        properties.setProperty("window.y", String.valueOf(windowLocation.y - screenBounds.y));

        storeProperties(properties);
    }

    public static void loadWindowPosition(JFrame window) {
        Properties properties = getProperties();

        if (properties.getProperty("window.display") == null) {
            // no position settings
            centerWindow(window);
            return;
        }

        if (properties.getProperty("window.x") == null || properties.getProperty("window.y") == null) {
            // no position settings
            centerWindow(window);
            return;
        }

        String displayID = properties.getProperty("window.display");

        GraphicsDevice[] gs = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        for (GraphicsDevice gd : gs) {
            if (gd.getIDstring().equals(displayID)) {
                int x = Integer.valueOf(properties.getProperty("window.x"));
                int y = Integer.valueOf(properties.getProperty("window.y"));
                Rectangle displayBounds = gd.getDefaultConfiguration().getBounds();
                window.setLocation(x + displayBounds.x, y + displayBounds.y);
                return;
            }
        }

        // could not find stored display
        centerWindow(window);
    }

    private static void centerWindow(JFrame window) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
        window.setLocation(x, y);
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
