package com.x20.frogger.game;
import com.x20.frogger.data.Controls;

import java.util.concurrent.ArrayBlockingQueue;

public class InputController {
    public static final ArrayBlockingQueue<Controls.MOVE> QUEUE_MOVEMENTS =
            new ArrayBlockingQueue<>(Controls.MOVE.values().length);

    public static void queueMoveInput(Controls.MOVE input) {
        try {
            InputController.QUEUE_MOVEMENTS.add(input);
        } catch (IllegalStateException e) {
            System.out.println("Failed to queue movement input! Is the queue full?");
        }
    }
}
