package com.x20.frogger.events;

public abstract class GameStateListener implements EventListener {

    public void onScoreUpdate(ScoreEvent e) {

    }

    public void onLivesUpdate(LivesEvent e) {

    }

    public void onGameEnd(GameEndEvent e) {

    }

    public static class ScoreEvent implements Event {

    }

    public static class LivesEvent implements Event {

    }

    public static class GameEndEvent implements Event {
        private boolean playerWon;
        public GameEndEvent(boolean playerWon) {
            this.playerWon = playerWon;
        }
        public boolean didPlayerWin() {
            return playerWon;
        }
    }
}
