package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Room {

    private int room_num;

   private String room_name;

   private String[] desc;

   private boolean visit;
   private int[] nav_tab;
   private ArrayList<Item> items;
   private Puzzle puzzle;
   private Monster monster;


    public Monster getMonster() {
        return monster;
    }

    public Room(@JsonProperty("room_num")int room_num, @JsonProperty("room_name") String room_name, @JsonProperty("desc") String[] desc,
                @JsonProperty("visit") boolean visit, @JsonProperty("nav_tab") int[] nav_tab,
                @JsonProperty("items") ArrayList<Item> items) {
       this.room_num = room_num;
       this.room_name = room_name;
       this.desc = desc;
       this.visit = visit;
       this.nav_tab = nav_tab;
       this.items = items;
    }

    public int getRoom_num() {
        return room_num;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public int getNorthID() {
        return nav_tab[0];
    }

    public int getEastID() {
        return nav_tab[1];
    }

    public int getSouthID() {
        return nav_tab[2];
    }

    public int getWestID() {
        return nav_tab[3];
    }

    public boolean itemExists() {
        if (!this.items.isEmpty()){
            return true;
        }
        return false;
    }

    public ArrayList<Item> roomItems(){
       return this.items;
    }
    public String getListItem() {return items.toString();}

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public String toString() {
        //String itemInfo = (item != null) ? String.format("\nItem Info:\n%s", item.toString()) : "";

        return String.format("Room Number: %d\nRoom Name: %s\nRoom Description:\n%s",
                room_num, room_name, String.join("\n", desc));
    }

}


