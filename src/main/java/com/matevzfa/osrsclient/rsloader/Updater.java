package com.matevzfa.osrsclient.rsloader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Updater extends JFrame {


    private static final long serialVersionUID = 1L;
    public static Updater window;
    public static boolean downloadComplete;
    public static int progress = 0;
    public static boolean downloadRequired = false;
    public static int maximum = 0;
    public static int size;
    public static Thread download = new Thread(new Runnable() {

        @Override
        public void run() {

            try {
                downloadGamePack();
                downloadRequired = false;
                downloadComplete = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    });
    private static JProgressBar progressBar;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public Updater() {
        setTitle("Update Required");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 387, 119);
        contentPane = new JPanel();
//        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
//        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 11, 349, 58);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblUpdatingGamepack = new JLabel("Updating Gamepack");
//        lblUpdatingGamepack.setForeground(Color.WHITE);
        lblUpdatingGamepack.setBounds(125, 11, 193, 14);
        panel.add(lblUpdatingGamepack);

        progressBar = new JProgressBar();
//        progressBar.setStringPainted(true);
//        progressBar.setBackground(Color.BLACK);
//        progressBar.setForeground(Color.ORANGE);
        progressBar.setBounds(10, 36, 329, 14);
        panel.add(progressBar);
    }

    public static JProgressBar getProgressBar() {
        return progressBar;
    }

    public static void setSelection(int selection) {
        progressBar.setValue(selection);
    }

    public static void checkUpdate() throws IOException {
        int localJarFileSize = localGamePackSize();
        int onlineJarFileSize = getFileSize(getLiveJarUrl());

        if (localJarFileSize == -1 || localJarFileSize != onlineJarFileSize) {
            download.start();
            try {
                window = new Updater();
                window.setVisible(true);
                progressBar.setMaximum(onlineJarFileSize);
                while (!downloadComplete) {
                    setSelection(progress);
                    progressBar.repaint();
                    Thread.sleep(200);
                }
                window.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadGamePack() throws UnsupportedEncodingException, IOException {
        BufferedInputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = getLiveJarUrl();
            URLConnection conn = url.openConnection();
            size = conn.getContentLength();
            Updater.maximum = size;
            if (size < 0) {
                System.out.println("Could not get the file size");
            } else {
                System.out.println("\nGamepack update required.");
            }
            File gamepackDirectory = new File(Loader.CLIENT_FILES_DIR);
            File gamepackFile = Loader.GAMEPACK;
            gamepackDirectory.mkdir();
            gamepackFile.createNewFile();
            in = new BufferedInputStream(url.openStream());
            out = new FileOutputStream(gamepackFile);
            byte[] data = new byte[1024];
            int count;
            double sumCount = 0.0;
            while ((count = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, count);
                sumCount += count;
                if (size > 0) {
                    Updater.progress = (int) (sumCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            Updater.downloadComplete = true;
        }
    }

    private static URL getLiveJarUrl() throws IOException {

        URL gamePack = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(Loader.GAME_URL).openStream()));

        String line;
        while ((line = reader.readLine()) != null) {

            if (gamePack == null && line.contains("archive"))
                gamePack = new URL(Loader.GAME_URL + line.substring(line.indexOf("archive=") + 8, line.indexOf(" ');")));
        }
        reader.close();

        return gamePack;
    }

    public static int localGamePackSize() {
        File file = Loader.GAMEPACK;
        return (int) file.length();
    }

    public static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }
}
