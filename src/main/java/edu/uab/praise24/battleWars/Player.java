/*
 * File: Player.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  battleWars - EE333 Fall 2019
 * Vers: 1.1.0 12/10/2019 PAD - Final debug for submission
 * Vers: 1.0.0 12/07/2019 PAD - initial coding
 *
 */
package edu.uab.praise24.battleWars;

import java.awt.MouseInfo;
import java.awt.Point;
import static java.lang.Math.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public class Player implements Actor {
    boolean started = false;
    int timer = 0;
    double r1, r2, r3;
    double R1, R2, R3;
    int xc, yc;
    int missileCount = 0;
    double acc;
    double xn, yn, xo, yo, xo2, yo2, xm, ym,
            launchX, launchY, k, tHeight = 50,
            velocity = 0, vMax = 3, dist = 0, R, rotVel = 0.1, wait = 0;
    double x1;
    double y1;
    double dy, dx, dyR, dxR;
    double theta, phi, beta, beta2;
    int prex = 0, prey = 0;
    boolean state = true;
    static final int run = 1;
    int position = 150 + 10;
    GraphicsContext g;
    boolean moving, moveW, moveS, moveA, moveD;
    String direction;
    int count = 0;
    Gun gun = new Gun("Main Player");
    int score = 0;
    String name;
    Glow glow;
    boolean isDestroyed = false, spawning = false;
    
    

    public static Player getInstance() {
        return PlayerHolder.INSTANCE;
    }

    @Override
    public boolean isSpawning() {
        return spawning;
    }

    private static class PlayerHolder {

        private static final Player INSTANCE = new Player();
    }

    private Player() {
        name = "Main Player";
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        tHeight = 50;
        tHeight = primaryScreenBounds.getHeight() * tHeight / 1080;
        velocity = primaryScreenBounds.getHeight() * velocity / 1080;
    }
    
    @Override
    public double getHeight(){
        return tHeight;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
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

        /*if (!BattleWars.isWidthChanged()) {
                tHeight = frame.getBounds().height / 12;
                vMax = frame.getBounds().height / 120;
                //aim();
                //System.out.println(x1 + "," + y1);
                //x1 = (x1 * frame.getBounds().width) / BattleWars.oldWidth;
                //y1 = (y1 * frame.getBounds().width) / BattleWars.oldWidth;
            }*/
        //System.out.println(xc + " + " + yc + "::::" + x1 + " + " + y1);
        if (monitorMouse() == true || xo != xo2) {
            aim();

            //System.out.println("===============aiming===============");
        } else {

            //System.out.println("===============idle===============");
        }

        /*if (EventListener.getKeys()[0].equals("W") && moveW != true) {
            System.out.println("onwaard =>>>>>>>>>>>>>>>>>>>>>>");
        }
        if (EventListener.getKeys()[1].equals("S") && moveS != true) {
            moveBackward();
        }*/
        if (moveW == true) {
            
            if (moveA == true) {
                moveForward(velocity*(sin(toRadians(45))));
                moveLeft(velocity*(cos(toRadians(45))));
                direction = "FL";
            } else if (moveD == true) {
                moveForward(velocity*(sin(toRadians(45))));
                moveRight(velocity*(cos(toRadians(45))));
                direction = "FR";
            } else {
                direction = "forward";
                moveForward(velocity);
            }
            
        } else {
            if (moveA == true) {
                moveLeft(velocity*(cos(toRadians(45))));
                direction = "left";
            }
            if (moveD == true) {
                direction = "right";
                moveRight(velocity*(cos(toRadians(45))));
            }
            //System.out.println(moving + " ==> " + direction);
        }
        if (moveS == true) {
            
            if (moveA == true) {
                moveBackward(velocity*(sin(toRadians(45))));
                moveLeft(velocity*(cos(toRadians(45))));
                direction = "BL";
            } else if (moveD == true) {
                moveBackward(velocity*(sin(toRadians(45))));
                moveRight(velocity*(cos(toRadians(45))));
                direction = "BR";
            } else {
                direction = "backward";
                moveBackward(velocity);
            }
            //System.out.println(moving + " ==> " + direction);
        } else {
            if (moveA == true) {
                direction = "left";
                moveLeft(velocity);
            }
            if (moveD == true) {
                direction = "right";
                moveRight(velocity);
            }
            //System.out.println(moving + " ==> " + direction);
        }

        if (moving && velocity < vMax) {
            velocity = velocity + 0.1;
            //System.out.println(this.direction);
        } else if (!moveW && velocity > 0 && direction.equals("forward")) {
            moveForward(velocity);
            velocity = velocity - 0.1;
        } else if (!moveS && velocity > 0 && direction.equals("backward")) {
            moveBackward(velocity);
            velocity = velocity - 0.1;
        } else if (!moveA && velocity > 0 && direction.equals("left")) {
            moveLeft(velocity);
            velocity = velocity - 0.1;
        } else if (!moveD && velocity > 0 && direction.equals("right")) {
            moveRight(velocity);
            velocity = velocity - 0.1;
        }else if (velocity > 0 && direction.equals("FL")) {
            moveForward(velocity*(sin(toRadians(45))));
            moveLeft(velocity*(cos(toRadians(45))));
            velocity = velocity - 0.1;
        } else if (velocity > 0 && direction.equals("FR")) {
            moveForward(velocity*(sin(toRadians(45))));
            moveRight(velocity*(cos(toRadians(45))));
            velocity = velocity - 0.1;
        } else if (velocity > 0 && direction.equals("BL")) {
            moveBackward(velocity*(sin(toRadians(45))));
            moveLeft(velocity*(cos(toRadians(45))));
            velocity = velocity - 0.1;
        } else if (velocity > 0 && direction.equals("BR")) {
            moveBackward(velocity*(sin(toRadians(45))));
            moveRight(velocity*(cos(toRadians(45))));
            velocity = velocity - 0.1;
        }
    }

    public boolean monitorMouse() {

        Point p2 = MouseInfo.getPointerInfo().getLocation();

        if (prex != p2.x && prey != p2.y) {
            prex = p2.x;
            prey = p2.y;
            return true; // mouse moved
        } else {

            return false;
        }
    }

    @Override
    public int[] getPosition() {
        return new int[]{(int) x1, (int) y1, (int) xc, (int) yc, (int) xn, (int) yn, (int) xm, (int) ym};
    }

    public void updateCursor(int xc, int yc) {
        this.xc = xc;
        this.yc = yc;
    }

    public void spawn(GraphicsContext gc) {
        g = gc;
        glow = new Glow();
        glow.setLevel(40);
        isDestroyed = false;
        //x1 = BattleWars.getWidth() / 2;
        //y1 = BattleWars.getHeight() / 2;
        R = 50 / sin(toRadians(60));
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
        System.out.println("player Spawned");
    }

    public void aim() {
        //Point p = MouseInfo.getPointerInfo().getLocation();
        //xc = p.x;// - frame.getLocationOnScreen().x;
        //yc = (p.y);// - 30 - frame.getLocationOnScreen().y);

        k = (int) (tHeight * tan(toRadians(30)));
        //if (yc > 0 && xc > 0) {
        //System.out.println(xn + "," + yn + " <==> " + yc + "," + xc);
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
        //System.out.println(xn + "," + yn + " == " + ym + "," + xm);
        //System.out.println(xo + "," + yo);
        //System.out.println("Theta = " + theta);
        //}
    }

    public void moving(boolean value, String direction) {
        this.moving = value;
        this.direction = direction;
        if (direction.equals("forward")) {
            moveW = value;
        } else if (direction.equals("backward")) {
            moveS = value;
        } else if (direction.equals("left")) {
            moveA = value;
        } else if (direction.equals("right")) {
            moveD = value;
        }
    }

    public void moveRight(double dx) {
        if (x1 <= BattleWars.getWidth()-tHeight/2) {
            //System.out.println("moving => 1");
            if (sqrt(pow(yc - y1, 2) + pow(xc - x1, 2)) > 10) {
                //System.out.println("moving");
                x1 = x1 + dx;
            }
        } else if (x1 >= BattleWars.getWidth()-tHeight/2) {
            x1 = BattleWars.getWidth()-tHeight/2;
        } 
    }

    
    public void moveLeft(double dx) {
        if (x1 >= tHeight/2) {
            //System.out.println("moving => 1");
            if (sqrt(pow(yc - y1, 2) + pow(xc - x1, 2)) > 10) {
                //System.out.println("moving");
                x1 = x1 - dx;
            }
        }else if (x1 <= tHeight/2) {
            x1 = tHeight/2;
        }
    }
    
    public void hit(){
        
    }

    public void moveForward(double dy) {
        if (y1 >= tHeight/2) {
            //System.out.println("moving => 1");
            if (sqrt(pow(yc - y1, 2) + pow(xc - x1, 2)) > 10) {
                //System.out.println("moving");
                y1 = y1 - dy;
            }
        } else if (y1 < tHeight/2) {
            y1 = tHeight/2;
        } 
    }

    public void moveBackward(double dy) {
        if (y1 <= BattleWars.getHeight()-tHeight/2) {
            //System.out.println("moving => 1");
            if (sqrt(pow(yc - y1, 2) + pow(xc - x1, 2)) > 10) {
                //System.out.println("moving");
                y1 = y1 + dy;
            }
        } else if (y1 > BattleWars.getHeight()-tHeight/2) {
            y1 = BattleWars.getHeight()-tHeight/2;
        } 
    }

    // ====================NEEDS REVISION FOR JFX
    @Override
    public void paintComponent(GraphicsContext gc) {
        //super.paintComponent(g);
        //GraphicsContext g2d = g;

        if (isCollided()) {
            //isDestroyed = true;
        }
        //System.out.println("painting");
        gc.setFill(Color.RED);
        gc.setEffect(glow);
        gc.fillPolygon(new double[]{xn, x1, xm}, new double[]{yn, y1, ym}, 3);
        if (!BattleWars.isPaused()){
            gun.shoot(gc);
        }
        
        //System.out.println(xn + "," + x1 + "," + xm
        //+ "," + yn + "," + y1 + "," + ym + " ==== " + xc + "," + yc);
        //missile.repaint();
    }

    public boolean isMoving() {
        return moving;
    }

    public void addScore(){
        score++;
    }
    
    public int getScore(){
        return score;
    }
    
    public void reset(){
        score = 0;
        velocity = 0;
        moveA = false;
        moveW = false;
        moveD = false;
        moveS = false;
        gun.reset();
    }
    
    public boolean isCollided() {

        for (Missile msl : gun.getMissiles()) {
            try {
                R1 = sin(toRadians(60));
                R1 = 50 / sin(toRadians(60));
                R2 = R1 * 2;
                //r1 = abs((msl.getPosition()[1]-yn)/(msl.getPosition()[0]-xn));
                //r2 = abs((msl.getPosition()[1]-y1)/(msl.getPosition()[0]-x1));
                //r3 = abs((msl.getPosition()[1]-ym)/(msl.getPosition()[0]-xm));
                r1 = sqrt(pow(100 - yn, 2) + pow(100 - xn, 2));
                r2 = sqrt(pow(100 - y1, 2) + pow(100 - x1, 2));
                r3 = sqrt(pow(100 - ym, 2) + pow(100 - xm, 2));
                //System.out.println((int) r1 + "," + (int) r2 + "," + (int) r3 + "=" + (int) (r1 + r2 + r3) + "||" + (int) R2);

                return ((r1 + r2 + r3) <= R2) && r1 <= R1 && r2 <= R1 && r3 <= R1;
            } catch (NullPointerException e) {

            }
        }
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void launchMissile() {
        //if (missileCount < 1){
        
        aim();
        gun.shoot(x1, y1, xo, yo, theta, g);
    }

    public Gun getGun() {
        return gun;
    }

    public void idle() {

        if (abs((int) x1 - (int) xc) >= 5 && abs((int) y1 - (int) yc) >= 5) {
            velocity = vMax;
            moving = true;
            spawning = true;
            aim();
            if (xc > x1 && yc < y1) {//Q1
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
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
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
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
                theta = toDegrees(atan((double) abs((yc - y1) / (xc - x1))));
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
                theta = toDegrees(atan((double) abs((xc - x1) / (yc - y1))));
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
        } else {
            spawning = false;
            moving = false;
            
        }
    }

}
