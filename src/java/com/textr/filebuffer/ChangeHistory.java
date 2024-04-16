package com.textr.filebuffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public final class ChangeHistory {

    private final Deque<Action> undoAbles;
    private final Deque<Action> redoAbles;

    public ChangeHistory(){
        this.undoAbles = new ArrayDeque<>();
        this.redoAbles = new ArrayDeque<>();
    }

    /**
     * Executes the {@link Action} and stores it for possible undo.
     * @param action The action. Cannot be null.
     * @param cursor The cursor. Cannot be null.
     */
    public void executeAndAddAction(Action action, ICursor cursor){
        Objects.requireNonNull(action, "Action is null.");
        Objects.requireNonNull(cursor, "Cursor is null.");
        action.execute(cursor);
        undoAbles.add(action);
    }

    /**
     * Undoes the last performed {@link Action}.
     * @param cursor The cursor. Cannot be null.
     */
    public void undo(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        if(undoAbles.size() == 0)
            return;
        Action action = undoAbles.removeLast();
        action.undo(cursor);
        redoAbles.addLast(action);
    }

    /**
     * Redoes the last {@link Action} that was undone.
     * @param cursor The cursor. Cannot be null.
     */
    public void redo(ICursor cursor){
        Objects.requireNonNull(cursor, "Cursor is null.");
        if(redoAbles.size() == 0)
            return;
        Action action = redoAbles.removeLast();
        action.execute(cursor);
        undoAbles.addLast(action);
    }
}