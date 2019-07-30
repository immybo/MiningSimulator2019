package com.rcampbell.miningsimulator2019.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.rcampbell.miningsimulator2019.model.tile.Tile;
import com.rcampbell.miningsimulator2019.Universe;

public class MainView extends View {
    private static final int X_SIZE = 11;
    private static final int Y_SIZE = 19;
    private static final int ROBOT_OFFSET = 8;

    private Universe universe;

    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (universe == null) return;

        Paint p = new Paint();

        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), p);

        int minY = (int)(universe.getRobot().getYPosition() - ROBOT_OFFSET - 2);

        Tile[][] tiles = universe.getTiles();
        for (int x = 0; x < tiles[0].length; x++) {
            for (int y = minY; y < minY + Y_SIZE + 4; y++) {
                if (universe.isInBounds(new Point(x, y))) {
                    tiles[y][x].draw(getSquareBounds(canvas, universe.getRobot().getYPositionIncludingMovement() - ROBOT_OFFSET, x, y), p, canvas);
                }
            }
        }

        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(getSquareBounds(canvas, 0, universe.getRobot().getXPositionIncludingMovement(), ROBOT_OFFSET), p);
    }

    public Rect getSquareBounds(Canvas canvas, double robotY, double x, double y) {
        double squareWidth = canvas.getWidth() / X_SIZE;
        double squareHeight = canvas.getHeight() / Y_SIZE;
        double yOffset = squareHeight * robotY;
        return new Rect((int)(x*squareWidth), (int)(y*squareHeight - yOffset), (int)(x*squareWidth + squareWidth), (int)(y*squareHeight + squareHeight - yOffset));
    }
}
