package com.textr.filebuffer;

public interface Action {

    void execute(ICursor cursor);
    void undo(ICursor cursor);
}
