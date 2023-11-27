package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SaveData {
    private Player player;

    private transient Map map;

    // default constructor
    public SaveData() {
    }

    // constructors, getters, and setters

    public SaveData(Player player, Map map) {
        this.player = player;
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

    // ... other methods ...
}
