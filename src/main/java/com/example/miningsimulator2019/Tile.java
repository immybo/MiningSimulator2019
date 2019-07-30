package com.example.miningsimulator2019;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public abstract class Tile {
    private final int x;
    private final int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void draw(Rect bounds, Paint p, Canvas c);

    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        // Default to doing nothing
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Tile getRandomTile(int x, int y) {
        Random r = new Random();
        if (r.nextDouble() < SapphireTile.getProbability(y)) {
            return new SapphireTile(x, y);
        } else if (r.nextDouble() < GoldTile.getProbability(y)) {
            return new GoldTile(x, y);
        } else if (r.nextDouble() < SilverTile.getProbability(y)) {
            return new SilverTile(x, y);
        } else {
            return new DirtTile(x, y);
        }
    }
}
