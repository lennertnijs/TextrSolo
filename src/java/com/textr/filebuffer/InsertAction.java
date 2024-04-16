package com.textr.filebuffer;

public final class InsertAction implements Action{

    private final char character;
    private final int insertIndex;
    public InsertAction(char character, int insertIndex){
        this.character = character;
        this.insertIndex = insertIndex;
    }

    public int getInsertIndex(){
        return insertIndex;
    }

    public void undo(IText text, ICursor cursor){
        cursor.setInsertIndex(insertIndex, text.getSkeleton());
        text.remove(insertIndex);
    }

    public void redo(IText text, ICursor cursor){
        cursor.setInsertIndex(insertIndex, text.getSkeleton());
        text.insert(insertIndex, character);
    }
}
