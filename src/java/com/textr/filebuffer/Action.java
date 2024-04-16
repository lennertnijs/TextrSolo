package com.textr.filebuffer;

public interface Action {

    /**
     * Executes the {@link Action}.
     * @param cursor The active {@link ICursor}. Cannot be null.
     */
    void execute(ICursor cursor);

    /**
     * Undoes the {@link Action}.
     * @param cursor The active {@link ICursor}. Cannot be null.
     */
    void undo(ICursor cursor);
}
