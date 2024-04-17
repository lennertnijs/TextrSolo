package com.textr.snake;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class SnakeManager {

    private final List<Point> body;
    private Direction headDirection;

    public SnakeManager(Direction direction){
        body = new LinkedList<>();
        this.headDirection = direction;
    }

    public Point getHead(){
        if(body.size() == 0)
            throw new NoSuchElementException("Snake is of length 0.");
        return body.getFirst();
    }

    public Direction getDirection(){
        return headDirection;
    }

    public void changeDirection(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        if(headDirection.isOpposite(direction) || headDirection == direction)
            return;
        this.headDirection = direction;
    }

    public List<Point> getSnake(){
        return body;
    }

    public boolean isSnake(Point p){
        Objects.requireNonNull(p, "Point is null.");
        return body.stream().anyMatch(segment -> segment.equals(p));
    }

    public Point getNextHeadPosition(){
        Point head = body.getFirst();
        switch(headDirection){
            case UP -> {return Point.create(head.getX(), head.getY() + 1);}
            case RIGHT -> {return Point.create(head.getX() + 1, head.getY());}
            case DOWN -> {return Point.create(head.getX(), head.getY() - 1);}
            case LEFT -> {return Point.create(head.getX() - 1, head.getY());}
        }
        throw new NoSuchElementException("Direction does not exist.");
    }

    public void move(){
        Point nextHead = getNextHeadPosition();
        body.addFirst(nextHead);
        body.removeLast();
    }
}
