package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Monster {
    private String id;
    private ArrayList<String> roomIDs;
    private String name, desc;
    private boolean isDead;
    private double HP, DEF, ATK;
    private ArrayList<String> drop;
    private ArrayList<Object> specialEffects;

    public Monster(@JsonProperty("ID") String id,
                   @JsonProperty("roomIDs") ArrayList<String> roomIDs,
                   @JsonProperty("name") String name,
                   @JsonProperty("desc") String desc,
                   @JsonProperty("isDead") boolean isDead,
                   @JsonProperty("HP") double HP,
                   @JsonProperty("DEF") double DEF,
                   @JsonProperty("ATK") double ATK,
                   @JsonProperty("drop") ArrayList<String> drop,
                   @JsonProperty("specialEffects") ArrayList<Object> specialEffects) {
        this.id = id;
        this.roomIDs = roomIDs;
        this.name = name;
        this.desc = desc;
        this.isDead = isDead;
        this.HP = HP;
        this.DEF = DEF;
        this.ATK = ATK;
        this.drop = drop;
        this.specialEffects = specialEffects;
    }

    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getHP() {
        return HP;
    }

    public double getDEF() {
        return DEF;
    }

    public double getATK() {
        return ATK;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }
    public ArrayList<String> getRoomIDs() {
        return roomIDs;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", isDead=" + isDead +
                ", HP=" + HP +
                ", DEF=" + DEF +
                ", ATK=" + ATK +
                '}';
    }
}