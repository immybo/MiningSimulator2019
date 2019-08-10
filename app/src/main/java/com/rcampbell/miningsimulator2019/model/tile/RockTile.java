package com.rcampbell.miningsimulator2019.model.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rcampbell.miningsimulator2019.TileActionHandler;
import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class RockTile extends Tile {
    public RockTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.rgb(100, 100, 100));
        p.setStyle(Paint.Style.FILL);
        c.drawRect(bounds, p);

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }

    @Override
    public boolean collides(MiningRobot robot) {
        return !robot.hasUpgrade("Adamantium Drill");
    }

    @Override
    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        listener.transform(this, new EmptyTile(this.getX(), this.getY()));
    }

    public static double getProbability(int yCoordinate) {
        if (yCoordinate < 5) {
            return 0;
        } else {
            return 0.01 * (yCoordinate-5);
        }
    }
}
