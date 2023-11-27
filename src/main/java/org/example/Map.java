package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Map implements Serializable {
    private ArrayList<Room> allRooms;
    private ArrayList<Item> allItems;
    private ArrayList<Puzzle> allPuzzles;
    private ArrayList<Monster> allMonster;

    public Map() throws IOException, InvalidItemException {
        allItems = new ArrayList<>();
        readItem("items.json");

        allMonster = new ArrayList<>();
        readMonster("monster.json");

        allPuzzles = new ArrayList<>();
        readPuzzle("puzzles.json");

        allRooms = new ArrayList<>();
        readMap("rooms.json");

    }

    @JsonCreator
    public Map(ArrayList<Room> rooms, ArrayList<Item> items, ArrayList<Puzzle> puzzles, ArrayList<Monster> monster) {
        this.allRooms = rooms;
        this.allPuzzles = puzzles;
        this.allItems = items;
        this.allMonster = monster;
    }

    public Room getRoom(String room_id) throws InvalidRoomException {
        if (room_id.equals("0")) {
            throw new InvalidRoomException("You can't go that direction.");
        }

        Room room = null;

        for (int i = 0; i < allRooms.size(); i++) {
            if (allRooms.get(i).getRoom_id().equals(room_id)) {
                room = allRooms.get(i);
                break;
            }
        }
        return room;
    }

    public Item getItem(String itemName) {
        try {
            for (Item item : allItems) {
                if (item.getName().equalsIgnoreCase(itemName)) {
                    return item;
                }
            }
            throw new InvalidItemException("Item not found: " + itemName);
        } catch (InvalidItemException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Puzzle getPuzzle(String puzzleID) {
        try {
            for (Puzzle puzzle : allPuzzles) {
                if (puzzle.getPuzzleID().equalsIgnoreCase(puzzleID)) {
                    return puzzle;
                }
            }
            throw new InvalidPuzzleException("Puzzle not found: " + puzzleID);
        } catch (InvalidPuzzleException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Monster getMonster(String monsterID)  {
        try {
            for (Monster monster : allMonster) {
                if (monster.getId().equalsIgnoreCase(monsterID)) {
                    return monster;
                }
            }
            throw new InvalidMonsterException("Monster not found: " + monsterID);
        } catch (InvalidMonsterException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Item> getAllItems() {
        return allItems;
    }

    public List readMap(String fileRoom) throws IOException, InvalidItemException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
        try {
            File roomFile = new File(fileRoom);
            if (!roomFile.exists()) {
                throw new FileNotFoundException("File not found: " + fileRoom);
            }
            allRooms = (ArrayList<Room>) mapper.readValue(roomFile, new TypeReference<List<Room>>() {});
            createItem();
            createMonster();
            createPuzzle();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }  catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidRoomException e) {
            throw new RuntimeException(e);
        } catch (InvalidPuzzleException e) {
            throw new RuntimeException(e);
        }
        return allRooms;

    }

    public ArrayList readItem(String fileItem) throws InvalidItemException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
        try {
            File itemFile = new File(fileItem);
            if (!itemFile.exists()) {
                throw new FileNotFoundException("File not found: " + fileItem);
            }
            allItems = (ArrayList<Item>) mapper.readValue(itemFile, new TypeReference<List<Item>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allItems;
    }

    public ArrayList readPuzzle(String filePuzzle) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
        try {
            File puzzleFile = new File(filePuzzle);
            if (!puzzleFile.exists()) {
                throw new FileNotFoundException("File not found: " + filePuzzle);
            }
            allPuzzles = (ArrayList<Puzzle>) mapper.readValue(puzzleFile, new TypeReference<List<Puzzle>>(){});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allPuzzles;
    }

    public ArrayList readMonster(String fileMonster) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            File monsterFile = new File(fileMonster);
            if (!monsterFile.exists()) {
                throw new FileNotFoundException("File not found: " + fileMonster);
            }
            allMonster = (ArrayList<Monster>) mapper.readValue(monsterFile, new TypeReference<List<Monster>>(){});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allMonster;
    }

    public void createItem() throws InvalidItemException {
        for(Room room: allRooms) {
            ArrayList<String> itemNames = room.getItems();
            for(String itemName: itemNames) {
                ArrayList<Item> inventory = room.getInventory();
                Item item = getItem(itemName);
                inventory.add(item);

            }
        }
    }

    public void createMonster() throws InvalidRoomException {
       for(Monster monster: allMonster) {
           ArrayList<String> roomIDs = monster.getRoomIDs();
           Random randomGenerator = new Random();
           int index = randomGenerator.nextInt(roomIDs.size());
           String roomID = roomIDs.get(index);
           getRoom(roomID).getMonsters().add(monster);
       }
    }

    public void createPuzzle() throws InvalidPuzzleException {
        for(Room room: allRooms) {
            String puzzleID = room.getPuzzleID();
            if(!puzzleID.equalsIgnoreCase("None")){
                Puzzle puzzle = getPuzzle(puzzleID);
                room.setPuzzle(puzzle);
            }
        }
    }
}
