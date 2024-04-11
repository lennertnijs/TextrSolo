package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SnakeView extends View {
    private Direction headorientation;
    private LinkedList<Point> snake;
    private ArrayList<Point> foods;

    private ArrayList<Point> unoccupied;
    private int timeSinceMove;

    private int moveDelay;
    /**
     * Constructor.
     * @param position
     * @param dimensions
     */
    public SnakeView(Point position, Dimension2D dimensions) {
        super(position, dimensions);
        this.snake = new LinkedList<>();
        this.foods = new ArrayList<>();
        this.unoccupied = new ArrayList<>();
        this.headorientation = Direction.RIGHT;
        this.timeSinceMove = 0;
        this.moveDelay = 1000;
        initializeGame();
    }

    /**
     * Starts the game.
     */
    private void initializeGame(){
        int xhead = getDimensions().getWidth()/2;
        int yhead = getDimensions().getHeight()/2;
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
    /**
     * Increment the timer. Move the snake if it is time to do so.
     */
    public void incrementTimer(){
        timeSinceMove+=10;
        if(timeSinceMove >= moveDelay){
            timeSinceMove-=moveDelay;
            moveSnake();
        }
    }

    /**
     * Move the snake in the direction of the head. End the game if it collides. Do things if it eats food.
     */
    private void moveSnake() {
        //TODO move the snake
    }
    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case ENTER -> restartGame();
            case ARROW_UP -> headorientation= Direction.UP;
            case ARROW_RIGHT -> headorientation= Direction.RIGHT;
            case ARROW_DOWN -> headorientation= Direction.DOWN;
            case ARROW_LEFT -> headorientation= Direction.LEFT;
            case TICK -> incrementTimer();
        }
    }

    /**
     * Restarts the game after the snake dies.
     */
    private void restartGame() {
        //TODO implement restarting the game
    }


}
