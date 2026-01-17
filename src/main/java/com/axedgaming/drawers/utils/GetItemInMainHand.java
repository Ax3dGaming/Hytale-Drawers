package com.axedgaming.drawers.utils;

import com.axedgaming.drawers.Drawers;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;

public class GetItemInMainHand {
    private GetItemInMainHand() {}

    public static ItemStack getItemInMainHand(Player player) {
        if(player == null) return null;
        return player.getInventory().getActiveHotbarItem();
    }

    public static void log(Player player) {
        ItemStack item = getItemInMainHand(player);
        if(item == null || item.isEmpty()) {
            Drawers.LOGGER.atInfo().log("Item is null or empty");
            return;
        }

        Drawers.LOGGER.atInfo().log("[Drawers] " + player.getDisplayName() + " holds " + item.toString() + " x " + item.getQuantity());
    }
}
