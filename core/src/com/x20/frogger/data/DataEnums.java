package com.x20.frogger.data;

public class DataEnums {
    public enum Character {
        STEVE("Steve"),
        ALEX("Alex"),
        ENDERMAN("Enderman");

        private final String string;

        private Character(String string) {
            this.string = string;
        }

        public String toString() {
            return string;
        }
    }

    public enum Difficulty {
        EASY("Easy", 10),
        NORMAL("Normal", 5),
        HARD("Hard", 1);

        private final String string;
        private final int lives;

        private Difficulty(String string, int lives) {
            this.string = string;
            this.lives = lives;
        }

        public String toString() {
            return string;
        }

        public int getLives() {
            return lives;
        }
    }

    public enum VehicleType {
        IRON_GOLEM("Iron Golem"),
        CREEPER("Creeper"),
        SKELETON("Skeleton");
        private final String string;

        private VehicleType(String string) {
            this.string = string;
        }

        public String toString() {
            return string;
        }
    }
}
