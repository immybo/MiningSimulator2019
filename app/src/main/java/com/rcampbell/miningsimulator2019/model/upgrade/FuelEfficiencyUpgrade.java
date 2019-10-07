package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class FuelEfficiencyUpgrade extends Upgrade {
    private final double newFuelPerTile;

    public FuelEfficiencyUpgrade(int cost, String name, Upgrade[] dependencies, int icon, double newFuelPerTile) {
        super(cost, name, dependencies, icon);

        this.newFuelPerTile = newFuelPerTile;
    }

    @Override
    public void apply(MiningRobot robot) {
        super.apply(robot);
        robot.setFuelPerTile(newFuelPerTile);
    }
}
