package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {

        Game();



    }

    public static void Game() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Room> rooms = objectMapper.readValue(new File("rooms.json"), new TypeReference<>() {
        });
        //Room Map
        HashMap<Integer, Room> roomMap = new HashMap<>();
        for(Room room: rooms){
            roomMap.put(room.getRoom_num(),room);
        }

        Room currentRoom=rooms.get(0);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your name");
        String in = scanner.nextLine();
        Player p1 = new Player(in,currentRoom);
        System.out.println(p1.printString());
        boolean play=true;
        while(play){
            if(p1.getCurrentRoom().isVisit()){
                System.out.println("You've been in this room before");

            }
            else{

                System.out.println("This is your first time in this room");
                p1.getCurrentRoom().setVisit(true);

            }
            ArrayList<AbstractItem> items = new ArrayList<>(p1.getInventory());
            Map<String, AbstractItem> itemMap = new HashMap<>();
            for (AbstractItem item : items) {
                itemMap.put(item.getName().toLowerCase(), item);
            }

            //Puzzle mechs
            if(p1.getCurrentRoom().getPuzzle()!=null){
                int attempts=p1.getCurrentRoom().getPuzzle().getNumAttempts();
                String ans= p1.getCurrentRoom().getPuzzle().getPuzzleA();
                while(attempts!=0 && !p1.getCurrentRoom().getPuzzle().isSolved()){
                    System.out.println(p1.getCurrentRoom().getPuzzle().getPuzzleQ());
                    String user_ans= scanner.nextLine();
                    if(ans.equalsIgnoreCase(user_ans)){
                        System.out.println("Correct");
                        p1.getCurrentRoom().getPuzzle().setSolved(true);
                    }
                    else{
                        attempts--;
                        System.out.println("Incorrect. Attempts remaining: " + attempts);
                    }
                    if(attempts==0){
                        System.out.println("Failed to solve.");
                    }
                }
            }

            //Monster mechs
            if (p1.getCurrentRoom().getMonster() != null) {
                System.out.println("There is a monster in this room: " + p1.getCurrentRoom().getMonster().getName());
                String examine = scanner.nextLine();
                Monster monster = p1.getCurrentRoom().getMonster();

                if (examine.equalsIgnoreCase("examine monster")) {
                    System.out.println(monster.toString());
                    System.out.println("Type 'attack' to engage with the monster. Type 'ignore' to not fight (it wil still be removed)");
                    String playerAction = scanner.nextLine();
                    boolean playerTurn = true;
                    while (!monster.isDead() ^ p1.getHp() <= 0) {
                        if (playerTurn) {
                            if (playerAction.equalsIgnoreCase("ignore")) {
                                System.out.println("Monster will be ignored and no longer respawn");
                                p1.getCurrentRoom().setMonster(null);
                                break;
                            } else {
                                handlePlayerTurn(p1, monster,itemMap,scanner);
                            }

                        } else {
                            handleMonsterTurn(p1, monster,scanner);
                        }
                        playerTurn = !playerTurn;
                    }
                }
            }




            //Room Travel
            System.out.println("\nEnter a direction (N,S,E,W) that you want to go. Type 'commands' to view all commands.");
            System.out.println("To view your inventory, press 'I'");
            String input=scanner.nextLine().toLowerCase();
            String[] parts=input.split("\\s+");
            String command;
            String parameter=null;
            if(parts.length==1){
                command=parts[0];
            }
            else{
                command = parts[0];
                parameter = parts[1];
            }
            switch(command){
                case "n":
                    p1.moveRoom(roomMap,0);
                    System.out.println(p1.printString());
                    break;
                case "e":
                    p1.moveRoom(roomMap,1);
                    System.out.println(p1.printString());
                    break;
                case "s":
                    p1.moveRoom(roomMap,2);
                    System.out.println(p1.printString());
                    break;
                case "w":
                    p1.moveRoom(roomMap,3);
                    System.out.println(p1.printString());
                    break;
                case "look":
                    if(p1.getCurrentRoom().itemExists()) {
                        for (AbstractItem i : p1.getCurrentRoom().roomItems()) {
                            System.out.println(i.getName());
                        }
                    }
                    else {
                        System.out.println("[]");
                    }
                    if(p1.getCurrentRoom().getMonster() != null){
                        System.out.println(p1.getCurrentRoom().getMonster());
                    }
                    if(p1.getCurrentRoom().getPuzzle() != null){
                        if(!p1.getCurrentRoom().getPuzzle().isSolved()) {
                            System.out.println(p1.getCurrentRoom().getPuzzle().getPuzzleName());
                        }
                    }
                    break;

                case "pickup":
                    if (p1.getCurrentRoom().itemExists()) {
                        List<AbstractItem> allItems = new ArrayList<>(p1.getCurrentRoom().roomItems());
                        for(AbstractItem i: allItems){
                            if(i.getName().toLowerCase().equals(parameter)){
                                p1.pickUp(i);
                                System.out.println(i.getName() + " has been picked up and stored in your inventory.");
                            }
                            else {
                                System.out.println("Nothing was picked up");
                            }
                            break;
                        }

                    }
                    break;

                case "i":
                    handleInventory(p1,scanner,itemMap);
                    break;
                case "stats":
                    System.out.println("HP: "+ p1.getHp());
                    System.out.println("DEF: "+ p1.getDef());
                    System.out.println("ATK: "+ p1.getAtk());

                    break;

                case "commands":
                    System.out.println("\n Movement commands: N(North), S(South), E(East), W(West)");
                    System.out.println("\n Inventory: I");
                    System.out.println("\n Examine monster if there is a monster in the room: Examine Monster");
                    System.out.println("\n Pickup item: Pickup {item}");
                    System.out.println("\n Explore a room: Explore");
                    System.out.println("\n Explore an item: explore {item} (must be inside inventory)");
                    System.out.println("\n Equip an item: equip {item} (must be inside inventory)");
                    System.out.println("\n Drop an item: drop {item} (must be inside inventory)");
                    System.out.println("\n Attack a monster: attack");
                    System.out.println("\n Ignore a monster: ignore");
                    System.out.println("\n Exit: can be used to exit the inventory or the game");
                    System.out.println("\n Restart: Restarts the game if the player loses to the monster.");
                    break;
                default:
                    System.out.println("Exiting game");
                    play=false;
            }
        }

    }


    public static void handlePlayerTurn(Player p, Monster m,Map<String, AbstractItem> itemMap, Scanner scanner){
        System.out.println("Player's turn: Type 'attack' to attack, 'inventory' to access inventory");
        String engage=scanner.nextLine();
        switch (engage.toLowerCase()) {
            case "attack" -> {
                double pDamage = p.getAtk()-m.getDEF();
                m.setHP(m.getHP() - pDamage);
                System.out.println("You did " +pDamage + " damage!" );
                System.out.println("Monster HP: " + m.getHP());
                if (m.getHP() <= 0) {
                    m.setDead(true);
                    System.out.println("You defeated the monster");
                    p.getCurrentRoom().setMonster(null);
                }
            }
            case "i" -> handleInventory(p, scanner, itemMap);
            default -> System.out.println("Invalid command");
        }

    }
    public static void handleMonsterTurn(Player p, Monster m,Scanner scanner) throws IOException {
        double monsterThreshold = 0.35;
        Random random = new Random();
        double randomValue = random.nextDouble();
        double mDamage=m.getATK();
        if(randomValue<monsterThreshold){
            mDamage=m.getATK()*2;
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
    public static void handleInventory(Player p1, Scanner scanner, Map<String, AbstractItem> itemMap) {
        boolean inInventory = true;
        if(!p1.getInventory().isEmpty()) {
            for (AbstractItem i : p1.getInventory()) {
                itemMap.put(i.getName(),i);
                System.out.println(i.getName());
            }
        }
        else {
            System.out.println("Inventory is empty");
        }

        while (inInventory) {
            String input = scanner.nextLine().toLowerCase();
            String[] parts1 = input.split(" ");
            String commandType = parts1[0];

            if (parts1.length > 1) {
                String parameterName = parts1[1];
                switch (commandType) {
                    case "explore" -> {
                        AbstractItem exploreItem = itemMap.get(parameterName);
                        if (exploreItem != null) {
                            System.out.println(exploreItem.toString());
                        } else {
                            System.out.println("Item does not exist in inventory.");
                        }
                    }
                    case "drop" -> {
                        AbstractItem dropItem = itemMap.get(parameterName);
                        if (dropItem != null) {
                            p1.drop(dropItem);
                            System.out.println(dropItem.getName() + " has been dropped");
                        } else {
                            System.out.println("Item does not exist in inventory.");
                        }
                    }
                    case "equip" -> {
                        AbstractItem equipItem = getItemFromInventory(parameterName,itemMap);
                        if (equipItem != null && equipItem instanceof Item) {
                            p1.equip((Item) equipItem);
                        } else {
                            System.out.println("Cannot equip item");
                        }
                    }
                    case "consume" -> {
                        AbstractItem consumeItem = itemMap.get(parameterName);
                        if (consumeItem != null) {
                            if (consumeItem instanceof ConsumableItem) {
                                p1.consume((ConsumableItem) consumeItem);
                                System.out.println(consumeItem.getName() +" consumed");
                            } else {
                                System.out.println("Wrong item type");
                            }
                        } else {
                            System.out.println("Cannot consume item");
                        }
                    }
                    case "unequip" -> {
                        AbstractItem unequipItem = getItemFromEquipped(p1, parameterName);
                        if (unequipItem != null && unequipItem instanceof Item) {
                            p1.unequip((Item) unequipItem);
                            System.out.println(unequipItem.getName() + " is unequipped");
                        } else {
                            System.out.println("Cannot unequip item");
                        }
                    }
                }
            } else {
                if ("equipped".equals(commandType)) {
                    AbstractItem[] equipped = p1.getEquipArr();
                    for (AbstractItem i : equipped) {
                        if (i != null) {
                            System.out.println("Equipped: " + i);
                        }

                    }
                } else {
                    System.out.println("Exiting inventory");
                    inInventory = false;
                }
            }
        }
    }
    private static AbstractItem getItemFromInventory(String itemName, Map<String, AbstractItem> itemMap ) {
        AbstractItem item= itemMap.get(itemName);
        if(item!=null && item instanceof Item){
            return item;
        }
        else{
            return null;
        }
    }
    private static AbstractItem getItemFromEquipped(Player p, String itemName) {
        Item[] arr=p.getEquipArr();
        for(Item i: arr){
            if (i!=null && i.getName().equalsIgnoreCase(itemName)){
                return i;
            }
        }
        return null;
    }
    public static void restart() throws IOException {
        Main game= new Main();
        game.Game();
    }
}

