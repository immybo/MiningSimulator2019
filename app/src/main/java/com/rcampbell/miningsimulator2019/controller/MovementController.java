package com.rcampbell.miningsimulator2019.controller;

import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.view.ViewUpdateListener;

import java.util.Timer;
import java.util.TimerTask;

public class MovementController {
    private final ViewUpdateListener view;
    private final MiningRobot robot;

    private boolean buttonIsHeldDown;
    private MiningRobot.Direction heldMovementDirection;

    public MovementController(ViewUpdateListener viewUpdateListener, MiningRobot robot) {
        this.view = viewUpdateListener;
        this.robot = robot;

        buttonIsHeldDown = false;
    }

    /**
     * Begins moving the robot in the given direction if possible, cancelling any
     * other movement in the process.
     */
    public void handleMovementStart(final MiningRobot.Direction direction) {
        buttonIsHeldDown = true;
        heldMovementDirection = direction;

        if (!robot.isMoving()) {
            startTimer(direction);
        }
    }

    /**
     * Stops moving in the given direction only if the robot is currently moving
     * in that direction. Otherwise does nothing, since the movement starting
     * in the new direction would have cancelled the movement in the given direction
     * already.
     */
    public void handleMovementStop(final MiningRobot.Direction direction) {
        if (direction == heldMovementDirection) {
            buttonIsHeldDown = false;
        }
    }

    private void startTimer(final MiningRobot.Direction direction) {
        if (!robot.canMove(direction)) {
            return;
        }

        robot.setIsMoving(direction);

        new Timer().schedule(new TimerTask() {
            private MiningRobot.Direction thisIterationDirection = direction;

            @Override
            public void run() {
                robot.moveInDirection(thisIterationDirection);
                thisIterationDirection = heldMovementDirection;
                view.refresh();

                if (buttonIsHeldDown) {
                    startTimer(heldMovementDirection);
                }
            }
        }, robot.getMiningDelay());
    }
}
