package com.textr.filebuffer;

public interface Action {

    /**
     * Executes the {@link Action}.
     */
    int execute();

    /**
     * Undoes the {@link Action}.
     */
    void undo();
}
