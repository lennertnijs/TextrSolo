package com.textr.filebuffer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public final class History {

    private final Deque<Action> undoAbles;
    private final Deque<Action> redoAbles;

    public History(){
        this.undoAbles = new ArrayDeque<>();
        this.redoAbles = new ArrayDeque<>();
    }

    /**
     * Executes the {@link Action} and stores it for possible undo.
     * @param action The action. Cannot be null.
     */
    public int executeAndAddAction(Action action){
        Objects.requireNonNull(action, "Action is null.");
        undoAbles.add(action);
        redoAbles.clear();
        return action.execute();
    }

    /**
     * Undoes the last performed {@link Action}.
     */
    public void undo(){
        if(undoAbles.size() == 0)
            return;
        Action action = undoAbles.removeLast();
        redoAbles.addLast(action);
        action.undo();
    }

    /**
     * Redoes the last {@link Action} that was undone.
     */
    public void redo(){
        if(redoAbles.size() == 0)
            return;
        Action action = redoAbles.removeLast();
        action.execute();
        undoAbles.addLast(action);
    }
}