package com.omralcorut.orderfood;

/**
 * Created by omral on 24.12.2016.
 */

public class Food {

    private String name, cost;
    private int id;

    public Food(){

    }

    public Food(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
