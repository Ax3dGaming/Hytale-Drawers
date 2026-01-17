package com.axedgaming.drawers;

import com.axedgaming.drawers.utils.GetItemInMainHand;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;

import javax.annotation.Nonnull;
public class GetItemInMainHandCommand extends CommandBase {

    public GetItemInMainHandCommand() {
        super("getItemInMainHand", "Prints the item in main hand of the player");
        this.setPermissionGroup(GameMode.Adventure); // Allows the command to be used by anyone, not just OP
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        if(!(ctx.isPlayer())) {
            ctx.sendMessage(Message.raw("Error: the sender must be a player!"));
            return;
        }

        Player player = (Player) ctx.sender();
        ItemStack item = GetItemInMainHand.getItemInMainHand(player);

        if (item == null || item.isEmpty()) {
            ctx.sendMessage(Message.raw("You have no item in your hand!"));
            GetItemInMainHand.log(player);
            return;
        }

        ctx.sendMessage(Message.raw("You hold " + item.getQuantity() + " x " + item.getItemId()));
        GetItemInMainHand.log(player);
    }
}