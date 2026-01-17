package com.axedgaming.drawers.listener;

import com.axedgaming.drawers.api.InventoryAPI;
import com.axedgaming.drawers.drawer.DrawerData;
import com.axedgaming.drawers.drawer.DrawerManager;
import com.axedgaming.drawers.drawer.DrawerTier;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.inventory.ItemStack;

public class DrawerInteractListener {

    public void onInteract(PlayerInteractEvent event) {

        if (event.getTargetBlock() == null) return;
        if (event.getActionType() != InteractionType.Use) return;

        Player player = event.getPlayer();
        ItemStack held = event.getItemInHand();

        DrawerData drawer = DrawerManager.getOrCreate(
                event.getTargetBlock(),
                DrawerTier.BASIC
        );

        if (held == null || held.isEmpty()) {
            ItemStack extracted = drawer.extract(64);
            if (extracted != null) {
                InventoryAPI.giveOrDrop(player, extracted);
            }
            event.setCancelled(true);
            return;
        }

        if (!drawer.canAccept(held)) return;

        int inserted = drawer.insert(held);
        if (inserted > 0) {
            InventoryAPI.removeFromHand(player, inserted);
        }

        event.setCancelled(true);
    }
}
