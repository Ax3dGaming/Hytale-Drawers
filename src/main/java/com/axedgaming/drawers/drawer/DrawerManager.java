package com.axedgaming.drawers.drawer;

import com.hypixel.hytale.math.vector.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class DrawerManager {

    private static final Map<Vector3i, DrawerData> DRAWERS = new HashMap<>();

    public static DrawerData getOrCreate(Vector3i pos, DrawerTier tier) {
        return DRAWERS.computeIfAbsent(pos, p -> new DrawerData(tier));
    }

    public static DrawerData get(Vector3i pos) {
        return DRAWERS.get(pos);
    }

    public static void remove(Vector3i pos) {
        DRAWERS.remove(pos);
    }
}
