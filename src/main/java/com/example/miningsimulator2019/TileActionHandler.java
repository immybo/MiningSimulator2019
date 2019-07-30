package com.example.miningsimulator2019;

public interface TileActionHandler {
    void transform(Tile oldTile, Tile newTile);
    void earnMoney(int amount);
}
