/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matevzfa.osrsclient.rsclient.coregui;

//import net.miginfocom.swing.MigLayout;
import org.pircbotx.exception.IrcException;
import com.matevzfa.osrsclient.rsloader.Loader;
import com.matevzfa.osrsclient.rsloader.Loader.Game;
import com.matevzfa.osrsclient.rsreflection.Reflector;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * @author ben
 */
public class RSClient {

    public static Reflector reflector = null;

    public static void main(String[] args) throws IrcException, IOException {

        initUI();
    }

    public static void initUI() {
        JFrame mainwnd = new JFrame("Vanilla Old School RuneScape Client");

        mainwnd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainwnd.setMinimumSize(new Dimension(781, 542));

        JPanel mainpanel = new JPanel(new GridLayout());
        mainpanel.setBackground(Color.black);
//        mainpanel.setPreferredSize(new Dimension(765, 503));
        mainpanel.setMinimumSize(new Dimension(765, 503));

        System.out.println(mainwnd.getWidth());
        System.out.println(mainwnd.getHeight());

        mainwnd.getContentPane().add(mainpanel);
//        JPanel gamepanel = new JPanel(new GridLayout());
//        gamepanel.setBackground(Color.gray);
//        gamepanel.setPreferredSize(new Dimension(765, 503));
//
//        gamepanel.setVisible(true);
//        mainpanel.add(gamepanel);
//        gamepanel.setVisible(true);

        mainpanel.setVisible(true);
//        mainpanel.setPreferredSize(new Dimension(765, 503));
//        mainwnd.pack();

//        mainwnd.setResizable(false);
        mainwnd.setVisible(true);
        mainwnd.pack();

        try {
            final Loader loader = new Loader(Game.OSRS);
            mainpanel.add(loader.applet);
            loader.applet.resize(765, 503);
            reflector = loader.loader;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
}
