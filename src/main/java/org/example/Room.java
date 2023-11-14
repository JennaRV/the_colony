package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private AbstractItem item;
    private int room_num;
   private String[] room_name;
   private String[] desc;
   private boolean visit;
   private int[] nav_tab;
   private ArrayList<AbstractItem> items = new ArrayList<>();
   private Puzzle puzzle;
   private Monster monster;

    public Monster getMonster() {
        return monster;
    }

    public Room(@JsonProperty("room_num") int room_num, @JsonProperty("room_name") String[] room_name,
                @JsonProperty("desc") String[] desc, @JsonProperty("visit") boolean visit,
                @JsonProperty("nav_tab") int[] nav_tab) throws IOException {
       this.room_num = room_num;
       this.room_name = room_name;
       this.desc = desc;
       this.visit = visit;
       this.nav_tab = nav_tab;
       ObjectMapper objectMapper = new ObjectMapper();

       //Assign items to rooms
       JsonNode rootNode = objectMapper.readTree(new File("items.json"));
       for (JsonNode node : rootNode) {
           int roomNum=node.get("room_num").asInt();
           String item_id = node.get("item_id").asText();
           String name = node.get("name").asText();
           String description = node.get("description").asText();
           String stats=node.get("stats").asText();

           AbstractItem newItem;
            if(this.room_num==roomNum){
                if("Physical".equals(item_id)){
                    newItem = new Item(name, description,stats);
                    this.items.add(newItem);
                } else{
                    newItem = new ConsumableItem(name, description,stats);
                    this.items.add(newItem);
                }
            }
            else{
                newItem=null;
            }
       }

    //Assign a puzzle to a room
       JsonNode puzzleNode = objectMapper.readTree(new File("puzzles.json"));
       for(JsonNode node: puzzleNode){
           int roomID=node.get("roomID").asInt();
           String puzzleQ=node.get("puzzleQ").asText();
           String puzzleA=node.get("puzzleA").asText();
           boolean isSolved=node.get("isSolved").asBoolean();
           int numAttempts=node.get("numAttempts").asInt();

           if(this.room_num==roomID){
               this.puzzle=new Puzzle(puzzleQ,puzzleA,isSolved,numAttempts);
               break;
           }
           else{
               this.puzzle=null;
           }
       }

       //Assign monster to a room

        JsonNode monsterNode = objectMapper.readTree(new File("monster.json"));

        for (JsonNode node : monsterNode) {
            int roomID = node.get("roomID").asInt();
            String name = node.get("name").asText();
            String mdesc = node.get("desc").asText();
            boolean isDead = node.get("isDead").asBoolean();
            double hp = node.get("HP").asDouble();
            double def = node.get("DEF").asDouble();
            double atk = node.get("ATK").asDouble();

            if (this.room_num == roomID) {
                this.monster = new Monster(name, mdesc, isDead, hp, def, atk);
            } else {
                this.monster = null;
            }
        }
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

    public int[] getNav_tab() {
        return nav_tab;
    }

    public boolean itemExists() {
        if (!this.items.isEmpty()){
            return true;
        }
        return false;
    }
    public ArrayList<AbstractItem> roomItems(){
       return this.items;
    }
    public AbstractItem getItem(){
       return this.item;
    }
    public void addItem(AbstractItem item){
       this.items.add(item);
    }

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
        String itemInfo = (item != null) ? String.format("\nItem Info:\n%s", item.toString()) : "";

        return String.format("Room Number: %d\nRoom Name: %s\nRoom Description:\n%s",
                room_num, Arrays.toString(room_name), Arrays.toString(desc));
    }
}


