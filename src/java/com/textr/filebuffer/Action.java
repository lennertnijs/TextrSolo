package com.textr.filebuffer;

public interface Action {

    void undo(String[] text);
    void redo(String[] text);
}
