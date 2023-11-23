package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.RandomAccess;

public class Room {

    private String room_id;

   private ArrayList<String> room_name;

   private String[] desc;

   private boolean visit;
   private String[] nav_tab;
   private ArrayList<String> items;
   private ArrayList<Item> inventory = new ArrayList<>();
   private String puzzleID;
   private Puzzle puzzle;
   private ArrayList<Monster> monsters;


    public Room(@JsonProperty("room_id")String room_id,
                @JsonProperty("room_name") ArrayList<String> room_name,
                @JsonProperty("desc") String[] desc,
                @JsonProperty("visit") boolean visit,
                @JsonProperty("items") ArrayList<String> items,
                @JsonProperty("nav_tab") String[] nav_tab,
                @JsonProperty("puzzle") String puzzleID,
                @JsonProperty("monsters") ArrayList<Monster> monsters) {
       this.room_id = room_id;
       this.room_name = room_name;
       this.desc = desc;
       this.visit = visit;
       this.nav_tab = nav_tab;
       this.items = items;
       this.puzzleID = puzzleID;
       this.monsters=monsters;
    }

    public String getRoom_id() {
        return room_id;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public String getNorthID() {
        return nav_tab[0];
    }

    public String getEastID() {
        return nav_tab[1];
    }

    public String getSouthID() {
        return nav_tab[2];
    }

    public String getWestID() {
        return nav_tab[3];
    }

    public ArrayList<String> getItems(){
       return items;
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }
    public ArrayList<String> getListItem() {
        ArrayList<String> listItem = new ArrayList<>();
        for(Item item : inventory) {
            listItem.add(item.getName());
        }
        return listItem;
    }

    public String getPuzzleID() {
        return puzzleID;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public String toString() {

        return String.format("Room Number: %s\nRoom Name: %s\nRoom Description:\n%s",
                room_id, room_name, String.join("\n", desc));
    }

}


