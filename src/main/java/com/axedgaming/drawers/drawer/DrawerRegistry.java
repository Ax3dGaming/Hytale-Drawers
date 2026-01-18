package com.axedgaming.drawers.drawer;

import java.util.HashMap;
import java.util.Map;

public final class DrawerRegistry {

    private static final Map<String, DrawerType> REGISTRY = new HashMap<>();

    private DrawerRegistry() {}

    /* ===== REGISTER ===== */

    public static void register(String blockId, DrawerTier tier) {
        REGISTRY.put(blockId, new DrawerType(blockId, tier));
    }

    /* ===== GET ===== */

    public static DrawerType get(String blockId) {
        return REGISTRY.get(blockId);
    }

    public static boolean isDrawer(String blockId) {
        return REGISTRY.containsKey(blockId);
    }
}
