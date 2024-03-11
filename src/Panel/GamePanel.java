package Panel;

import Database.DatabaseConnection;
import Windows.StartWindow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static com.sun.org.apache.xml.internal.serialize.LineSeparator.Windows;

public class GamePanel extends JPanel {
    //GamePanel class inherits all that have JPanel class, Swing
    private ArrayList<Block> blocks = new ArrayList<>();
    private int score = 0;
    private boolean gameActive = false;
    private int timeLeft = 30;
    private Random rand = new Random();
    private Timer timer;
    private JButton startButton = new JButton("Start Game");
    private JButton backButton = new JButton("Back to Main Menu");
    private JTextField usernameField;
    //Constructor
    public GamePanel() {
        setLayout(null);//to control position manually, not automatically
        setFocusable(true);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 340, 200, 40);
        add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(170, 340, 200, 40);
        add(usernameField);

        startButton.setBounds(170, 410, 200, 40);
        add(startButton);

        backButton.setBounds(170, 480, 200, 40);
        add(backButton);

        timer = new Timer(1000, new ActionListener() {
            @Override
            //annotation
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                } else {
                    gameActive = false;
                    timer.stop();
                    startButton.setVisible(true);
                    backButton.setVisible(true);
                    usernameLabel.setVisible(true);
                    usernameField.setVisible(true);
                }
                repaint();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // username field check
                String username = usernameField.getText();
                if(username.isEmpty()){
                    JOptionPane.showMessageDialog(usernameField, "Username is required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!gameActive) {
                    gameActive = true;
                    score = 0;
                    timeLeft = 30;
                    blocks.clear();
                    startButton.setVisible(false);
                    backButton.setVisible(false);
                    usernameLabel.setVisible(false);
                    usernameField.setVisible(false);

                    timer.start();

                    generateBlock();

                    repaint();
                }
            }
        });

        //Calls for opening of Main menu and closing of Game Window on click of the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);//getWindowAncestor(comp) - static method of swing class Utilities,that returns parent window
                StartWindow startWindow = new StartWindow();
                startWindow.setVisible(true);
                win.dispose();//close window, and delete from memory
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameActive) return;
                Iterator<Block> iterator = blocks.iterator();
                while (iterator.hasNext()) {
                    Block block = iterator.next();
                    if (block.rect.contains(e.getPoint())) {
                        iterator.remove();
                        shootingSound(); //shooting sound on click
                        if (block.color.equals(Color.RED.darker())) {
                            timeLeft += 2; // PowerUp block
                        }
                        score++;
                        generateBlock();
                        break;
                    }
                }
                repaint();
            }
        });
    }

    //shooting sound on click
    private void shootingSound(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/ShootinSound/shotgun-firing-3-14483.wav"));//Java Sound metode AudioSystem.getAudioInputStream
            Clip clip = AudioSystem.getClip();//clip dod iespeju izmantot skanas kontroles metodes - start, stop, open
            clip.open(audioInputStream);//atver clip izmantojot audioinputstream
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Rezultāts: " + score, 10, 20);
        g.drawString("Atlikušais laiks: " + timeLeft, 10, 40);

        if (!gameActive && timeLeft == 0) {
            addScore();
            g.drawString("Laiks beidzies! Rezultāts: " + score, getWidth() / 2 - 150, getHeight() / 2);
        }

        for (Block block : blocks) {
            g.setColor(block.color);
            g.fillRect(block.rect.x, block.rect.y, block.rect.width, block.rect.height);
        }
    }

    private void generateBlock() {
        if (!gameActive) return;
        int x = rand.nextInt(Math.max(1, this.getWidth() - 50));
        int y = rand.nextInt(Math.max(1, this.getHeight() - 50));
        int width = 20 + rand.nextInt(81);
        int height = 20 + rand.nextInt(81);


        //random color and PowerUp Block color
        Color color;
        if (rand.nextInt(10) == 0) { // 1 in 10 chance for a special block (for testing, if it works)
            color = Color.RED.darker(); // Dark red color for powerUp Block
        } else {
            color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }
        blocks.add(new Block(new Rectangle(x, y, width, height), color));
    }

    private static class Block {
        Rectangle rect;
        Color color;

        Block(Rectangle rect, Color color) {
            this.rect = rect;
            this.color = color;
        }
    }

    //Adds score to database
    public void addScore(){
        String username = usernameField.getText();

        //insert query
        String sql = "INSERT INTO users (username, score) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn. prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, score);

            pstmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException ex){
            JOptionPane.showMessageDialog(this, "Error during registration: " + ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

}