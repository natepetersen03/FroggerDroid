package com.x20.frogger;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.x20.frogger.graphics.GoldTile;
import com.x20.frogger.graphics.GrassTile;
import com.x20.frogger.graphics.RoadTile;
import com.x20.frogger.graphics.WaterTile;


public class MapGenerator {

    private TiledMap tileMap;
    private TiledMapTileSet tileSet;
    private TiledMapTileLayer mapLayer;
    private int mapWidth;
    private int mapHeight;
    private int tileSize;
    private int rowNum;

    public MapGenerator(int mapWidth, int mapHeight) {
        this.tileMap = new TiledMap();
        this.tileSet = new TiledMapTileSet();
        tileSet.putTile(0, new GrassTile());
        tileSet.putTile(1, new RoadTile());
        tileSet.putTile(2, new WaterTile());
        tileSet.putTile(3, new GoldTile());


        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileSize = 64;
        this.rowNum = 0;

        this.mapLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileSize, tileSize);
    }

    public void createMap1() {
        //generate rows based on percentages of total pixel height
        int grass1 = (int) (mapHeight * 0.1);
        int road1 = (int) (mapHeight * 0.25);
        int grass2 = (int) (mapHeight * 0.05);
        int water1 = (int) (mapHeight * 0.25);
        int grass3 = (int) (mapHeight * 0.05);
        int gold1 = (int) (mapHeight * 0.3);

        // generates rows onto map layer
        generateRow(grass1, 0);
        generateRow(road1, 1);
        generateRow(grass2, 0);
        generateRow(water1, 2);
        generateRow(grass3, 0);
        generateRow(gold1, 3);

        // adds map layer to tilemap
        tileMap.getLayers().add(mapLayer);
    }

    private void generateRow(int rowCount, int idNum) {
        for (int row = rowNum; row < rowCount + rowNum; row++) {
            for (int col = 0; col < mapWidth; col++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(tileSet.getTile(idNum));
                mapLayer.setCell(col, row, cell);
            }
        }
        rowNum += rowCount;
    }

    public TiledMap returnMap() {
        return tileMap;
    }

}
