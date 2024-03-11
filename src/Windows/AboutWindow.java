package Windows;

import javax.swing.*;
import java.awt.*;

public class AboutWindow extends JFrame {

    private JButton backButton;
    Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
    public AboutWindow(){
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("About");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setLayout(null);
        setFocusable(true);
        setCursor(cursor);
        createUI();
        repaint();
    }
    //Method for creating UI
    private void createUI(){
        //Adding label
        JLabel instructionLabel = new JLabel(
                "<html> " +
                        "<h1>About 2D Shooter</h1><br>" +
                        "<h2> How to play 2D Shooter</h2><br>" +
                        "* Your goal is to shoot as many enemies as you can in 30 seconds to get the highest score! <br>" +
                        "* Enemies in 2D Shooter are represented as rectangles in various colors.<br>" +
                        "* To shoot the enemy simply click on the rectangle. <br> " +
                        "* Once current enemy is shot it will disappear and a new enemy will spawn.<br>" +
                        "* In addition the game will spawn allies (displayed as rectangles in dark red color).<br> " +
                        "* Clicking on allies will add additional 2 seconds to your game time<br>" +
                        "<h2>What is 2D Shooter and why it exists</h2><br>"+
                        "2D Shooter is a simple and fun game!<br>" +
                        "Created by Jūlija Taranda and Oskars Staļģis<br>" +
                        "for Riga Coding school as a final project!<br>" +
                        "<h1>Good luck and have fun!</h1><br>"+
                        "</html>"
        );
        instructionLabel.setBounds(30,10, 100,100);
        instructionLabel.setSize(instructionLabel.getPreferredSize());
        add(instructionLabel);
        //Initializing, setting size and adding button
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(170, 410, 200, 40);
        add(backButton);
        //Adding functionality to button
        backButton.addActionListener(e -> backToMenu());

    }
    //Method for going back to Main Menu
    private void backToMenu(){
        StartWindow startWindow = new StartWindow();
        startWindow.setVisible(true);
        dispose();
    }
}