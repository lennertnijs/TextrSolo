package com.textr.snake;

import com.textr.util.Direction;

import java.util.*;

public final class SnakeManager {

    private final List<GamePoint> body;
    private Direction headDirection;

    public SnakeManager(Direction direction){
        body = new LinkedList<>();
        this.headDirection = direction;
    }

    public GamePoint getHead(){
        if(body.size() == 0)
            throw new NoSuchElementException("Snake is of length 0.");
        return body.get(0);
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

    public List<GamePoint> getSnake(){
        return body;
    }

    public boolean isSnake(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        return body.stream().anyMatch(segment -> segment.equals(p));
    }

    public GamePoint getNextHeadPosition(){
        GamePoint head = body.get(0);
        switch(headDirection){
            case UP -> {return new GamePoint(head.x(), head.y() + 1);}
            case RIGHT -> {return new GamePoint(head.x() + 1, head.y());}
            case DOWN -> {return new GamePoint(head.x(), head.y() - 1);}
            case LEFT -> {return new GamePoint(head.x() - 1, head.y());}
        }
        throw new NoSuchElementException("Direction does not exist.");
    }

    public void move(){
        GamePoint nextHead = getNextHeadPosition();
        body.add(0, nextHead);
        body.remove(body.size() - 1);
    }

    public void replace(GamePoint old, GamePoint p){
        Objects.requireNonNull(old, "Old GamePoint is null.");
        Objects.requireNonNull(p, "New GamePoint is null.");
        int index;
        if((index = body.indexOf(old)) == -1)
            throw new NoSuchElementException("The old GamePoint was not in the snake.");
        body.remove(index);
        body.add(index, p);
    }

    public void cut(GamePoint p){
        Objects.requireNonNull(p, "GamePoint is null.");
        if(!body.contains(p))
            throw new NoSuchElementException("The GamePoint is not in the snake.");
        boolean cutOffFound = false;
        Iterator<GamePoint> iterator = body.iterator();
        while(iterator.hasNext()){
            GamePoint gamePoint = iterator.next();
            if(gamePoint.equals(p))
                cutOffFound = true;
            if(cutOffFound)
                iterator.remove();
        }
    }
}
