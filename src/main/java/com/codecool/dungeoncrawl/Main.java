package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.items.Inventory;
import com.codecool.dungeoncrawl.logic.particles.BloodParticles;
import com.codecool.dungeoncrawl.logic.particles.FireParticles;
import com.codecool.dungeoncrawl.logic.actors.FreeActor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.particles.ParticleSystemCollection;
import com.codecool.dungeoncrawl.logic.particles.RainParticles;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    // STATICS
    public final static int VIEWPORT_WIDTH = 1200;
    public final static int VIEWPORT_HEIGHT = 900;

    public final static int TORCH_OFFSET_X = -12;
    public final static int TORCH_OFFSET_Y = -14;

    final static float FRAMERATE = 50f;

    public final static int VIEW_DISTANCE_MIN = 2;
    public final static int VIEW_DISTANCE_MAX = 5;

    public final static int VIEW_DISTANCE_TORCH_MIN = 5;
    public final static int VIEW_DISTANCE_TORCH_MAX = 8;

    public final static int CANDLE_LIGHT_MIN = 0;
    public final static int CANDLE_LIGHT_MAX = 3;

    final static String DAMAGE_PREFIX = "Damage: ";
    final static String ARMOR_PREFIX = "Armor: ";

    // TODO: sound pool class
    Music backgroundMusic;
    Music swordSound;
    Music shieldSound;
    Music hitSound;
    Music openSound;
    Music torchSound;
    Music pickUpSound;


    final static int MOVE_SPEED = 3;
    //Enemy setup

    public List<FreeActor> enemies;

    GameDatabaseManager saveState = new GameDatabaseManager();

    // UI
    public GameMap map;
    Canvas canvas;
    GraphicsContext context;



    // UI elements
    Label utilityLabel = new Label("");

    // Game logic(?)
    PlayerInput input = new PlayerInput();

    // Display
    public Display display;

    // MISC
    double framerateInterval() { return (double) (1000f / FRAMERATE); }

    ParticleSystemCollection particles = new ParticleSystemCollection(this);
    ParticleSystemCollection screenParticles = new ParticleSystemCollection(this);
    FireParticles torchParticle;
    List<BloodParticles> bloodParticles = new ArrayList<>();
    private void checkBloodParticles()
    {
        List<BloodParticles> newBlood = new ArrayList<>();
        for (BloodParticles particles : bloodParticles)
        {
            if (particles.isEnded())
                continue;
            newBlood.add(particles);
        }
        bloodParticles = newBlood;
    }


    public static void main(String[] args) {

        launch(args);
    }

    private void loadSounds()
    {
        backgroundMusic = new Music("src/main/resources/music_lower.wav");
        swordSound = new Music("src/main/resources/sword.wav");
        shieldSound = new Music("src/main/resources/shield.wav");
        hitSound = new Music("src/main/resources/cut.wav");
        openSound = new Music("src/main/resources/open.wav");
        torchSound = new Music("src/main/resources/torch.wav");
        pickUpSound = new Music("src/main/resources/pick.wav");
    }

    public void setSoundIsPlaying(Music music, boolean isPlaying)
    {
        music.stop();

        if (isPlaying)
            music.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        loadSounds();

        setSoundIsPlaying(backgroundMusic, true);

        //TODO much more to be done here for the main menu
        Stage mainMenu = new Stage();
        AnchorPane menuBox = new AnchorPane();
        Button newGame = new Button("New Game");
        newGame.setPrefSize(200, 100);
        newGame.setLayoutX(540);
        newGame.setLayoutY(200);
        Button loadGame = new Button("Load Game");
        loadGame.setPrefSize(200, 100);
        loadGame.setLayoutX(540);
        loadGame.setLayoutY(320);
        Button exitGame = new Button("Exit Game");
        exitGame.setPrefSize(200, 100);
        exitGame.setLayoutX(540);
        exitGame.setLayoutY(440);
        /** New Game should take us to a textbox where we can put in the player's name
         * There should be a Load Game, which gives us a list of available savegames to load
         * A query should get these saves of course and on clicking an OK button
         * The map should be loaded and then we should use the tables we have created to get rid of items that the player already got
         * How to do the same with monsters? If we load up everything and we have monsters in the DB with changed positions...?
         * */
        newGame.setOnAction(event -> {
            mainMenu.close();
            enemies = new ArrayList<>();
            map = MapLoader.loadMap("map1");
            canvas = new Canvas(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
            context = canvas.getGraphicsContext2D();

            GridPane ui = new GridPane();
            ui.setPrefWidth(200);
            ui.setPadding(new Insets(10));

            BorderPane borderPane = new BorderPane();

            borderPane.setCenter(canvas);

            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);

            scene.setOnKeyPressed(input::onKeyPressed);
            scene.setOnKeyReleased(input::onKeyReleased);

            scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                if (mouseEvent.getButton().toString().equals("PRIMARY")) {
                    int mouseX = (int) mouseEvent.getX();
                    int mouseY = (int) mouseEvent.getY();
                    Inventory inventory = map.getPlayer().getInventory();
                    int itemIndex = (mouseY-85)/(Tiles.TILE_WIDTH+2);
                    if (mouseX > 24 && mouseX < 51) {
                        if (mouseY > 85 && mouseY < 85 + inventory.size()*(Tiles.TILE_WIDTH+2)) {
                            if (inventory.get(itemIndex).isEquippable()) {
                                map.getPlayer().selectInventoryItem(itemIndex);
                                if (inventory.get(itemIndex).getTileName().equals("sword")) setSoundIsPlaying(swordSound, true);
                                else if (inventory.get(itemIndex).getTileName().contains("shield")) setSoundIsPlaying(shieldSound, true);
                            }
                        }
                    }
                }
            });

            primaryStage.setTitle("Dungeon Crawl");
            primaryStage.show();

            display = new Display(context, Main.this);

            // Particle test
            int rainColumns = 30;
            float widthMultiplier = 1f / rainColumns;
            for (int i=-rainColumns / 3; i<rainColumns * 3; i++)
                screenParticles.add(new RainParticles(Util.randomRange(10, 16), Util.randomRange(4, 5), 0, Main.VIEWPORT_HEIGHT), Math.round(Main.VIEWPORT_WIDTH * (widthMultiplier * i)), 0);

            torchParticle = new FireParticles(10, 3, 1.5f);

            // Start framerate
            KeyFrame keyFrame = new KeyFrame(Duration.millis(framerateInterval()), ae -> {
                try {
                    update();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            Timeline timeline = new Timeline(keyFrame);
            timeline.setCycleCount(-1);
            timeline.play();
        });
        menuBox.getChildren().add(newGame);
        menuBox.getChildren().add(loadGame);
        menuBox.getChildren().add(exitGame);
        menuBox.setLayoutX(0);
        menuBox.setLayoutY(0);
        menuBox.getStyleClass().add("menu");
        menuBox.getStylesheets().add("style.css");

        mainMenu.setTitle("Main Menu");
        mainMenu.setScene(new Scene(menuBox, 1280, 720));
        mainMenu.show();



    }

    private void getEnemies()
    {
        enemies.clear();

        Cell[][] cells = map.getCells();
        for(Cell[] row: cells){
            for (Cell cell: row){
                if (cell.getActor() != null){
                    if (cell.getActor() instanceof Monster){
                        enemies.add((FreeActor) cell.getActor());
                    }
                }
            }
        }
    }

    private void update() throws SQLException {
        //TODO exit should not just close app without a word
        if (map.getPlayer().isDead()) {
            utilityLabel.setText("Your escape has come to a shorter, \n more cruel way than expected");
            System.exit(0);
        }

        getEnemies();

        for (FreeActor enemy: enemies){
            ((Monster) enemy).enemyMove();
        }


        map.getPlayer().moveSlightly(input.getAxisX() * MOVE_SPEED, input.getAxisY() * MOVE_SPEED);

        if (input.isUseButton()){
            useItem();
            map.getPlayer().pickupItem();
            setSoundIsPlaying(pickUpSound, true);
        }


        if (input.isSaveButton()){
            long millis = System.currentTimeMillis();
            Timestamp currentTime = new Timestamp(millis);
            saveState.saveGame(map.getPlayer(),
                    new GameState("/"+map.getMapName()+".txt", currentTime, new PlayerModel(map.getPlayer())),
                    enemies);
        }

        checkBloodParticles();

        if (map.getPlayer().getIsHit()) {
            display.vibrate();
            setSoundIsPlaying(hitSound, true);

            for (int i=0; i<40; i++) {
                float maxDistance = Util.randomRange(80, 160);
                bloodParticles.add(new BloodParticles(3, 0.6f, Util.randomRange(0, (int)maxDistance) - maxDistance / 2, Util.randomRange(0, (int)maxDistance) - maxDistance / 2));
            }
        }

        display.drawFrame();
        display.drawUI();
    }

    private void useItem()
    {
        checkDoor(map.getPlayer().hasBlueKey(), map.getBlueDoorLocation(), CellType.OPENBLUEDOOR);
        checkDoor(map.getPlayer().hasRedKey(), map.getRedDoorLocation(), CellType.FLOOR);

        checkExit();
    }

    private void checkExit()
    {
        Cell playerCell = map.getPlayer().getCell();

        // TODO: ne csak map2
        if (playerCell.getType() == CellType.EXIT)
            map = MapLoader.loadMap("map2", map.getPlayer());
    }

    private void checkDoor(boolean hasKey, int[] doorLoc, CellType cellType)
    {
        if (!hasKey)
            return;

        Cell lookCell = map.getPlayer().getCellFrontOfActor(map);

        if (lookCell.getX() != doorLoc[0] || lookCell.getY() != doorLoc[1])
            return;

        setSoundIsPlaying(openSound, true);
        Cell door = map.getCell(doorLoc[0], doorLoc[1]);
        door.setType(cellType);
    }


}
