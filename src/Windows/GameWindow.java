package Windows;

import Panel.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
    public GameWindow() {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("2D Shooter");
        this.setResizable(false);
        this.add(new GamePanel());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setCursor(cursor);
    }
}