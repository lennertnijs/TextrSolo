package com.textr.filebuffer;

import com.textr.util.Direction;

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
        cursor.move(Direction.RIGHT, getSkeleton());
    }

    public void removeCharacterBefore(ICursor cursor){
        if(cursor.getInsertIndex() == 0)
            return;
        builder.deleteCharAt(cursor.getInsertIndex() - 1);
        cursor.move(Direction.LEFT, getSkeleton());
    }

    public void removeCharacterAfter(ICursor cursor){
        if(cursor.getInsertIndex() == builder.length())
            return;
        builder.deleteCharAt(cursor.getInsertIndex());
    }

    public void insertNewLine(ICursor cursor){
        builder.insert(cursor.getInsertIndex(), "\n");
    }


    public ITextSkeleton getSkeleton(){
        List<Integer> lineLengths = new ArrayList<>();
        String[] lines = builder.toString().split("\n");
        for(String line : lines){
            lineLengths.add(line.length() + 1);
        }
        return new TextSkeleton(lineLengths);
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof LineText text))
            return false;
        return builder.toString().contentEquals(text.builder);
    }

    @Override
    public int hashCode(){
        return builder.toString().hashCode();
    }

    @Override
    public String toString(){
        return String.format("LineText[Text=%s]", builder.toString());
    }
}
