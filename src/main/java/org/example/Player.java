package org.example;


import java.util.*;

public class Player {
    private String name;
    private Room currentRoom;
    private Item[] equipArr= {null, null};

    private double hp=150;
    private double def=10;
    private double atk=15;
    private ArrayList<AbstractItem> inventory=new ArrayList<>();
    public Player(String name, Room currentRoom) {
        this.name=name;
        this.currentRoom = currentRoom;
    }


    public Room getCurrentRoom() {
        return this.currentRoom;
    }


    public void moveRoom(HashMap<Integer, Room> roomMap, int index){
        if (this.currentRoom.getNav_tab()[index]!=0){
            this.currentRoom=roomMap.get(this.currentRoom.getNav_tab()[index]);
        }
        else{
            System.out.println("You can't go that way");
        }

    }
    public void pickUp(AbstractItem item) {
        this.inventory.add(item);

        Iterator<AbstractItem> iterator = this.currentRoom.roomItems().iterator();
        while (iterator.hasNext()) {
            AbstractItem roomItem = iterator.next();
            if (roomItem.equals(item)) {
                iterator.remove();
                break;
            }
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

    public double getAtk() {
        return atk;
    }

    public void equip(Item item) {
        String stats = item.getStats();
        String[] splice = stats.split(" ");
        int mod = Integer.parseInt(splice[0]);
        String type = splice[1];
        if (this.inventory.contains(item)) {
            switch (type) {
                case "DEF":
                    if (this.equipArr[0] == null) {
                        this.equipArr[0] = item;
                        this.def += mod;
                    } else {
                        System.out.println("There is an armor already equipped.");
                    }
                    break;  // Add break statement here
                case "ATK":
                    if (this.equipArr[1] == null) {
                        this.equipArr[1] = item;
                        this.atk += mod;
                        this.inventory.remove(item);
                    } else {
                        System.out.println("There is a weapon already equipped");
                    }
                    break;  // Add break statement here
            }
        } else {
            System.out.println("This item is not in your inventory");
        }
    }

    public void consume(ConsumableItem item){
        if(this.inventory.contains(item)) {
            String stats = item.getStats();
            String[] splice = stats.split(" ");
            int mod = Integer.parseInt(splice[0]);
            this.hp += mod;
        }
        else{
            System.out.println("This item is not in your inventory");
        }

    }
    public void unequip(Item item){
        String stats= item.getStats();
        String[] splice= stats.split(" ");
        int mod=Integer.parseInt(splice[0]);
        String type=splice[1];
        switch(type){
            case "DEF":
                if(this.equipArr[0]!=null) {
                    this.equipArr[0] = null;
                    this.def -= mod;
                    this.inventory.add(item);
                }
                else{
                    System.out.println("There is no armor equipped");
                }
            case "ATK":
                if(this.equipArr[1]!=null) {
                    this.equipArr[1] = null;
                    this.atk-= mod;
                    this.inventory.add(item);
                }
                else{
                    System.out.println("There is no weapon equipped");
                }
        }
    }



    public ArrayList<AbstractItem> getInventory() {
        return inventory;
    }

    public void drop(AbstractItem item){
        this.inventory.remove(item);
        this.currentRoom.addItem(item);
    }

    public Item[] getEquipArr() {
        return this.equipArr;
    }

    public String printString() {
        return String.format("Player: %s\n%s", name, currentRoom.toString());
    }


}
