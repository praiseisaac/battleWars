/*
 * File: Enemy.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.0.0 11/29/2019 PAD - initial coding
 *
 * Credits:  (if any for sections of code)
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.praise24.battleWars;


//import static edu.uab.prause24.demo.Play.renderer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import static java.lang.Math.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public class Enemy implements Actor {

    private static Player player = Player.getInstance();
    boolean started = false;
    int timer = 0;
    double r1, r2, r3;
    double R1, R2, R3;
    int wait = 0;
    Random randGen = new Random();
    int missileCount = 0;
    double acc;
    double xn, yn, xo, yo, xm, ym, xc, yc,
            launchX, launchY, k, tHeight = 50,
            velocity = 1, vMax = 7, R = 400, tempX1 = 1, tempX2 = 1;
    double x1;
    double y1;
    static double dy, dx;
    double theta, phi;
    int prex = 0, prey = 0;
    boolean state = true;
    static int run = 1;
    int position = 150 + 10;
    int randVal = 0;
    double[] temps = new double[2];
    Graphics g;
    boolean moving;
    String direction;
    int count = 0;
    Gun gun = new Gun("Enemy Player");
    String name;
    boolean isDestroyed = false;
    static int ID = 0;
    Missile msl;

    public Enemy() {
        name = "Enemy Player" + String.valueOf(ID);
        temps[0] = tempX1;
        temps[1] = tempX2;
        ID++;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void update() {
        //missile.update();
        aim();
        //System.out.println("===============aiming===============");
        if (wait >= 300) {
            moveForward();
        }
    }


    boolean isSpawning() {
        return wait <= 300;
    }

    public void spawn() {

        xm = 0;
        ym = 0;
        xn = 0;
        yn = 0;
        velocity = 1;
        tempX1 = 1;
        tempX1 = 2;
        Random rand = new Random();
        y1 = rand.nextInt((int)App.getHeight());
        x1 = rand.nextInt((int)App.getWidth());

        rand = null;

        k = (int) (tHeight * tan(toRadians(30)));
        System.out.println(x1 + "," + y1 + "," + xc + "," + yc);
        if (yc > 0 && xc > 0) {
            if (xc > x1 && yc < y1) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = (x1 - tHeight * cos(toRadians(theta)));
                yo = (y1 + tHeight * sin(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo - k * cos(toRadians(phi)));
                yn = (yo - k * sin(toRadians(phi)));
                xm = (xo + k * cos(toRadians(phi)));
                ym = (yo + k * sin(toRadians(phi)));
                System.out.println(0 + " " + xn + "," + yn + " " + xm + "," + ym);
            }
            if (xc > x1 && yc > y1) {
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
                phi = 180 - 90 - theta;
                xo = (x1 - tHeight * sin(toRadians(theta)));
                yo = (y1 - tHeight * cos(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo + k * sin(toRadians(phi)));
                yn = (yo - k * cos(toRadians(phi)));
                xm = (xo - k * sin(toRadians(phi)));
                ym = (yo + k * cos(toRadians(phi)));
                System.out.println(1 + " " + xn + "," + yn + " " + xm + "," + ym + " == " + yc + "," + xc);
            }
            if (xc < x1 && yc > y1) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = (x1 + tHeight * cos(toRadians(theta)));
                yo = (y1 - tHeight * sin(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo + k * cos(toRadians(phi)));
                yn = (yo + k * sin(toRadians(phi)));
                xm = (xo - k * cos(toRadians(phi)));
                ym = (yo - k * sin(toRadians(phi)));
                System.out.println(2 + " " + xn + "," + yn + " " + xm + "," + ym + " == " + yc + "," + xc);
            }
            if (xc < x1 && yc < y1) {
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
                phi = 180 - 90 - theta;
                xo = (x1 + tHeight * sin(toRadians(theta)));
                yo = (y1 + tHeight * cos(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo - k * sin(toRadians(phi)));
                yn = (yo + k * cos(toRadians(phi)));
                xm = (xo + k * sin(toRadians(phi)));
                ym = (yo - k * cos(toRadians(phi)));
                System.out.println(3 + " " + xn + "," + yn + " " + xm + "," + ym + " == " + yc + "," + xc);
            }
            if ((yc - y1 > 0) && (int) xc - x1 == 0) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = x1;
                yo = (y1 - tHeight);
                launchX = xo;
                launchY = yo;
                xn = (xo + k * cos(toRadians(phi)));
                yn = yo;
                xm = (xo - k * cos(toRadians(phi)));
                ym = yo;

            }
        }
        //xn = 300;
        //yn = 200;
        //xm = 100;
        //ym = 200;
        //System.out.println((int) xn + "," +(int) x1 + "," + (int) xm + "," + (int) yn+ "," + (int) y1+ "," + (int) ym);

        System.out.println("player Spawned");
    }

    public void aim() {

        xc = player.getPosition()[0];
        yc = player.getPosition()[1];
        k = (int) (tHeight * tan(toRadians(30)));
        if (yc > 0 && xc > 0) {

            if (xc > x1 && yc < y1) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = (x1 - tHeight * cos(toRadians(theta)));
                yo = (y1 + tHeight * sin(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo - k * cos(toRadians(phi)));
                yn = (yo - k * sin(toRadians(phi)));
                xm = (xo + k * cos(toRadians(phi)));
                ym = (yo + k * sin(toRadians(phi)));
                //System.out.println(xn + "," + yn);
            }
            if (xc > x1 && yc > y1) {
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
                phi = 180 - 90 - theta;
                xo = (x1 - tHeight * sin(toRadians(theta)));
                yo = (y1 - tHeight * cos(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo + k * sin(toRadians(phi)));
                yn = (yo - k * cos(toRadians(phi)));
                xm = (xo - k * sin(toRadians(phi)));
                ym = (yo + k * cos(toRadians(phi)));
                //System.out.println(xn + "," + yn + " == " + yc + "," + xc);
            }
            if (xc < x1 && yc > y1) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = (x1 + tHeight * cos(toRadians(theta)));
                yo = (y1 - tHeight * sin(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo + k * cos(toRadians(phi)));
                yn = (yo + k * sin(toRadians(phi)));
                xm = (xo - k * cos(toRadians(phi)));
                ym = (yo - k * sin(toRadians(phi)));
                //System.out.println(xn + "," + yn + " == " + yc + "," + xc);
            }
            if (xc < x1 && yc < y1) {
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
                phi = 180 - 90 - theta;
                xo = (x1 + tHeight * sin(toRadians(theta)));
                yo = (y1 + tHeight * cos(toRadians(theta)));
                launchX = xo;
                launchY = yo;
                xn = (xo - k * sin(toRadians(phi)));
                yn = (yo + k * cos(toRadians(phi)));
                xm = (xo + k * sin(toRadians(phi)));
                ym = (yo - k * cos(toRadians(phi)));
                //System.out.println(xn + "," + yn + " == " + yc + "," + xc);
            }
            if ((yc - y1 > 0) && (int) xc - x1 == 0) {
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
                phi = 180 - 90 - theta;
                xo = x1;
                yo = (y1 - tHeight);
                launchX = xo;
                launchY = yo;
                xn = (xo + k * cos(toRadians(phi)));
                yn = yo;
                xm = (xo - k * cos(toRadians(phi)));
                ym = yo;
            }
        }

    }

    public void moving(boolean value, String direction) {
        this.moving = value;
        this.direction = direction;
    }

    public void moveForward() {
        //Point p = MouseInfo.getPointerInfo().getLocation();
        xc = player.getPosition()[0];
        yc = player.getPosition()[1];
        //System.out.println(velocity);
        //System.out.println("velocity = " + velocity);
        if (sqrt(pow(yc - y1, 2) + pow(xc - x1, 2)) > 0) {
            if (xc > x1 && yc < y1) {//Q1

                dy = velocity * sin(toRadians(theta));
                dx = velocity * cos(toRadians(theta));
                y1 = (y1 - dy);
                x1 = (x1 + dx);
                yo = (yo - dy);
                xo = (xo + dx);
                yn = (yn - dy);
                xn = (xn + dx);
                ym = (ym - dy);
                xm = (xm + dx);
            }
            if (xc > x1 && yc > y1) {//Q2

                dy = velocity * cos(toRadians(theta));
                dx = velocity * sin(toRadians(theta));
                y1 = (y1 + dy);
                x1 = (x1 + dx);
                yo = (yo + dy);
                xo = (xo + dx);
                yn = (yn + dy);
                xn = (xn + dx);
                ym = (ym + dy);
                xm = (xm + dx);
            }
            if (xc < x1 && yc > y1) {//Q3
                dy = velocity * sin(toRadians(theta));
                dx = velocity * cos(toRadians(theta));
                y1 = (y1 + dy);
                x1 = (x1 - dx);
                yo = (yo + dy);
                xo = (xo - dx);
                yn = (yn + dy);
                xn = (xn - dx);
                ym = (ym + dy);
                xm = (xm - dx);
            }
            if (xc < x1 && yc < y1) {//Q4
                dy = velocity * cos(toRadians(theta));
                dx = velocity * sin(toRadians(theta));
                y1 = (y1 - dy);
                x1 = (x1 - dx);
                yo = (yo - dy);
                xo = (xo - dx);
                yn = (yn - dy);
                xn = (xn - dx);
                ym = (ym - dy);
                xm = (xm - dx);
            }
            if (abs(yc - y1) > 0 && xc - x1 == 0) {
                y1 = (y1 + velocity);
                yo = (yo + velocity);
                yn = (yn + velocity);
                ym = (ym + velocity);
            }
        } else {
            //
        }

    }

    // ====================NEEDS REVISION FOR JFX
    @Override
    public void paintComponent(GraphicsContext g) {
        //super.paintComponent(g);

        if (wait >= 250 && !App.isPaused()) {
            GraphicsContext g2d = g;
            //this.g = g;
            //g2d.setColor(Color.red);
            if (isCollided()) {
                //isDestroyed = true;
                //System.out.println("Missile => " + isCollided());
            }
            //System.out.println("painting");
            g2d.setFill(Color.YELLOW);
            g2d.fillPolygon(new double[]{xn, x1, xm}, new double[]{yn, y1, ym}, 3);
        } else {
            GraphicsContext g2d = g;
            int[] Spawny = new int[8], size1 = new int[8], Spawnx = new int[8];
            for (int i = 0; i < Spawny.length; i++) {
                Spawny[i] = randGen.nextInt((int) tHeight);
                Spawnx[i] = randGen.nextInt((int) tHeight);
                size1[i] = randGen.nextInt(10);

                //this.g = g;
                //g2d.setColor(Color.red);
                //System.out.println(x1 + "," + xo + "," + Spawnx[i]);
                g2d.setFill(Color.YELLOW);

                if (x1 > xo) {
                    if (y1 > yo) {
                        g2d.fillOval((int) abs((x1 - xo) / 2) + (int) xo + Spawnx[i], (int) abs((y1 - yo) / 2) + (int) yo + Spawny[i], (int) size1[i], (int) size1[i]);
                    } else {
                        g2d.fillOval((int) abs((x1 - xo) / 2) + (int) xo + Spawnx[i], (int) abs((y1 - yo) / 2) + (int) y1 + Spawny[i], (int) size1[i], (int) size1[i]);
                    }
                } else {
                    if (y1 > yo) {
                        g2d.fillOval((int) abs((x1 - xo) / 2) + (int) x1 + Spawnx[i], (int) abs((y1 - yo) / 2) + (int) yo + Spawny[i], (int) size1[i], (int) size1[i]);
                    } else {
                        g2d.fillOval((int) abs((x1 - xo) / 2) + (int) x1 + Spawnx[i], (int) abs((y1 - yo) / 2) + (int) y1 + Spawny[i], (int) size1[i], (int) size1[i]);
                    }
                }
            }
            wait++;
        }
//System.out.println((int) xn + "," +(int) x1 + "," + (int) xm + "," + (int) yn+ "," + (int) y1+ "," + (int) ym);
        //missile.repaint();
    }

    public boolean isCollided() {
        int i = 0;

        return false;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void launchMissile() {
        //if (missileCount < 1){
        /*String missileName = "missile" + String.valueOf(missileCount);
        //Point p = MouseInfo.getPointerInfo().getLocation();
        //xc = p.x - frame.getLocationOnScreen().x;
        //yc = (p.y - 30 - frame.getLocationOnScreen().y);
        if (!gun.isEmpty()) {
            Missile missile = new Missile(missileName, xc, yc, x1, y1);
            try {
                gun.addMissiles(missile);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            missileCount++;
        } else {
            missileCount = 0;
        }*/

        //}
        //System.out.println(missileCount);
    }

    @Override
    public int[] getPosition() {
        return new int[]{(int) x1, (int) y1, (int) xc, (int) yc, (int) xn, (int) yn, (int) xm, (int) ym};
    }

    @Override
    public void destroy() {
        spawn();
        wait = 0;
        isDestroyed = false;
    }

}
