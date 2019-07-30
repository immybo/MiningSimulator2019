package com.example.miningsimulator2019;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SapphireTile extends Tile {
    public SapphireTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.rgb(15, 82, 186));
        p.setStyle(Paint.Style.FILL);
        c.drawRect(bounds, p);

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }

    @Override
    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        listener.transform(this, new EmptyTile(this.getX(), this.getY()));
        listener.earnMoney(1000);
    }

    public static double getProbability(int yCoordinate) {
        if (yCoordinate < 100) {
            return 0;
        } else {
            return 0.003;
        }
    }
}
