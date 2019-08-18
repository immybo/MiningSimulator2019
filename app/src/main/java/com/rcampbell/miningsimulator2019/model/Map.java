package com.rcampbell.miningsimulator2019.model;

import android.graphics.Point;

import com.rcampbell.miningsimulator2019.model.tile.EmptyTile;
import com.rcampbell.miningsimulator2019.model.tile.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private java.util.Map<Point, Chunk> chunks;

    public Map() {
        chunks = new HashMap<Point, Chunk>();
    }

    public Tile getTile(Point position) {
        return getChunkContaining(position).getTile(translateToChunkCoordinates(position));
    }

    public boolean isInBounds(Point p) {
        return p.y >= 0;
    }

    private Chunk getChunkContaining(Point position) {
        Point chunkIndex = getChunkIndex(position);

        if (!chunks.containsKey(chunkIndex)) {
            chunks.put(chunkIndex, new Chunk(chunkIndex.x * Chunk.CHUNK_SIZE, chunkIndex.y * Chunk.CHUNK_SIZE));
        }

        return chunks.get(chunkIndex);
    }

    private Point getChunkIndex(Point position) {
        int xIndex = position.x / Chunk.CHUNK_SIZE;
        int yIndex = position.y / Chunk.CHUNK_SIZE;

        return new Point(xIndex, yIndex);
    }

    private Point translateToChunkCoordinates(Point position) {
        int x = position.x % Chunk.CHUNK_SIZE;
        int y = position.y % Chunk.CHUNK_SIZE;

        if (x < 0) {
            x = Chunk.CHUNK_SIZE + x;
        }

        if (y < 0) {
            y = Chunk.CHUNK_SIZE + y;
        }

        return new Point(x, y);
    }

    public void transform(Tile oldTile, Tile newTile) {
        Point pos = new Point(oldTile.getX(), oldTile.getY());
        Point chunkCoordinate = translateToChunkCoordinates(pos);
        getChunkContaining(pos).setTile(chunkCoordinate, newTile);
    }
}
