package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.R;
import com.rcampbell.miningsimulator2019.model.MiningRobot;

public abstract class Upgrade {
    private final int cost;
    private final String name;
    private final Upgrade[] dependencies;
    private final int icon;

    public Upgrade(int cost, String name, Upgrade[] dependencies, int icon) {
        this.cost = cost;
        this.name = name;
        this.dependencies = dependencies;
        this.icon = icon;
    }

    public void apply(MiningRobot robot) {
        robot.addUpgrade(this);
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Upgrade[] getDependencies() {
        return dependencies;
    }

    public int getIcon() {
        return icon;
    }

    public boolean hasAllDependencies(MiningRobot robot) {
        for (Upgrade dependency : getDependencies()) {
            if (!robot.hasUpgrade(dependency)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        try {
            return ((Upgrade)other).getName().equals(getName());
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public final static Upgrade MiningSpeed1 = new MiningSpeedUpgrade(100, "Superior Drill", new Upgrade[0], R.mipmap.default_image, 500);
    public final static Upgrade MiningSpeed2 = new MiningSpeedUpgrade(500, "Adamantium Drill", new Upgrade[]{MiningSpeed1}, R.mipmap.default_image, 200);
    public final static Upgrade FuelTank1 = new FuelAmountUpgrade(100, "Large Fuel Tank", new Upgrade[0], R.mipmap.default_image, 200);
    public final static Upgrade FuelTank2 = new FuelAmountUpgrade(1000, "Huge Fuel Tank", new Upgrade[]{FuelTank1}, R.mipmap.default_image, 500);
    public final static Upgrade FuelEfficiency1 = new FuelEfficiencyUpgrade(500, "Fuel Recycling", new Upgrade[0], R.mipmap.default_image, 0.5);

    public static Upgrade[] getAll() {
        return new Upgrade[] {
                MiningSpeed1,
                MiningSpeed2,
                FuelTank1,
                FuelTank2,
                FuelEfficiency1
        };
    }
}
