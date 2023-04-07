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
        EASY("Easy"),
        NORMAL("Normal"),
        HARD("Hard");

        private final String string;

        private Difficulty(String string) {
            this.string = string;
        }

        public String toString() {
            return string;
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
