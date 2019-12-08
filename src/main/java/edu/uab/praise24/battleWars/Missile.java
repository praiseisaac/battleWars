/*
 * File: Missile.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.0.0 10/13/2019 PAD - initial coding
 *
 * Credits:  (if any for sections of code)
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.praise24.battleWars;
import static edu.uab.praise24.battleWars.Board.frame;
import javax.swing.JPanel;
import javafx.scene.paint.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
//import javax.swing.JComponent;


/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public class Missile implements Actor {
    Graphics g;
    double xc = 0, yc = 0, x1 = 0,
            y1 = 0, dx = 0, dy = 0, xn = 0, xnPrev, yn = 0,
            ynPrev, tHeight = 50, velocity = 5,theta;
    private static Board board = Board.getInstance();
    int index;
    static ArrayList<Missile> allMissiles = new ArrayList<>();
    boolean hit = false;
    static int id = 0;
    String ID;
    private static int missileID = 0;
    Thread t;
    boolean start = true;
    int missileSize = 0;
    
    Missile(String id){
        ID = id + " missile";
        missileID++;
        addMissile(this);
    }
    
    private void addMissile(Missile missile){
        allMissiles.add(missile);
    }
    
    public static void updateMissiles(){
        for (int i = 0;i < allMissiles.size();i++){
            if (allMissiles.get(i).isDestroyed()){
                allMissiles.get(i).destroy();
            }
        }
    }
    
    public static ArrayList<Missile> getMissiles(){
        return allMissiles;
    }

    public double[] startCoordinates(){
        double[] cords = new double[4];
        cords[0] = xn;
        cords[1] = yn;
        cords[3] = dx;
        cords[4] = dy;
        
        
        return cords;
    }
    
    @Override
    public String toString(){
        return ID;
    }
    
    // ==========NEEDS REVISION FOR JAVAFX
    

    @Override
    public void update(){
        //System.out.println(xn + "," + yn + "[]" + xc + "," + yc);
        xnPrev = xn;
        ynPrev = yn;
        if (xc > x1 && yc < y1) {
            yn = (yn - dy);
            xn = (xn + dx);
        }
        if (xc > x1 && yc > y1) {
            yn = (yn + dy);
            xn = (xn + dx);
            //update.run();
            //System.out.println(dx + "," + dy);
        }
        if (xc < x1 && yc > y1) {
            yn = (yn + dy);
            xn = (xn - dx);
            //update.run();
            //System.out.println(dx + "," + dy);
        }
        if (xc < x1 && yc < y1) {
            yn = (yn - dy);
            xn = (xn - dx);
            //update.run();
            //System.out.println(dx + "," + dy);
        }
        
        //System.out.println(xnPrev + "," + ynPrev + "[]" + xn + "," + yn);
    }
    
    public int[] getPosition(){
        return new int[]{(int) xn, (int)yn};
    }
    
    public boolean isDestroyed(){
        //System.out.println("ahhhhhhhhhhhhhh");
        int fHeight = (int)App.getHeight();
        int fWidth = (int)App.getWidth();
        //System.out.println(xn + "," + yn + "[]" + frame.getBounds().width + "," + frame.getBounds().height);
        return (xn >= fWidth)
                || (yn >= fHeight)
                || (yn <= 0)
                || (xn <= 0) || hit;
        
    }
    
    public void hit(Boolean hit){
        this.hit = hit;
    }

    void spawn() {
        //missileID = String.valueOf(id);
        id++;
    }

    public void launch(double xc, double yc, double x1, double y1, double theta) {
        xn = x1;
        yn = y1;
        this.xc = xc;
        this.yc = yc;
        missileSize =10;
        //frame.getContentPane().add(this);
        start = false;
        if (xc > x1 && yc < y1) {
            dy = -velocity * sin(toRadians(theta));
            dx = velocity * cos(toRadians(theta));
        }
        if (xc > x1 && yc > y1) {
            dy = velocity * cos(toRadians(theta));
            dx = velocity * sin(toRadians(theta));
        }
        if (xc < x1 && yc > y1) {
            dy = velocity * sin(toRadians(theta));
            dx = -velocity * cos(toRadians(theta));
        }
        if (xc < x1 && yc < y1) {
            dy = -velocity * cos(toRadians(theta));
            dx = -velocity * sin(toRadians(theta));
        }

        //System.out.println(xn + "," + yn + "[]" + xc + "," + yc);
    }
    
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        hit = false;
        missileSize = 0;
        xn =0;
        yn = 0;
        xc = 0;
        x1 = 0;
        yc =0;
        y1 =0;
    }

    @Override
    public void paintComponent(GraphicsContext g){
        //super.paintComponent(g);
        GraphicsContext g2d = g;
        g2d.setFill(Color.GREEN);
        g2d.fillOval((int)xn, (int) yn, missileSize, missileSize);
    }
    
    public void paintComponent(Graphics2D g) {
        
        //System.out.println("missile" + missileID);
        //}
    }
    
}
