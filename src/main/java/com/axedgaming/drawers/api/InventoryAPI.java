package com.axedgaming.drawers.api;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;

public final class InventoryAPI {

    private InventoryAPI() {}

    /**
     * Remove X items from the player hand
     */
    public static void removeFromHand(Player player, int amount) {

        Inventory inv = player.getInventory();
        byte slot = inv.getActiveHotbarSlot();

        if (slot == -1) return;

        ItemContainer hotbar = inv.getHotbar();
        ItemStack stack = hotbar.getItemStack(slot);

        if (stack == null || stack.isEmpty()) return;

        SimpleItemContainer trash = new SimpleItemContainer((short) 1);
        hotbar.moveItemStackFromSlot(slot, amount, trash);
    }

    /**
     * Gives an item to the player, or drop it on ground
     */
    public static void giveOrDrop(Player player, ItemStack stack) {

        Inventory inv = player.getInventory();
        ItemContainer target = inv.getCombinedHotbarFirst();

        SimpleItemContainer source = new SimpleItemContainer((short) 1);
        source.setItemStackForSlot((short) 0, stack);

        source.moveItemStackFromSlot((short) 0, stack.getQuantity(), target);

        source.dropAllItemStacks();
    }
}
