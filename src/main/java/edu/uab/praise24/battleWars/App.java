package edu.uab.praise24.battleWars;

import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * JavaFX App
 */
public class App extends Application {

    static String tempKeyW = "null", tempKeyS = "null", tempKeyA = "null", tempKeyD = "null";
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
    static GridPane grid, grid2;
    Stage stage;
    static Timeline timeline, timeline2, timeline3;
    static Label label = new Label("Battle Wars");
    static Label mainPlayer = new Label("Player: ");
    static Scene scene1, scene;
    boolean start = true, inProgress = false;
    static ArrayList<Actor> subscribers = new ArrayList<>();
    static Random rand = new Random();
    TextInputDialog dialog = new TextInputDialog();
    TextInputDialog td = new TextInputDialog("enter any text");
    static ArrayList<String[]> players = new ArrayList<>();
    static int enemyCount = 5;
    static Enemy[] enemies;

    @Override
    public void start(Stage stage) {
        if (start) {
            this.stage = stage;
            player.update();
            start = false;
            MainMenu();
        }
    }

    public void Play() {
        player.spawn(gc);
        player.moving(false, "null");
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        grid = new GridPane();
        paused = false;

        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> {
            if (!paused) {
                player.updateCursor((int) e.getX(), (int) e.getY());
            }
        });
        canvas.setOnMouseClicked(e -> {
            System.out.println("booooooooooyyyyahhhhhh");
            player.aim();
            player.launchMissile();
        });

        canvas.setOnKeyPressed(e -> {
            System.out.println("keyPressed=" + e.getCode());
            if (e.getCode() == KeyCode.ESCAPE) {
                timeline.pause();
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
        });

        canvas.setOnKeyReleased(e -> {
            System.out.println("keyReleased = " + e.getCode());
            //System.out.println("keyReleased=" + KeyEvent.getKeyText(e.getKeyCode()));

            if (!paused) {
                if (e.getCode() == KeyCode.W) {
                    player.moving(false, "forward");
                    System.out.println("W ========================> released");
                    tempKeyW = "null";
                }
                if (e.getCode() == KeyCode.S) {
                    player.moving(false, "backward");
                    System.out.println("S ========================> released");
                    tempKeyS = "null";
                }
                if (e.getCode() == KeyCode.A) {
                    player.moving(false, "left");
                    System.out.println("A ========================> released");
                    tempKeyA = "null";
                }
                if (e.getCode() == KeyCode.D) {
                    player.moving(false, "right");
                    System.out.println("D ========================> released");
                    tempKeyD = "null";
                }
            }

        });
        canvas.setFocusTraversable(true);

        if (!inProgress) {
            for (int i = 0; i < enemyCount; i++) {
                enemies[i].spawn();
            }
        }
        grid.add(canvas, 0, 0);
        grid.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);
        stage.setScene(new Scene(grid));
        
        stage.setTitle("Battle Wars");
        stage.show();
    }

    public void MainMenu() {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        grid2 = new GridPane();
        paused = true;
        scene = new Scene(grid2);
        button1 = new MyButton("playButton.png", width / 2, (int) canvas.getHeight() - 200, 250, 80);
        playerName.setMaxWidth(100);
        playerName.setMaxHeight(10);
        playerName.setLayoutX(30);
        playerName.setLayoutY(50);
        button2 = new MyButton("changePlayer.png", button1.getCenter()[0] - 300, (int) canvas.getHeight() - 200, 250, 80);
        button3 = new MyButton("leaderboard.png", button1.getCenter()[0] + 300, (int) canvas.getHeight() - 200, 250, 80);
        System.out.println(label.getWidth());

        mainPlayer.setLayoutX(canvas.getWidth() - 250);
        mainPlayer.setLayoutY(canvas.getHeight() - 50);

        label.setLayoutX(canvas.getWidth() / 2 - 60.978515625 / 2);
        label.setLayoutY(canvas.getHeight() / 2 - 350);
        canvas.setOnMouseMoved(e -> {
            if (e.getX() >= button1.getPosition()[0]
                    && e.getY() >= button1.getPosition()[1]
                    && e.getX() <= button1.getPosition()[2]
                    && e.getY() <= button1.getPosition()[3]) {
                button1.inflate();
                button1.mouseIsOver(true);
            } else {
                button1.mouseIsOver(false);
                button1.deflate();
            }
            if (e.getX() >= button2.getPosition()[0]
                    && e.getY() >= button2.getPosition()[1]
                    && e.getX() <= button2.getPosition()[2]
                    && e.getY() <= button2.getPosition()[3]) {
                button2.mouseIsOver(true);
                button2.inflate();
            } else {
                button2.mouseIsOver(false);
                button2.deflate();
            }
            if (e.getX() >= button3.getPosition()[0]
                    && e.getY() >= button3.getPosition()[1]
                    && e.getX() <= button3.getPosition()[2]
                    && e.getY() <= button3.getPosition()[3]) {
                button3.mouseIsOver(true);
                button3.inflate();
            } else {
                button3.mouseIsOver(false);
                button3.deflate();
            }
        });
        canvas.setOnMouseClicked(e -> {
            System.out.println("menu 2");
            if (button1.mouseOver()) {
                timeline2.pause();

                Play();
            } else if (button2.mouseOver()) {
                dialog.setTitle("o7planning");
                dialog.setHeaderText("Enter your name:");
                dialog.setContentText("Name:");

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(name -> {
                    this.mainPlayer.setText("Player: " + name);
                });
            } else if (button3.mouseOver()) {

            }
        });

        timeline2.play();

        canvas.setOnKeyPressed(e -> {
            System.out.println("keyPressed=" + e.getCode());
            if (e.getCode() == KeyCode.ESCAPE) {

            }
        });
        canvas.setFocusTraversable(true);

        player.x1 = width / 2;
        player.y1 = height / 2;

        player.spawn(gc);
        pane.getChildren().clear();

        pane.getChildren().add(canvas);
        pane.getChildren().add(label);
        pane.getChildren().add(mainPlayer);
        grid2.add(pane, 0, 0);
        grid2.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);

        stage.setScene(scene);
        stage.setTitle("Battle Wars");
        stage.show();
    }

    public void PauseMenu() {
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        paused = true;
        grid2 = new GridPane();
        scene = new Scene(grid2);
        button2 = new MyButton("resume.png", width / 2 + 300, (int) canvas.getHeight() - 200, 250, 80);

        button1 = new MyButton("mainMenu.png", width / 2 - 300, (int) canvas.getHeight() - 200, 250, 80);
        System.out.println(label.getWidth());

        label.setLayoutX(canvas.getWidth() / 2 - 60.978515625 / 2);
        label.setLayoutY(canvas.getHeight() / 2 - 200);
        canvas.setOnMouseMoved(e -> {
            if (e.getX() >= button1.getPosition()[0]
                    && e.getY() >= button1.getPosition()[1]
                    && e.getX() <= button1.getPosition()[2]
                    && e.getY() <= button1.getPosition()[3]) {
                button1.inflate();
                button1.mouseIsOver(true);
            } else {
                button1.mouseIsOver(false);
                button1.deflate();
            }
            if (e.getX() >= button2.getPosition()[0]
                    && e.getY() >= button2.getPosition()[1]
                    && e.getX() <= button2.getPosition()[2]
                    && e.getY() <= button2.getPosition()[3]) {
                button2.mouseIsOver(true);
                button2.inflate();
            } else {
                button2.mouseIsOver(false);
                button2.deflate();
            }
        });
        canvas.setOnMouseClicked(e -> {
            System.out.println("menu 2");
            if (button1.mouseOver()) {
                timeline3.pause();
                MainMenu();
            } else if (button2.mouseOver()) {
                timeline3.pause();
                Play();
            }
        });

        timeline3.play();

        canvas.setOnKeyPressed(e -> {
            System.out.println("keyPressed=" + e.getCode());
            if (e.getCode() == KeyCode.ESCAPE) {
                timeline3.pause();
                Play();
            }
        });
        canvas.setFocusTraversable(true);

        pane.getChildren().clear();
        pane.getChildren().add(canvas);
        pane.getChildren().add(label);
        grid2.add(pane, 0, 0);
        grid2.add(new Label("Praise Daramola, Zachery White, Timothy Thornton"), 0, 1);
        stage.setScene(scene);
        stage.setTitle("Battle Wars");
        stage.show();
    }

    public static void pause() {
        paused = true;
    }

    public static void resume() {
        paused = false;
    }

    public static boolean isPaused() {
        //System.out.println(paused);
        return paused;
    }

    public static void main(String[] args) throws FileNotFoundException {
        players = Loader.load();
        player = Player.getInstance();
        canvas = new Canvas(width, height);
        player.spawn(gc);
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy();
            subscribe(enemies[i]);
        }

        subscribers.add(player);

        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> run(gc)));
        timeline2 = new Timeline(new KeyFrame(Duration.millis(25), e -> run2(gc)));
        timeline3 = new Timeline(new KeyFrame(Duration.millis(25), e -> run3(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline3.setCycleCount(Timeline.INDEFINITE);
        scene1 = new Scene(pane);
        label.setScaleX(3);
        label.setScaleY(3);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("Constantia"));
        mainPlayer.setScaleX(2);
        mainPlayer.setScaleY(2);
        mainPlayer.setTextFill(Color.WHITE);
        mainPlayer.setFont(Font.font("Constantia"));

        launch();
        Loader.save(players);
    }

    public static void run(GraphicsContext gc) {
        //gc2.setFill(javafx.scene.paint.Color.RED);
        //gc2.fillPolygon(new double[]{player.xn, player.x1, player.xm},
        //new double[]{player.yn, player.y1, player.ym}, 3);
        //player.update();
        if (player.spawning()) {
            player.updateCursor((int) getWidth() / 2, (int) getHeight() / 2);
            player.idle();
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, width, height);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(20));
            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + "40", 60, 20);
            player.paintComponent(gc);
        } else {
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, width, height);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font(20));
            gc.setFill(Color.WHITE);
            gc.fillText("Score: " + "40", 60, 20);
            Collider.update();
            //System.out.println(player.xc + "," + player.yc);

            //System.out.println(player.xn + "," + player.x1 + "," + player.xm
            //+ "," + player.yn + "," + player.y1 + "," + player.ym + " ==== " + player.xc + "," + player.yc);
            for (Actor sub : subscribers) {
                sub.update();
            }
            for (Actor sub : subscribers) {
                sub.paintComponent(gc);
            }
        }
    }

    public static void run2(GraphicsContext gc) {
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

        button2.update();
        button3.update();
        button1.update();

        player.idle();
        player.paintComponent(gc);
        gc.setEffect(new Glow(0));
        button2.paintComponent(gc);
        button3.paintComponent(gc);
        button1.paintComponent(gc);

    }

    public static void run3(GraphicsContext gc) {
        //gc2.setFill(javafx.scene.paint.Color.RED);
        //gc2.fillPolygon(new double[]{player.xn, player.x1, player.xm},
        //new double[]{player.yn, player.y1, player.ym}, 3);
        //player.update();
        gc.setEffect(new Glow(40));
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);

        button2.update();

        button1.update();
        gc.setEffect(new Glow(0));
        button2.paintComponent(gc);
        button1.paintComponent(gc);
        player.paintComponent(gc);

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
    static class MyButton implements Actor {

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

        @Override
        public void update() {

        }

        public boolean mouseOver() {
            System.out.println(mouseOver);
            return mouseOver;
        }

        public void mouseIsOver(boolean over) {
            mouseOver = over;
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
            g.drawImage(image, corner1x, corner2y, bWidth, bHeight);
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
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

    }
}
