package com.rcampbell.miningsimulator2019.model.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.TileActionHandler;

public class SilverTile extends Tile {
    public SilverTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.rgb(180, 180, 180));
        p.setStyle(Paint.Style.FILL);
        c.drawRect(bounds, p);

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }

    @Override
    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        listener.transform(this, new EmptyTile(this.getX(), this.getY()));
        listener.earnMoney(50);
    }

    public static double getProbability(int yCoordinate) {
        if (yCoordinate < 5) {
            return 0;
        } else {
            return 0.05;
        }
    }
}
