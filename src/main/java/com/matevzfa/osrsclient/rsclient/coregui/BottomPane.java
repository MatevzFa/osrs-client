/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.matevzfa.osrsclient.rsclient.coregui;

import com.matevzfa.osrsclient.rsclient.notes.NotesPanel;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import com.matevzfa.osrsclient.rsclient.chat.IrcPanel;

/**
 *
 * @author ben
 */
public class BottomPane extends JTabbedPane {

    public BottomPane() {
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        setTabPlacement(RIGHT);

        ImageIcon settingsicon = new ImageIcon(
                getClass().getClassLoader().getResource("image2020.png"));

        ImageIcon notesicon = new ImageIcon(
                getClass().getClassLoader().getResource("pencil.png"));

        ImageIcon chaticon = new ImageIcon(
                getClass().getClassLoader().getResource("chaticon.png"));

        ImageIcon miscicon = new ImageIcon(
                getClass().getClassLoader().getResource("miscicon.png"));

        
        addTab(null, notesicon, new NotesPanel());
        addTab(null, chaticon, new IrcPanel());
        addTab(null, miscicon, new JPanel());
        addTab(null, settingsicon, new JPanel());

    }

}
