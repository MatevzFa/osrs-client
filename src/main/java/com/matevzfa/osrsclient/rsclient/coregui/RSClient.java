package com.matevzfa.osrsclient.rsclient.coregui;

import com.matevzfa.osrsclient.rsloader.Loader;
import com.matevzfa.osrsclient.rsloader.Updater;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RSClient {

    private static final int GAME_WIDTH = 765;
    private static final int GAME_HEIGHT = 503;

    public static void main(String[] args) throws InterruptedException {
        try {
            Updater.checkUpdate();
            initUI();
        } catch (IOException e) {
            System.err.println("Error while updating gamepack: " + e.getMessage());
        }
    }

    private static void initUI() throws InterruptedException {

        JFrame mainwnd = new JFrame("Old School RuneScape");

        mainwnd.setIconImages(Stream.of("icon_16.png", "icon_32.png", "icon_64.png", "icon_128.png")
                                    .map(RSClient::imageFromFile)
                                    .collect(Collectors.toList()));

        mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainwnd.getContentPane().setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        JPanel mainpanel = new JPanel(new GridLayout());
        mainpanel.setBackground(Color.black);
        mainpanel.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        mainwnd.add(mainpanel);
        mainwnd.getContentPane().setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        mainwnd.pack();

        Insets insets = mainwnd.getInsets();
        mainwnd.setMinimumSize(new Dimension(GAME_WIDTH + insets.left + insets.right,
                                             GAME_HEIGHT + insets.top + insets.bottom));

        mainwnd.setVisible(true);

        try {
            final Loader loader = new Loader();
            Applet applet = loader.getApplet();
            // sleep for 0.5s so it doesn't hang on Ubuntu (tested on 17.10)
            Thread.sleep(500);
            mainpanel.add(applet);
            loader.getApplet().resize(GAME_WIDTH, GAME_HEIGHT);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage imageFromFile(String imgName) {
        try {
            return ImageIO.read(RSClient.class.getResourceAsStream("/" + imgName));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
