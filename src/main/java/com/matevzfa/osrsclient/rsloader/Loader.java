package com.matevzfa.osrsclient.rsloader;

import com.matevzfa.osrsclient.config.WindowConfig;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loader implements AppletStub {

    public static final String CLIENT_FILES_DIR = System.getProperty("user.home") + "/.osrs-client/";
    public static final File GAMEPACK = new File(CLIENT_FILES_DIR + "gamepack.jar");
    public static final String GAME_URL = "http://oldschool" + WindowConfig.getDefaultWorld() + ".runescape.com/";

    private Applet applet;

    private HashMap<String, String> parameters = new HashMap<>();
    private URL gamePack;
    private String mainClass;

    public Loader() {

        try {
            this.getParams(new URL(GAME_URL));
            URLClassLoader loader = new URLClassLoader(new URL[]{GAMEPACK.toURI().toURL()});

            applet = (Applet) loader.loadClass(mainClass).newInstance();
            applet.setStub(this);
            applet.init();
            applet.setLayout(null);
            applet.setBounds(0, 0, 765, 503);
            applet.resize(765, 503);
            applet.start();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    public Applet getApplet() {
        return applet;
    }

    private void getParams(URL url) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        List<String> params = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {

            if (line.contains("param name"))
                params.add(line);

            if (gamePack == null && line.contains("archive"))
                gamePack = new URL(GAME_URL + line.substring(line.indexOf("archive=") + 8, line.indexOf(" ');")));

            if (mainClass == null && line.contains("code="))
                mainClass = line.substring(line.indexOf("code=") + 5, line.indexOf(".class"));
        }

        reader.close();

        params.forEach(s -> parameters.put(getParamName(s), getParamValue(s)));
    }

    private String getParamName(String param) {
        try {
            int startIndex = param.indexOf("<param name=\"") + 13;
            int EndIndex = param.indexOf("\" value");
            return param.substring(startIndex, EndIndex);
        } catch (StringIndexOutOfBoundsException e) {
            int startIndex = param.indexOf("<param name=") + 12;
            int endIndex = param.indexOf(" value");
            return param.substring(startIndex, endIndex);
        }
    }

    private String getParamValue(String param) {
        try {
            int StartIndex = param.indexOf("value=\"") + 7;
            int EndIndex = param.indexOf("\">');");
            return param.substring(StartIndex, EndIndex);
        } catch (StringIndexOutOfBoundsException e) {
            int StartIndex = param.indexOf("value=") + 6;
            int EndIndex = param.indexOf(">');");
            return param.substring(StartIndex, EndIndex);
        }
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL(GAME_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(GAME_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getParameter(String arg0) {
        return parameters.get(arg0);
    }

    @Override
    public AppletContext getAppletContext() {
        return null;
    }

    @Override
    public void appletResize(int arg0, int arg1) {
    }
}
