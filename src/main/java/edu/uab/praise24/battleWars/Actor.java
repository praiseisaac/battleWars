/*
 * File: Actor.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  Demo - EE333 Fall 2019
 * Vers: 1.0.0 10/12/2019 PAD - initial coding
 *
 */
package edu.uab.praise24.battleWars;


import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Praise Daramola praise24@uab.edu
 */
public interface Actor {
    public void update();
    public String getName();
    public void paintComponent(GraphicsContext g);
    public int[] getPosition();
    public boolean isDestroyed();
    public void destroy();
    public boolean isSpawning();
    public double getHeight();
}
