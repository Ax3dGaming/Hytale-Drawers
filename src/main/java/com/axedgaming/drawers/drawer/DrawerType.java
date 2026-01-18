package com.axedgaming.drawers.drawer;

public class DrawerType {

    private final String blockId;
    private final DrawerTier tier;

    public DrawerType(String blockId, DrawerTier tier) {
        this.blockId = blockId;
        this.tier = tier;
    }

    public String getBlockId() {
        return blockId;
    }

    public DrawerTier getTier() {
        return tier;
    }
}
