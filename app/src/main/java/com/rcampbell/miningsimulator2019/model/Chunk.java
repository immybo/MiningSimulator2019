package com.rcampbell.miningsimulator2019.model;

import android.graphics.Point;

import com.rcampbell.miningsimulator2019.model.tile.EmptyTile;
import com.rcampbell.miningsimulator2019.model.tile.Tile;

public class Chunk {
    public static final int CHUNK_SIZE = 50;

    private final int top;
    private final int left;

    private Tile[][] tiles;

    public Chunk(int left, int top) {
        this.left = left;
        this.top = top;

        tiles = new Tile[CHUNK_SIZE][CHUNK_SIZE];

        for (int x = 0; x < tiles[0].length; x++)
        {
            for (int y = 0; y < tiles.length; y++) {
                tiles[y][x] = Tile.getRandomTile(left + x, top + y);
            }
        }
    }

    public Tile getTile(Point position) {
        return tiles[position.y][position.x];
    }

    public void setTile(Point position, Tile newTile) {
        tiles[position.y][position.x] = newTile;
    }
}
