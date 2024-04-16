package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private final int index;

    public DeleteAction(int index, char character){
        this.character = character;
        this.index = index;
    }

    public int getInsertIndex(){
        return index;
    }

    public void undo(IText text, ICursor cursor){
        cursor.setInsertIndex(index, text.getSkeleton());
        text.insert(index, character);
    }

    public void redo(IText text, ICursor cursor){
        cursor.setInsertIndex(index, text.getSkeleton());
        text.remove(index);
    }
}