package com.axedgaming.drawers.drawer;

import com.axedgaming.drawers.Drawers;
import com.hypixel.hytale.math.vector.Vector3i;

import java.util.HashMap;
import java.util.Map;

public final class DrawerManager {

    private static final Map<Vector3i, DrawerData> DRAWERS = new HashMap<>();

    private DrawerManager() {}

    public static void placeDrawer(Vector3i pos, String blockId) {
        DrawerType type = DrawerRegistry.get(blockId);
        if (type == null) return;

        DRAWERS.put(pos, new DrawerData(type.getTier().getCapacity()));

        Drawers.LOGGER.atInfo().log("Drawer placed at " + pos.x + " " + pos.y + " " + pos.z);
    }

    public static DrawerData get(Vector3i pos) {
        return DRAWERS.get(pos);
    }

    public static void remove(Vector3i pos) {
        DRAWERS.remove(pos);
    }
}

