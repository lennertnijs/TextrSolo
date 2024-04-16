package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private final int insertIndex;

    public DeleteAction(char character, int insertIndex){
        this.character = character;
        this.insertIndex = insertIndex;
    }

    public int getInsertIndex(){
        return insertIndex;
    }

    public void undo(IText text, ICursor cursor){
        cursor.setInsertIndex(insertIndex, text.getSkeleton());
        text.insertCharacter(character, cursor);
    }

    public void redo(IText text, ICursor cursor){
        cursor.setInsertIndex(insertIndex, text.getSkeleton());
        text.removeCharacterBefore(cursor);
    }
}