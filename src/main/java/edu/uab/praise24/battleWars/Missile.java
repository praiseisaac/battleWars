/*
 * File: Missile.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.1.0 12/10/2019 PAD - Final debug for submission
 * Vers: 1.0.0 10/13/2019 PAD - initial coding
 *
 */
package edu.uab.praise24.battleWars;

import javafx.scene.paint.Color;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;

/**
 * This class is used to create a missile object 
 * 
 * @author Praise Daramola praise24@uab.edu
 */
public class Missile implements Actor {

    static GraphicsContext g;
    double xc = 0, yc = 0, x1 = 0,
            y1 = 0, dx = 0, dy = 0, xn = 0, xnPrev, yn = 0,
            ynPrev, tHeight = 50, velocity = 5, theta;
    int index;
    static ArrayList<Missile> allMissiles = new ArrayList<>();
    boolean hit = false;
    static int id = 0;
    String ID;
    Thread t;
    boolean start = true;
    int missileSize = 0, maxMissileSize = 10;
    Missile missile;
    int missileCount = 7, missileIndex = 0;
    static int missileIter = 0;
    double[][] reload;
    boolean started;
    int timer = 0;
    int totalLaunched = 0;
    String name;
    String missileName;
    int tick = 0;
    static int centerX, centerY;
    double thetaVal = 0;
    boolean isDestroyed = false;

    Missile(String id) {
        ID = id + " missile";
        name = ID;
        this.id++;
        addMissile(this);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        maxMissileSize = (int) primaryScreenBounds.getHeight() * maxMissileSize / 1080;
        velocity = primaryScreenBounds.getHeight() * velocity / 1080;

    }

    private void addMissile(Missile missile) {
        allMissiles.add(missile);
    }

    /**
     * this method is used to update all existing missiles
     */
    public static void updateMissiles() {
        for (int i = 0; i < allMissiles.size(); i++) {
            try {
                if (allMissiles.get(i).isDestroyed()) {
                    allMissiles.get(i).destroy();
                }
            } catch (NullPointerException e) {
            }
        }
    }

    /**
     * this method is used to get all missiles launched
     * 
     * @return ArrayList
     */
    public static ArrayList<Missile> getMissiles() {
        return allMissiles;
    }

    @Override
    public String toString() {
        return ID;
    }

    /**
     * this method is used to update the missiles position and/or direction
     */
    @Override
    public void update() {
        xnPrev = xn;
        ynPrev = yn;
        if (xc > x1 && yc < y1) {
            yn = (yn - dy);
            xn = (xn + dx);
        }
        if (xc > x1 && yc > y1) {
            yn = (yn + dy);
            xn = (xn + dx);
        }
        if (xc < x1 && yc > y1) {
            yn = (yn + dy);
            xn = (xn - dx);
        }
        if (xc < x1 && yc < y1) {
            yn = (yn - dy);
            xn = (xn - dx);
        }
        if ((abs(yc - y1) > 0) && (int) xc - x1 == 0) {
            if (yc > y1) {
                yn = (yn + dy);
            } else {
                yn = (yn - dy);
            }
        } else if ((abs(xc - x1) > 0) && (int) yc - y1 == 0) {
            if (xc > x1) {
                xn = (xn + dx);
            } else {
                xn = (xn - dx);
            }
        }

        System.out.println((int) abs(dy) + "  +-  " + (int) ynPrev + " = " + (int) yn);
    }

    /**
     * this method returns the position of the missile in x,y coordinates
     * 
     * @return int[]
     */
    public int[] getPosition() {
        return new int[]{(int) xn, (int) yn};
    }

    /**
     * this method is used to check if the missile is destroyed
     * 
     * @return boolean
     */
    public boolean isDestroyed() {
        int fHeight = (int) BattleWars.getHeight();
        int fWidth = (int) BattleWars.getWidth();
        return (xn >= fWidth)
                || (yn >= fHeight)
                || (yn <= 0)
                || (xn <= 0) || isDestroyed;

    }

    /**
     * used to determine if the missile has hit a target
     * 
     * @param hit
     */
    public void hit(boolean hit) {
        this.hit = hit;
    }

    void spawn() {
        id++;
    }

    /**
     * used to launch the missile
     * 
     * @param xc
     * @param yc
     * @param x1
     * @param y1
     * @param theta
     * @param g
     */
    public void launch(double xc, double yc, double x1, double y1, double theta, GraphicsContext g) {
        isDestroyed = false;
        this.g = g;
        xn = xc;
        yn = yc;
        this.xc = xc;
        this.yc = yc;
        missileSize = maxMissileSize;
        System.out.println((int) xc + "," + (int) yc + "  :::  " + (int) x1 + "," + (int) y1 + "==>" + (int) theta);
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
        if ((abs(yc - y1) > 0) && (int) xc - x1 == 0) {
            dy = velocity;
        } else if ((abs(xc - x1) > 0) && (int) yc - y1 == 0) {
            dx = velocity;
        }

        System.out.println(dx + ", " + dy);
    }

    /**
     * used to get the ID of the missile
     * 
     * @return String
     */
    @Override
    public String getName() {
        return ID;
    }

    /**
     * used to destroy the missile
     */
    @Override
    public void destroy() {
        hit = false;
        missileSize = 0;
        isDestroyed = true;
        xn = 0;
        yn = 0;
        xc = 0;
        x1 = 0;
        yc = 0;
        y1 = 0;
    }

    /**
     * used to paint the missile
     * 
     * @param g
     * @param destroy
     */
    public static void paintComponent(GraphicsContext g, boolean destroy) {
        GraphicsContext g2d = g;
        if (destroy) {
            for (int i = 0; i < allMissiles.size(); i++) {
                try {
                    allMissiles.get(i).destroy();
                } catch (NullPointerException e) {
                }
            }
        } else {
            for (int i = 0; i < allMissiles.size(); i++) {
                try {
                    if (allMissiles.get(i).isDestroyed()) {
                        allMissiles.get(i).destroy();
                    }
                } catch (NullPointerException e) {
                }
            }

            for (int i = 0; i < allMissiles.size(); i++) {
                try { // 1
                    if (!allMissiles.get(i).isDestroyed()) {
                        allMissiles.get(i).update();
                        allMissiles.get(i).paintComponent(g);
                    } else {

                    }

                } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
                }
            }

        }
    }
    @Override
    public boolean isSpawning() {
        return false;
    }

    /**
     * used to render the missile
     * 
     * @param g
     */
    @Override
    public void paintComponent(GraphicsContext g) {

        if (name.contains("Main")) {
            g.setFill(Color.GREEN);
            g.fillOval(xn, yn, missileSize, missileSize);
        } else {
            g.setFill(Color.YELLOWGREEN);
            g.fillOval(xn, yn, missileSize, missileSize);
        }
    }

    /**
     * used to get the height of the missile
     * 
     * @return double
     */
    @Override
    public double getHeight() {
        return missileSize;
    }

}
