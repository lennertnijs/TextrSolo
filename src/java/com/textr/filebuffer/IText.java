package com.textr.filebuffer;

import com.textr.util.Direction;

public interface IText {

    String[] getLines();
    int getAmountOfLines();
    int getAmountOfChars();
    void insertCharacter(char character);
    void removeCharacterBefore();
    void removeCharacterAfter();
    void insertNewLine();
    void moveCursor(Direction direction);
}
