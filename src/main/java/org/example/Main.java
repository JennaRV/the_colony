package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {

        Game();

    }

    public static void Game() throws IOException, InvalidRoomException, InvalidPuzzleException, InvalidItemException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your name");
        String in = scanner.nextLine();
        Player p1 = new Player(in);
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
            while (!p1.getCurrentRoom().getMonsters().isEmpty()) {
                HashMap<String, Monster> monsterMap= new HashMap<>();
                System.out.println("There is a monster in this room. " );
                for(Monster i: p1.getCurrentRoom().getMonsters()){
                    if(i!=null){
                        System.out.println(i.getName());
                        monsterMap.put(i.getName(),i);

                    }
                }
                System.out.println("\nSelect a monster to examine");
                String examine = scanner.nextLine();
                Monster monster = monsterMap.get(examine);
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
                                handlePlayerTurn(p1, monster,scanner, monsterMap);
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
            System.out.println("\nEnter a direction (N,S,E,W) that you want to go. Type 'commands' to view all commands.");
            System.out.println("To view your inventory, press 'I'");
            String command=scanner.nextLine().toLowerCase();

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


    public static void handlePlayerTurn(Player p, Monster m, Scanner scanner, HashMap<String, Monster> monsterMap) throws InvalidItemException, IOException {
        System.out.println("Player's turn: Type 'attack' to attack, 'inventory' to access inventory");
        String engage=scanner.nextLine();
        Random random = new Random();
        switch (engage.toLowerCase()) {
            case "attack" -> {
                double pDamage = handlePlayerEffect(p,m,random)-m.getDEF();
                m.setHP(m.getHP() - pDamage);
                System.out.println("You did " +pDamage + " damage!" );
                System.out.println("Monster HP: " + m.getHP());
                if (m.getHP() <= 0) {
                    m.setDead(true);
                    System.out.println("You defeated the monster");
                    ArrayList<Item> droppedItems= m.getDropItems();
                    ArrayList<Double> dropChances=m.getDropChance();
                    if(!droppedItems.isEmpty()){
                        for (int i = 0; i < droppedItems.size(); i++) {
                            double chance = random.nextDouble();

                            if (dropChances.get(i) >= chance) {
                                System.out.println(droppedItems.get(i) + " has been dropped from the monster. Placing it in your inventory.");
                                p.getInventory().add(droppedItems.get(i));
                            }
                        }

                    }
                    p.getCurrentRoom().getMonsters().remove(m);
                    monsterMap.remove(m.getName());
                }
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

    }
    public static void handleMonsterTurn(Player p, Monster m,Scanner scanner) throws IOException, InvalidItemException, InvalidRoomException, InvalidPuzzleException {
        Random random = new Random();
        double mDamage=handleMonsterEffect(m,random);
        if(mDamage-p.getAmr()>0){
            mDamage-= p.getAmr();
            p.setAmr(0);
        }
        else{
            p.setAmr(p.getAmr()-mDamage);
            mDamage=0;
        }
        p.setHp(p.getHp()-(mDamage-p.getDef()));
        System.out.println("Monster did " +(mDamage-p.getDef()) + " damage!" );
        System.out.println("Player HP: " + p.getHp());
        if(p.getHp()<=0){
            System.out.println("The monster has defeated you. You can exit or restart the game.");
            String input= scanner.nextLine().toLowerCase();
            if(input.equalsIgnoreCase("exit")){
                System.exit(0);
            }
            else if(input.equalsIgnoreCase("restart")){
                System.out.println("Game restarted");
                restart();
            }

        }
    }
    public static double handlePlayerEffect(Player p,Monster m, Random rand){
        double damage=p.getAtk();
        ArrayList<Object> specialEffects=m.getSpecialEffects();
        if(specialEffects!=null){
            String effect= (String) specialEffects.get(0);
            String type= (String) specialEffects.get(1);
            double chance= (double) specialEffects.get(2);
            if(effect.equalsIgnoreCase("player")){
                    if(type.equalsIgnoreCase("noDamage")){
                        double prob = rand.nextDouble();
                        if(chance>=prob){
                            return 0;
                        }
                    }
                    else if(type.equalsIgnoreCase("playerMiss")){
                        double prob = rand.nextDouble();
                        System.out.println("Monster effect: " + type);
                        if(chance>=prob){
                            return 0;
                        }
                    }
                    else if (type.equalsIgnoreCase("blockAction")){
                        System.out.println("Monster effect: " + type);
                        double prob = rand.nextDouble();
                        if(chance>=prob){
                            return 0;
                        }
                    }
                    else if(type.equalsIgnoreCase("halfDamage")){
                        System.out.println("Monster effect: " + type);
                        double prob = rand.nextDouble();
                        if(chance>=prob){
                            return p.getAtk()/2;
                        }
                    }
                    else if(type.equalsIgnoreCase("noWeapon")){
                        System.out.println("Monster effect: " + type);
                        ArrayList<Equipment> equippedItems= p.getEquippedItems();
                        for(Equipment e: equippedItems){
                            if(e.getAtkModifier()==0 && e.getSort().equalsIgnoreCase("weapon")){
                                return 1;
                            }
                        }
                    }
                    else{
                        return damage;
                    }

            }
        }
        return damage;
    }
    public static double handleMonsterEffect(Monster m, Random rand){
        double damage=m.getATK();
        ArrayList<Object> specialEffects=m.getSpecialEffects();
        if(specialEffects!=null){
            String effect= (String) specialEffects.get(0);
            String type= (String) specialEffects.get(1);
            double chance= (double) specialEffects.get(2);
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

