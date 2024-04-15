package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.Objects;

public final class LineText implements IText{

    private final static char LINEBREAK = '\n';
    private final StringBuilder builder;
    private int insertionIndex;

    private LineText(StringBuilder builder){
        this.builder = builder;
        insertionIndex = 0;
    }

    public static LineText createFromString(String string){
        Objects.requireNonNull(string, "String is null.");
        String replacedLineBreaks = string.replace("\r\n", "\n").replace("\r", "\n");
        return new LineText(new StringBuilder(replacedLineBreaks));
    }

    public String[] getLines(){
        return builder.toString().split(String.valueOf(LINEBREAK));
    }

    public int getAmountOfLines(){
        return builder.toString().split(String.valueOf(LINEBREAK)).length;
    }

    public int getAmountOfChars(){
        return builder.toString().length();
    }

    public void insertCharacter(char character){
        builder.insert(character, insertionIndex);
    }

    public void removeCharacterBefore(){
        if(insertionIndex == 0)
            return;
        builder.deleteCharAt(insertionIndex - 1);
    }

    public void removeCharacterAfter(){
        if(insertionIndex == builder.length())
            return;
        builder.deleteCharAt(insertionIndex);
    }

    public void insertNewLine(){
        builder.insert(insertionIndex, "\n");
    }

    public void moveCursor(Direction direction){
        Objects.requireNonNull(direction, "Direction is null.");
        switch(direction){
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
            case UP -> moveUp();
            case DOWN -> moveDown();
        }
    }

    private void moveRight(){
        if(insertionIndex + 1 <= builder.length())
            insertionIndex++;
    }

    private void moveLeft(){
        if(insertionIndex - 1 >= 0)
            insertionIndex--;
    }

    private void moveUp(){
        Point cursor2D = translate1DCursorTo2D(insertionIndex);
        if(cursor2D.getY() == 0)
            return;
        String[] lines = builder.toString().split("\n");
        cursor2D.decrementY();
        int lengthOfPrevLine = lines[cursor2D.getY()].length();
        cursor2D.setX(Math.min(lengthOfPrevLine, cursor2D.getX()));
        insertionIndex = translate2DCursorTo1D(cursor2D);
    }

    private void moveDown(){
        String[] lines = builder.toString().split("\n");
        Point cursor2D = translate1DCursorTo2D(insertionIndex);
        if(cursor2D.getY() == lines.length - 1)
            return;
        cursor2D.incrementY();
        int lengthOfNextLine = lines[cursor2D.getY()].length();
        cursor2D.setX(Math.min(lengthOfNextLine, cursor2D.getX()));
        insertionIndex = translate2DCursorTo1D(cursor2D);
    }

    public Point translate1DCursorTo2D(int number){
        if(number < 0 || number > builder.length())
            throw new IllegalArgumentException("The integer falls outside the text.");
        String[] lines = builder.toString().split("\n");
        int count = 0;
        int row = -1;
        for(int i = 0; i < lines.length ; i++){
            if(count + lines[i].length() + 1 > number) {
                row = i;
                break;
            }
            count += lines[i].length() + 1;
        }
        int col = number - count;
        return Point.create(row, col);
    }

    public int translate2DCursorTo1D(Point point){
        Objects.requireNonNull(point, "Point is null.");
        String[] lines = builder.toString().split("\n");
        if(point.getX() >= lines.length)
            throw new IllegalArgumentException("Y value of the Point is outside valid values.");
        if(point.getY() > lines[point.getX()].length())
            throw new IllegalArgumentException("X Value of the Point is outside valid values.");
        int count = 0;
        for(int i = 0; i < point.getX(); i++){
            count += lines[i].length() + 1;
        }
        count += point.getY();
        return count;
    }

}
