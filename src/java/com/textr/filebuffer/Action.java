package com.textr.filebuffer;

public interface Action {

    void execute(IText text, ICursor cursor);
    void undo(IText text, ICursor cursor);
}
