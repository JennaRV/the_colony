package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Map {
    private ArrayList<Room> allRooms;
    private ArrayList<Item> allItems;
    private ArrayList<Puzzle> allPuzzles;
    private ArrayList<Monster> allMonster;

    public Map() throws IOException, InvalidItemException {
        allItems = new ArrayList<>();
        readItem("items.json");

        allRooms = new ArrayList<>();
        readMap("rooms.json");

//
//        allPuzzles = new ArrayList<>();
//        readPuzzle("puzzles.json");
//
//        allMonster = new ArrayList<>();
//        readMonster("monster.json");
    }

    public Map(ArrayList<Room> rooms, ArrayList<Item> items, ArrayList<Puzzle> puzzles, ArrayList<Monster> monster) {
        this.allRooms = rooms;
        this.allPuzzles = puzzles;
        this.allItems = items;
        this.allMonster = monster;
    }

    public Room getRoom(int room_num) throws InvalidRoomException {
        if (room_num == 0) {
            throw new InvalidRoomException("You can't go that direction.");
        }

        Room room = null;

        for (int i = 0; i < allRooms.size(); i++) {
            if (this.allRooms.get(i).getRoom_num() == room_num) {
                room = allRooms.get(i);
                break;
            }
        }
        return room;
    }

    public Item getItem(String itemName) throws InvalidItemException {
        for (Item item : allItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        throw new InvalidItemException("Item not found: " + itemName);
    }

    public Puzzle getPuzzle(String puzzleID) throws InvalidPuzzleException {
        for (Puzzle puzzle : allPuzzles) {
            if (puzzle.getPuzzleID().equalsIgnoreCase(puzzleID)) {
                return puzzle;
            }
        }
        throw new InvalidPuzzleException("Puzzle not found: " + puzzleID);
    }

    public Monster getMonster(String monsterID) throws InvalidMonsterException {
        for (Monster monster : allMonster) {
            if (monster.getId().equalsIgnoreCase(monsterID)) {
                return monster;
            }
        }
        throw new InvalidMonsterException("Monster not found ");
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
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }  catch (IOException e) {
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
            System.out.println(itemNames.toString());
            for(String itemName: itemNames) {
                ArrayList<Item> inventory = room.getInventory();
                Item item = getItem(itemName);
                inventory.add(item);
            }
        }
    }

}
