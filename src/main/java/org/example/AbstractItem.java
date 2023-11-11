package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractItem {
    private String name;
    private String description;
    private String stats;


    public AbstractItem(@JsonProperty("name") String name, @JsonProperty("description") String description,@JsonProperty("stats") String stats) {
        this.name = name;
        this.description = description;
        this.stats=stats;
    }
    public abstract void use(Player player);


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public String getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "AbstractItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

class Item extends AbstractItem {


    public Item(String name, String description, String stats) {
        super(name,description,stats);

    }
    @Override
    public void use(Player player){
        player.equip(this);
    }






}

class ConsumableItem extends AbstractItem {
    public ConsumableItem(String name, String description, String stats) {

        super(name, description,stats);
    }
    @Override
    public void use(Player player){
        player.consume(this);
    }

}