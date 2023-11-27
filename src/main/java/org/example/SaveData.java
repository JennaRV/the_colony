package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveData {
    private Player player;
    private ArrayList<Room> rooms;
    private ArrayList<Monster> monsters;
    private ArrayList<Puzzle> puzzles;

    private transient Map map;

    // default constructor
    public SaveData() {

    }

    // constructors, getters, and setters

    public SaveData(Player player, ArrayList<Room> rooms, ArrayList<Monster> monsters, ArrayList<Puzzle> puzzles, Map map) {
        this.player = player;
        this.rooms = rooms;
        this.monsters = monsters;
        this.puzzles = puzzles;
        this.map = map;
    }


    // getters and setters

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void saveGame(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gamestate.dat"))) {
            oos.writeObject(this);
            System.out.println("Game state saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //loading game I got from chatgpt
//    public static Game loadGame() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("gamestate.dat"))) {
//            Game loadedGame = (Game) ois.readObject();
//            System.out.println("Game state loaded successfully!");
//            return loadedGame;
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
}
