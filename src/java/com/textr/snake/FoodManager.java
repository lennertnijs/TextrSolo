package com.textr.snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class FoodManager {

    /**
     * List of {@link GamePoint}s representing the locations of food in the game.
     */
    private final List<GamePoint> foods;

    /**
     * Initializes a new {@link FoodManager} with no foods.
     */
    public FoodManager(){
        foods = new LinkedList<>();
    }

    /**
     * Creates a new {@link FoodManager}.
     * @param foods The foods. Cannot be null or contain null.
     */
    private FoodManager(List<GamePoint> foods){
        this.foods = foods;
    }

    /**
     * @return The amount of foods.
     */
    public int getFoodCount(){
        return foods.size();
    }

    /**
     * @return A copy of the List of foods.
     */
    public List<GamePoint> getFoods(){
        return new LinkedList<>(foods);
    }

    /**
     * Checks whether the given {@link GamePoint} holds food.
     * @param p The {@link GamePoint}. Cannot be null.
     *
     * @return True if it holds food. False otherwise.
     */
    public boolean isFood(GamePoint p){
        Objects.requireNonNull(p, "GamePoint is null.");
        return foods.stream().anyMatch(food -> food.equals(p));
    }

    /**
     * Adds the given {@link GamePoint} to the List of foods.
     * @param p The {@link GamePoint}. Cannot be null.
     *
     * @throws IllegalStateException If the given {@link GamePoint} already held food.
     */
    public void add(GamePoint p){
        Objects.requireNonNull(p, "GamePoint is null.");
        if(foods.stream().anyMatch(food -> food.equals(p)))
            throw new IllegalStateException("Duplicate food detected.");
        foods.add(p);
    }

    /**
     * Removes the {@link GamePoint} if it held a piece of food.
     * @param p The {@link GamePoint}. Cannot be null.
     */
    public void remove(GamePoint p){
        Objects.requireNonNull(p, "GamePoint is null.");
        foods.remove(p);
    }

    /**
     * Removes all foods.
     */
    public void clearFoods(){
        foods.clear();
    }

    /**
     * @return A deep copy of this {@link FoodManager}.
     */
    public FoodManager copy(){
        return new FoodManager(new ArrayList<>(foods));
    }

    /**
     * @return True if the two objects are equal. False otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof  FoodManager f))
            return false;
        return foods.equals(f.foods);
    }

    /**
     * @return The hash code of this {@link FoodManager}.
     */
    @Override
    public int hashCode(){
        return foods.hashCode();
    }

    /**
     * @return A string representation of this {@link FoodManager}.
     */
    @Override
    public String toString(){
        return String.format("FoodManager[foods=%s]", foods);
    }
}
