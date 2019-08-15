package com.rcampbell.miningsimulator2019.model;

import android.graphics.Point;

import com.rcampbell.miningsimulator2019.TileActionHandler;
import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.model.tile.EmptyTile;
import com.rcampbell.miningsimulator2019.model.tile.Tile;
import com.rcampbell.miningsimulator2019.view.ViewUpdateListener;

public class Universe implements TileActionHandler {
    private MiningRobot robot;
    private int playerMoney;

    private final ViewUpdateListener viewListener;

    private Map map;

    public Universe(ViewUpdateListener viewListener) {
        robot = new MiningRobot(this);

        playerMoney = 1000; //0;
        this.viewListener = viewListener;

        this.map = new Map();
    }

    public Tile getTile(Point position) {
        return map.getTile(position);
    }

    public MiningRobot getRobot() {
        return robot;
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

        map.transform(oldTile, newTile);
    }

    @Override
    public void earnMoney(int amount) {
        playerMoney += amount;
    }

    public boolean isInBounds(Point p) {
        return map.isInBounds(p);
    }

    @Override
    public void loseGame() {
        viewListener.loseGame();
    }
}
