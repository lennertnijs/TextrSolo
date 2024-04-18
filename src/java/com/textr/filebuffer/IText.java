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
    void addListener(TextListener newListener);
    void removeListener(TextListener oldListener);
    int getListenerCount();

}
