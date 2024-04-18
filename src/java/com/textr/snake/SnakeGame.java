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
        running = false;
    }

    public void initialiseGame(){

    }

    public void update(int timeInMillis){
        if(!running)
            return;
        clock.increase(timeInMillis);
        if(clock.shouldMove()){
            clock.reset();
            board.moveSnake();
        }
    }

    public void resize(Dimension2D dimensions){
        board.resize(dimensions);
    }

    public void changeSnakeDirection(Direction direction){
        board.changeSnakeDirection(direction);
    }

    public void start(){
        this.running = true;
    }

    public void pause(){
        this.running = false;
    }
}
