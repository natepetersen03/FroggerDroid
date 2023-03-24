package com.x20.frogger.game;

import com.x20.frogger.data.TileDatabase;
import com.x20.frogger.data.TileStruct;

public class TileMap {

    // todo: determine if the memory overhead of storing the entire TileStruct is worth it
    // access by tilemap[x][y]
    private TileStruct[][] tilemap;

    public int[] getDimensions() {
        return new int[] {tilemap.length, tilemap[0].length};
    }

    public int getWidth() {
        return tilemap.length;
    }

    public int getHeight() {
        return tilemap[0].length;
    }

    public TileStruct getTileStruct(int x, int y) {
        if (x < 0 || x >= tilemap.length || y < 0 || y >= tilemap.length) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        return tilemap[x][y];
    }

    // MUST HAVE A PERFECTLY RECTANGULAR STRING OR BAD THINGS MIGHT HAPPEN

    /**
     * Populate the tilemap using an array of strings which indicate the tile type
     * Precondition: Each string in the array are of equal length
     * @param str Array of tile strings
     */
    public void generateTileMapFromStringArray(String[] str) {
        tilemap = new TileStruct[str[0].length()][str.length];
        for (int x = 0; x < str[0].length(); x++) {
            for (int y = 0; y < str.length; y++) {
                // have to go in this order or the map is upside down
                tilemap[x][str.length - y - 1] = generateTileFromChar(str[y].charAt(x));
            }
        }
    }

    private TileStruct generateTileFromChar(char charAt) {
        return TileDatabase.getDatabase().get(TileDatabase.getCharToKey().get(charAt));
    }

    /**
     * Get the corresponding array of tile strings for an existing tilemap
     * @return the string array representation of the tilemap
     */
    public String[] generateStringArrayFromTileMap() {
        String[] str = new String[tilemap[0].length];
        char[] row = new char[tilemap.length];
        for (int y = 0; y < tilemap[0].length; y++) {
            for (int x = 0; x < tilemap.length; x++) {
                // have to go in this order or the string ends up upside down
                row[x] = TileDatabase.getKeyToChar()
                    .get(tilemap[x][str.length - y - 1].getTile().getName());
            }
            str[y] = String.valueOf(row);
        }
        return str;
    }

    public String toString() {
        return String.join("\n", generateStringArrayFromTileMap());
    }
}
