package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class FoodManagerTest {

    private GamePoint point1;
    private GamePoint point2;
    private GamePoint point3;
    private GamePoint duplicatePoint;
    private FoodManager foodManager;
    @BeforeEach
    public void initialise(){
        point1 = new GamePoint(1,2);
        point2 = new GamePoint(-5, -4);
        point3 = new GamePoint(3, 4);
        duplicatePoint = new GamePoint(1, 2);
        foodManager = new FoodManager();
    }

    @Test
    public void getFoods(){
        assertEquals(foodManager.getFoods(), new ArrayList<>());
    }

    @Test
    public void testAdd(){
        foodManager.add(point1);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point1)));

        foodManager.add(point2);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point1, point2)));

        foodManager.add(point3);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point1, point2, point3)));

        assertThrows(IllegalStateException.class, () -> foodManager.add(duplicatePoint));
    }

    @Test
    public void testGetFoodCount(){
        assertEquals(foodManager.getFoodCount(), 0);

        foodManager.add(point1);
        assertEquals(foodManager.getFoodCount(), 1);

        foodManager.add(point2);
        assertEquals(foodManager.getFoodCount(), 2);

        foodManager.add(point3);
        assertEquals(foodManager.getFoodCount(), 3);
    }

    @Test
    public void testIsFood(){
        foodManager.add(point1);
        assertTrue(foodManager.isFood(point1));
        assertFalse(foodManager.isFood(point2));
        assertTrue(foodManager.isFood(duplicatePoint));
    }

    @Test
    public void testRemove(){
        foodManager.add(point1);
        foodManager.add(point2);
        foodManager.add(point3);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point1, point2, point3)));

        foodManager.remove(point1);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point2, point3)));

        foodManager.remove(point2);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point3)));

        foodManager.remove(point3);
        assertEquals(foodManager.getFoods(), new ArrayList<>());
    }

    @Test
    public void testRemoveWithNullPoint(){
        assertThrows(NullPointerException.class, () -> foodManager.remove(null));
    }

    @Test
    public void testReplace(){
        foodManager.add(point1);
        foodManager.add(point2);
        foodManager.add(point3);

        GamePoint point4 = new GamePoint(45, 40);
        foodManager.replace(point1, point4);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point4, point2, point3)));

        GamePoint point5 = new GamePoint(35, 40);
        foodManager.replace(point2, point5);
        assertEquals(foodManager.getFoods(), new ArrayList<>(List.of(point4, point5, point3)));
    }

    @Test
    public void testReplaceWithElementNotPresent(){
        GamePoint point4 = new GamePoint(45, 40);
        assertThrows(NoSuchElementException.class, () -> foodManager.replace(point4, point1));
    }

    @Test
    public void testReplaceWithNull(){
        assertThrows(NullPointerException.class, () -> foodManager.replace(null, point1));
        assertThrows(NullPointerException.class, () -> foodManager.replace(point1, null));
    }

}
