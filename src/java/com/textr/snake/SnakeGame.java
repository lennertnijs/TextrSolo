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

    public void update(){
        if(clock.shouldMove()){
            clock.reset();
            board.moveSnake();
        }
    }

    public void resize(Dimension2D dimensions){
        board.resize(dimensions);
    }

    public void changeDirection(Direction direction){
        board.changeDirection(direction);
    }

    public void setRunning(boolean b){
        this.running = b;
    }
}
