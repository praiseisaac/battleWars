/*
 * File: Gun.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.1.0 12/10/2019 PAD - Final debug for submission
 * Vers: 1.0.0 10/15/2019 P.D - initial coding
 *
 */
package edu.uab.praise24.battleWars;

import java.awt.Graphics2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * This class is used to create a gun object
 * 
 * @author Praise Daramola praise24@uab.edu
 */
public class Gun {

    Missile missile;
    int missileCount = 7, missileIndex = 0, missileSize = 10, radius = 30;
    Missile[] missiles = new Missile[missileCount];
    static int missileIter = 0;
    
    double[][] reload;
    boolean started;
    int timer = 0;
    int totalLaunched = 0;
    String name;
    static int id = 0;
    String missileName;
    int tick = 0;
    int centerX, centerY;
    double theta, thetaVal = 0;

    Gun(String name) {
        this.name = String.valueOf(name) + " gun";
        reload = new double[missileCount][2];
        theta = 360 / missileCount;
        id++;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        missileSize = (int)(primaryScreenBounds.getHeight() * missileSize / 1080);
        radius = (int)(primaryScreenBounds.getHeight() * radius / 1080);
        for (int i = 0; i < missileCount; i++) {
            missileName = String.valueOf(name) + " missile " + String.valueOf(missileIndex);
            missile = new Missile(missileName);
            missiles[i] = missile;
            missileIndex++;
        }

        for (int i = 0; i < missileCount; i++) {
            reload[i][0] = radius * sin(toRadians(thetaVal));
            reload[i][1] = radius * cos(toRadians(thetaVal));
            thetaVal += theta;
        }
    }

    /**
     * used to get the gun capacity
     * @return int
     */
    public int getCapacity() {
        return missileCount;
    }

    /**
     * used to manually reload the gun
     * 
     * @param missile
     * @throws InterruptedException
     */
    public void addMissiles(Missile missile) throws InterruptedException {
        for (int missileC = 0; missileC < missileCount; missileC++) {
            if (missiles[missileC] == null) {
                missiles[missileC] = (missile);
                missiles[missileC].update();
                break;
            }
        }
        if (totalLaunched < missileCount) {
            totalLaunched--;
        }
    }

    /**
     * used to get the missiles in the gun
     * 
     * @return Missile[]
     */
    public Missile[] getMissiles() {
        return missiles;
    }

    /**
     * used to update and paint the missiles launched from the gun
     * 
     * @param g
     */
    public void shoot(GraphicsContext g) {
        centerX = (int) BattleWars.getWidth() - 60;
        centerY = (int) BattleWars.getHeight() - 60;
        if (name.contains("Main")) {
            for (int i = 0; i < missileCount; i++) {
                if (i >= totalLaunched) {
                    g.setFill(Color.GREEN);
                    g.fillOval(centerX + reload[i][0], centerY + reload[i][1], missileSize, missileSize);
                } else {
                    g.setFill(Color.RED);
                    g.fillOval(centerX + reload[i][0], centerY + reload[i][1], missileSize, missileSize);
                }
            }
        }

        tick++;
        if (tick >= missileCount * 100) {
            tick = 0;
            totalLaunched = 0;
        }
    }

    /**
     * used to launch missiles from the gun
     * 
     * @param xc
     * @param yc
     * @param x1
     * @param y1
     * @param theta
     * @param g
     */
    public void shoot(double xc, double yc, double x1, 
            double y1, double theta, GraphicsContext g) {
        if (totalLaunched < missileCount && tick < missileCount * 100) {
            for (missileIter = totalLaunched; missileIter < missileCount; missileIter++) {
                if (missiles[missileIter].isDestroyed()) {
                    missiles[missileIter].launch(xc, yc, x1, y1, theta, g);
                    totalLaunched++;
                    break;
                } else {

                }
            }
        } else if (tick % 100 == 0) {
            System.out.println("reloading");
            totalLaunched--;
        }

    }

    /**
     * used to reset the gun and missiles within the gun to their initial values
     */
    public void reset() {
        for (int i = 0; i < missileCount; i++) {
            Missile missile = new Missile(missileName);
            missiles[i] = missile;
            missileIndex++;
        }
        theta = 360 / missileCount;
        totalLaunched = 0;
    }

    /**
     * used to get the total number of missiles launched by the gun
     * @return int
     */
    public int getTotalLaunched() {
        return totalLaunched;
    }

    /**
     * used to check if the gun is full
     * 
     * @return boolean
     */
    public boolean isFull() {
        for (int i = 0; i < missileCount; i++) {
            if (missiles[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * used to check if the gun is empty
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return totalLaunched == missileCount;
    }

    /**
     * used to update the gun and missiles contained in the gun
     */
    public void update() {
        for (missileIter = 0; missileIter < missileCount; missileIter++) {
            try { // 1
                if (missiles[missileIter] != null) {
                    missiles[missileIter].update();
                }
                if (missiles[missileIter].isDestroyed()) {

                    missiles[missileIter] = null;
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            }
        }
    }

}
