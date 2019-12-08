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
    /**
     * Collider(int point1, Actor actor) { name = actor.getName();
     * points.add(point1); subscribers.add(actor); }
     *
     * Collider(int pt1, int pt2, int pt3, Actor actor) { for (Actor act :
     * subscribers) { if (act.getName().equals(actor.getName())) {
     * System.out.println("Actor already has a collider"); } else { name =
     * actor.getName(); points.add(pt1); points.add(pt2); points.add(pt3);
     * subscribers.add(actor); } }
    }
     */
    public static void update() {
        Missile.updateMissiles();
        int j;
        for (int i = 0; i < App.getSubscribers().size(); i++) {
            j = 0;
            if (App.getSubscribers().get(i).toString().contains("Player")) {
                x1 = App.getSubscribers().get(i).getPosition()[0];
                y1 = App.getSubscribers().get(i).getPosition()[1];
                xn = App.getSubscribers().get(i).getPosition()[4];
                yn = App.getSubscribers().get(i).getPosition()[5];
                xm = App.getSubscribers().get(i).getPosition()[6];
                ym = App.getSubscribers().get(i).getPosition()[7];
                for (Missile actr : Missile.getMissiles()) {
                    if (actr != null) {
                        R1 = sin(toRadians(60));
                        R1 = 50 / sin(toRadians(60));
                        R2 = R1 * 2;
                        //r1 = abs((actr.getPosition()[1] - yn) / (actr.getPosition()[0] - xn));
                        //r2 = abs((actr.getPosition()[1] - y1) / (actr.getPosition()[0] - x1));
                        //r3 = abs((actr.getPosition()[1] - ym) / (actr.getPosition()[0] - xm));
                        r1 = sqrt(pow(actr.getPosition()[1] - yn, 2) + pow(actr.getPosition()[0] - xn, 2));
                        r2 = sqrt(pow(actr.getPosition()[1] - y1, 2) + pow(actr.getPosition()[0] - x1, 2));
                        r3 = sqrt(pow(actr.getPosition()[1] - ym, 2) + pow(actr.getPosition()[0] - xm, 2));
                        //System.out.println((int) xn + "," + (int) x1 + "," + (int) xm + "::" + (int) yn + "," + (int) y1 + "," + (int) ym + " => " + (int) (r1 + r2 + r3) + "||" + (int) R2);
                        //System.out.println("111 -> " + actr.getPosition()[0] + "::" + actr.getPosition()[1]);
                        if ((((r1 + r2 + r3) <= R2) && r1 <= R1 && r2 <= R1 && r3 <= R1) &&
                                !actr.toString().contains(App.getSubscribers().get(i).toString())){
                            System.out.println(actr + "::" + App.getSubscribers().get(i));
                            Missile.getMissiles().get(j).destroy();
                            App.getSubscribers().get(i).destroy();
                            Missile.getMissiles().get(j).hit(true);
                            System.out.println("Missile destroyed =====>>" + Missile.getMissiles().get(i));
                        }
                        //return actr;
                    }
                    j++;
                }
            }
        }
    }

}
