package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class View {
    private Player player; // Reference to the Player model

    public View(Player player) {
        this.player = player;
    }

    // Methods to display information to the user
    public void displayCurrentRoom() {
        System.out.println(player.printString());
    }

    public void displayInventory() {
        System.out.println("Inventory:");
        for (AbstractItem item : player.getInventory()) {
            System.out.println(item.toString());
        }
    }

    public void displayEquippedItems() {
        System.out.println("Equipped Items:");
        for (Item item : player.getEquipArr()) {
            if (item != null) {
                System.out.println(item.toString());
            }
        }
    }

    // Other methods for displaying player status, messages, etc.

    // Methods to handle user input (part of the Controller)
    public void movePlayer(HashMap<Integer, Room> roomMap, int index) {
        player.moveRoom(roomMap, index);
    }

    public void pickUpItem(AbstractItem item) {
        player.pickUp(item);
    }

    public void equipItem(Item item) {
        player.equip(item);
    }

    public void consumeItem(ConsumableItem item) {
        player.consume(item);
    }

    public void unequipItem(Item item) {
        player.unequip(item);
    }

    public void dropItem(AbstractItem item) {
        player.drop(item);
    }

    public void ignoreMonster() {
    }
}
