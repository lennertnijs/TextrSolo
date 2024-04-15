package com.textr.filebuffer;

import com.textr.util.Direction;

public interface IText {

    String[] getLines();
    String getContent();
    int getAmountOfLines();
    int getAmountOfChars();
    void insertCharacter(char character, ICursor cursor);
    void removeCharacterBefore(ICursor cursor);
    void removeCharacterAfter(ICursor cursor);
    void insertNewLine(ICursor cursor);
    void moveCursor(Direction direction, ICursor cursor);
}
