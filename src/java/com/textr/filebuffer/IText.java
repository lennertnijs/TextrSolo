package com.textr.filebuffer;

public interface IText {

    String[] getLines();
    String getContent();
    int getLineAmount();
    int getCharAmount();
    char getCharacter(int index);
    ITextSkeleton getSkeleton();
    void insert(int index, char character);
    void remove(int index);
    void insertLineBreak(int index);
    boolean addListener(TextListener newListener);
    boolean removeListener(TextListener oldListener);

}
