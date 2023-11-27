package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.ArrayList;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Equipment.class, name = "Equipment"),
        @JsonSubTypes.Type(value = ConsumableItem.class, name = "Consumable"),
        @JsonSubTypes.Type(value = StaticItem.class, name = "Static")
})
public abstract class Item implements Serializable {
    private String id;
    private String name;
    private String description;
    private String type;

    public Item(){}
    @JsonCreator
    public Item(@JsonProperty("id")String id, @JsonProperty("name")String name, @JsonProperty("description")String description, @JsonProperty("type")String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getId() {return id;}
    public String getName() {
        return name;
    }

    public String getInformation() {
        return description;
    }
    public String getType() {return type;}

}

class Equipment extends Item {
    private String sort;
    private ArrayList<Double> stats;
    private int useCount;
    private boolean isEquip;

    public Equipment(){}
    @JsonCreator
    public Equipment(@JsonProperty("id") String id,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("type") String type,
                     @JsonProperty("sort") String sort,
                     @JsonProperty("stats") ArrayList<Double> stats,
                     @JsonProperty("useCount") int useCount,
                     @JsonProperty("isEquip") boolean isEquip) {
        super(id,name,description,type);
        this.sort = sort;
        this.stats = stats;
        this.useCount = useCount;
        this.isEquip = isEquip;
    }

    public String getSort() {return sort;}
    public void setUseCount(int newUseCount) {this.useCount = newUseCount;}
    @Override
    public String getInformation() {
        return  super.getInformation() + "\nHP: " + stats.get(0) + "\nDEF: " + stats.get(1) + "\nAMR: "
                + stats.get(2) + "\nATK: " + stats.get(3);
    }

    public double getHPModifier() {
        return stats.get(0);
    }

    public double getDefModifier() {
        return stats.get(1);
    }

    public double getAmrModifier() {
        return stats.get(2);
    }

    public double getAtkModifier() {
        return stats.get(3);
    }

}

class ConsumableItem extends Item {
    private String sort;
    private int effect;
    private int limit;
    private String required;

    public ConsumableItem(){}
    @JsonCreator
    public ConsumableItem(@JsonProperty("id")String id,
                          @JsonProperty("name")String name,
                          @JsonProperty("description")String description,
                          @JsonProperty("type")String type,
                          @JsonProperty("sort")String sort,
                          @JsonProperty("effect") int effect,
                          @JsonProperty("limit") int limit,
                          @JsonProperty("required")String required) {
        super(id,name,description,type);
        this.sort = sort;
        this.effect = effect;
        this.limit = limit;
        this.required = required;
    }

    public String getSort() {return sort;}
    public int getLimit() {return limit;}
    public int getEffect() {return effect;}
    public String getRequired() {return required;}

    @Override
    public String getInformation() {
        return super.getInformation() + "\n" + getSort() + ": " + getEffect();
    }
}

class StaticItem extends Item {

    public StaticItem(){}
    @JsonCreator
    public StaticItem(@JsonProperty("id")String id, @JsonProperty("name")String name,
                      @JsonProperty("description")String description, @JsonProperty("type")String type) {
        super(id,name,description,type);
    }
}