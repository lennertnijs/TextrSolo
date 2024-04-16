package com.textr.filebuffer;

import java.util.List;
import java.util.Objects;

public final class TextSkeleton implements ITextSkeleton{

    private final List<Integer> lineLengths;
    private final int characterCount;

    private TextSkeleton(List<Integer> lineLengths, int characterCount){
        this.lineLengths = lineLengths;
        this.characterCount = characterCount;
    }

    public static TextSkeleton create(List<Integer> lineLengths){
        Objects.requireNonNull(lineLengths, "Line lengths List is null.");
        if(lineLengths.contains(null))
            throw new NullPointerException("Line lengths List contains null.");
        for(Integer integer : lineLengths){
            if(integer < 0)
                throw new IllegalArgumentException("Length of a line is negative.");
        }
        int characterCount = 0;
        for(Integer integer : lineLengths)
            characterCount += integer;
        return new TextSkeleton(lineLengths, characterCount);
    }

    @Override
    public int getCharacterCount(){
        return characterCount;
    }

    @Override
    public int getLineLength(int index){
        if(index < 0 || index >= lineLengths.size())
            throw new IllegalArgumentException("Index outside amount of lines.");
        return lineLengths.get(index);
    }

    @Override
    public int getAmountOfLines(){
        return lineLengths.size();
    }
}
