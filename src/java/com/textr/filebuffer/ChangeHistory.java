package com.textr.filebuffer;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ChangeHistory {

    private final Deque<Action> undoAbles;
    private final Deque<Action> redoAbles;

    public ChangeHistory(){
        this.undoAbles = new ArrayDeque<>();
        this.redoAbles = new ArrayDeque<>();
    }

    public void executeAndAddAction(Action action, ICursor cursor){
        action.execute(cursor);
        undoAbles.add(action);
    }

    public void undo(ICursor cursor){
        if(undoAbles.size() == 0)
            return;
        Action action = undoAbles.removeLast();
        action.undo(cursor);
        redoAbles.addLast(action);
    }

    public void redo(ICursor cursor){
        if(redoAbles.size() == 0)
            return;
        Action action = redoAbles.removeLast();
        action.execute(cursor);
        undoAbles.addLast(action);
    }
}