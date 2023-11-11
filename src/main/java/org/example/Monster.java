package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Monster {
    private String name, desc;
    private boolean isDead;
    private double HP, DEF, ATK;

    public Monster(@JsonProperty("name") String name, @JsonProperty("desc") String desc, @JsonProperty("isDead") boolean isDead,
                   @JsonProperty("HP") double HP, @JsonProperty("DEF") double DEF, @JsonProperty("ATK") double ATK) {
        this.name = name;
        this.desc = desc;
        this.isDead = isDead;
        this.HP = HP;
        this.DEF = DEF;
        this.ATK = ATK;
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
