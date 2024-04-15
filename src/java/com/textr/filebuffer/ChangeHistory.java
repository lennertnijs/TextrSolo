package com.textr.filebuffer;

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

    public void undo(String[] text){
        if(undoIndex == -1)
            return;
        actionHistory.get(undoIndex).undo(text);
        undoIndex--;
    }

    public void redo(String[] text){
        int redoIndex = undoIndex + 1;
        if(redoIndex == actionHistory.size())
            return;
        actionHistory.get(redoIndex).redo(text);
        undoIndex++;
    }
}