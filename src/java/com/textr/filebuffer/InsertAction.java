package com.textr.filebuffer;

import com.textr.util.Point;

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
        text.removeCharacterBefore(cursor);
    }

    public void redo(IText text, ICursor cursor){
        cursor.setInsertIndex(insertIndex, text.getSkeleton());
        text.insertCharacter(character, cursor);
    }
}
