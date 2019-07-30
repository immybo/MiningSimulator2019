package com.example.miningsimulator2019;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class DirtTile extends Tile {
    public DirtTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.rgb(87, 59, 12));
        p.setStyle(Paint.Style.FILL);
        c.drawRect(bounds, p);

        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }

    @Override
    public void onTouch(MiningRobot robot, TileActionHandler listener) {
        listener.transform(this, new EmptyTile(this.getX(), this.getY()));
    }
}
