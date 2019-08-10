package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.model.MiningRobot;

public class FuelAmountUpgrade extends Upgrade {
    private final int newFuelAmount;

    public FuelAmountUpgrade(int cost, String name, String[] dependencies, int icon, int newFuelAmount) {
        super(cost, name, dependencies, icon);

        this.newFuelAmount = newFuelAmount;
    }

    @Override
    public void apply(MiningRobot robot) {
        super.apply(robot);
        robot.setMaximumFuel(newFuelAmount);
    }
}
