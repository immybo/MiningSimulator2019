package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class MiningSpeedUpgrade extends Upgrade {
    private final int newMiningDelay;

    public MiningSpeedUpgrade(int cost, String name, String[] dependencies, int newMiningDelay) {
        super(cost, name, dependencies);
        this.newMiningDelay = newMiningDelay;
    }

    @Override
    public void apply(MiningRobot robot) {
        super.apply(robot);
        robot.setMiningDelay(newMiningDelay);
    }
}