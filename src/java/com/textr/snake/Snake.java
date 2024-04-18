package com.textr.snake;

import com.textr.util.Direction;

import java.util.*;

public final class Snake {

    /**
     * List of {@link GamePoint}s representing the locations of the snake.
     * The first element represents the head of the snake.
     */
    private final List<GamePoint> body;
    /**
     * The {@link Direction} in which the snake is going to move.
     */
    private Direction headDirection;
    private final List<GamePoint> atePoints;

    /**
     * Creates a new snake. The snake has length 0 at creation.
     * @param direction The {@link Direction} in which the snake should move. Cannot be null.
     */
    public Snake(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        body = new LinkedList<>();
        this.headDirection = direction;
        atePoints = new ArrayList<>();
    }

    /**
     * Creates a new {@link Snake}.
     * @param body The body of the snake. Cannot be null, contain null, or not be connected.
     * @param direction The direction. Cannot be null, or be into its own body.
     * @param atePoints The points where the snake ate. Cannot be null or contain null.
     */
    private Snake(List<GamePoint> body, Direction direction, List<GamePoint> atePoints){
        this.body = body;
        this.headDirection = direction;
        this.atePoints = atePoints;
    }

    /**
     * @return The length of the snake. Includes the head.
     */
    public int getLength(){
        return body.size();
    }

    /**
     * @return A copy of the List of {@link GamePoint}s representing the snake. Includes the head.
     */
    public List<GamePoint> getBody(){
        return new LinkedList<>(body);
    }

    /**
     * @return The {@link GamePoint} representing the head of the snake.
     * @throws NoSuchElementException If the snake has no head. (length 0)
     */
    public GamePoint getHead(){
        if(body.size() == 0)
            throw new NoSuchElementException("Snake does not have a head, because it is length 0.");
        return body.get(0);
    }

    /**
     * Checks whether the given {@link GamePoint} is part of the snake.
     * @param p The {@link GamePoint}. Cannot be null.
     *
     * @return True if part of the snake. False otherwise.
     */
    public boolean isSnake(GamePoint p){
        Objects.requireNonNull(p, "GamePoint is null.");
        return body.stream().anyMatch(segment -> segment.equals(p));
    }

    public void addAtePoint(GamePoint p){
        atePoints.add(p);
    }

    /**
     * Adds the given {@link GamePoint} to the end of the snake.
     * @param p The {@link GamePoint}. Cannot be null.
     *
     * @throws IllegalStateException If the {@link GamePoint} is already part of the snake.
     * @throws IllegalStateException If the {@link GamePoint} is not next to the snake's head.
     * @throws IllegalStateException If the {@link GamePoint} is inserted where the snake would move next.
     *                               (This makes the moving {@link Direction} invalid!)
     */
    public void add(GamePoint p){
        Objects.requireNonNull(p, "Point is null.");
        boolean alreadyPartOfSnake = body.stream().anyMatch(segment -> segment.equals(p));
        boolean notNextToHead = body.size() >= 1 && !isNextTo(body.get(body.size() - 1), p);
        boolean isNextHeadPosition = body.size() >= 1 && getNextHeadPosition().equals(p);
        if(alreadyPartOfSnake)
            throw new IllegalStateException("GamePoint is already part of the snake.");
        if(notNextToHead)
            throw new IllegalStateException("GamePoint is not next to the snake's last segment.");
        if(isNextHeadPosition)
            throw new IllegalStateException("GamePoint is the snake's next position. Invalidates the Direction.");
        body.add(p);
    }

    /**
     * Checks whether the two {@link GamePoint}s are directly next to each other. Diagonally does not count.
     * More precisely, checks whether the Euclidean distance between the two is exactly 1.
     * @param p1 The first {@link GamePoint}. Cannot be null.
     * @param p2 The second {@link GamePoint}. Cannot be null.
     *
     * @return True if next to one another. False otherwise.
     */
    private boolean isNextTo(GamePoint p1, GamePoint p2){
        return p1.x() == p2.x() && Math.abs(p1.y() - p2.y()) == 1 ||
                Math.abs(p1.x() - p2.x()) == 1 && p1.y() == p2.y();
    }

    /**
     * Removes the entire snake, effectively putting it back at length 0.
     */
    public void clear(){
        body.clear();
    }

    /**
     * @return The {@link Direction} the snake will move in, if it is moved.
     */
    public Direction getDirection(){
        return headDirection;
    }

    /**
     * Changes the {@link Direction} of the snake to the given {@link Direction}.
     * If the {@link Direction} would result in the snake's head directly turning into its second segment, does nothing.
     * @param direction The new {@link Direction}. Cannot be null.
     */
    public void changeDirection(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        boolean movingIntoSegmentAfterHead = body.size() >= 2 && body.get(1).equals(nextGamePoint(getHead(), direction));
        if(movingIntoSegmentAfterHead)
            return;
        this.headDirection = direction;
    }

    /**
     * @return The {@link GamePoint} of the snake's head, if it was moved once.
     */
    public GamePoint getNextHeadPosition(){
        return nextGamePoint(getHead(), headDirection);
    }

    /**
     * Calculates and returns a {@link GamePoint}, if moved by one unit in the given {@link Direction}
     * from the given {@link GamePoint}.
     * @param p The {@link GamePoint}. Cannot be null.
     * @param dir The direction. Cannot be null.
     *
     * @return The {@link GamePoint}.
     */
    private GamePoint nextGamePoint(GamePoint p, Direction dir){
        switch(dir){
            case UP -> {return new GamePoint(p.x(), p.y() + 1);}
            case RIGHT -> {return new GamePoint(p.x() + 1, p.y());}
            case DOWN -> {return new GamePoint(p.x(), p.y() - 1);}
            case LEFT -> {return new GamePoint(p.x() - 1, p.y());}
        }
        throw new AssertionError("Should be unreachable.");
    }

    /**
     * Moves the snake one unit in it's {@link Direction}.
     * If the snake's tail is part of the ate points, does not remove the tail, but removes the ate point.
     */
    public void move(){
        GamePoint nextHead = getNextHeadPosition();
        body.add(0, nextHead);
        if(!atePoints.contains(body.get(body.size() - 1)))
            body.remove(body.size() - 1);
        else{
            atePoints.remove(body.get(body.size() - 1));
        }
    }

    /**
     * @return A deep copy of this {@link Snake}.
     */
    public Snake copy(){
        return new Snake(new ArrayList<>(body), headDirection, new ArrayList<>(atePoints));
    }

    /**
     * @return True if the two objects are equal. False otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Snake snake))
            return false;
        return body.equals(snake.body) &&
                headDirection.equals(snake.headDirection) &&
                atePoints.equals(snake.atePoints);
    }

    /**
     * @return The hash code for this {@link Snake}.
     */
    @Override
    public int hashCode(){
        int result = body.hashCode();
        result = result * 31 + headDirection.hashCode();
        result = result * 31 + atePoints.hashCode();
        return result;
    }

    /**
     * @return The string representation of this {@link Snake}.
     */
    @Override
    public String toString(){
        return String.format("Snake[body=%s, headDirection=%s, atePoints=%s]", body, headDirection, atePoints);
    }
}
