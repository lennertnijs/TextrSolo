package com.textr.snake;

import com.textr.util.Dimension2D;
import com.textr.util.Direction;

import java.util.List;

public interface IGameBoard {

    Dimension2D getDimensions();
    List<GamePoint> getSnakePoints();
    List<GamePoint> getFoods();
    int getScore();
    void moveSnake();
    void resize(Dimension2D dimensions);
    void changeSnakeDirection(Direction direction);
    void spawnFood();
    Direction getDirection();
    boolean willEatOnMove();
    IGameBoard copy();
}
