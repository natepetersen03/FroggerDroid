package com.x20.frogger.game;


public class Tile {
    private boolean canTraverse;
    private int length;
    //Safe = 0, Road = 1, River = 2, Goal = 3
    public Tile(int tileType, int length) {
        switch (tileType) {
        case 0:
            //tileImg
            canTraverse = true;
            break;
        case 1:
            //tileImg
            canTraverse = true;
            break;
        case 2:
            //tileImg
            canTraverse = false;
            break;
        case 3:
            //tileImg
            canTraverse = true;
            break;
        default:
            break;
        }
        this.length = length;
    }
}
