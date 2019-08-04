package com.rcampbell.miningsimulator2019.model;

import android.graphics.Point;

public class MiningRobot {
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private int xPosition;
    private int yPosition;
    private Universe universe;

    private boolean isMoving;
    private Direction movementDirection;
    private long movementStartTime;
    private long scheduledMovementEndTime;

    private int currentFuel;
    private int maximumFuel;
    private final static int FUEL_PER_TILE = 1;
    private final static int COST_PER_FUEL = 5;

    private int miningDelay;
    private final static int BASE_MINING_DELAY = 1000;

    public MiningRobot(Universe universe) {
        xPosition = 5;
        yPosition = 0;
        isMoving = false;
        this.universe = universe;

        maximumFuel = 100;
        currentFuel = maximumFuel;

        miningDelay = BASE_MINING_DELAY;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void moveInDirection(Direction d) {
        Point newPosition = getNewPosition(d);
        if (universe.isInBounds(newPosition) && currentFuel >= FUEL_PER_TILE) {
            this.moveTo(newPosition);
            universe.getTiles()[yPosition][xPosition].onTouch(this, universe);
            currentFuel -= FUEL_PER_TILE;
        }
        stopMoving();
    }

    public int refuel(int moneyToSpend) {
        int totalCost = getRefuelCost();
        if (moneyToSpend >= totalCost) {
            currentFuel = maximumFuel;
            return totalCost;
        } else {
            int amountRefuelled = (int)(moneyToSpend / COST_PER_FUEL);
            currentFuel += amountRefuelled;
            return amountRefuelled * COST_PER_FUEL;
        }
    }

    public int getFuelPercentage() {
        return (int)((currentFuel+0.0)/maximumFuel*100);
    }

    private void moveTo(Point newPoint) {
        this.xPosition = newPoint.x;
        this.yPosition = newPoint.y;
    }

    public Point getNewPosition(Direction d) {
        if (d == Direction.UP) {
            return new Point(xPosition, yPosition-1);
        } else if (d == Direction.DOWN) {
            return new Point(xPosition, yPosition+1);
        } else if (d == Direction.LEFT) {
            return new Point(xPosition-1, yPosition);
        } else if (d == Direction.RIGHT) {
            return new Point(xPosition+1, yPosition);
        }

        throw new IllegalArgumentException();
    }

    public int getRefuelCost() {
        return (maximumFuel - currentFuel) * COST_PER_FUEL;
    }

    public void setIsMoving(Direction d) {
        isMoving = true;
        movementDirection = d;
        movementStartTime = System.currentTimeMillis();
        scheduledMovementEndTime = System.currentTimeMillis() + getMiningDelay();
    }

    public void stopMoving() {
        isMoving = false;
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public double getMovementProportionDone() {
        return (System.currentTimeMillis() - movementStartTime + 0.0) / (scheduledMovementEndTime - movementStartTime);
    }

    public double getYPositionIncludingMovement() {
        if (!isMoving()) {
            return getYPosition();
        } else {
            int multiplier = 0;
            if (movementDirection == Direction.UP) {
                multiplier = -1;
            } else if (movementDirection == Direction.DOWN) {
                multiplier = 1;
            }

            return getYPosition() + getMovementProportionDone()*multiplier;
        }
    }

    public double getXPositionIncludingMovement() {
        if (!isMoving()) {
            return getXPosition();
        } else {
            int multiplier = 0;
            if (movementDirection == Direction.LEFT) {
                multiplier = -1;
            } else if (movementDirection == Direction.RIGHT) {
                multiplier = 1;
            }

            return getXPosition() + getMovementProportionDone()*multiplier;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public int getMiningDelay() {
        return miningDelay;
    }

    public void setMiningDelay(int miningDelay) {
        this.miningDelay = miningDelay;
    }

    public boolean canMove(Direction direction) {
        return universe.isInBounds(getNewPosition(direction)) && currentFuel >= FUEL_PER_TILE;
    }
}
