import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel {
    //islaiciga atmina, arraylist
    private ArrayList<Block> blocks = new ArrayList<>();
    private int score = 0;
    //default score =0
    private boolean gameActive = false;
    //default spele nav aktiva sakumam
    private int timeLeft = 30;
    private Random rand = new Random();
    private Timer timer;
    //importesim no Java Swing
    private JButton startButton = new JButton("Start Game");

    public GamePanel() {
        setLayout(null);
        setFocusable(true);

        //lai ker kliksanu uz to lauku
        startButton.setBounds(350, 250, 100, 50);
        add(startButton);
        //add vajag lai pievienot pogu panelim

        timer = new Timer(1000, new ActionListener() {
            //delay - cik atri tas darbosies
            @Override
            public void actionPerformed(ActionEvent e) {
                //parbaude, vai laiku vajag samazinat vai spele ir beigusies
                if (timeLeft > 0) {
                    timeLeft--;
                } else {
                    gameActive = false;
                    timer.stop();
                    startButton.setVisible(true);
                }
                repaint();
            }
        });

        // start game
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameActive) {
                    //ja spele nav aktiva, padarisim vinu aktiivu
                    gameActive = true;
                    score = 0;
                    timeLeft = 30;
                    blocks.clear();
                    startButton.setVisible(false);

                    timer.start();

                    generateBlock();

                    repaint();
                }
            }
        });

        // mouse action funkcija
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameActive) return;

                Iterator<Block> iterator = blocks.iterator();
                //iterator - objekts, block - tips
                //bloku saglabajam araylistee, tad mes gribam iet cauri arailistei
                //ciklejam cauri, iterator satur tad katru bloku, kas mums bija

                while (iterator.hasNext()) {
                    //kamer ir vertiba (hasnext)
                    Block block = iterator.next();

                    // te vins saprot, ka e - event(klikskis uz bloka) ir tiesi klikskis uz taa bloka no arraylist
                    if (block.rect.contains(e.getPoint())) {
                        iterator.remove();
                        shootingSound();
                        if (block.color.equals(Color.RED.darker())) { // Check if it's a special block
                            timeLeft += 2; // Increase time left by 2 seconds
                        }
                        //powerUpBlock();
                        score++;
                        //ja mes nokeram klikski uz taa bloka, tad notiek tas kas auksaa
                        generateBlock();
                        break;
                    }
                }
                repaint();
            }
        });


    }

    private void shootingSound(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/ShootinSound/shotgun-firing-3-14483.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    //ta funkcija jau ir defineta java swing, izpildisies automatiski
    //overlays, gredu kartosanas
    //bus jau gatava, kada ir hierarhija

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //super - seit mes izsaucam kadu funkcionalitati no parent komponentes
        //parent ir paintComponents komponente java Swing
        //izdesis visu, kas bija uz panela, iztirisim bet ne vizuali, bet to ko mes neredzam

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Rezultāts: " + score, 10, 20);
        //atradisies kreisaja augsejaa sturii
        g.drawString("Atlikušais laiks: " + timeLeft, 10, 40);

        if (!gameActive && timeLeft == 0) {
            g.drawString("Laiks beidzies! Rezultāts: " + score, getWidth() / 2 - 100, getHeight() / 2);
        }

        for (Block block : blocks) {
            g.setColor(block.color);
            g.fillRect(block.rect.x, block.rect.y, block.rect.width, block.rect.height);
        }
    }


    private void generateBlock() {
        if (!gameActive) return;
        //ja spele nav aktiva, tad mes neko nedaram programma, agtriezam atpakal
        //apstasies viss process
        int x = rand.nextInt(Math.max(1, this.getWidth() - 50));
        int y = rand.nextInt(Math.max(1, this.getHeight() - 50));
        //-50, lai vins negenerejas pie pasam malam, bus atstarpite

        int width = 20 + rand.nextInt(81);
        int height = 20 + rand.nextInt(81);
        //no 20 lidz 81

        Color color;
        if (rand.nextInt(10) == 0) { // 1 in 10 chance for a special block
            color = Color.RED.darker(); // Dark red color for special block
        } else {
            //iedevam 3 krasas konstruktoram rgb
            color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        }
        blocks.add(new Block(new Rectangle(x, y, width, height), color));
        //te rectangle ir tips
    }


    //klase ieks klases
    private static class Block {
        Rectangle rect;
        Color color;
        //color objekts


        //constructor
        Block(Rectangle rect, Color color) {
            this.rect = rect;
            this.color = color;
        }
    }
}