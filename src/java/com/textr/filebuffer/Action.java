package com.textr.filebuffer;

public interface Action {

    int getIndex();
    void undo(IText text, ICursor cursor);
    void redo(IText text, ICursor cursor);
}
