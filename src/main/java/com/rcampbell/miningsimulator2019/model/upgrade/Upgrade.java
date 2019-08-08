package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.R;
import com.rcampbell.miningsimulator2019.model.MiningRobot;

public abstract class Upgrade {
    // TODO enforce checking of names at compile time when these are referenced
    private static Upgrade[] allUpgrades = new Upgrade[] {
            new MiningSpeedUpgrade(100, "Superior Drill", new String[0], R.mipmap.default_image, 500),
            new MiningSpeedUpgrade(500, "Adamantium Drill", new String[]{"Superior Drill"}, R.mipmap.default_image, 200),
            new FuelAmountUpgrade(100, "Large Fuel Tank", new String[0], R.mipmap.default_image, 200),
            new FuelAmountUpgrade(1000, "Huge Fuel Tank", new String[]{"Large Fuel Tank"}, R.mipmap.default_image, 500),
            new FuelEfficiencyUpgrade(500, "Fuel Recycling", new String[0], R.mipmap.default_image, 0.5)
    };

    private final int cost;
    private final String name;
    private final String[] dependencies;
    private final int icon;

    public Upgrade(int cost, String name, String[] dependencies, int icon) {
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

    public String[] getDependencies() {
        return dependencies;
    }

    public int getIcon() {
        return icon;
    }

    public boolean hasAllDependencies(MiningRobot robot) {
        for (String dependency : getDependencies()) {
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

    public static Upgrade[] getAllUpgrades() {
        return allUpgrades;
    }
}
