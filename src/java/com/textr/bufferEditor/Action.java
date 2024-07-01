package com.textr.bufferEditor;

public interface Action {

    /**
     * Executes the {@link Action}. Updates the buffer's state appropriately.
     *
     * @return The new insert index, as a result of executing the action.
     */
    int execute();

    /**
     * Undoes the {@link Action}. Updates the buffer's state appropriately.
     *
     * @return The new insert index, as a result of undo-ing the action.
     */
    int undo();
}
