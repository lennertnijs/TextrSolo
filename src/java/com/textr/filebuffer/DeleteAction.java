package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;

    public DeleteAction(int index, char character){
        this.character = character;
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    public void undo(IText text, ICursor cursor){
        text.insert(index, character);
        cursor.setInsertIndex(index + 1, text.getSkeleton());
    }

    public void redo(IText text, ICursor cursor){
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }
}