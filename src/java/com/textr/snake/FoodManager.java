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

    public void replace(GamePoint old, GamePoint p){
        Objects.requireNonNull(old, "Old GamePoint is null.");
        Objects.requireNonNull(p, "New GamePoint is null.");
        int index;
        if((index = foods.indexOf(old)) == -1)
            throw new NoSuchElementException("The old GamePoint was not in the snake.");
        foods.remove(index);
        foods.add(index, p);
    }
}
