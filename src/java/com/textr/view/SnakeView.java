package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Integer.max;

public class SnakeView extends View {

    /**
     * The direction the head of the snake looks at, and the direction the snake will move next.
     */
    private Direction headOrientation;
    /**
     * The positions occupied by the snake, in order from head to tail.
     */
    private LinkedList<Point> snake;
    /**
     * The positions of the food items in the game.
     * There are always 3, except when the snake (nearly) fills the gamefield,
     * leaving less than 3 spaces.
     */
    private ArrayList<Point> foods;
    /**
     * The unoccupied places on the field.
     */
    private ArrayList<Point> unoccupied;
    /**
     * Time since the snake last moved.
     */
    private int timeSinceMove;
    /**
     * Delay between moves. Gets shorter when the snake eats food items.
     */
    private int moveDelay;
    /**
     * The moves the snake will still grow. When eating a food item, the snake will grow for that move and two more.
     */
    private int stillToGrow;
    /**
     * The state of the game. True when it is running, false when it is game over. Does NOT signify whether
     * the view is active/inactive.
     */
    private boolean running;

    /**
     * A random seed to allow for quasi-randomised food spawning.
     */
    private final Random randomSeed;
    private int score;

    /**
     * Constructor.
     * @param position
     * @param dimensions
     */
    public SnakeView(Point position, Dimension2D dimensions) {
        super(position, dimensions);
        this.randomSeed = new Random();
        initializeGame();
    }

    public ArrayList<Point> getSnake() {
        ArrayList<Point> result = new ArrayList<>();
        for (Point point : snake){
            result.add(Point.create(point.getX(),point.getY()));
        }
        return result;
    }
    public ArrayList<Point> getFoods() {
        ArrayList<Point> result = new ArrayList<>();
        for (Point point : foods){
            result.add(Point.create(point.getX(),point.getY()));
        }
        return result;
    }
    public Direction getHeadOrientation() {
        return headOrientation;
    }

    /**
     * Initializes the game. Allows restart of the game within the same view.
     */
    public void initializeGame(){
        this.snake = new LinkedList<>();
        this.foods = new ArrayList<>();
        this.unoccupied = new ArrayList<>();
        this.headOrientation = Direction.RIGHT;
        this.timeSinceMove = 0;
        this.moveDelay = 1000;
        this.stillToGrow = 0;
        this.running = true;
        this.score = 0;
        int xhead = getDimensions().getWidth()/2;
        int yhead = getDimensions().getHeight()/2;
        int i = 0;
        while(i<getDimensions().getWidth()-1){
            int j=0;
            while(j<getDimensions().getHeight()-1){
                unoccupied.add(Point.create(i,j));
                j++;
            }
            i++;
        }
        snake.add(Point.create(xhead, yhead));
        int k = 1;
        while(k<6){
            snake.add(Point.create(xhead-k, yhead));
            k++;
        }
        unoccupied.removeIf(this::occupiedBySnake);
        int l= 0;
        while (l<2){
            spawnFood();
            l++;
        }
    }
    /**
     * Spawns a food item in an unoccupied cell.
     */
    private void spawnFood(){
        if(unoccupied.isEmpty()){return;}
        int index = randomSeed.nextInt(unoccupied.size());
        Point food = unoccupied.get(index);
        unoccupied.remove(food);
        foods.add(food);
    }

    /**
     * Checks whether a cell is occupied by a part of the snake. Returns true if it is.
     * @param tocheck
     * @return
     */
    private boolean occupiedBySnake(Point tocheck){
        for(Point segment : snake ){
            if(tocheck.equals(segment))
                return true;
        }
        return false;
    }
    /**
     * Checks whether a cell is occupied by food. Returns true if it is.
     * @param tocheck
     * @return
     */
    private boolean occupiedByFood(Point tocheck){
        for(Point food : foods){
            if (tocheck.equals(food))
                return true;
        }
        return false;
    }

    /**
     * checks whether a cell is out of bounds.
     * @param cell
     * @return
     */
    private boolean outOfBounds(Point cell){
        return cell.getX()<0 || cell.getX()>= getDimensions().getWidth()-1 ||
                cell.getY()<0 || cell.getY()>= getDimensions().getHeight()-1;
    }


    /**
     * Increment the timer. Move the snake if it is time to do so.
     */
    public boolean incrementTimer(){
        timeSinceMove+=10;
        if(timeSinceMove >= moveDelay && running){
            timeSinceMove=0;
            moveSnake();
            return true;
        }
        else
            return false;
    }

    /**
     * Move the snake in the direction of the head. End the game if it collides. Do things if it eats food.
     */
    private void moveSnake() {
        Point headpos = snake.getFirst();
        Point nextcell;
        switch (headOrientation){
            case LEFT -> nextcell = Point.create(headpos.getX()-1, headpos.getY());
            case RIGHT -> nextcell = Point.create(headpos.getX()+1, headpos.getY());
            case DOWN -> nextcell = Point.create(headpos.getX(), headpos.getY()-1);
            case UP -> nextcell = Point.create(headpos.getX(), headpos.getY()+1);
            default -> nextcell = Point.create(headpos.getX(), headpos.getY());
        }
        if(outOfBounds(nextcell) || occupiedBySnake(nextcell)){
            //end the game
            this.running = false;
        }
        else if(occupiedByFood(nextcell)){
            //allow snake to grow
            foods.removeIf((point)->point.equals(nextcell));
            unoccupied.removeIf(nextcell::equals);
            snake.addFirst(nextcell);
            stillToGrow= 2;
            spawnFood();
            moveDelay= (moveDelay*8)/10;
            score+=10;
        }
        else if(stillToGrow>0){
            //allow the snake to grow
            stillToGrow--;
            unoccupied.removeIf(nextcell::equals);
            snake.addFirst(nextcell);
            score++;
        }
        else {
            //move the snake
            unoccupied.removeIf(nextcell::equals);
            snake.addFirst(nextcell);
            Point behindTail = snake.removeLast();
            unoccupied.add(behindTail);
            score++;
        }
    }

    /**
     * Gets the direction from the head to the first part of the body.
     * @return
     */
    private Direction intoBody() {
        Point head= snake.getFirst();
        Point startBody = snake.get(1);
        if(head.getX()== startBody.getX()){
            if (head.getY()>startBody.getY()){
                return Direction.DOWN;
            }
            else
                return Direction.UP;
        } else if (head.getX()<startBody.getX()) {
            return Direction.RIGHT;
        }
        else
            return Direction.LEFT;
    }

    /**
     * Resizes the gamefield to fit in the current view dimensions.
     */
    public void resize(Dimension2D newdimensions){
        //cut down or add to the size in both dimensions where necessary
        if(newdimensions.getWidth()< getDimensions().getWidth()){
            cutWidth(newdimensions.getWidth());
        }
        if (newdimensions.getHeight()<getDimensions().getHeight()){
            cutHeight(newdimensions.getHeight());
        }
        if (newdimensions.getWidth()> getDimensions().getWidth()){
            addWidth(newdimensions.getWidth());
        }
        if (newdimensions.getHeight()>getDimensions().getHeight()){
            addHeight(newdimensions.getHeight());
        }
        //set the new dimensions
        setDimensions(newdimensions);
        //remove all objects now outside the gamefield
        LinkedList<Point> newSnake = new LinkedList<>();
        for(Point point : snake){
            if (outOfBounds(point)){
                break;
            }
            newSnake.add(point);
        }
        snake = newSnake;
        foods.removeIf(this::outOfBounds);
        //restore unoccupied
        this.unoccupied= new ArrayList<>();
        int i = 0;
        while(i<newdimensions.getWidth()-1){
            int j=0;
            while(j<newdimensions.getHeight()-1){
                unoccupied.add(Point.create(i,j));
                j++;
            }
            i++;
        }
        unoccupied.removeIf((point)->(occupiedBySnake(point)||occupiedByFood(point)));
        //spawn extra food until 3 are available or game field is full
        while (foods.size()<3&& !unoccupied.isEmpty()){
            spawnFood();
        }
    }

    private void addHeight(int newHeight) {
        int heightDiff = newHeight - getDimensions().getHeight();
        snake.forEach((point)->point.addOtherToThis(Point.create(0, heightDiff/2)));
        foods.forEach((point)->point.addOtherToThis(Point.create(0, heightDiff/2)));
    }
    private void addWidth(int newWidth) {
        int widthDiff = newWidth - getDimensions().getWidth();
        snake.forEach((point)->point.addOtherToThis(Point.create(widthDiff/2,0)));
        foods.forEach((point)->point.addOtherToThis(Point.create( widthDiff/2,0)));
    }
    private void cutHeight(int newHeight) {
        int heightDiff = getDimensions().getHeight()- newHeight;
        Point oldHead = snake.getFirst();
        if (getDimensions().getHeight()-oldHead.getY()-newHeight/2<0){
            snake.forEach((point)->point.subtractOtherFromThis(Point.create(0, heightDiff)));
            foods.forEach((point)->point.subtractOtherFromThis(Point.create(0, heightDiff)));
        }
        else if(oldHead.getY()- newHeight/2>0) {
            int moveBy = oldHead.getY()- newHeight / 2 ;
            snake.forEach((point) -> point.subtractOtherFromThis(Point.create(0, moveBy)));
            foods.forEach((point) -> point.subtractOtherFromThis(Point.create(0, moveBy)));
        }
    }
    private void cutWidth(int newWidth) {
        int widthDiff = getDimensions().getWidth()- newWidth;
        Point oldHead = snake.getFirst();
        if (getDimensions().getWidth()-oldHead.getX()-newWidth/2<0){
            int opposite = -widthDiff;
            snake.forEach((point)->point.addOtherToThis(Point.create(opposite, 0)));
            foods.forEach((point)->point.addOtherToThis(Point.create(-widthDiff, 0)));
        }
        else if(oldHead.getX()- newWidth/2>0){
            int moveBy = newWidth/2- oldHead.getX();
            snake.forEach((point)->point.addOtherToThis(Point.create(moveBy, 0)));
            foods.forEach((point)->point.addOtherToThis(Point.create(moveBy, 0)));
        }
    }

    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case ENTER -> {
                if(!running){
                    initializeGame();
                }
            }
            case ARROW_UP -> {
                if(!intoBody().equals(Direction.UP)){
                    headOrientation = Direction.UP;
                }
            }
            case ARROW_RIGHT -> {
                if(!intoBody().equals(Direction.RIGHT)){
                    headOrientation = Direction.RIGHT;
                }
            }
            case ARROW_DOWN -> {
                if(!intoBody().equals(Direction.DOWN)){
                headOrientation = Direction.DOWN;
                }
            }
            case ARROW_LEFT -> {
                if(!intoBody().equals(Direction.LEFT)){
                    headOrientation = Direction.LEFT;
                }
            }
            case TICK -> incrementTimer();
        }
    }

    public boolean getRunning() {
        return running;
    }
}
