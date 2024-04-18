package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

import java.util.List;

public final class SnakeGame {

    private boolean running;
    private final IGameBoard board;
    private final IClock clock;

    public SnakeGame(IGameBoard board, IClock clock){
        this.board = board;
        this.clock = clock;
        running = false;
    }

    public boolean hasChanged(){
        return clock.shouldMove();
    }

    public boolean update(int timeInMillis){
        if(!running)
            return false;
        clock.increase(timeInMillis);
        if(clock.shouldMove()){
            clock.reset();
            board.moveSnake();
            return true;
        }
        return false;
    }

    public void resize(Dimension2D dimensions){
        board.resize(dimensions);
    }

    public void changeSnakeDirection(Direction direction){
        board.changeSnakeDirection(direction);
    }

    public boolean isRunning(){
        return running;
    }

    public void start(){
        this.running = true;
    }

    public void pause(){
        this.running = false;
    }

    public Direction getDirection(){
        return board.getDirection();
    }

    public List<GamePoint> getSnake(){
        return board.getSnakePoints();
    }

    public List<GamePoint> getFoods(){
        return board.getFoods();
    }
}
