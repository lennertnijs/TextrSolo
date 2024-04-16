package com.textr.filebuffer;

import java.util.ArrayList;
import java.util.List;

public final class ChangeHistory {

    private final List<Action> undoAbles;
    private final List<Action> redoAbles;

    public ChangeHistory(){
        this.undoAbles = new ArrayList<>();
        this.redoAbles = new ArrayList<>();
    }

    public void addInsertAction(char character, ICursor cursor){
        Action insertAction = new InsertAction(character, cursor.getInsertIndex());
        undoAbles.add(insertAction);
    }

    public void addDeleteAction(char character, ICursor cursor){
        Action deleteAction = new DeleteAction(character, cursor.getInsertIndex());
        undoAbles.add(deleteAction);
    }

    public void undo(IText text, ICursor cursor){
        if(undoAbles.size() == 0)
            return;
        Action action = undoAbles.get(undoAbles.size() - 1);
        action.undo(text, cursor);
        undoAbles.remove(undoAbles.size() - 1);
        redoAbles.add(action);
    }

    public void redo(IText text, ICursor cursor){
        if(redoAbles.size() == 0)
            return;
        Action action = redoAbles.get(redoAbles.size() - 1);
        action.redo(text, cursor);
        redoAbles.remove(redoAbles.size() - 1);
        undoAbles.add(action);
    }
}