package com.textr.filebuffer;

import java.util.List;
import java.util.Objects;

public final class TextSkeleton implements ITextSkeleton{

    private final List<Integer> lineLengths;

    public TextSkeleton(List<Integer> lineLengths){
        validateList(lineLengths);
        this.lineLengths = lineLengths;
    }

    private void validateList(List<Integer> lineLengths){
        Objects.requireNonNull(lineLengths, "Line lengths List is null.");
        if(lineLengths.contains(null))
            throw new NullPointerException("Line lengths List contains null.");
        for(Integer integer : lineLengths){
            if(integer < 0)
                throw new IllegalArgumentException("Length of a line is negative.");
        }
    }

    @Override
    public int getCharacterCount(){
        int count = 0;
        for(Integer integer : lineLengths)
            count += integer;
        return count;
    }

    @Override
    public int getAmountOfLines(){
        return lineLengths.size();
    }

    @Override
    public int getLineLength(int index){
        if(index < 0 || index >= lineLengths.size())
            throw new IllegalArgumentException("Index outside amount of lines.");
        return lineLengths.get(index);
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof TextSkeleton skeleton))
            return false;
        return lineLengths.equals(skeleton.lineLengths);
    }

    @Override
    public int hashCode(){
        return lineLengths.hashCode();
    }

    @Override
    public String toString(){
        return String.format("TextSkeleton[LineLengths=%s]", lineLengths);
    }
}
