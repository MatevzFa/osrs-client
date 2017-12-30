package com.matevzfa.osrsclient.rsclient.coregui;

import com.matevzfa.osrsclient.rsloader.Loader;
import com.matevzfa.osrsclient.rsloader.Updater;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RSClient {

    private static final int GAME_WIDTH = 765;
    private static final int GAME_HEIGHT = 503;

    public static void main(String[] args) {
        try {
            Updater.checkUpdate();
            initUI();
        } catch (IOException e) {
            System.err.println("Error while updating gamepack: " + e.getMessage());
        }
    }

    private static void initUI() {

        JFrame mainwnd = new JFrame("Old School RuneScape");

        mainwnd.setIconImages(
                Stream.of("icon_16.png", "icon_32.png", "icon_64.png", "icon_128.png")
                      .map(RSClient::imageFromFile)
                      .collect(Collectors.toList()));


        mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainwnd.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        JPanel mainpanel = new JPanel();
        mainpanel.setBackground(Color.black);
        mainpanel.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        mainwnd.getContentPane().add(mainpanel);
        mainpanel.setVisible(true);
        mainwnd.pack();

        int mainwndMinWidth = GAME_WIDTH + (mainwnd.getWidth() - mainpanel.getWidth());
        int mainwndMinHeight = GAME_HEIGHT + (mainwnd.getHeight() - mainpanel.getHeight());
        mainwnd.setMinimumSize(new Dimension(mainwndMinWidth, mainwndMinHeight));

        mainwnd.setVisible(true);

        try {
            final Loader loader = new Loader();
            mainpanel.add(loader.getApplet());
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
