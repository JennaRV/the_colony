package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static ArrayList<String> savedPlayers = loadSavedPlayers();
    public static Player p1 = null;

    public static void main(String[] args) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {

        Game();

    }

//    ArrayList<String> savedPlayers = new ArrayList<>();
    public static void Game() throws IOException, InvalidRoomException, InvalidPuzzleException, InvalidItemException {
        Scanner scanner = new Scanner(System.in);

        p1 = buildPlayer();
        View view=new View(p1);
//        String answer = scanner.nextLine();
//
//        if (answer.equalsIgnoreCase("new")) {
//            System.out.println("Type your name");
//            String in = scanner.nextLine();
//            p1 = new Player(in);
//        }
//        else if (answer.equalsIgnoreCase("load")){
//            p1 = loadGame();
//            if (p1 == null) {
//                System.out.println("Failed to load the game. Starting a new game instead.");
//                System.out.println("Type your name");
//                String in = scanner.nextLine();
//                p1 = new Player(in);
//            } else {
//                System.out.println("Game loaded successfully.");
//            }
//        }
        boolean play=true;
        while(play){

            view.playerString();
            if (p1.getCurrentRoom().isVisit()) {
                System.out.println("You've been in this room before");
            } else {
                System.out.println("This is your first time in this room");
                p1.getCurrentRoom().setVisit(true);
            }
            //Monster mechs
            while (!p1.getCurrentRoom().getMonsters().isEmpty()) {
                HashMap<String, Monster> monsterMap= new HashMap<>();
                System.out.println("There is a monster in this room. ");
                for(Monster i: p1.getCurrentRoom().getMonsters()){
                    if(i!=null){
                        System.out.println(i.getName());
                        monsterMap.put(i.getName(),i);

                    }
                }
                System.out.println("\nSelect a monster to examine");
                String examine = scanner.nextLine();
                Monster monster = monsterMap.get(examine);
                boolean flee;
                if (monster!=null) {
                    System.out.println(monster);
                    System.out.println("Type 'attack' to engage with the monster. Type 'ignore' to not fight (it wil still be removed)");
                    String playerAction = scanner.nextLine();
                    boolean playerTurn = true;
                    while (!monster.isDead() ^ p1.getHp() <= 0) {
                        if (playerTurn) {
                            if (playerAction.equalsIgnoreCase("ignore")) {
                                System.out.println("Monster will be ignored and no longer respawn");
                                p1.getCurrentRoom().getMonsters().remove(monster);
                                monsterMap.remove(monster.getName());
                                break;
                            } else {
                                flee=handlePlayerTurn(p1, monster,scanner, monsterMap);
                                if(flee==true){
                                    break;
                                }
                            }

                        } else {
                            handleMonsterTurn(p1, monster,scanner);
                        }
                        playerTurn = !playerTurn;
                    }
                }
                else{
                    System.out.println("Invalid command. Exiting monster stage");
                    break;
                }
            }
            //Room Travel
            try {
                activateNavigation(p1, scanner, play,view);
            } catch (InvalidRoomException | InvalidItemException | InvalidPuzzleException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void activateNavigation(Player p1, Scanner scanner, boolean play, View view) throws InvalidRoomException, InvalidPuzzleException, InvalidItemException, IOException {
        System.out.println("\nEnter a direction (N,S,E,W) that you want to go. Type 'commands' to view all commands.");
        System.out.println("To view your inventory, press 'I'");
        String command = scanner.nextLine().toLowerCase();

        String[] parts = command.split(" ");

        if (parts[0].equalsIgnoreCase("pickup") && parts.length > 1) {
            String itemName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));
            p1.pickupItem(itemName);
            System.out.println(itemName + " has been picked up from the room and successfully added to the player inventory.");
        } else if (parts[0].equalsIgnoreCase("i")) {
            handleInventory(p1, scanner);
        } else if (parts[0].equalsIgnoreCase("look")) {
            p1.look();
        } else if (parts[0].equalsIgnoreCase("solve")) {
            p1.solvePuzzle(scanner, p1.getCurrentRoom().getPuzzle());
        } else if (parts[0].equalsIgnoreCase("stats")) {
           view.showStats();
        } else if (parts[0].equalsIgnoreCase("commands")) {
           view.showCommands();
        } else if (parts[0].equalsIgnoreCase("restart")) {
            restart();
        }
        else if (parts[0].equalsIgnoreCase("save")){
            saveGame(p1);
        }
        else if (parts[0].equalsIgnoreCase("exit")) {
            System.exit(0);
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

    public static boolean handlePlayerTurn(Player p, Monster m, Scanner scanner, HashMap<String, Monster> monsterMap) throws InvalidItemException, IOException {
        boolean flee=false;
        System.out.println("Player's turn: Type 'attack' to attack, 'inventory' to access inventory");
        String engage = scanner.nextLine();
        Random random = new Random();
        switch (engage.toLowerCase()) {
            case "attack" -> {
                double pDamage = handlePlayerEffect(p, m, random);
                if (pDamage - m.getDEF() > 0) {
                    pDamage -= m.getDEF();
                } else {
                    pDamage = 0;
                }
                m.setHP(m.getHP() - pDamage);
                System.out.println("You did " + pDamage + " damage!");
                System.out.println("Monster HP: " + m.getHP());
                if (m.getHP() <= 0) {
                    m.setDead(true);
                    System.out.println("You defeated the monster");
                    ArrayList<Item> droppedItems = m.getDropItems();
                    ArrayList<Double> dropChances = m.getDropChance();
                    if (!droppedItems.isEmpty()) {
                        for (int i = 0; i < droppedItems.size(); i++) {
                            double chance = random.nextDouble();

                            if (dropChances.get(i) >= chance) {
                                System.out.println(droppedItems.get(i).getName() + " has been dropped from the monster. Placing it in your inventory.");
                                p.getInventory().add(droppedItems.get(i));
                            }
                        }

                    }
                    p.getCurrentRoom().getMonsters().remove(m);
                    monsterMap.remove(m.getName());
                }
            }
            case "flee" -> {
                System.out.println("Fleeing combat");
                p.setCurrentRoom(p.getPreviousRoom());
                flee=true;
            }
            case "i" -> {
                try {
                    handleInventory(p, scanner);
                } catch (InvalidItemException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> System.out.println("Invalid command");
        }
        return flee;
    }

    public static void handleMonsterTurn(Player p, Monster m, Scanner scanner) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {
        Random random = new Random();
        double mDamage = handleMonsterEffect(m, random);
        if (mDamage - p.getDef() > 0) {
            mDamage -= p.getDef();
            if (mDamage - p.getAmr() > 0) {
                mDamage -= p.getAmr();
                p.setAmr(0);
            } else {
                p.setAmr(p.getAmr() - mDamage);
                mDamage = 0;
            }
        } else {
            mDamage = 0;
        }
        p.setHp(p.getHp() - (mDamage - p.getDef()));
        System.out.println("Monster did " + (mDamage - p.getDef()) + " damage!");
        System.out.println("Player HP: " + p.getHp());
        if (p.getHp() <= 0) {
            System.out.println("The monster has defeated you. You can exit or restart the game.");
            String input = scanner.nextLine().toLowerCase();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else if (input.equalsIgnoreCase("restart")) {
                System.out.println("Game restarted");
                restart();
            }

        }
    }
    public static double handlePlayerEffect(Player p,Monster m, Random rand){
        double damage=p.getAtk();
        ArrayList<LinkedHashMap<String, Object>> specialEffects=m.getSpecialEffects();
        if (specialEffects != null && specialEffects.size() > 0) {
            for (LinkedHashMap<String, Object> effects : specialEffects) {
                String effect = (String) effects.get("effect");
                String type = (String) effects.get("type");
                double chance = (double) effects.get("chance");
                if (effect.equalsIgnoreCase("player")) {
                    if (type.equalsIgnoreCase("noDamage")) {
                        double prob = rand.nextDouble();
                        if (chance >= prob) {
                            return 0;
                        }
                    } else if (type.equalsIgnoreCase("playerMiss")) {
                        double prob = rand.nextDouble();
                        System.out.println("Monster effect: " + type);
                        if (chance >= prob) {
                            return 0;
                        }
                    } else if (type.equalsIgnoreCase("blockAction")) {
                        System.out.println("Monster effect: " + type);
                        double prob = rand.nextDouble();
                        if (chance >= prob) {
                            return 0;
                        }
                    } else if (type.equalsIgnoreCase("halfDamage")) {
                        System.out.println("Monster effect: " + type);
                        double prob = rand.nextDouble();
                        if (chance >= prob) {
                            return p.getAtk() / 2;
                        }
                    } else if (type.equalsIgnoreCase("noWeapon")) {
                        if(p.getWeapon() == null) {
                            return 1;
                        }
                        return 1;
                    } else {
                        return damage;
                    }

                }
            }
        }
            return damage;

    }
    public static double handleMonsterEffect(Monster m, Random rand){
        double damage=m.getATK();
        ArrayList<LinkedHashMap<String, Object>> specialEffects=m.getSpecialEffects();
        if (specialEffects != null && specialEffects.size() > 0) {
            LinkedHashMap<String, Object> firstEffect = specialEffects.get(0);
            String effect = (String) firstEffect.get("effect");
            String type = (String) firstEffect.get("type");
            double chance = (double) firstEffect.get("chance");
            if(effect.equalsIgnoreCase("monster")){
                if(type.equalsIgnoreCase("QuadrupleDamage")){
                    System.out.println("Monster effect: " + type);
                    double prob=rand.nextDouble();
                    if(chance>=prob) {
                        return m.getATK() * 4;
                    }
                }
            }
        }
        return damage;
    }

    public static void handleInventory(Player p1, Scanner scanner) throws InvalidItemException {
        boolean inInventory = true;

        while (inInventory) {
            p1.printInventory();
            System.out.println("Enter 'explore' + item name to get information of item, " +
                    "'drop' + item name to take item out of inventory, 'equip' + item name to equip item, " +
                    "'un-equip to un-equip item, 'consume' + item name to use consumable item, 'exit' to exit inventory view.");
            String command = scanner.nextLine().toLowerCase();
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
            } else if ((parts[0].equalsIgnoreCase("exit"))) {
                System.out.println("Exiting inventory");
                inInventory = false;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public static void restart() throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {
        Main game = new Main();
        game.Game();
    }

//    public static void saveGame() {
//        GameState gameState = new GameState(player, map);  // Replace 'map' with your actual map object
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name + ".dat"))) {
//            oos.writeObject(gameState);
//            System.out.println("Game saved successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public static ArrayList<String> loadSavedPlayers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savedPlayers.dat"))) {
            return (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No savedPlayers file found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    public static void saveSavedPlayers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedPlayers.dat"))) {
            oos.writeObject(savedPlayers);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void saveGame(Player player) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(player.getName() + ".dat"))) {
            oos.writeObject(player);
            savedPlayers.add(player.getName());
            saveSavedPlayers(); // Save the updated list of saved players
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static Player loadGame(String username) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username + ".dat"))) {
            Player loadedPlayer = (Player) ois.readObject();
            return loadedPlayer;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Save file not found for username: " + username);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Player buildPlayer() throws InvalidItemException, IOException, InvalidRoomException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String playerName = input.nextLine();

        Player player = null;

        // Check if the player name is already saved
        if (savedPlayers.contains(playerName)) {
            System.out.println("\nA profile already exists with that name. What would you like to do?");
            System.out.println("Enter one of the following commands: \n'Load': Load game \n'Overwrite': Overwrite saved game, \n'new': Choose new name");
            String command = input.nextLine();
            Boolean validCommand = false;

            while (!validCommand) {
                if (command.equalsIgnoreCase("load")) {
                    player = loadGame(playerName);
                    validCommand = true;
                } else if (command.equalsIgnoreCase("overwrite")) {
                    savedPlayers.remove(playerName);
                    player = new Player(playerName);
                    validCommand = true;
                } else if (command.equalsIgnoreCase("new")) {
                    player = buildPlayer();
                    validCommand = true;
                } else {
                    System.out.println("Please enter a valid command.");
                }
            }
        } else {
            // If the player name is not saved, create a new player
            player = new Player(playerName);
        }

        return player;
    }
}


