/**
 * File: Loader.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  P5-praise24 - EE333 Fall 2019
 * Vers: 1.0.1 11/05/2019 P.D - debugging
 * Vers: 1.0.1 11/01/2019 P.D - debugging
 * Vers: 1.0.0 10/22/2019 P.D - initial coding
 *
 */
package edu.uab.praise24.battleWars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  This class is used to load and save history
 * 
 * @author Praise Daramola praise24@uab.edu
 */
public class Loader {

    /**
     *  This method is used to save history.
     * 
     * @param players
     * @param name
     * @throws FileNotFoundException
     */
    public static void save(ArrayList<String[]> players, String name) throws FileNotFoundException {
        // saves the files to a folder called history
        File filename;

        
        filename = new File(System.getProperty("user.dir") + "/History/highScore.txt");
        filename.getParentFile().mkdirs();
        PrintStream writer = new PrintStream(new FileOutputStream(filename));
        writer.println(name);
        for (String[] player : players){
            try {
                writer.println(player[0] + ":" + player[1]);
            } catch (IndexOutOfBoundsException e){
                
            }
        }
        writer.close();
    }

    private static boolean containedIn(ArrayList<String> roles, String role) {
        for (String val : roles) {
            if (val.equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to load saved history
     * 
     * @return Calendar
     * @throws FileNotFoundException
     */
    public static ArrayList<String[]> load() throws FileNotFoundException {
        Scanner file;
        String fname;
        ArrayList<String[]> players = new ArrayList<>();
        // pass the path to the file as a parameter
        File filename;
        try {
            filename = new File(System.getProperty("user.dir") + "\\History\\highScore.txt");
            file = new Scanner(filename,"utf-8");
            players.add(file.nextLine().split(":",2));
            while (file.hasNextLine()) {
                fname = file.nextLine();
                players.add(fname.split(":",2));
            }
            file.close();
        } catch (FileNotFoundException e) {
            filename = new File(System.getProperty("user.dir") + "\\History\\highScore.txt");
            filename.getParentFile().mkdirs();
        }
        
        return players;
    }
}
