package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

public final class SnakeGame {

    private boolean running;
    private final IGameBoard board;
    private final IClock clock;

    public SnakeGame(IGameBoard board, IClock clock){
        this.board = board;
        this.clock = clock;
        running = true;
    }

    public IGameBoard getBoard(){
        return board.copy();
    }


    public boolean hasChanged(){
        return clock.shouldMove();
    }


    public boolean update(int timeInMillis){
        if(!running)
            return false;
        if(clock.shouldMove()){
            if(board.willEatOnMove())
                clock.changeSecondsBetweenMove(0.9f);
            clock.reset();
            try{
                board.moveSnake();
            }catch(IllegalStateException e){
                this.running = false;
            }
            return true;
        }
        clock.increase(timeInMillis);
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
}
