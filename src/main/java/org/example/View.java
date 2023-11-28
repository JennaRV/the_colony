package org.example;

public class View {
    private Player player;

    public View(Player player) {
        this.player = player;
    }
    public void showCommands(){
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
    }
    public void playerString(){
        System.out.println(this.player.printString());
    }
    public void showStats(){
        System.out.println("HP: " + this.player.getHp());
        System.out.println("DEF: " + this.player.getDef());
        System.out.println("AMR: " + this.player.getAmr());
        System.out.println("ATK: " + this.player.getAtk());
    }



}
