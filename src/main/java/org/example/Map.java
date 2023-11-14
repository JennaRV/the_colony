package org.example;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Map {
    private ArrayList<Room> allRooms;
    private ArrayList<Item> allItems;
    private ArrayList<Puzzle> allPuzzles;
    private ArrayList<Monster> allMonster;

    public Map() throws FileNotFoundException {
        allRooms = new ArrayList<>();
        readMap("rooms.json");

        allItems = new ArrayList<>();
        readItem("items.json");

        allPuzzles = new ArrayList<>();
        readPuzzle("puzzles.json");

        allMonster = new ArrayList<>();
        readMonster("monster.json");
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
            if (allRooms.get(i).getRoom_num() == room_num) {
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

    public ArrayList readMap(String fileRoom) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            allRooms = (ArrayList<Room>) mapper.readValue(fileRoom, new TypeReference<List<Room>>(){});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return allRooms;
    }

    public ArrayList readItem(String fileItem) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            allItems = (ArrayList<Item>) mapper.readValue(fileItem, new TypeReference<List<Item>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return allItems;
    }

    public ArrayList readPuzzle(String filePuzzle) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            allPuzzles = (ArrayList<Puzzle>) mapper.readValue(filePuzzle, new TypeReference<List<Puzzle>>(){});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return allPuzzles;
    }

    public ArrayList readMonster(String fileMonster) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            allMonster = (ArrayList<Monster>) mapper.readValue(fileMonster, new TypeReference<List<Monster>>(){});
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return allMonster;
    }
}
