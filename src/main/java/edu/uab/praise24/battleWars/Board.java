/*
 * File: Board.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  Board - EE333 Fall 2019
 * Vers: 1.0.0 09/26/2019 PAD - initial coding
 *
 * Credits:  (if any for sections of code)
 */
package edu.uab.praise24.battleWars;

//import static edu.uab.prause24.demo.Play.renderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel implements Actor {

    JPanel play = new JPanel();
    //private static Player player;
    static int width, height, oldWidth, oldHeight;
    static int done = 0;
    boolean changed;
    private static Board demo2 = Board.getInstance();
    static JFrame frame = new JFrame("Aim and Dodge");
    //final JFXPanel fxPanel = new JFXPanel();

    //static MyKeyListener listen = new MyKeyListener();
    private Board() {
    }

    public static Board getInstance() {
        return BoardHolder.INSTANCE;
    }

    @Override
    public int[] getPosition() {
        return new int[]{0};
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void paintComponent(GraphicsContext g){

    }

    private static class BoardHolder {
        private static final Board INSTANCE = new Board();
    }

    @Override
    public void update(){
        //

        if (frame.getBounds().height != frame.getBounds().width && width != frame.getBounds().width) {
            frame.setBounds(frame.getBounds().x, frame.getBounds().y, frame.getBounds().width, frame.getBounds().width);
            //System.out.println(frame.getBounds().height + "," + frame.getBounds().width);

            changed = true;
            //width = frame.getBounds().width;
            //height = frame.getBounds().width;
        }
        if (frame.getBounds().height != frame.getBounds().width && height != frame.getBounds().height) {
            frame.setBounds(frame.getBounds().x, frame.getBounds().y, frame.getBounds().height, frame.getBounds().height);
            //System.out.println(frame.getBounds().height + "," + frame.getBounds().width);
            changed = true;

        }
        oldWidth = width;
        oldHeight = height;
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (changed == true) {
            if (done == 10) {
                width = frame.getBounds().width;
                height = frame.getBounds().height;
                done = 0;
                widthChanged(false);
            }
            //System.out.println(height +"," + width + ";" + oldHeight + "," + oldWidth);
            done++;
        }

        //demo2.repaint();
    }

    @Override
    public String toString(){
        return "Board";
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void widthChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isWidthChanged() {
        return changed;
    }

    public void create() {
        //player = Player.getInstance();
        //System.out.println(player);
        //listen.defineTimers();
        
        frame.add(demo2);
        frame.setSize(600, 600);
        frame.setMaximumSize(new Dimension(700, 700));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        width = frame.getBounds().width;
        height = frame.getBounds().height;
        oldWidth = width;
        oldHeight = height;

        //}
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

    }
}
