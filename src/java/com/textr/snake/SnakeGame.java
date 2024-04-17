package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

public final class SnakeGame {

    private boolean running;
    private final GameBoard board;
    private final Clock clock;

    public SnakeGame(GameBoard board, Clock clock){
        this.board = board;
        this.clock = clock;
        running = true;
    }

    public void update(){
        if(clock.shouldMove()){
            clock.reset();
            board.moveSnake();
        }
    }

    public void resize(Dimension2D dimensions){
        board.resizeBoard(dimensions);
    }

    public void changeDirection(Direction direction){
        board.changeDirection(direction);
    }

    public void setRunning(boolean b){
        this.running = b;
    }
}
