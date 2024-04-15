package com.textr.filebuffer;

import com.textr.util.Point;

public final class DeleteAction implements Action {

    private final char character;
    private int row;
    private int col;

    public DeleteAction(char character, int row, int col){
        this.character = character;
        this.row = row;
        this.col = col;
    }

    public Point getPosition(){
        return Point.create(col, row);
    }

    public void undo(Text text){
        text.insertCharacter(character, row, col);

    }

    public void redo(Text text){
        text.removeCharacter(row, col);
    }
}