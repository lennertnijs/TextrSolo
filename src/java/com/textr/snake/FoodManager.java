package com.textr.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class FoodManager {

    private final List<GamePoint> foods;

    public FoodManager(){
        foods = new ArrayList<>();
    }

    public int getFoodCount(){
        return foods.size();
    }
    public List<GamePoint> getFoods(){
        return foods;
    }

    public boolean isFood(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        return foods.stream().anyMatch(food -> food.equals(p));
    }

    public void add(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        if(foods.stream().anyMatch(food -> food.equals(p)))
            throw new IllegalStateException("Duplicate Food detected.");
        foods.add(p);
    }


    public void remove(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        foods.remove(p);
    }

    public void clearFoods(){
        foods.clear();
    }
}
