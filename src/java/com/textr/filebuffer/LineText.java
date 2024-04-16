package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class LineText implements IText{

    private final StringBuilder builder;

    private LineText(StringBuilder builder){
        this.builder = builder;
    }

    public static LineText createFromString(String string){
        Objects.requireNonNull(string, "String is null.");
        String replacedLineBreaks = string.replace("\r\n", "\n").replace("\r", "\n");
        return new LineText(new StringBuilder(replacedLineBreaks));
    }

    public String[] getLines(){
        return builder.toString().split(String.valueOf('\n'));
    }

    public String getContent(){
        return builder.toString();
    }

    public int getAmountOfLines(){
        return builder.toString().split(String.valueOf('\n')).length;
    }

    public int getAmountOfChars(){
        return builder.toString().length();
    }

    public void insertCharacter(char character, ICursor cursor){
        builder.insert(cursor.getInsertIndex(), character);
        cursor.moveRight(getSkeleton());
    }

    public void removeCharacterBefore(ICursor cursor){
        if(cursor.getInsertIndex() == 0)
            return;
        builder.deleteCharAt(cursor.getInsertIndex() - 1);
        cursor.moveLeft(getSkeleton());
    }

    public void removeCharacterAfter(ICursor cursor){
        if(cursor.getInsertIndex() == builder.length())
            return;
        builder.deleteCharAt(cursor.getInsertIndex());
    }

    public void insertNewLine(ICursor cursor){
        builder.insert(cursor.getInsertIndex(), "\n");
    }

    public void moveCursor(Direction direction, ICursor cursor){
        Objects.requireNonNull(direction, "Direction is null.");
        switch(direction){
            case UP -> moveUp(cursor);
            case RIGHT -> moveRight(cursor);
            case DOWN -> moveDown(cursor);
            case LEFT -> moveLeft(cursor);
        }
    }

    private void moveRight(ICursor cursor){
        int incrementedCursorIndex = cursor.getInsertIndex() + 1;
        if(incrementedCursorIndex <= builder.length())
            cursor.setInsertIndex(incrementedCursorIndex, getSkeleton());
    }

    private void moveLeft(ICursor cursor){
        if(cursor.getInsertIndex() - 1 >= 0)
            cursor.setInsertIndex(cursor.getInsertIndex() - 1, getSkeleton());
    }

    private void moveUp(ICursor cursor){
        Point cursor2D = cursor.getInsertPoint();
        if(cursor2D.getY() == 0)
            return;
        String[] lines = builder.toString().split("\n");
        cursor2D.decrementY();
        int lengthOfPrevLine = lines[cursor2D.getY()].length();
        cursor2D.setX(Math.min(lengthOfPrevLine, cursor2D.getX()));
        cursor.setInsertPoint(cursor2D, getSkeleton());
    }

    private void moveDown(ICursor cursor){
        String[] lines = builder.toString().split("\n");
        Point cursor2D = cursor.getInsertPoint();
        if(cursor2D.getY() == lines.length - 1)
            return;
        cursor2D.incrementY();
        int lengthOfNextLine = lines[cursor2D.getY()].length();
        cursor2D.setX(Math.min(lengthOfNextLine, cursor2D.getX()));
        cursor.setInsertPoint(cursor2D, getSkeleton());
    }

    public List<Integer> getLineLengths(){
        String[] lines = builder.toString().split("\n");
        List<Integer> lengths = new ArrayList<>();
        for (String line : lines) {
            lengths.add(line.length());
        }
        return lengths;
    }

    public ITextSkeleton getSkeleton(){
        List<Integer> lineLengths = new ArrayList<>();
        String[] lines = builder.toString().split("\n");
        for(String line : lines){
            lineLengths.add(line.length() + 1);
        }
        return TextSkeleton.create(lineLengths);
    }
}
