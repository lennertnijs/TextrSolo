package com.textr.filebufferV2;

public interface Action {

    /**
     * Executes the {@link Action}.
     *
     * @return The new insert index, as a result of executing the action.
     */
    int execute();

    /**
     * Undoes the {@link Action}.
     *
     * @return The new insert index, as a result of undo-ing the action.
     */
    int undo();
}
