package org.example;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    private Player player;
    private View view;

    public Controller(Player player, View view) {
        this.player = player;
        this.view = view;
    }

    public void startGame() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            view.displayCurrentRoom();
            view.displayInventory();
            view.displayEquippedItems();

            System.out.println("Enter your command:");
            String command = scanner.nextLine();

            // Process user input
            processCommand(command, scanner);

            // Check for game over condition or other exit conditions
            if (/* Check for game over condition */) {
                System.out.println("Game Over!");
                break;
            }
        }

        scanner.close();
    }

    private void processCommand(String command, Scanner scanner) throws IOException {
        // Split the user input into parts, you might want to implement a more sophisticated parsing logic
        String[] parts = command.split(" ");
        String action = parts[0].toLowerCase();

        switch (action) {
            case "move":
                // Example: move 1
                if (parts.length > 1) {
                    int index = Integer.parseInt(parts[1]);
                    view.movePlayer(index); // You should pass the room map to the View class
                }
                break;
            case "pickup":
                // Example: pickup item1
                if (parts.length > 1) {
                    String itemName = parts[1];
                    AbstractItem item = /* Get item by name */; // Implement this logic
                    view.pickUpItem(item);
                }
                break;
            case "attack":
                // You need to implement handlePlayerTurn method and provide required parameters
                handlePlayerTurn(player, monster, itemMap, scanner);
                break;
            case "ignore":
                view.ignoreMonster();
                break;
            case "equip":
                // You need to provide the parameterName and itemMap here
                AbstractItem equipItem = getItemFromInventory(parameterName, itemMap);
                if (equipItem != null && equipItem instanceof Item) {
                    player.equip((Item) equipItem);
                } else {
                    System.out.println("Cannot equip item");
                }
                break;
            case "consume":
                // You need to provide the parameterName and itemMap here
                AbstractItem consumeItem = itemMap.get(parameterName);
                if (consumeItem != null) {
                    if (consumeItem instanceof ConsumableItem) {
                        player.consume((ConsumableItem) consumeItem);
                        System.out.println(consumeItem.getName() + " consumed");
                    } else {
                        System.out.println("Wrong item type");
                    }
                } else {
                    System.out.println("Cannot consume item");
                }
                break;
            case "unequip":
                // You need to provide the parameterName and player here
                AbstractItem unequipItem = getItemFromEquipped(player, parameterName);
                if (unequipItem != null && unequipItem instanceof Item) {
                    player.unequip((Item) unequipItem);
                    System.out.println(unequipItem.getName() + " is unequipped");
                } else {
                    System.out.println("Cannot unequip item");
                }
                break;
            default:
                System.out.println("Invalid command. Try again.");
        }
    }

    // Implement the following methods based on your requirements:

    private AbstractItem getItemFromInventory(String itemName, Map<String, AbstractItem> itemMap) {
        // Implement logic to get item from inventory
        return itemMap.get(itemName);
    }

    private AbstractItem getItemFromEquipped(Player player, String itemName) {
        // Implement logic to get item from equipped items
        return player.getEquippedItem(itemName);
    }

    private void handlePlayerTurn(Player player, Monster monster, Map<String, AbstractItem> itemMap, Scanner scanner) {
        // Implement logic for handling player's turn in combat
    }
}
