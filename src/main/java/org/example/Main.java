package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {

        Game();

    }

    public static void Game() throws IOException, InvalidRoomException, InvalidPuzzleException, InvalidItemException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your name");
        String in = scanner.nextLine();
        Player p1 = new Player(in);
        System.out.println("1");
        boolean play=true;
        while(play){
            System.out.println(p1.printString());
            if(p1.getCurrentRoom().isVisit()){
                System.out.println("You've been in this room before");
            }
            else{
                System.out.println("This is your first time in this room");
                p1.getCurrentRoom().setVisit(true);
            }

            //Puzzle mechs
//            if(p1.getCurrentRoom().getPuzzle()!=null){
//                int attempts=p1.getCurrentRoom().getPuzzle().getNumAttempts();
//                String ans= p1.getCurrentRoom().getPuzzle().getPuzzleA();
//                while(attempts!=0 && !p1.getCurrentRoom().getPuzzle().isSolved()){
//                    System.out.println(p1.getCurrentRoom().getPuzzle().getPuzzleQ());
//                    String user_ans= scanner.nextLine();
//                    if(ans.equalsIgnoreCase(user_ans)){
//                        System.out.println("Correct");
//                        p1.getCurrentRoom().getPuzzle().setSolved(true);
//                    }
//                    else{
//                        attempts--;
//                        System.out.println("Incorrect. Attempts remaining: " + attempts);
//                    }
//                    if(attempts==0){
//                        System.out.println("Failed to solve.");
//                    }
//                }
//            }

            //Monster mechs
//            if (p1.getCurrentRoom().getMonster() != null) {
//                System.out.println("There is a monster in this room: " + p1.getCurrentRoom().getMonster().getName());
//                String examine = scanner.nextLine();
//                Monster monster = p1.getCurrentRoom().getMonster();
//
//                if (examine.equalsIgnoreCase("examine monster")) {
//                    System.out.println(monster.toString());
//                    System.out.println("Type 'attack' to engage with the monster. Type 'ignore' to not fight (it wil still be removed)");
//                    String playerAction = scanner.nextLine();
//                    boolean playerTurn = true;
//                    while (!monster.isDead() ^ p1.getHp() <= 0) {
//                        if (playerTurn) {
//                            if (playerAction.equalsIgnoreCase("ignore")) {
//                                System.out.println("Monster will be ignored and no longer respawn");
//                                p1.getCurrentRoom().setMonster(null);
//                                break;
//                            } else {
//                                //handlePlayerTurn(p1, monster,itemMap,scanner);
//                            }
//
//                        } else {
//                            handleMonsterTurn(p1, monster,scanner);
//                        }
//                        playerTurn = !playerTurn;
//                    }
//                }
//            }



            //Room Travel
            System.out.println("\nEnter a direction (N,S,E,W) that you want to go. Type 'commands' to view all commands.");
            System.out.println("To view your inventory, press 'I'");
            String input=scanner.nextLine().toLowerCase();
            String command = input;
            String[] parts = command.split(" ");

            if (parts[0].equalsIgnoreCase("pickup") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.pickupItem(itemName);
                System.out.println(itemName + " has been picked up from the room and successfully added to the player inventory.");
            }
           else if (parts[0].equalsIgnoreCase("i")) {
               handleInventory(p1,scanner);
           }
            else if (parts[0].equalsIgnoreCase("look")) {
                p1.look();
            } else if (parts[0].equalsIgnoreCase("stats")) {
                System.out.println("HP: "+ p1.getHp());
                System.out.println("DEF: "+ p1.getDef());
                System.out.println("AMR: "+ p1.getAmr());
                System.out.println("ATK: "+ p1.getAtk());
            } else if (parts[0].equalsIgnoreCase("commands")) {
                System.out.println("\n Movement commands: N(North), S(South), E(East), W(West)");
                System.out.println("\n Inventory: I");
                System.out.println("\n Examine monster if there is a monster in the room: Examine Monster");
                System.out.println("\n Pickup item: Pickup {item}");
                System.out.println("\n Explore a room: look");
                System.out.println("\n Explore an item: explore {item} (must be inside inventory)");
                System.out.println("\n Equip an item: equip {item} (must be inside inventory)");
                System.out.println("\n Drop an item: drop {item} (must be inside inventory)");
                System.out.println("\n Attack a monster: attack");
                System.out.println("\n Ignore a monster: ignore");
                System.out.println("\n Exit: can be used to exit the inventory or the game");
                System.out.println("\n Restart: Restarts the game if the player loses to the monster.");
            }  else if (parts[0].equalsIgnoreCase("restart")) {
                restart();
            } else if (parts[0].equalsIgnoreCase("exit")) {
                play = false;
            } else if (parts[0].equalsIgnoreCase("n")) {
                p1.moveNorth();
            } else if (parts[0].equalsIgnoreCase("e")) {
                p1.moveEast();
            } else if (parts[0].equalsIgnoreCase("s")) {
                p1.moveSouth();
            } else if (parts[0].equalsIgnoreCase("w")) {
                p1.moveWest();
            } else {
                System.out.println("Invalid command!");
            }
        }
    }


//    public static void handlePlayerTurn(Player p, Monster m, Scanner scanner) throws InvalidItemException {
//        System.out.println("Player's turn: Type 'attack' to attack, 'inventory' to access inventory");
//        String engage=scanner.nextLine();
//        switch (engage.toLowerCase()) {
//            case "attack" -> {
//                double pDamage = p.getAtk()-m.getDEF();
//                m.setHP(m.getHP() - pDamage);
//                System.out.println("You did " +pDamage + " damage!" );
//                System.out.println("Monster HP: " + m.getHP());
//                if (m.getHP() <= 0) {
//                    m.setDead(true);
//                    System.out.println("You defeated the monster");
//                    p.getCurrentRoom().setMonster(null);
//                }
//            }
//            case "i" -> handleInventory(p, scanner);
//            default -> System.out.println("Invalid command");
//        }
//
//    }
//    public static void handleMonsterTurn(Player p, Monster m,Scanner scanner) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {
//        double monsterThreshold = 0.35;
//        Random random = new Random();
//        double randomValue = random.nextDouble();
//        double mDamage=m.getATK();
//        if(randomValue<monsterThreshold){
//            mDamage=m.getATK()*2;
//        }
//        p.setHp(p.getHp()-(mDamage-p.getDef()));
//        System.out.println("Monster did " +(mDamage-p.getDef()) + " damage!" );
//        System.out.println("Player HP: " + p.getHp());
//        if(p.getHp()<=0){
//            System.out.println("The monster has defeated you. You can exit or restart the game.");
//            String input= scanner.nextLine().toLowerCase();
//            if(input.equalsIgnoreCase("exit")){
//                System.exit(0);
//            }
//            else if(input.equalsIgnoreCase("restart")){
//                System.out.println("Game restarted");
//                restart();
//            }
//
//        }
//    }
    public static void handleInventory(Player p1, Scanner scanner) throws InvalidItemException {
        boolean inInventory = true;

        while (inInventory) {
            p1.getInventory();
            System.out.println("Enter 'explore' + item name to get information of item, " +
                    "'drop' + item name to take item out of inventory, 'equip' + item name to equip item, " +
                    "'un-equip to un-equip item, 'consume' + item name to use consumable item, 'exit' to exit inventory view.");
            String input = scanner.nextLine().toLowerCase();
            String command = input;
            String[] parts = command.split(" ");
            if (parts[0].equalsIgnoreCase("explore") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.exploreItem(itemName);
            } else if (parts[0].equalsIgnoreCase("drop") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.dropItem(itemName);
                System.out.println(itemName + " has been dropped successfully from the player inventory and placed in the room.");
            } else if (parts[0].equalsIgnoreCase("equip") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.equip(itemName);
            } else if (parts[0].equalsIgnoreCase("un-equip") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.unequip(itemName);
            } else if (parts[0].equalsIgnoreCase("consume") && parts.length > 1) {
                String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
                p1.consume(itemName);
            } else if ((parts[0].equalsIgnoreCase("exit"))){
                    System.out.println("Exiting inventory");
                    inInventory = false;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }
    public static void restart() throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {
        Main game= new Main();
        game.Game();
    }
}

