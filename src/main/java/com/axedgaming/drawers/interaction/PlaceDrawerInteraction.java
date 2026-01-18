package com.axedgaming.drawers.interaction;

import com.axedgaming.drawers.Drawers;
import com.axedgaming.drawers.drawer.DrawerManager;
import com.axedgaming.drawers.drawer.DrawerRegistry;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public class PlaceDrawerInteraction extends SimpleInstantInteraction {

    public static final BuilderCodec<PlaceDrawerInteraction> CODEC =
            BuilderCodec.builder(
                    PlaceDrawerInteraction.class,
                    PlaceDrawerInteraction::new,
                    SimpleInstantInteraction.CODEC
            ).build();

    @Override
    protected void firstRun(
            @Nonnull InteractionType interactionType,
            @Nonnull InteractionContext context,
            @Nonnull CooldownHandler cooldownHandler
    ) {
        Drawers.LOGGER.atInfo().log("PlaceDrawerInteraction firstRun CALLED");

        var commandBuffer = context.getCommandBuffer();
        if (commandBuffer == null) return;

        Store<EntityStore> store = commandBuffer.getExternalData().getStore();
        World world = commandBuffer.getExternalData().getWorld();

        Ref<EntityStore> ref = context.getEntity();
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) return;

        ItemStack held = context.getHeldItem();
        if (held == null) return;

        String blockId = held.getItemId();

        if (!DrawerRegistry.isDrawer(blockId)) return;

        Object target = context.getMetaStore().getMetaObject(Interaction.TARGET_BLOCK);
        if(!(target instanceof BlockPosition bp)) return;

        Vector3i pos = new Vector3i(bp.x, bp.y, bp.z);

        DrawerManager.placeDrawer(pos, blockId);
    }
}
