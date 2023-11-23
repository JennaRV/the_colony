package org.example;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Player {
    private String name;
    private Room currentRoom;
    private Room previousRoom;

    private Map map;
    private ArrayList<Equipment> equippedItems;

    private double hp;
    private double def;
    private double amr;
    private double atk;
    private ArrayList<Item> inventory;

    public Player(String name) throws InvalidRoomException, IOException, InvalidItemException {
        this.name=name;
        map = new Map();
        this.currentRoom = map.getRoom("1");
        inventory = new ArrayList<>();
        equippedItems = new ArrayList<>();
        hp = 10;
        def = 0;
        amr = 0;
        atk = 0;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void moveNorth() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getNorthID());
    }

    public void moveWest() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getWestID());
    }

    public void moveSouth() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getSouthID());
    }

    public void moveEast() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getEastID());
    }

    public void pickupItem(String itemName) throws InvalidItemException {
        Item item = map.getItem(itemName);
        if (currentRoom.getInventory().contains(item)) {
            if (item instanceof ConsumableItem) {
                ConsumableItem consumableItem = (ConsumableItem) item;
                if(inventory.contains(consumableItem)){
                    int count = 0;
                    for(Item item1:inventory){
                        if(item1.getName().equalsIgnoreCase(itemName)){
                            count++;
                        }
                    }
                    if(consumableItem.getLimit() == count) {
                        System.out.println("You already reach the limit of this item. You can't not pick up " + itemName +" now.");
                        return;
                    }
                }
            }
            inventory.add(item);
            currentRoom.getInventory().remove(item);
            System.out.println(currentRoom.getInventory().toString());
            System.out.println("You picked up " + itemName);
        } else {
            System.out.println("Item not found in the room.");
        }
    }

    public void dropItem(String itemName) throws InvalidItemException {
        Item item = map.getItem(itemName);
        if (inventory.contains(item)) {
            inventory.remove(item);
            currentRoom.getInventory().add(item);
            System.out.println("You dropped " + itemName);
        } else {
            System.out.println("Item not found in your inventory.");
        }
    }


    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getDef() {
        return def;
    }
    public double getAmr() {return amr;}

    public void setAmr(double amr) {
        this.amr = amr;
    }

    public double getAtk() {
        return atk;
    }

    public void equip(String itemName) throws InvalidItemException {
        Item item = map.getItem(itemName);
        if (inventory.contains(item)) {
            if (item instanceof Equipment) {
                Equipment equipment = (Equipment) item;
                for (Equipment item1 : equippedItems) {
                    if (item1.getSort().equalsIgnoreCase(equipment.getSort())) {
                        System.out.println("You already equip " + equipment.getSort() + " item.");
                        return;
                    }
                }

                hp += equipment.getHPModifier();
                def += equipment.getDefModifier();
                amr += equipment.getAmrModifier();
                atk += equipment.getAtkModifier();

                equippedItems.add(equipment);
                inventory.remove(equipment);
                System.out.println("3");
                System.out.println(itemName + " has been equipped successfully from the player inventory");
            } else {
                System.out.println("You can't equip that item.");
            }
        } else {
            System.out.println("You don't have that item yet.");
        }
    }

    public void unequip(String itemName) throws InvalidItemException {
        Item item  = map.getItem(itemName);
        if (equippedItems.contains(item)) {
            Equipment equipment = (Equipment) item;
            if(hp - equipment.getHPModifier() <= 0) {
                System.out.println("You will be death after un-equip this item.");
                return;
            }
            hp -= equipment.getHPModifier();
            def -= equipment.getDefModifier();
            amr -= equipment.getAmrModifier();
            atk -= equipment.getAtkModifier();
            equippedItems.remove(equipment);
            inventory.add(equipment);
            System.out.println(itemName + " has been un-equipped successfully to the player inventory");
        } else {
            System.out.println("You did not equip that item yet.");
        }
    }

    public void consume(String itemName) throws InvalidItemException {
        Item item = map.getItem(itemName);
        if(inventory.contains(item)){
            if(item instanceof ConsumableItem) {
                ConsumableItem consumableItem = (ConsumableItem) item;
                if(!consumableItem.getRequired().equalsIgnoreCase("None")){
                    Item requiredItem = map.getItem(consumableItem.getRequired());
                    if(!inventory.contains(requiredItem)){
                        System.out.println("You need " + requiredItem.getName() + " to use this item.");
                        return;
                    }
                }
                if(consumableItem.getSort().equalsIgnoreCase("Healing")){
                    hp += consumableItem.getEffect();
                } else if (consumableItem.getSort().equalsIgnoreCase("Armor")) {
                    amr += consumableItem.getEffect();
                } else if (consumableItem.getSort().equalsIgnoreCase("Bullet")) {
                    Equipment weapon =(Equipment) map.getItem(consumableItem.getRequired());
                    weapon.setUseCount(consumableItem.getEffect());
                }
                inventory.remove(consumableItem);
            } else {
                System.out.println("That's not a consumable item.");
            }
        } else {
            System.out.println("This item is not in your inventory");
        }
    }

    public void printInventory() {
        ArrayList<String> listItem = new ArrayList<>();
        for(Item item : inventory) {
            listItem.add(item.getName());
        }
        System.out.println(listItem);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void exploreItem(String itemName) throws InvalidItemException {
        Item item = map.getItem(itemName);
        if (inventory.contains(item) || currentRoom.getInventory().contains(item)) {
            item.getInformation();
            if(item.getType().equalsIgnoreCase("Equipment")){

            }
        } else {
            System.out.println("You can't inspect this item now.");
        }
    }


    public ArrayList<Equipment> getEquippedItems () {
        return this.equippedItems;
    }

    public void look() {
        System.out.println("Item: " + currentRoom.getListItem());
    }

    public String printString() {
        return String.format("Player: %s\n%s", name, currentRoom.toString());
    }


}
