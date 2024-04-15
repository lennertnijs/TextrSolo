package com.textr.filebuffer;

import com.textr.util.Point;

import java.util.ArrayList;
import java.util.List;

public final class ChangeHistory {

    private int undoIndex;
    private final List<Action> actionHistory;

    public ChangeHistory(){
        this.undoIndex = -1;
        this.actionHistory = new ArrayList<>();
    }

    public void addInsertAction(char character, int row, int col){
        actionHistory.add(new InsertAction(character, row, col));
        undoIndex = actionHistory.size() - 1;
    }

    public void addDeleteAction(char character, int row, int col){
        actionHistory.add(new DeleteAction(character, row, col));
        undoIndex = actionHistory.size() - 1;
    }

    public void undo(Text text, Point cursor){
        if(undoIndex == -1)
            return;
        Action action = actionHistory.get(undoIndex);
        cursor.setX(action.getPosition().getX());
        cursor.setY(action.getPosition().getY());
        action.undo(text);
        undoIndex--;
    }

    public void redo(Text text, Point cursor){
        int redoIndex = undoIndex + 1;
        if(redoIndex == actionHistory.size())
            return;
        Action action = actionHistory.get(redoIndex);
        cursor.setX(action.getPosition().getX() + 1);
        cursor.setY(action.getPosition().getY());
        action.redo(text);
        undoIndex++;
    }
}