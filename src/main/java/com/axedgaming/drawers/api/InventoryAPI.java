package com.axedgaming.drawers.api;

import com.hypixel.hytale.component.ComponentAccessor;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class InventoryAPI {

    private InventoryAPI() {}

    /* ===============================
       ITEM EN MAIN (HOTBAR ACTIVE)
       =============================== */

    public static ItemStack getActiveHandItem(Player player) {
        if (player == null) return null;
        return player.getInventory().getActiveHotbarItem();
    }

    /* ===============================
       RETIRER X ITEMS DE LA MAIN
       =============================== */

    public static void removeFromActiveHand(Player player, int amount) {
        if (player == null || amount <= 0) return;

        Inventory inv = player.getInventory();
        byte slot = inv.getActiveHotbarSlot();
        if (slot == -1) return;

        ItemContainer hotbar = inv.getHotbar();
        ItemStack stack = hotbar.getItemStack(slot);
        if (stack == null || stack.isEmpty()) return;

        // Retrait transactionnel propre
        hotbar.moveItemStackFromSlot(
                slot,
                amount,
                inv.getCombinedEverything()
        );
    }

    /* ===============================
       DONNER OU DROP UN ITEMSTACK
       =============================== */

    public static void giveOrDrop(Player player, ItemStack stack) {
        if (player == null || stack == null || stack.isEmpty()) return;

        Ref<EntityStore> ref = player.getReference();
        ComponentAccessor<EntityStore> store = ref.getStore();

        ItemContainer target = player
                .getInventory()
                .getCombinedHotbarFirst();

        // ✅ MÉTHODE OFFICIELLE (SimpleItemContainer)
        SimpleItemContainer.addOrDropItemStack(
                store,
                ref,
                target,
                stack
        );
    }

    /* ===============================
       CONTAINER POUR INSERTION BULK
       =============================== */

    public static ItemContainer getBulkInsertContainer(Player player) {
        return player.getInventory().getCombinedBackpackStorageHotbar();
    }
}