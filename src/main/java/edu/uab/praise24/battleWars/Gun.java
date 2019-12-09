/*
 * File: Gun.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.0.0 10/15/2019 P.D - initial coding
 *
 * Credits:  (if any for sections of code)
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.praise24.battleWars;

import java.awt.Graphics2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public class Gun implements Actor {

    Missile missile;
    int missileCount = 7, missileIndex = 0;
    Missile[] missiles = new Missile[missileCount];
    static int missileIter = 0;
    double[][] reload;
    boolean started;
    int timer = 0;
    int totalLaunched = 0;
    Thread t;
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
        for (int i = 0; i < missileCount; i++) {
            missileName = String.valueOf(name) + " missile " + String.valueOf(missileIndex);
            Missile missile = new Missile(missileName);
            missiles[i] = missile;
            missileIndex++;
        }

        for (int i = 0; i < missileCount; i++) {
            reload[i][0] = 30 * sin(toRadians(thetaVal));
            reload[i][1] = 30 * cos(toRadians(thetaVal));
            thetaVal += theta;
        }

        //renderer.subscribe(this);
    }

    public int getCapacity() {
        return missileCount;
    }

    public void addMissiles(Missile missile) throws InterruptedException {
        t = new Thread("runner") {
            @Override
            public void run() {
                while (true) {
                    if (totalLaunched > 0) {
                        started = true;
                        while (timer < 10) {
                            timer++;
                            System.out.println("running" + timer);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                            }
                        }

                        totalLaunched = 0;
                        timer = 0;
                    }
                }
            }
        };

        for (int missileC = 0; missileC < missileCount; missileC++) {
            if (missiles[missileC] == null) {
                missiles[missileC] = (missile);
                missiles[missileC].update();
                break;
            }
        }
        if (totalLaunched < missileCount) {
            totalLaunched++;
        }

        /*if (started == false || timer == 0) {
            t.start();
        }*/
        System.out.println(totalLaunched);
    }

    public Missile[] getMissiles() {
        return missiles;
    }

    public void shoot(GraphicsContext g) {
        centerX = (int) App.getWidth() - 60;
        centerY = (int) App.getHeight() - 60;
        if (name.contains("Main")) {
            for (int i = 0; i < missileCount; i++) {
                if (i >= totalLaunched) {
                    g.setFill(Color.GREEN);
                    g.fillOval(centerX + reload[i][0], centerY + reload[i][1], 10, 10);
                } else {
                    g.setFill(Color.RED);
                    g.fillOval(centerX + reload[i][0], centerY + reload[i][1], 10, 10);
                }
            }
        }

        tick++;
        if (tick >= missileCount * 100) {
            tick = 0;
            totalLaunched = 0;
        }
    }

    public void shoot(double xc, double yc, double x1, double y1, double theta, GraphicsContext g) {
        if (totalLaunched < missileCount && tick < missileCount * 100) {
            for (missileIter = totalLaunched; missileIter < missileCount; missileIter++) {
                if (missiles[missileIter].isDestroyed()) {
                    //System.out.println(xc + " + " + yc + "::::" + x1 + " + " + y1);
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

    public void reset() {
        for (int i = 0; i < missileCount; i++) {
            //missileName = String.valueOf(name) + " missile " + String.valueOf(missileIndex);
            Missile missile = new Missile(missileName);
            missiles[i] = missile;
            missileIndex++;
        }
        theta = 360 / missileCount;
        totalLaunched = 0;
    }

    public int getTotalLaunched() {
        return totalLaunched;
    }

    public boolean isFull() {
        for (int i = 0; i < missileCount; i++) {
            if (missiles[i] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return totalLaunched == missileCount;
    }

    @Override
    public void update() {
        for (missileIter = 0; missileIter < missileCount; missileIter++) {
            try { // 1
                if (missiles[missileIter] != null) {
                    missiles[missileIter].update();
                    //System.out.println("mmmmm = " + missiles[missileIter]);
                }
                if (missiles[missileIter].isDestroyed()) {
                    //System.out.println("nahhhhh = " + missiles[missileIter]);

                    missiles[missileIter] = null;
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void paintComponent(GraphicsContext g) {

    }

    @Override
    public int[] getPosition() {
        return null;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean isSpawning() {
        return false;
    }
}
