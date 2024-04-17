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

    public List<GamePoint> getSnake(){
        return body;
    }

    public boolean isSnake(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        return body.stream().anyMatch(segment -> segment.equals(p));
    }

    public GamePoint getNextHeadPosition(){
        GamePoint head = body.getFirst();
        switch(headDirection){
            case UP -> {return new GamePoint(head.getX(), head.getY() + 1);}
            case RIGHT -> {return new GamePoint(head.getX() + 1, head.getY());}
            case DOWN -> {return new GamePoint(head.getX(), head.getY() - 1);}
            case LEFT -> {return new GamePoint(head.getX() - 1, head.getY());}
        }
        throw new NoSuchElementException("Direction does not exist.");
    }

    public void move(){
        GamePoint nextHead = getNextHeadPosition();
        body.addFirst(nextHead);
        body.removeLast();
    }

    public void replace(GamePoint old, GamePoint p){
        Objects.requireNonNull(old, "Old GamePoint is null.");
        Objects.requireNonNull(p, "New GamePoint is null.");
        if(!body.contains(old))
            throw new NoSuchElementException("The old GamePoint was not in the snake.");
        for(int i = 0; i < body.size(); i++){
            if(body.get(i).equals(old)) {
                body.set(i, p);
                break;
            }
        }
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
