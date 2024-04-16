package com.textr.filebuffer;

public final class InsertAction implements Action{

    private final char character;
    private final int index;

    public InsertAction(int index, char character){
        this.index = index;
        this.character = character;
    }

    public int getIndex(){
        return index;
    }

    public void undo(IText text, ICursor cursor){
        text.remove(index);
        cursor.setInsertIndex(index, text.getSkeleton());
    }

    public void redo(IText text, ICursor cursor){
        text.insert(index, character);
        cursor.setInsertIndex(index, text.getSkeleton());
    }
}
