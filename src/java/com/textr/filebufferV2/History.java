package com.textr.filebufferV2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Represents a history of {@link Action}s.
 */
public final class History {

    /**
     * The double-ended queue of undo-able actions.
     */
    private final Deque<Action> undoAbles;
    /**
     * The double-ended queue of redo-able actions.
     */
    private final Deque<Action> redoAbles;

    /**
     * Creates a new MUTABLE {@link History}.
     */
    public History(){
        this.undoAbles = new ArrayDeque<>();
        this.redoAbles = new ArrayDeque<>();
    }

    /**
     * Executes and stores the given {@link Action}. Returns the updated index.
     * Whenever a new action is executed and stored, the redo-queue will be wiped.
     *
     * @return The updated index.
     */
    public int executeAndAddAction(Action action){
        undoAbles.add(Objects.requireNonNull(action, "Action is null."));
        redoAbles.clear();
        return action.execute();
    }

    /**
     * Undoes the last performed {@link Action}.
     */
    public int undo(int currentIndex){
        if(undoAbles.size() == 0)
            return currentIndex;
        Action action = undoAbles.removeLast();
        redoAbles.addLast(action);
        return action.undo();
    }

    /**
     * Redoes the last {@link Action} that was undone.
     */
    public int redo(int currentIndex){
        if(redoAbles.size() == 0)
            return currentIndex;
        Action action = redoAbles.removeLast();
        undoAbles.addLast(action);
        return action.execute();
    }
}