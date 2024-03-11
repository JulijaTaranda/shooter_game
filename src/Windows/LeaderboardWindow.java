package Windows;

import Database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class LeaderboardWindow extends JFrame {
    private ArrayList<String> username = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private JButton backButton;
    Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
    //Constructor
    public LeaderboardWindow() {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Leaderboard");
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setLayout(null);
        setFocusable(true);
        setCursor(cursor);
        createUI();
        repaint();
    }
    //Creates UI
    private void createUI(){
        //Adding lable
        JLabel leaderboardLabel = new JLabel("<html>" +
                "<h1>2D Shooter TOP 10</h1>" +
                "</html>");
        leaderboardLabel.setBounds(170, 60, 200,40);
        leaderboardLabel.setSize(leaderboardLabel.getPreferredSize());
        add(leaderboardLabel);
        //Calling for method to create leaderboard
        createLeaderboard();
        //Initializing, setting size and adding button
        backButton = new JButton("Back to Main Menu");
        backButton.setBounds(170, 410, 200, 40);
        add(backButton);
        //Adding functionality to button
        backButton.addActionListener(e ->backToMenu());
    }
    //Method for creating leaderboard
    private void createLeaderboard(){
        //Sorts database by score and selects first 10 players with!
        String store = "SELECT score, username FROM users ORDER BY score DESC LIMIT 10";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(store)) {

            ResultSet rs = pstmt.executeQuery(store);

            while (rs.next()) {
                username.add(rs.getString("username"));
                score.add(rs.getInt("score"));
            }
        }catch (SQLException | ClassNotFoundException ex){
            JOptionPane.showMessageDialog(this, "Error during registration: " + ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
        //Adding leaderboard labels
        JLabel tableLabel = new JLabel("Position           " + "Score             " + "Username    ");
        tableLabel.setBounds(180, 100,250,25);
        add(tableLabel);

        //Iterrates trough arraylist of top 10 players from mysql and for each player creates a label
        for( int i = 0; i < username.size() ; i++){
            addLabel(i+1, username.get(i),score.get(i), 120+(20*i));
        }
    }
    //Method for adding a lable for players in top10
    private void addLabel(int place, String username, int score, int position){
        JLabel placeLabel = new JLabel("    " + place + ".                    " + score + "                    " + username);
        placeLabel.setBounds(180, position,250,25);
        add(placeLabel);
    }
    //Method for going back to Main Menu
    private void backToMenu(){
        StartWindow startWindow = new StartWindow();
        startWindow.setVisible(true);
        dispose();
    }
}
