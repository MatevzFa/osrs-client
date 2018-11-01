package com.matevzfa.osrsclient.rsclient.coregui;

import com.matevzfa.osrsclient.config.WindowConfig;
import com.matevzfa.osrsclient.rsloader.Loader;
import com.matevzfa.osrsclient.rsloader.Updater;
import lombok.extern.java.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class RSClient {

    private static final int GAME_WIDTH = 765;
    private static final int GAME_HEIGHT = 503;

    public static void main(String[] args) {
        try {
            Updater.checkUpdate();
            initUI();
        } catch (IOException e) {
            log.severe("Error while updating the gamepack: " + e.getMessage());
        }
    }

    private static void initUI() {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            log.warning(e.getMessage());
        }

        JFrame mainwnd = new JFrame("Old School RuneScape");

        mainwnd.setIconImages(Stream.of("icon_16.png", "icon_32.png", "icon_64.png", "icon_128.png")
                                    .map(RSClient::imageFromFile)
                                    .collect(Collectors.toList()));

        mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainwnd.getContentPane().setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        mainwnd.setVisible(true);

        JPanel mainpanel = new JPanel(new GridLayout());
        mainpanel.setBackground(Color.black);
        mainpanel.setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));


        try {
            final Loader loader = new Loader();
            Applet applet = loader.getApplet();
            mainpanel.add(applet);
            loader.getApplet().resize(GAME_WIDTH, GAME_HEIGHT);
        } catch (IllegalArgumentException e) {
            log.severe(e.getMessage());
        }

        mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainwnd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null, " Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    WindowConfig.storeWindowSize(mainwnd);
                    WindowConfig.storeWindowPosition(mainwnd);
                    mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    mainwnd.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });


        mainwnd.add(mainpanel);
        mainwnd.getContentPane().setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        mainwnd.pack();
        WindowConfig.loadWindowPosition(mainwnd);
        WindowConfig.loadWindowSize(mainwnd);
        mainwnd.setVisible(true);
        Insets insets = mainwnd.getInsets();
        log.fine(String.format("Insets: %d %d %d %d%n", insets.top, insets.right, insets.bottom, insets.left));
        mainwnd.setMinimumSize(new Dimension(GAME_WIDTH + insets.left + insets.right, GAME_HEIGHT + insets.top + insets.bottom));
    }

    private static BufferedImage imageFromFile(String imgName) {
        try {
            return ImageIO.read(RSClient.class.getResourceAsStream("/" + imgName));
        } catch (IOException e) {
            log.severe(e.getMessage());
            return null;
        }
    }
}
