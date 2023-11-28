package org.example;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private static final long serialVersionUID = 7560846660268210980L;
    private String name;
    private Room currentRoom;
    private Room previousRoom;
    private Map map;
    private Equipment weapon;
    private Equipment gear;
    private Equipment flashlight;
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
        weapon=null;
        gear=null;
        flashlight=null;
        hp = 10;
        def = 0;
        amr = 0;
        atk = 0;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public String getName() {
        return name;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void moveNorth() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getNorthID());
        if(currentRoom.isPathless()){//Pathless Forest(R8)'s NSWE is determined by the last room the player has entered
            currentRoom.setSouthID(previousRoom.getRoom_id());
        }
        if(previousRoom.isPathless()){//Reset nav_tab after go out Pathless Forest(R8)
            previousRoom.setNorthID("0");
        }
    }

    public void moveWest() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getWestID());
        if(currentRoom.isPathless()){
            currentRoom.setEastID(previousRoom.getRoom_id());
            System.out.println("test1");
        }
        if(previousRoom.isPathless()){
            previousRoom.setWestID("0");
        }
    }

    public void moveSouth() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getSouthID());
        if(currentRoom.isPathless()){
            currentRoom.setNorthID((previousRoom.getRoom_id()));
        }
        if(previousRoom.isPathless()){
            previousRoom.setSouthID("0");
        }
    }

    public void moveEast() throws InvalidRoomException, InvalidPuzzleException {
        this.previousRoom = currentRoom;
        this.currentRoom = map.getRoom(currentRoom.getEastID());
        if(currentRoom.isPathless()){
            currentRoom.setWestID((previousRoom.getRoom_id()));
        }
        if(previousRoom.isPathless()){
            previousRoom.setEastID("0");
        }
    }

    public Room getPreviousRoom() {
        return previousRoom;
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
    public Equipment getWeapon() {return weapon;}

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
                if(equipment.getSort().equalsIgnoreCase("Weapon")){
                    if(weapon != null){
                        System.out.println("You already equip " + equipment.getSort() + " item.");
                        return;
                    } else {
                        weapon = equipment;
                    }
                } else if(equipment.getSort().equalsIgnoreCase("Gear")) {
                    if(gear != null){
                        System.out.println("You already equip " + equipment.getSort() + " item.");
                        return;
                    } else {
                        gear = equipment;
                    }
                } else if(equipment.getSort().equalsIgnoreCase("Flashlight")) {
                    if(flashlight != null){
                        System.out.println("You already equip " + equipment.getSort() + " item.");
                        return;
                    } else {
                        flashlight = equipment;
                    }
                }

                hp += equipment.getHPModifier();
                def += equipment.getDefModifier();
                amr += equipment.getAmrModifier();
                atk += equipment.getAtkModifier();

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
        Equipment equipment = (Equipment) item;
        if(equipment == weapon) {
            weapon = null;
        } else if(equipment == gear) {
            gear = null;
        } else if(equipment == flashlight) {
            flashlight = null;
        } else {
            System.out.println("You did not equip that item yet.");
            return;
        }
        hp -= equipment.getHPModifier();
        def -= equipment.getDefModifier();
        amr -= equipment.getAmrModifier();
        atk -= equipment.getAtkModifier();
        inventory.add(equipment);
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

    public void look() {

        System.out.println("Item: " + currentRoom.getListItem());
        System.out.println("Puzzles: " + currentRoom.getPuzzle().getPuzzleName());
    }

    public void solvePuzzle(Scanner scanner, Puzzle puzzle) throws InvalidItemException {
        if(puzzle!=null) {
            Integer attempts = puzzle.getNumAttempts();
            int currentAttempts = 0;
            String ans = puzzle.getPuzzleA();
            ArrayList<String> puzzleHints = puzzle.getHints();
            ArrayList<String> drops = puzzle.getPuzzleDrops();
            while (attempts != 0 && !puzzle.isSolved()) {
                System.out.println(puzzle.getPuzzleQ());
                String user_ans = scanner.nextLine();
                System.out.println();
                switch (user_ans) {
                    case "attack":
                        System.out.println("You cannot attack while engrossed in this puzzle.");
                        break;
                    case "exit room":
                        System.out.println("You are too engrossed in this puzzle to leave right now.");
                        break;
                    case "exit puzzle", "exit":
                        System.out.println("You have exited the puzzle");
                        System.out.println();
                        return;
                    default:
                        if (puzzle.checkAnswer(user_ans)) {
                            if (checkConditions(user_ans)) {
                                System.out.println("Success! " + puzzle.getSuccessMessage());
                                System.out.println();
                                if(!"none".equals(puzzle.getPart2ID())){
                                    solvePuzzle(scanner, map.getPuzzle(puzzle.getPart2ID()));
                                }
                                puzzle.setSolved(true);
                                for (String item : drops) {
                                    Item puzzleDrop = map.getItem(item);
                                    getCurrentRoom().getInventory().add(puzzleDrop);
                                    System.out.println(item + " has been dropped in the room");
                                }
                                System.out.println();
                                if(currentRoom.locked) {
                                    currentRoom.unlock();
                                }
                            }

                        }
                        else {
                            System.out.println("That didn't work. Try again.");
                            if (attempts != -1) {
                                currentAttempts++;
                                attempts--;
                                System.out.println("You have " + attempts + " attempts left.");
                                for (int i = 0; i < currentAttempts && i < puzzleHints.size(); i++) {
                                    System.out.println();
                                    System.out.println(puzzleHints.get(i));
                                }

                            }

                        }
                        if (attempts == 0) {
                            System.out.println();
                            System.out.println("Failed to solve. " + puzzle.getFailureMessage());
                            if (puzzle.killsPlayer()) {
                                System.exit(0);

                            }
                        }
                }
            }
        }
        else {
            System.out.println("There is nothing to solve in this room");
        }
    }

    public boolean checkConditions(String answer) throws InvalidItemException {
        String[] conditions = answer.split("\\s+", 2);

        if (conditions.length > 0) {
            String condition = conditions[0];
            String itemName = (conditions.length > 1) ? conditions[1] : "";
//            Item item = map.getItem(itemName);

            switch (condition.toLowerCase()) {
                case "use":
                    Item item = map.getItem(itemName);
                    if(getInventory().contains(item)){
                        System.out.println("You used the " + itemName);
                        return true;
                    }
                    else {
                        System.out.println("You are not currently carrying that item.");
                        return false;
                    }
                case "drop":
                    item = map.getItem(itemName);
                    if(getInventory().contains(item)){
                        dropItem(itemName);
                        return true;
                    }
                    else {
                        System.out.println("You are not currently carrying that item.");
                        return false;
                    }
                default:
                    return true;

            }
        }

        return false;
    }

    public String printString() {
        return String.format("Player: %s\n%s", name, currentRoom.toString());
    }

}
