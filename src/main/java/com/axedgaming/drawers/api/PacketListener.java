package com.axedgaming.drawers.api;

import com.axedgaming.drawers.Drawers;
import com.axedgaming.drawers.drawer.DrawerManager;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.player.ClientPlaceBlock;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.adapter.PacketWatcher;

public class PacketListener implements PacketWatcher {
    @Override
    public void accept(PacketHandler packetHandler, Packet packet){
        Drawers.LOGGER.atInfo().log("PacketListener called");
        if(packet.getId()!=117) return;
        ClientPlaceBlock block = (ClientPlaceBlock)packet;
        Drawers.LOGGER.atInfo().log("Block pos");
        if(block.position==null) return;
        Drawers.LOGGER.atInfo().log("Block pos not null");
        BlockType blockType = BlockType.getAssetMap().getAsset(block.placedBlockId);
        Drawers.LOGGER.atInfo().log("Block type "+blockType);
        if(blockType==null) return;
        if(!(blockType.getId().equals("Drawers_Blackwood_Simple"))) return;
        Vector3i pos = new Vector3i(block.position.x, block.position.y, block.position.z);
        Drawers.LOGGER.atInfo().log("Placed Drawer with listener");
        DrawerManager.placeDrawer(pos, blockType.getId());
    }
}
