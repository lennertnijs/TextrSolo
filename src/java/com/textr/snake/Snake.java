package com.textr.snake;

import com.textr.util.Direction;

import java.util.*;

public final class Snake {

    private final List<GamePoint> body;
    private Direction headDirection;

    public Snake(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        body = new LinkedList<>();
        this.headDirection = direction;
    }

    public int getLength(){
        return body.size();
    }

    public List<GamePoint> getBody(){
        return new LinkedList<>(body);
    }

    public GamePoint getHead(){
        if(body.size() == 0)
            throw new NoSuchElementException("Snake is of length 0.");
        return body.get(0);
    }

    public boolean isSnake(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        return body.stream().anyMatch(segment -> segment.equals(p));
    }

    public void add(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        if(body.stream().anyMatch(segment -> segment.equals(p)))
            throw new IllegalStateException();
        if(body.size() >= 1 && !isNextTo(body.get(body.size() - 1), p))
            throw new IllegalStateException();
        if(body.size() >= 1 && getNextHeadPosition().equals(p))
            throw new IllegalStateException();
        body.add(p);
    }

    private boolean isNextTo(GamePoint p1, GamePoint p2){
        return p1.x() == p2.x() && Math.abs(p1.y() - p2.y()) == 1 ||
                Math.abs(p1.x() - p2.x()) == 1 && p1.y() == p2.y();
    }

    public void clear(){
        body.clear();
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

    public GamePoint getNextHeadPosition(){
        GamePoint head = getHead();
        switch(headDirection){
            case UP -> {return new GamePoint(head.x(), head.y() + 1);}
            case RIGHT -> {return new GamePoint(head.x() + 1, head.y());}
            case DOWN -> {return new GamePoint(head.x(), head.y() - 1);}
            case LEFT -> {return new GamePoint(head.x() - 1, head.y());}
        }
        throw new AssertionError("Direction does not exist.");
    }

    public void move(){
        GamePoint nextHead = getNextHeadPosition();
        body.add(0, nextHead);
        body.remove(body.size() - 1);
    }
}
