package com.rcampbell.miningsimulator2019;

import android.graphics.Point;

import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.model.tile.EmptyTile;
import com.rcampbell.miningsimulator2019.model.tile.Tile;

public class Universe implements TileActionHandler {
    private MiningRobot robot;
    private Tile[][] tiles;
    private int playerMoney;

    public Universe() {
        robot = new MiningRobot(this);
        tiles = new Tile[500][11];

        for (int x = 0; x < tiles[0].length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                if (y == 0) {
                    tiles[y][x] = new EmptyTile(x, y);
                } else {
                    tiles[y][x] = Tile.getRandomTile(x, y);
                }
            }
        }

        playerMoney = 0;
    }

    public MiningRobot getRobot() {
        return robot;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void spendMoney(int amount) {
        assert amount <= playerMoney;
        playerMoney -= amount;
    }

    public boolean robotIsAboveGround() {
        return robot.getYPosition() <= 1;
    }

    @Override
    public void transform(Tile oldTile, Tile newTile) {
        assert oldTile.getX() == newTile.getX();
        assert oldTile.getY() == newTile.getY();

        tiles[oldTile.getY()][oldTile.getX()] = newTile;
    }

    @Override
    public void earnMoney(int amount) {
        playerMoney += amount;
    }

    public boolean isInBounds(Point p) {
        return !(p.x < 0 || p.y < 0 || p.x >= tiles[0].length || p.y >= tiles.length);
    }
}
