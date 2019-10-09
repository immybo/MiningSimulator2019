package com.rcampbell.miningsimulator2019.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class MiningRobotAnimator {
    private final MiningRobot robot;

    private int frame;

    public MiningRobotAnimator(MiningRobot robot) {
        this.robot = robot;
        this.frame = 0;
    }

    public void draw(Rect bounds, Paint p, Canvas c) {
        if (robot.hasExploded()) {
            int robotColor = Color.argb(frame*3, 200, 200, 200);
            p.setColor(robotColor);
            p.setStyle(Paint.Style.FILL);
            c.drawRect(bounds, p);

            frame++;
        } else {
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.FILL);
            c.drawRect(bounds, p);
        }
    }
}
