package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Room {

    private String room_id;

   private ArrayList<String> room_name;

   private ArrayList<String> desc;

   private boolean visit;
   private ArrayList<ArrayList<String>> nav_tab;
   private ArrayList<String> items;
   private ArrayList<Item> inventory = new ArrayList<>();
   private String puzzleID;
   private Puzzle puzzle;
   private ArrayList<Monster> monsters;
   boolean pathless;
   boolean locked;

    public Room(@JsonProperty("room_id")String room_id,
                @JsonProperty("room_name") ArrayList<String> room_name,
                @JsonProperty("desc") ArrayList<String> desc,
                @JsonProperty("visit") boolean visit,
                @JsonProperty("items") ArrayList<String> items,
                @JsonProperty("nav_tab") ArrayList<ArrayList<String>> nav_tab,
                @JsonProperty("puzzle") String puzzleID,
                @JsonProperty("monsters") ArrayList<Monster> monsters,
                @JsonProperty("pathless") boolean pathless,
                @JsonProperty("locked") boolean locked) {
       this.room_id = room_id;
       this.room_name = room_name;
       this.desc = desc;
       this.visit = visit;
       this.nav_tab = nav_tab;
       this.items = items;
       this.puzzleID = puzzleID;
       this.monsters=monsters;
       this.pathless = pathless;
       this.locked = locked;
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
        return nav_tab.get(0).get(0);
    }
    public void setNorthID(String id) {nav_tab.get(0).set(0, id);}
    public String getEastID() {
        return nav_tab.get(0).get(1);
    }
    public void setEastID(String id) {nav_tab.get(0).set(1, id);}
    public String getSouthID() {
        return nav_tab.get(0).get(2);
    }
    public void setSouthID(String id) {nav_tab.get(0).set(2, id);}
    public String getWestID() {
        return nav_tab.get(0).get(3);
    }
    public void setWestID(String id) {nav_tab.get(0).set(3, id);}
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
    public boolean isPathless() {
        return pathless;
    }
    public boolean isLocked() {return locked;}
    public void unlock() {
        room_name.remove(0);
        desc.remove(0);
        nav_tab.remove(0);
        locked = false;
    }
    public String toString() {
        return String.format("Room Number: %s\nRoom Name: %s\nRoom Description:\n%s",
                room_id, room_name.get(0), String.join("\n", desc.get(0)));
    }

}


