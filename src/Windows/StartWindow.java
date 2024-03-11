package Windows;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame {
    private JButton startButton;
    private JButton scoreButton;
    private JButton instructionsButton;
    private JButton quitButton;

    Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
//            StartWindow.setCursor(cursor);
//            StartWindow.setVisible(true);

    //Constructor
    public StartWindow() {
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("2D Shooter");
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setFocusable(true);
        setCursor(cursor);
        createUI();
        repaint();


    }
    //Creates UI
    private void createUI(){
        //Adding label to UI
        JLabel welcomeLabel = new JLabel("2D Shooter");
        welcomeLabel.setBounds(240, 130, 200, 40);
        add(welcomeLabel);
        //Initializing, setting size and adding buttons to UI
        startButton = new JButton("New Game");
        startButton.setBounds(180, 200, 200, 40);
        add(startButton);
        scoreButton = new JButton("Leaderboard");
        scoreButton.setBounds(180, 270, 200, 40);
        add(scoreButton);
        instructionsButton = new JButton("About 2D Shooter");
        instructionsButton.setBounds(180, 340, 200, 40);
        add(instructionsButton);
        quitButton = new JButton("Quit 2D Shooter");
        quitButton.setBounds(180, 410, 200, 40);
        add(quitButton);
        //Adding functionality to buttons
        startButton.addActionListener(e-> openGame());
        scoreButton.addActionListener(e-> openLeaderboard());
        instructionsButton.addActionListener(e-> openAbout());
        quitButton.addActionListener(e -> System.exit(0));
    }
    //Method to open GameWindow
    private void openGame(){
        GameWindow gameWindow = new GameWindow();

        gameWindow.setVisible(true);

        dispose();
    }
    //Method to open LeaderboardWindow
    private void openLeaderboard(){
        LeaderboardWindow leaderboardWindow = new LeaderboardWindow();
        leaderboardWindow.setVisible(true);
        dispose();
    }
    //Method to open AboutWindow
    private void openAbout(){
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.setVisible(true);
        dispose();
    }


}