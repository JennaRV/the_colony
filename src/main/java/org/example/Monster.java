package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Monster {
    private String id;
    private ArrayList<Integer> roomIDs;
    private String name, desc;
    private boolean isDead;
    private double HP, DEF, ATK;
    private ArrayList<String> drop;
    private ArrayList<Object> specialEffects;
    private ArrayList<Double> chance = new ArrayList<>();
    private String effect;


    public Monster(@JsonProperty("ID") String id,
                   @JsonProperty("roomIDs") ArrayList<Integer> roomIDs,
                   @JsonProperty("name") String name,
                   @JsonProperty("desc") String desc,
                   @JsonProperty("isDead") boolean isDead,
                   @JsonProperty("HP") double HP,
                   @JsonProperty("DEF") double DEF,
                   @JsonProperty("ATK") double ATK,
                   @JsonProperty("effect") String effect,
                   @JsonProperty("drop") ArrayList<String> drop,
                   @JsonProperty("chance") ArrayList<Double> chance,
                   @JsonProperty("specialEffects") ArrayList<Object> specialEffects) {
        this.id = id;
        this.roomIDs = roomIDs;
        this.name = name;
        this.desc = desc;
        this.isDead = isDead;
        this.HP = HP;
        this.DEF = DEF;
        this.ATK = ATK;
        this.effect=effect;
        this.drop = drop;
        this.chance=chance;
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

    public ArrayList<Item> getDropItems() throws InvalidItemException, IOException {
         Map map=new Map();
        ArrayList<Item> monsterItems=new ArrayList<>();
        ArrayList<String> allItemStrings=new ArrayList<>();

        for(Item i: map.getAllItems()){
            allItemStrings.add(i.getName());
        }
        for (String itemName : drop) {
            if (allItemStrings.contains(itemName)) {
                Item item = map.getItem(itemName);
                if(item!=null) {
                    monsterItems.add(item);
                }
            }
        }
        return monsterItems;

    }

    public ArrayList<Double> getDropChance() {
        return chance;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }
    public ArrayList<Integer> getRoomIDs() {
        return roomIDs;
    }

    public ArrayList<Object> getSpecialEffects() {
        return specialEffects;
    }



    @Override
    public String toString() {
        return "Monster{" +
                "\nid='" + id + '\'' +
                ", \nroomIDs=" + roomIDs +
                ", \nname='" + name + '\'' +
                ", \ndesc='" + desc + '\'' +
                ", \nisDead=" + isDead +
                ", \nHP=" + HP +
                ", \nDEF=" + DEF +
                ", \nATK=" + ATK +
                ", \neffects=" + specialEffects +
                ", \ndrop=" + drop +
                ", \nchance=" + chance +
                ", \nspecialEffects=" + specialEffects +
                '}';
    }
}