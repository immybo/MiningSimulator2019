package com.rcampbell.miningsimulator2019;

import com.rcampbell.miningsimulator2019.model.tile.Tile;

public interface TileActionHandler {
    void transform(Tile oldTile, Tile newTile);
    void earnMoney(int amount);
}
