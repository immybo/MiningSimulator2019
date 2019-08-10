package com.rcampbell.miningsimulator2019.model.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rcampbell.miningsimulator2019.TileActionHandler;
import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class MagmaTile extends Tile {
    public MagmaTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.rgb(255, 150, 0));
        p.setStyle(Paint.Style.FILL);
        c.drawRect(bounds, p);

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }

    @Override
    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        listener.loseGame();
    }

    public static double getProbability(int yCoordinate) {
        if (yCoordinate < 50) {
            return 0;
        } else {
            return 0.02;
        }
    }
}
