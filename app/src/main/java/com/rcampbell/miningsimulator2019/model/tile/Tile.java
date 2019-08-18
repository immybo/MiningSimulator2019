package com.rcampbell.miningsimulator2019.model.tile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.TileActionHandler;

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

    public boolean collides(MiningRobot robot) {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Tile getRandomTile(int x, int y) {
        if (y == 0) {
            return new EmptyTile(x, y);
        }

        Random r = new Random();
        if (r.nextDouble() < SapphireTile.getProbability(y)) {
            return new SapphireTile(x, y);
        } else if (r.nextDouble() < GoldTile.getProbability(y)) {
            return new GoldTile(x, y);
        } else if (r.nextDouble() < SilverTile.getProbability(y)) {
            return new SilverTile(x, y);
        } else if (r.nextDouble() < MagmaTile.getProbability(y)) {
            return new MagmaTile(x, y);
        } else if (r.nextDouble() < RockTile.getProbability(y)) {
            return new RockTile(x, y);
        } else {
            return new DirtTile(x, y);
        }
    }
}
