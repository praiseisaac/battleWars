/*
 * File: myButton.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  battleWars - EE333 Fall 2019
 * Vers: 1.0.0 12/10/2019 PAD - initial coding
 *
 * Credits:  (if any for sections of code)
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uab.praise24.battleWars;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
/**
     * used to create custom buttons
     */
public class MyButton extends Button implements Actor {

        int corner1x, corner1y, corner2x, corner2y;
        int c1, c2;
        int[] Position;
        int bWidth, bHeight, bwMax, bwMin, bhMax, bhMin;
        int hMax, hMin, wMax, wMin;
        GraphicsContext g;
        boolean inflated;
        boolean mouseOver;
        String text = "", img;
        int fontSize = 50;
        Image image;
        int xc, yc;

        MyButton(String img, int c1, int c2, int bWidth, int bHeight) {
            this.c1 = c1;
            this.c2 = c2;
            this.img = img;
            hMax = c2 - (int) (0.2 * c2);
            wMax = c1 - (int) (0.2 * c1);
            this.bWidth = bWidth;
            this.bHeight = bHeight;
            bwMin = bWidth;
            bwMax = bWidth + (int) (0.2 * bWidth);
            bhMin = bHeight;
            bhMax = bHeight + (int) (0.2 * bHeight);
            corner1x = c1 - bWidth / 2;
            corner1y = c2 - bHeight / 2;
            corner2x = c1 + bWidth / 2;
            corner2y = c2 + bHeight / 2;
            hMin = corner1y;
            wMin = corner1x;

            image = new Image("file:\\\\\\" + System.getProperty("user.dir") + "/assets/" + img);
        }

        public void setName(String img) {
            image = new Image("file:\\\\\\" + System.getProperty("user.dir") + "/assets/" + img);
        }

        @Override
        public void update() {

        }

        public boolean mouseOver() {
            return (xc >= wMin) && (xc <= wMin + bwMin)
                    && (yc >= hMin) && (yc <= hMin + bhMin);
        }

        public void inflate() {

            bWidth = bwMax;
            bHeight = bhMax;
            corner1x = c1 - bWidth / 2;
            corner1y = c2 - bHeight / 2;
            fontSize = 80;
            inflated = true;

        }

        public void deflate() {
            bWidth = bwMin;
            bHeight = bhMin;
            corner1x = c1 - bWidth / 2;
            corner1y = c2 - bHeight / 2;
            fontSize = 50;
            inflated = false;
        }

        public boolean fullSize() {
            return true;
        }

        @Override
        public int[] getPosition() {
            return new int[]{wMin, hMin + bHeight, wMin + bWidth, hMin + 2 * bHeight, bWidth, bHeight};
        }

        @Override
        public String getName() {
            return this.toString();
        }

        public void paintComponent(GraphicsContext g) {
            this.g = g;
            g.drawImage(image, corner1x, corner1y, bWidth, bHeight);
        }

        public void updateCursor(int xc, int yc) {
            this.xc = xc;
            this.yc = yc;
        }

        public int[] getCenter() {
            return new int[]{c1, c2};
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
