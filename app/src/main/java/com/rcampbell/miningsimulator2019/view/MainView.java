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
import com.rcampbell.miningsimulator2019.model.Universe;

public class MainView extends View {
    private static final int X_SIZE = 11;
    private static final int Y_SIZE = 19;
    private static final int ROBOT_OFFSET = 8;
    private static final int ROBOT_X_OFFSET = X_SIZE / 2;

    private Universe universe;

    private MiningRobotAnimator miningRobotAnimator;

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
        this.miningRobotAnimator = new MiningRobotAnimator(universe.getRobot());
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (universe == null) return;

        Paint p = new Paint();

        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), p);

        int minY = (int)(universe.getRobot().getYPosition() - ROBOT_OFFSET  - 1);
        int maxY = minY + Y_SIZE + 2;

        int minX = (int)(universe.getRobot().getXPosition() - ROBOT_X_OFFSET - 1);
        int maxX = minX + X_SIZE + 2;

        if (minY < 0) {
            minY = 0;
        }

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                universe.getTile(new Point(x, y)).draw(getSquareBounds(canvas, universe.getRobot().getXPositionIncludingMovement() - ROBOT_X_OFFSET, universe.getRobot().getYPositionIncludingMovement() - ROBOT_OFFSET, x, y), p, canvas);
            }
        }

        miningRobotAnimator.draw(getSquareBounds(canvas,  0, 0, X_SIZE/2, ROBOT_OFFSET), p, canvas);
    }

    public Rect getSquareBounds(Canvas canvas, double robotX, double robotY, double x, double y) {
        double squareWidth = canvas.getWidth() / X_SIZE;
        double squareHeight = canvas.getHeight() / Y_SIZE;
        double yOffset = squareHeight * robotY;
        double xOffset = squareWidth * robotX;
        return new Rect((int)(x*squareWidth - xOffset), (int)(y*squareHeight - yOffset), (int)(x*squareWidth + squareWidth - xOffset), (int)(y*squareHeight + squareHeight - yOffset));
    }
}
