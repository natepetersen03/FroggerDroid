package com.x20.frogger.game;
import com.x20.frogger.data.Controls;

public class GameLogic {
    private static GameLogic instance;

    // todo: get tile board information here (for bounds)
    // the following are TEMPORARY variables for the purposes of testing
    private int xMin = 0;
    private int xMax = 20;
    private int yMin = 0;
    private int yMax = 20;

    // other info the Game needs to know about
    private Player player = new Player();

    public Player getPlayer() {
        return player;
    }

    private GameLogic() {
        System.out.println("GameLogic singleton initialized");
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }


    // todo: move to Player.java
    public void processMovement() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            // replace with EnumHandler
            switch (InputController.QUEUE_MOVEMENTS.poll()) {
            default:
                break;
            }
            System.out.println("Inputs processed");
            System.out.println("Player position: " + player.getX() + ", " + player.getY());
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }
}
