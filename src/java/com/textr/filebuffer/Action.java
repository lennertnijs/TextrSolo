package com.textr.filebuffer;

public interface Action {

    /**
     * Executes the {@link Action}.
     */
    void execute();

    /**
     * Undoes the {@link Action}.
     */
    void undo();
}
