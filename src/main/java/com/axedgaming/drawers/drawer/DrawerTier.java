package com.axedgaming.drawers.drawer;

public enum DrawerTier {

    BASIC(512),
    IRON(2048),
    GOLD(8192),
    DIAMOND(32768);

    private final int capacity;

    DrawerTier(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
