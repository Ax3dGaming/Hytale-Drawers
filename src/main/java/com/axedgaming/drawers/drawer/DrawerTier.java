package com.axedgaming.drawers.drawer;

public enum DrawerTier {

    DEFAULT(500),
    COPPER(1000),
    IRON(5000),
    THORIUM(10000),
    COBALT(50000),
    ADAMANTITE(100000),
    MITHRIL(500000),
    CREATIVE(Integer.MAX_VALUE);

    private final int capacity;

    DrawerTier(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
