package com.rcampbell.miningsimulator2019.model.upgrade;

import com.rcampbell.miningsimulator2019.model.MiningRobot;

import java.util.List;

public abstract class Upgrade {
    private static Upgrade[] allUpgrades = new Upgrade[] {
            new MiningSpeedUpgrade(100, "Superior Drill", new String[0], 500),
            new MiningSpeedUpgrade(500, "Adamantium Drill", new String[]{ "Superior Drill" }, 200)
    };

    private final int cost;
    private final String name;
    private final String[] dependencies;

    public Upgrade(int cost, String name, String[] dependencies) {
        this.cost = cost;
        this.name = name;
        this.dependencies = dependencies;
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
