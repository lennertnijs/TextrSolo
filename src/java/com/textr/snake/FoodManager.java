package com.textr.snake;

import com.textr.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FoodManager {

    private final List<Point> foods;

    public FoodManager(){
        foods = new ArrayList<>();
    }

    public List<Point> getFoods(){
        return foods;
    }

    public boolean isFood(Point p){
        Objects.requireNonNull(p, "Point is null.");
        return foods.stream().anyMatch(food -> food.equals(p));
    }

    public void add(Point p){
        Objects.requireNonNull(p, "Point is null.");
        if(foods.stream().anyMatch(food -> food.equals(p)))
            throw new IllegalStateException("Duplicate Food detected.");
        foods.add(p);
    }

    public void remove(Point p){
        Objects.requireNonNull(p, "Point is null.");
        foods.remove(p);
    }
}
