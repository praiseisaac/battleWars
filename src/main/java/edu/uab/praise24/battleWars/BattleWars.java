/*
 * File: Missile.java
 * Author: Praise Daramola praise24@uab.edu
 * Assignment:  AimnDodge - EE333 Fall 2019
 * Vers: 1.1.0 12/10/2019 PAD - Final debug for submission
 * Vers: 1.0.0 10/13/2019 PAD - initial coding
 *
 */

package edu.uab.praise24.battleWars;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;

/**
 * JavaFX BattleWars
 */
public class BattleWars extends Application {

    static String tempKeyW = "null", tempKeyS = "null", tempKeyA = "null", tempKeyD = "null", name;
    static Thread t;
    static boolean paused = false;
    double i = 1;
    private static GraphicsContext gc;
    static int width = 1000, height = 800;
    double mousePos;
    TextField playerName = new TextField();
    private static Player player;
    static MyButton button1, button2, button3;
    static Pane pane = new Pane();
    KeyEvent key;
    static Canvas canvas;
    static GridPane grid, grid2, leaderboard, leaderBoardMenu;
    Stage stage;
    static Timeline play, MainMenu, pauseMenu, Leaderboard;
    static Label label = new Label("Battle Wars");
    static Label boardNames, boardScores = new Label("");
    static Label mainPlayer = new Label("Player: ");
    static Scene scene1, scene;
    boolean start = true, inProgress = false;
    static ArrayList<Actor> subscribers = new ArrayList<>();
    static Random rand = new Random();
    TextInputDialog dialog = new TextInputDialog();
    TextInputDialog td = new TextInputDialog("enter any text");
    static Label[] boardNameLabel, boardScoreLabel;
    
    static ArrayList<String[]> players = new ArrayList<>(), playerScores;
    static int enemyCount = 5, index = 0;
    static Enemy[] enemies;
    static Label highScores = new Label("High Scores"),
            leaderBoardLabel = new Label("Leaderboard");
    static Label holder = new Label("");
    static String shootSound = "laserShot.wav", emptySound = "empty.wav";
    static MediaPlayer mediaPlayer;
    static Media sound;
    int buttonHeight = 80, buttonWidth = 250, buttonDist = 200,
            gridWidth, gridScale, titlePos, buttonCenterY, buttonCenterX,
            titleScale, leaderboardVgap = 15, leaderboardHgap = 15,
            leadLayout = 200, playerNameLayoutX = 30, playerNameLayoutY = 30,
            playerNameHeight = 10, playerNameWidth = 100, gridPaddingX = 50,
            gridPaddingY = 50, gridPaddingU = 100, gridPaddingD = 100,
            playerLabelX, playerLabelY, bWarsLayoutX, bWarsLayoutY,
            holderLayoutX, holderLayoutY, holderScale = 15, scoreFontW = 100,
            scoreFontH = 50, scoreFontSize = 20, upperArrowCenterX = 300,
            upperArrowCenterY = 100, bottomArrowCenterX = 300, bottomArrowCenterY = 100,
            arrowSize = 100, arrowDist = 150;
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    

    @Override
    public void start(Stage stage) {
        if (start) {
            this.stage = stage;
            setup();
            MainMenu();
        }
    }

    public void setup() {
        

        play = new Timeline(new KeyFrame(Duration.millis(20), e -> playRun(gc)));
        MainMenu = new Timeline(new KeyFrame(Duration.millis(25), e -> mainRun(gc)));
        pauseMenu = new Timeline(new KeyFrame(Duration.millis(25), e -> pauseRun(gc)));
        Leaderboard = new Timeline(new KeyFrame(Duration.millis(25), e -> leaderboardRun(gc)));
        play.setCycleCount(Timeline.INDEFINITE);
        MainMenu.setCycleCount(Timeline.INDEFINITE);
        pauseMenu.setCycleCount(Timeline.INDEFINITE);
        Leaderboard.setCycleCount(Timeline.INDEFINITE);
        double W, H;

        // buttons
        W = 300;
        buttonCenterY = (int) ((canvas.getHeight()
                - buttonDist * (primaryScreenBounds.getHeight() / 1080))
                * primaryScreenBounds.getHeight() / 1080);
        buttonCenterX = (int) (W * primaryScreenBounds.getWidth() / 1920);
        buttonWidth = (int) (buttonWidth * (primaryScreenBounds.getWidth() / 1920));
        buttonHeight = (int) (buttonHeight * (primaryScreenBounds.getHeight() / 1080));

        // arrow buttons
        arrowSize = (int) (arrowSize * primaryScreenBounds.getHeight() / 1080);
        upperArrowCenterY = (int) (canvas.getHeight() / 2
                - arrowDist * (primaryScreenBounds.getHeight() / 1080));
        upperArrowCenterX = (int) (canvas.getWidth() / 2
                + upperArrowCenterX * primaryScreenBounds.getWidth() / 1920);
        bottomArrowCenterY = (int) (canvas.getHeight() / 2
                + arrowDist * (primaryScreenBounds.getHeight() / 1080));
        bottomArrowCenterX = (int) (canvas.getWidth() / 2
                + bottomArrowCenterX * primaryScreenBounds.getWidth() / 1920);

        // leaderboard
        leadLayout = (int) (leadLayout * primaryScreenBounds.getWidth() / 1920);
        leaderboardVgap = (int) (leaderboardVgap * primaryScreenBounds.getWidth() / 1920);
        leaderboardHgap = (int) (leaderboardHgap * primaryScreenBounds.getHeight() / 1080);

        // battle wars label
        W = 60.978515625 / 2;
        H = 350;
        bWarsLayoutX = (int) ((canvas.getWidth() / 2 - W) * primaryScreenBounds.getWidth() / 1920);
        bWarsLayoutY = (int) ((canvas.getHeight() / 2 - H) * primaryScreenBounds.getHeight() / 1080);

        // scoreboard
        scoreFontW = (int) (scoreFontW * primaryScreenBounds.getWidth() / 1920);
        scoreFontH = (int) (scoreFontH * primaryScreenBounds.getHeight() / 1080);

        //main player label
        W = 250 * primaryScreenBounds.getWidth() / 1920;
        H = 50 * primaryScreenBounds.getHeight() / 1080;
        playerLabelX = (int) ((canvas.getWidth() - W) * primaryScreenBounds.getWidth() / 1920);
        playerLabelY = (int) ((canvas.getHeight() - H) * primaryScreenBounds.getHeight() / 1080);

        // holder
        holderScale = (int) (holderScale * primaryScreenBounds.getWidth() / 1920);
        W = 10 * primaryScreenBounds.getWidth() / 1920;
        H = 50 * primaryScreenBounds.getHeight() / 1080;
        holderLayoutX = (int) ((getWidth() / 2 - W) * primaryScreenBounds.getWidth() / 1920);
        holderLayoutY = (int) ((getHeight() / 2 - H) * primaryScreenBounds.getHeight() / 1080);

        //set Stage boundaries to visible bounds of the main screen
        width = (int) primaryScreenBounds.getWidth() * width / 1920;
        height = (int) primaryScreenBounds.getHeight() * height / 1080;
        player.update();
        start = true;

        // grid
        gridPaddingX = (int) (gridPaddingX * primaryScreenBounds.getWidth() / 1920);
        gridPaddingY = (int) (gridPaddingY * primaryScreenBounds.getHeight() / 1080);
        gridPaddingU = (int) (gridPaddingU * primaryScreenBounds.getWidth() / 1920);
        gridPaddingD = (int) (gridPaddingX * primaryScreenBounds.getHeight() / 1080);
        
        // leaderboard labels
        boardNameLabel = new Label[5];
        boardScoreLabel = new Label[5];
        ArrayList<String[]> plyers = sortPlayers();
        for(int i = 1;i < 6;i++){
            try{
                boardNameLabel[i-1] = new Label(plyers.get(i)[0]);
                boardScoreLabel[i-1] = new Label(plyers.get(i)[1]);
            }catch (IndexOutOfBoundsException e){
                boardNameLabel[i-1] = new Label("*********");
                boardScoreLabel[i-1] = new Label("*********");
            }
        }

    }

    public void Play() {
        MainMenu.pause();
        pauseMenu.pause();

        // For example
        player.moving(false, "null");
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        grid = new GridPane();
        paused = false;

        play.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> {
            if (!paused && !player.isSpawning()) {
                player.updateCursor((int) e.getX(), (int) e.getY());
            }
        });
        canvas.setOnMouseClicked(e -> {
            if (!player.isSpawning()) {
                if (!player.getGun().isEmpty()) {
                    sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + shootSound).toURI().toString());
                    mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                } else {
                    sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + emptySound).toURI().toString());
                    mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
                player.aim();
                player.launchMissile();
            }

        });

        canvas.setOnKeyPressed(e -> {

            if (!player.isSpawning()) {
                if (e.getCode() == KeyCode.ESCAPE) {
                    setScore(name, player.getScore());
                    PauseMenu();
                }
                if (!paused) {
                    if (e.getCode() == KeyCode.W) {
                        player.moving(true, "forward");
                        key = e;
                        tempKeyW = e.getCode().toString();
                    }
                    if (e.getCode() == KeyCode.S) {
                        player.moving(true, "backward");
                        key = e;
                        tempKeyS = e.getCode().toString();
                    }
                    if (e.getCode() == KeyCode.A) {
                        player.moving(true, "left");
                        key = e;
                        tempKeyA = e.getCode().toString();
                    }
                    if (e.getCode() == KeyCode.D) {
                        player.moving(true, "right");
                        key = e;
                        tempKeyD = e.getCode().toString();
                    }
                }
            }

        });

        canvas.setOnKeyReleased(e -> {
            if (!player.isSpawning()) {
                if (!paused) {
                    if (e.getCode() == KeyCode.W) {
                        player.moving(false, "forward");
                        tempKeyW = "null";
                    }
                    if (e.getCode() == KeyCode.S) {
                        player.moving(false, "backward");
                        tempKeyS = "null";
                    }
                    if (e.getCode() == KeyCode.A) {
                        player.moving(false, "left");
                        tempKeyA = "null";
                    }
                    if (e.getCode() == KeyCode.D) {
                        player.moving(false, "right");
                        tempKeyD = "null";
                    }
                }
            }

        });
        canvas.setFocusTraversable(true);

        if (!inProgress) {
            for (int i = 0; i < enemyCount; i++) {
                enemies[i].spawn();
            }
        }
        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        grid.add(pane, 0, 0);
        grid.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);

        stage.setScene(new Scene(grid));
        stage.setResizable(false);
        inProgress = true;
        stage.show();
    }

    public void MainMenu() {
        play.pause();
        pauseMenu.pause();
        Leaderboard.pause();
        inProgress = false;
        if (start) {
            start = false;
            MainMenu();
        }
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].kill();
        }
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        grid2 = new GridPane();
        leaderboard = new GridPane();
        paused = true;
        scene = new Scene(grid2);
        button1 = new MyButton("playButton.png", width / 2, buttonCenterY, buttonWidth, buttonHeight);
        playerName.setMaxWidth(playerNameWidth);
        playerName.setMaxHeight(playerNameHeight);
        playerName.setLayoutX(playerNameLayoutX);
        playerName.setLayoutY(playerNameLayoutY);

        leaderboard.setHgap(leaderboardHgap);
        leaderboard.setVgap(leaderboardVgap);
        leaderboard.setPadding(new Insets(gridPaddingX, gridPaddingU,
                gridPaddingY, gridPaddingD));
        leaderboard.add(highScores, 0, 0, 2, 1);
        highScores.setTextFill(Color.WHITE);
        highScores.setScaleX(1.5 * primaryScreenBounds.getHeight() / 1080);
        highScores.setScaleY(1.5 * primaryScreenBounds.getHeight() / 1080);
        highScores.setEffect(new Glow(2));
        leaderboard.setHalignment(highScores, HPos.CENTER);
        playerScores = sortPlayers();

        for (int j = 1; j < 6; j++) {
            try {
                boardNames = new Label(playerScores.get(j)[0]);
                boardScores = new Label(playerScores.get(j)[1]);

                boardNames.setTextFill(Color.WHITE);
                boardNames.setScaleX(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardNames.setScaleY(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardScores.setTextFill(Color.WHITE);
                boardScores.setScaleX(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardScores.setScaleY(1.5 * primaryScreenBounds.getHeight() / 1080);

                boardNames.setEffect(new Glow(2));
                leaderboard.setHalignment(boardNames, HPos.LEFT);
                leaderboard.add(boardNames, 0, j + 1);
                boardNames.setMinWidth(100);
                boardScores.setEffect(new Glow(2));
                leaderboard.setHalignment(boardScores, HPos.LEFT);
                leaderboard.add(boardScores, 1, j + 1);
            } catch (IndexOutOfBoundsException e) {
                boardNames = new Label("*********");
                boardScores = new Label("*********");

                boardNames.setTextFill(Color.WHITE);
                boardNames.setScaleX(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardNames.setScaleY(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardScores.setTextFill(Color.WHITE);
                boardScores.setScaleX(1.5 * primaryScreenBounds.getHeight() / 1080);
                boardScores.setScaleY(1.5 * primaryScreenBounds.getHeight() / 1080);

                boardNames.setEffect(new Glow(2));
                boardNames.setMinWidth(100);
                leaderboard.setHalignment(boardNames, HPos.LEFT);
                leaderboard.add(boardNames, 0, j + 1);
                boardScores.setEffect(new Glow(2));
                leaderboard.setHalignment(boardScores, HPos.LEFT);
                leaderboard.add(boardScores, 1, j + 1);
            }
        }

        button2 = new MyButton("changePlayer.png", width / 2 - buttonCenterX, buttonCenterY, buttonWidth, buttonHeight);
        //button2 = new MyButton("mainMenu.png", width / 2 - buttonCenterX, 
        //buttonCenterY, buttonWidth, buttonHeight);
        button3 = new MyButton("leaderboard.png", width / 2 + buttonCenterX, buttonCenterY, buttonWidth, buttonHeight);

        mainPlayer.setLayoutX(playerLabelX);
        mainPlayer.setLayoutY(playerLabelY);
        Bounds bounds = leaderboard.getLayoutBounds();

        // setting positions on pane
        leaderboard.setLayoutY(leadLayout);

        // battle wars label
        label.setLayoutX(bWarsLayoutX);
        label.setLayoutY(bWarsLayoutY);

        // setting mouse movement
        canvas.setOnMouseMoved(e -> {
            button1.updateCursor((int) e.getX(), (int) e.getY());
            button2.updateCursor((int) e.getX(), (int) e.getY());
            button3.updateCursor((int) e.getX(), (int) e.getY());
            if (button3.mouseOver()) {
                button3.inflate();
            } else {
                button3.deflate();
            }
            if (button1.mouseOver()) {
                button1.inflate();
            } else {
                button1.deflate();
            }

            if (button2.mouseOver()) {
                button2.inflate();
            } else {
                button2.deflate();
            }

        });
        canvas.setOnMouseClicked(e -> {

            if (button1.mouseOver()) {
                player.spawn(gc);
                player.reset();
                Play();
            } else if (button2.mouseOver()) {
                dialog.setTitle("Select Player");
                dialog.setHeaderText("Enter your name:");
                dialog.setContentText("Name:");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    this.mainPlayer.setText("Player: " + name);
                    this.name = name;
                    if (!playerExists(name)) {
                        players.add(new String[]{name, "0"});
                    }
                });
            } else if (button3.mouseOver()) {
                start = true;
                LeaderBoard();
            }
        });

        MainMenu.play();

        canvas.setFocusTraversable(true);
        player.spawn(gc);
        pane.getChildren().clear();

        pane.getChildren().add(canvas);
        pane.getChildren().add(label);
        pane.getChildren().add(leaderboard);
        pane.getChildren().add(mainPlayer);
        grid2.add(pane, 0, 0);
        grid2.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Battle Wars");
        stage.show();
    }

    public static void setScore(String player, int Score) {
        for (int i = 1; i < players.size(); i++) {
            if (players.size() > 1) {
                if (players.get(i)[0].equals(player)) {
                    if (Integer.valueOf(players.get(i)[1]) < Score) {
                        players.get(i)[1] = String.valueOf(Score);
                    }
                }
            }
        }
    }

    public ArrayList<String[]> sortPlayers() {
        ArrayList<String[]> plyers = players;
        String[] temp = new String[2];
        boolean sorting = true;
        if (players.size() > 1) {
            while (sorting) {
                for (int i = 0; i < plyers.size() - 1; i++) {
                    sorting = false;
                    try {
                        if (Integer.valueOf(plyers.get(i)[1]) < Integer.valueOf(plyers.get(i + 1)[1])) {
                            temp = plyers.get(i);
                            plyers.set(i, plyers.get(i + 1));
                            plyers.set(i + 1, temp);
                            sorting = true;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {

                    }
                }
            }
        }
        return plyers;
    }

    public boolean playerExists(String name) {
        for (String[] plyer : players) {
            if (plyer[0].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void PauseMenu() {
        play.pause();
        canvas = new Canvas(width, height);
        holder.setText(String.valueOf(player.getScore()));
        holder.setTextFill(Color.WHITE);
        holder.setEffect(new Glow(0.1));
        holder.setScaleX(holderScale);
        holder.setScaleY(holderScale);
        holder.setLayoutX(holderLayoutX);
        holder.setLayoutY(holderLayoutY);
        gc = canvas.getGraphicsContext2D();
        paused = true;
        grid2 = new GridPane();
        scene = new Scene(grid2);
        button2 = new MyButton("resume.png", width / 2 + buttonCenterX,
                buttonCenterY, buttonWidth, buttonHeight);

        button1 = new MyButton("mainMenu.png", width / 2 - buttonCenterX,
                buttonCenterY, buttonWidth, buttonHeight);

        label.setLayoutX(bWarsLayoutX);
        label.setLayoutY(bWarsLayoutY);
        canvas.setOnMouseMoved(e -> {
            button1.updateCursor((int) e.getX(), (int) e.getY());
            button2.updateCursor((int) e.getX(), (int) e.getY());
            if (button1.mouseOver()) {
                button1.inflate();
            } else {
                button1.deflate();
            }
            if (button2.mouseOver()) {
                button2.inflate();
            } else {
                button2.deflate();
            }
        });
        canvas.setOnMouseClicked(e -> {
            if (button1.mouseOver()) {
                Missile.paintComponent(gc, false);
                MainMenu();
            } else if (button2.mouseOver()) {
                Play();
            }
        });

        pauseMenu.play();

        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                pauseMenu.pause();
                Play();
            }
        });
        canvas.setFocusTraversable(true);

        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        pane.getChildren().add(label);
        pane.getChildren().add(holder);
        grid2.add(pane, 0, 0);
        grid2.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        paused = false;
    }

    public static boolean isPaused() {
        return paused;
    }

    public void killedMenu() {

    }

    public void LeaderBoard() {
        play.pause();
        MainMenu.pause();
        pauseMenu.pause();
        if (start) {
            start = false;
            LeaderBoard();
        }
        inProgress = false;
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        grid2 = new GridPane();
        leaderBoardMenu = new GridPane();

        leaderboard = new GridPane();
        leaderboard.setAlignment(Pos.CENTER);
        paused = true;
        scene = new Scene(grid2);
        button1 = new MyButton("mainMenu.png", width / 2, buttonCenterY, buttonWidth, buttonHeight);
        button2 = new MyButton("upArrowWhite.png", upperArrowCenterX, upperArrowCenterY, arrowSize, arrowSize);
        button3 = new MyButton("downArrowWhite.png", bottomArrowCenterX, bottomArrowCenterY, arrowSize, arrowSize);
        leaderBoardLabel.setEffect(new Glow(0.1));
        playerScores = sortPlayers();
        leaderBoardLabel.setScaleX(3);
        leaderBoardLabel.setScaleY(3);
        leaderBoardLabel.setTextFill(Color.WHITE);
        leaderboard.add(leaderBoardLabel, 0, 0, 2, 1);
        leaderboard.setHgap(leaderboardHgap + 90);
        leaderboard.setVgap(leaderboardVgap + 30);
        leaderboard.setPadding(new Insets(gridPaddingX, gridPaddingU,
                gridPaddingY, gridPaddingD));
        GridPane.setHalignment(leaderBoardLabel, HPos.LEFT);

        loadLeaderboard();

        mainPlayer.setLayoutX(playerLabelX);
        mainPlayer.setLayoutY(playerLabelY);
        Bounds bounds = leaderboard.getLayoutBounds();

        // setting positions on pane
        leaderBoardMenu.add(leaderboard, 0, 0, 1, 3);

        // battle wars label
        label.setLayoutX(bWarsLayoutX);
        label.setLayoutY(bWarsLayoutY);
        
        button3.setOnAction(e -> {
            updateLeaderboard(index);
        });
        
        button2.setOnAction(e -> {
            updateLeaderboard(index);
        });

        canvas.setOnMouseMoved(e -> {
            button1.updateCursor((int) e.getX(), (int) e.getY());
            button2.updateCursor((int) e.getX(), (int) e.getY());
            button3.updateCursor((int) e.getX(), (int) e.getY());
            if (button3.mouseOver()) {
                button3.inflate();
            } else {
                button3.deflate();
            }
            if (button1.mouseOver()) {
                button1.inflate();
            } else {
                button1.deflate();
            }

            if (button2.mouseOver()) {
                button2.inflate();
            } else {
                button2.deflate();
            }

        });
        canvas.setOnMouseClicked(e -> {

            if (button1.mouseOver()) {
                index = 0;
                MainMenu();
            } else if (button2.mouseOver()) {
                if (index > 0) {
                    index -= 6;
                    button2.fire();
                    button3.setName("downArrowWhite.png");
                } else {
                    button2.setName("upButtonGrey.png");
                }
            } else if (button3.mouseOver()) {
                if (index < players.size() - 1) {
                    index += 6;
                    button3.fire();
                    button2.setName("upArrowWhite.png");
                } else {
                    button3.setName("downButtonGrey.png");
                }
            }
        });

        Leaderboard.play();

        canvas.setFocusTraversable(true);

        pane.getChildren().clear();
        leaderBoardMenu.setLayoutX(canvas.getWidth() / 2 - leaderBoardMenu.getBoundsInLocal().getWidth() / 2);
        pane.getChildren().add(canvas);
        pane.getChildren().add(leaderBoardMenu);

        grid2.add(pane, 0, 0);
        grid2.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void updateLeaderboard(int index) {
        playerScores = sortPlayers();
        for (int j = 1+index; j < 6+index; j++) {
            try {
                boardNameLabel[j-1-index].setText(playerScores.get(j)[0]);
                boardScoreLabel[j-1-index].setText(playerScores.get(j)[1]);

                boardNameLabel[j-1-index].setTextFill(Color.WHITE);
                boardNameLabel[j-1-index].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1-index].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1-index].setTextFill(Color.WHITE);
                boardScoreLabel[j-1-index].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1-index].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1-index].setMinWidth(150);
                boardNameLabel[j-1-index].setEffect(new Glow(0.1));
                boardScoreLabel[j-1-index].setEffect(new Glow(0.1));
            } catch (IndexOutOfBoundsException e) {
                boardNameLabel[j-1-index].setText("*********");
                boardScoreLabel[j-1-index].setText("*********");

                boardNameLabel[j-1-index].setTextFill(Color.WHITE);
                boardNameLabel[j-1-index].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1-index].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1-index].setTextFill(Color.WHITE);
                boardScoreLabel[j-1-index].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1-index].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1-index].setMinWidth(150);
                boardNameLabel[j-1-index].setEffect(new Glow(0.1));
                boardScoreLabel[j-1-index].setEffect(new Glow(0.1));
            }
        }
    }
    
    public void loadLeaderboard(){
        playerScores = sortPlayers();
        for (int j = 1; j < 6; j++) {
            try {

                boardNameLabel[j-1].setTextFill(Color.WHITE);
                boardNameLabel[j-1].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1].setTextFill(Color.WHITE);
                boardScoreLabel[j-1].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1].setMinWidth(150);
                boardNameLabel[j-1].setEffect(new Glow(0.1));
                GridPane.setHalignment(boardNameLabel[j-1], HPos.LEFT);
                leaderboard.add(boardNameLabel[j-1], 0, j + 1);
                boardScoreLabel[j-1].setEffect(new Glow(0.1));
                GridPane.setHalignment(boardScoreLabel[j-1], HPos.LEFT);
                leaderboard.add(boardScoreLabel[j-1], 1, j + 1);
            } catch (IndexOutOfBoundsException e) {

                boardNameLabel[j-1].setTextFill(Color.WHITE);
                boardNameLabel[j-1].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1].setTextFill(Color.WHITE);
                boardScoreLabel[j-1].setScaleX(3 * primaryScreenBounds.getHeight() / 1080);
                boardScoreLabel[j-1].setScaleY(3 * primaryScreenBounds.getHeight() / 1080);
                boardNameLabel[j-1].setMinWidth(150);
                boardNameLabel[j-1].setEffect(new Glow(0.1));
                GridPane.setHalignment(boardNameLabel[j-1], HPos.LEFT);
                leaderboard.add(boardNameLabel[j-1], 0, j + 1);
                boardScoreLabel[j-1].setEffect(new Glow(0.1));
                GridPane.setHalignment(boardScoreLabel[j-1], HPos.LEFT);
                leaderboard.add(boardScoreLabel[j-1], 1, j + 1);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        players = Loader.load();
        name = players.get(0)[0];
        mainPlayer.setText("Player: " + players.get(0)[0]);
        if (players.get(0)[0].equals("null")) {
            mainPlayer.setText("Player: ");
        }
        
        player = Player.getInstance();
        canvas = new Canvas(width, height);
        player.spawn(gc);
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy();
            subscribe(enemies[i]);
        }
        sound = new Media(new File(System.getProperty("user.dir") + "/assets/" + shootSound).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        subscribers.add(player);

        scene = new Scene(pane);
        label.setScaleX(3);
        label.setScaleY(3);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Constantia"));
        mainPlayer.setScaleX(2);
        mainPlayer.setScaleY(2);
        mainPlayer.setTextFill(Color.WHITE);
        mainPlayer.setFont(Font.font("Constantia"));

        launch();
        setScore(name, player.getScore());
        Loader.save(players, name);
    }

    public void playRun(GraphicsContext gc) {
        if (player.isSpawning()) {
            player.updateCursor((int) getWidth() / 2, (int) getHeight() / 2);
            player.idle();
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, width, height);

            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(20));
            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + player.getScore(), scoreFontW, scoreFontH);
            player.paintComponent(gc);
        } else {
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, width, height);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(20));
            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + player.getScore(), scoreFontW, scoreFontH);
            Collider.update();
            for (Actor sub : subscribers) {
                sub.update();
            }
            for (Actor sub : subscribers) {
                sub.paintComponent(gc);
            }
            Missile.paintComponent(gc, false);
        }
        if (player.isDestroyed()) {
            Missile.paintComponent(gc, true);;
            MainMenu();
        }
    }

    public static void mainRun(GraphicsContext gc) {
        //gc2.setFill(javafx.scene.paint.Color.RED);
        //gc2.fillPolygon(new double[]{player.xn, player.x1, player.xm},
        //new double[]{player.yn, player.y1, player.ym}, 3);
        //player.update();
        gc.setEffect(new Glow(40));
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);
        if (!player.isMoving()) {
            player.updateCursor(rand.nextInt((int) width),
                    rand.nextInt((int) height));
        }

        button1.update();
        button2.update();
        button3.update();

        player.idle();
        player.paintComponent(gc);
        gc.setEffect(new Glow(0.5));
        button1.paintComponent(gc);
        button2.paintComponent(gc);
        button3.paintComponent(gc);

    }

    public static void pauseRun(GraphicsContext gc) {
        //gc2.setFill(javafx.scene.paint.Color.RED);
        //gc2.fillPolygon(new double[]{player.xn, player.x1, player.xm},
        //new double[]{player.yn, player.y1, player.ym}, 3);
        //player.update();
        gc.setEffect(new Glow(40));
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);

        button2.update();
        player.paintComponent(gc);
        button1.update();
        gc.setEffect(new Glow(0.5));
        button2.paintComponent(gc);
        button1.paintComponent(gc);

    }

    public static void leaderboardRun(GraphicsContext gc) {
        //gc2.setFill(javafx.scene.paint.Color.RED);
        //gc2.fillPolygon(new double[]{player.xn, player.x1, player.xm},
        //new double[]{player.yn, player.y1, player.ym}, 3);
        //player.update();
        gc.setEffect(new Glow(40));
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);
        if (!player.isMoving()) {
            player.updateCursor(rand.nextInt((int) width),
                    rand.nextInt((int) height));
        }

        button1.update();

        player.idle();
        player.paintComponent(gc);
        gc.setEffect(new Glow(0.5));
        button1.paintComponent(gc);
        button2.paintComponent(gc);
        button3.paintComponent(gc);

    }

    public static void subscribe(Actor actor) {
        subscribers.add(actor);
    }

    public static ArrayList<Actor> getSubscribers() {
        return subscribers;
    }

    public static double getWidth() {
        return canvas.getWidth();
    }

    public static double getHeight() {
        return canvas.getHeight();
    }
    //========================PLAYER START ============================

    //========================Player stop =============================
    static class MyButton extends Button implements Actor {

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
}
