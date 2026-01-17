package com.axedgaming.drawers.drawer;

import com.hypixel.hytale.server.core.inventory.ItemStack;

public class DrawerData {

    private final DrawerTier tier;
    private String itemId;
    private int amount;

    public DrawerData(DrawerTier tier) {
        this.tier = tier;
    }

    public boolean isEmpty() {
        return itemId == null || amount <= 0;
    }

    public boolean canAccept(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        return isEmpty() || stack.getItemId().equals(itemId);
    }

    public int insert(ItemStack stack) {
        if (!canAccept(stack)) return 0;

        if (isEmpty()) {
            itemId = stack.getItemId();
        }

        int space = tier.getCapacity() - amount;
        int inserted = Math.min(space, stack.getQuantity());

        amount += inserted;
        return inserted;
    }

    public ItemStack extract(int max) {
        if (isEmpty()) return null;

        int extracted = Math.min(max, amount);
        amount -= extracted;

        ItemStack stack = new ItemStack(itemId, extracted);

        if (amount <= 0) {
            itemId = null;
        }

        return stack;
    }
}