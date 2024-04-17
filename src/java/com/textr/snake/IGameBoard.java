package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

public interface IGameBoard {

    int getScore();
    void moveSnake();
    void resize(Dimension2D dimensions);
    void changeDirection(Direction direction);
}
