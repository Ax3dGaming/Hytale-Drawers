package com.axedgaming.drawers.listener;

import com.axedgaming.drawers.api.InventoryAPI;
import com.axedgaming.drawers.drawer.DrawerData;
import com.axedgaming.drawers.drawer.DrawerManager;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;

public class DrawerInteractListener {

    private static final int EXTRACT_ONE = 1;
    private static final int EXTRACT_STACK = 100;

    public void onInteract(PlayerInteractEvent event) {

        /* =====================
           POSITION / DRAWER
           ===================== */

        Vector3i pos = event.getTargetBlock();
        if (pos == null) return;

        DrawerData drawer = DrawerManager.get(pos);
        if (drawer == null) return;

        Player player = event.getPlayer();
        if (player == null) return;

        InteractionType type = event.getActionType();

        /* =====================
           Right Click → Extract
           ===================== */
        if (type == InteractionType.Primary) {

            ItemStack hand = event.getItemInHand();
            int amount = (hand == null || hand.isEmpty())
                    ? EXTRACT_STACK   // main vide = stack
                    : EXTRACT_ONE;    // main pleine = 1

            ItemStack extracted = drawer.extract(amount);
            if (extracted != null) {
                InventoryAPI.giveOrDrop(player, extracted);
            }

            event.setCancelled(true);
            return;
        }

        /* =====================
           Left Click → Insert
           ===================== */
        if (type == InteractionType.Secondary) {

            ItemStack hand = event.getItemInHand();

            if (hand != null && !hand.isEmpty() && drawer.canAccept(hand)) {
                int inserted = drawer.insert(hand);
                if (inserted > 0) {
                    InventoryAPI.removeFromActiveHand(player, inserted);
                }
            }

            insertAllCompatible(player, drawer);

            event.setCancelled(true);
        }
    }

    /* ==================================================
       Insertion of all the compatible inventory
       ================================================== */

    private void insertAllCompatible(Player player, DrawerData drawer) {

        ItemContainer container = InventoryAPI.getBulkInsertContainer(player);

        for (short slot = 0; slot < container.getCapacity(); slot++) {

            ItemStack stack = container.getItemStack(slot);
            if (stack == null || stack.isEmpty()) continue;
            if (!drawer.canAccept(stack)) continue;

            int inserted = drawer.insert(stack);
            if (inserted <= 0) continue;

            container.moveItemStackFromSlot(
                    slot,
                    inserted,
                    new SimpleItemContainer((short) 1)
            );
        }
    }
}