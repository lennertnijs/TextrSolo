package com.textr.view;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SnakeView extends View {
    Direction headoriëntation;
    LinkedList<Point> snake;
    ArrayList<Point> foods;

    ArrayList<Point> unoccupied;

    /**
     * Constructor.
     * @param position
     * @param dimensions
     */
    public SnakeView(Point position, Dimension2D dimensions) {
        super(position, dimensions);
        this.snake = new LinkedList<>();
        this.headoriëntation = Direction.RIGHT;
        initializeGame();
    }

    /**
     * Starts the game.
     */
    private void initializeGame(){
        int xhead = getDimensions().getWidth()/2;
        int yhead = getDimensions().getHeight()/2;
        ArrayList<Point> unoccupied = new ArrayList<>();
        int i = 0;
        while(i<getDimensions().getWidth()){
            int j=0;
            while(j<getDimensions().getHeight()){
                unoccupied.add(Point.create(i,j));
                j++;
            }
            i++;
        }
        snake.add(Point.create(xhead, yhead));
        int k = 1;
        while(k<6){
            snake.add(Point.create(xhead-k, yhead));
            k++;
        }
        unoccupied.removeIf(this::Occupied);
        int l= 0;
        while (l<2){
            spawnFood();
            l++;
        }
    }
    Random rand = new Random();

    /**
     * Spawns a food item in an unoccupied cell.
     */
    private void spawnFood(){
        if(unoccupied.isEmpty()){return;}
        int index = rand.nextInt(unoccupied.size());
        Point food = unoccupied.get(index);
        unoccupied.remove(food);
        foods.add(food);
    }

    /**
     * Checks whether a cell is occupied. Returns true if it is.
     * @param tocheck
     * @return
     */
    private boolean Occupied(Point tocheck){
        for(Point segment : snake ){
            if(tocheck.equals(segment))
                return true;
        }
        for(Point food : foods){
            if (tocheck.equals(food))
                return true;
        }
        return false;
    }
}
