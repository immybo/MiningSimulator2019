package com.example.miningsimulator2019;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class EmptyTile extends Tile {
    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Rect bounds, Paint p, Canvas c) {
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(bounds, p);
    }
}
