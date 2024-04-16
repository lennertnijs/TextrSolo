package com.textr.filebuffer;

public interface IText {

    String[] getLines();
    String getContent();
    int getLineAmount();
    int getCharAmount();
    char getCharacter(int index);
    void insert(int index, char character);
    void remove(int index);
    void insertLineBreak(int index);
    ITextSkeleton getSkeleton();
}
