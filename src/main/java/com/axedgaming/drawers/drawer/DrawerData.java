package com.axedgaming.drawers.drawer;

import com.hypixel.hytale.server.core.inventory.ItemStack;

public class DrawerData {

    private final int capacity;
    private String itemId; // null si vide
    private int amount;

    public DrawerData(int capacity) {
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return itemId == null || amount <= 0;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean canAccept(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        return isEmpty() || stack.getItemId().equals(itemId);
    }

    /**
     * @return how many were inserted
     */
    public int insert(ItemStack stack) {
        if (!canAccept(stack)) return 0;

        if (isEmpty()) {
            itemId = stack.getItemId();
        }

        int space = capacity - amount;
        if (space <= 0) return 0;

        int inserted = Math.min(space, stack.getQuantity());
        amount += inserted;
        return inserted;
    }

    /**
     * @return extracted stack (or null)
     */
    public ItemStack extract(int max) {
        if (isEmpty() || max <= 0) return null;

        int taken = Math.min(max, amount);
        amount -= taken;

        ItemStack out = new ItemStack(itemId, taken);

        if (amount <= 0) {
            amount = 0;
            itemId = null;
        }

        return out;
    }
}