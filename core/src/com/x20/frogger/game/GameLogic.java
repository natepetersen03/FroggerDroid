package com.x20.frogger.game;
import com.x20.frogger.data.Controls;

public class GameLogic {
    private static GameLogic instance;

    // update loop information
    private boolean running = true;
    private boolean paused = false;
    private Thread updateLoop;
    private final double fps = 60;
    private final double fixedDeltaTime = 1000000000 / fps;
    private final int maxCatchupUpdates = 3;
    private double nanoTimeLastUpdate = System.nanoTime();

    // todo: get tile board information here (for bounds)
    // the following are TEMPORARY variables for the purposes of testing
    private int xMin = 0;
    private int xMax = 20;
    private int yMin = 0;
    private int yMax = 20;

    private Player player = new Player();

    public Player getPlayer() {
        return player;
    }

    private GameLogic() {
        updateLoop = new UpdateLoop();
        System.out.println("GameLogic singleton initialized");
        updateLoop.start();
    }

    public void togglePauseGameLoop() {
        paused = !paused;
    }

    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    // todo: move to Player.java
    public boolean checkCollision(int x, int y) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // todo: move to Player.java
    public boolean checkBounds(int x, int y) {
        // todo: update these with the proper calls from the tile board
        if (x < xMin || x > xMax || y < yMin || y > yMax) {
            return false;
        }
        return true;
    }

    // todo: move to Player.java
    public void queueMoveInput(Controls.MOVE input) {
        try {
            InputController.QUEUE_MOVEMENTS.add(input);
        } catch (IllegalStateException e) {
            System.out.println("Failed to queue movement input! Is the queue full?");
        }
    }

    // todo: move to Player.java
    public void processMovement() {
        if (!InputController.QUEUE_MOVEMENTS.isEmpty()) {
            switch (InputController.QUEUE_MOVEMENTS.poll()) {
            case UP:
                if (checkBounds(player.getX(), player.getY() + 1)) {
                    player.setPos(player.getX(), player.getY() + 1);
                }
                break;
            case DOWN:
                if (checkBounds(player.getX(), player.getY() - 1)) {
                    player.setPos(player.getX(), player.getY() - 1);
                }
                break;
            case LEFT:
                if (checkBounds(player.getX() - 1, player.getY())) {
                    player.setPos(player.getX() - 1, player.getY());
                }
                break;
            case RIGHT:
                if (checkBounds(player.getX() + 1, player.getY())) {
                    player.setPos(player.getX() + 1, player.getY());
                }
                break;
            default:
                break;
            }
            System.out.println("Inputs processed");
            System.out.println("Player position: " + player.getX() + ", " + player.getY());
            InputController.QUEUE_MOVEMENTS.clear();
        }
    }

    // todo: move to a separate class
    private class UpdateLoop extends Thread {
        // todo: once moved into a separate class, add a way to pause and stop the thread
        public void run() {
            System.out.println("Game loop started");
            while (running && !paused) {
                double nanoTimeNow = System.nanoTime();
                double deltaTime = nanoTimeNow - nanoTimeLastUpdate;
                int updateCount = 0;
                while (updateCount < maxCatchupUpdates && deltaTime >= fixedDeltaTime) {
                    deltaTime -= fixedDeltaTime;
                    updateCount++;
                }

                // run updates
                while (updateCount > 0) {
                    updateCount--;

                    processMovement();
                }

                nanoTimeLastUpdate = nanoTimeNow;

                // yield thread for remaining time
                while (deltaTime < fixedDeltaTime) {
                    Thread.yield();
                    nanoTimeNow = System.nanoTime();
                    deltaTime = nanoTimeNow - nanoTimeLastUpdate;
                }
            }
            // yield thread if game is paused
            while (paused) {
                Thread.yield();
            }
        }
    }
}
