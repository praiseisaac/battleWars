/*
 * File: Collider.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.0.0 11/28/2019 PAD - initial coding
 *
 * Credits:  (if any for sections of code)
 */
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.praise24.battleWars;

import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public class Collider {

    ArrayList<Integer> points = new ArrayList<>();
    static ArrayList<Actor> subscribers;
    String name;
    static double r1, r2, r3;
    static double R1, R2, R3;
    static double xn, yn, xo, yo, xm, ym, xc, yc, x1, y1;
    static Player player = Player.getInstance();

    /**
     * updates the collider to check for missile collision
     */
    public static void update() {
        Missile.updateMissiles();
        int j;
        for (int i = 0; i < BattleWars.getSubscribers().size(); i++) {
            j = 0;
            if (BattleWars.getSubscribers().get(i).toString().contains("Main")) {
                x1 = BattleWars.getSubscribers().get(i).getPosition()[0];
                y1 = BattleWars.getSubscribers().get(i).getPosition()[1];
                xn = BattleWars.getSubscribers().get(i).getPosition()[4];
                yn = BattleWars.getSubscribers().get(i).getPosition()[5];
                xm = BattleWars.getSubscribers().get(i).getPosition()[6];
                ym = BattleWars.getSubscribers().get(i).getPosition()[7];
                for (Missile actr : Missile.getMissiles()) {
                    if (actr != null) {
                        R1 = BattleWars.getSubscribers().get(i).getHeight() / sin(toRadians(60));
                        R2 = R1 * 2;
                        r1 = sqrt(pow(actr.getPosition()[1] - yn, 2) + pow(actr.getPosition()[0] - xn, 2));
                        r2 = sqrt(pow(actr.getPosition()[1] - y1, 2) + pow(actr.getPosition()[0] - x1, 2));
                        r3 = sqrt(pow(actr.getPosition()[1] - ym, 2) + pow(actr.getPosition()[0] - xm, 2));
                        if ((((r1 + r2 + r3) <= R2) && r1 <= R1 && r2 <= R1 && r3 <= R1)
                                && !actr.toString().contains(BattleWars.getSubscribers().get(i).toString())) {
                            if (!BattleWars.getSubscribers().get(i).isSpawning() && !actr.isDestroyed()){
                                System.out.println(actr + "::" + BattleWars.getSubscribers().get(i));
                                
                                Missile.getMissiles().get(j).destroy();
                                BattleWars.getSubscribers().get(i).destroy();
                                Missile.getMissiles().get(j).hit(true);
                                System.out.println("Missile destroyed =====>>" + Missile.getMissiles().get(i));
                            }
                        }
                    }
                    j++;
                }

            }
            // checking collision of enemies
            if (BattleWars.getSubscribers().get(i).toString().contains("Enemy")) {
                x1 = BattleWars.getSubscribers().get(i).getPosition()[0];
                y1 = BattleWars.getSubscribers().get(i).getPosition()[1];
                xn = BattleWars.getSubscribers().get(i).getPosition()[4];
                yn = BattleWars.getSubscribers().get(i).getPosition()[5];
                xm = BattleWars.getSubscribers().get(i).getPosition()[6];
                ym = BattleWars.getSubscribers().get(i).getPosition()[7];
                for (Missile actr : Missile.getMissiles()) {
                    if (actr != null) {
                        R1 = BattleWars.getSubscribers().get(i).getHeight() / sin(toRadians(60));
                        R2 = R1 * 2;
                        r1 = sqrt(pow(actr.getPosition()[1] - yn, 2) + pow(actr.getPosition()[0] - xn, 2));
                        r2 = sqrt(pow(actr.getPosition()[1] - y1, 2) + pow(actr.getPosition()[0] - x1, 2));
                        r3 = sqrt(pow(actr.getPosition()[1] - ym, 2) + pow(actr.getPosition()[0] - xm, 2));
                        if ((((r1 + r2 + r3) <= R2) && r1 <= R1 && r2 <= R1 && r3 <= R1)
                                && !actr.toString().contains("Enemy")) {
                            if (!BattleWars.getSubscribers().get(i).isSpawning()){
                                System.out.println(actr + "::" + BattleWars.getSubscribers().get(i));
                                player.addScore();
                                Missile.getMissiles().get(j).destroy();
                                BattleWars.getSubscribers().get(i).destroy();
                                Missile.getMissiles().get(j).hit(true);
                                System.out.println("Missile destroyed =====>>" + Missile.getMissiles().get(i));
                            }
                        }
                    }
                    j++;
                }

            }
        }
    }

}
