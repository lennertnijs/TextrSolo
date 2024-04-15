package com.textr.filebuffer;

public final class DeleteAction implements Action {

    private final char character;
    private int row;
    private int col;

    public DeleteAction(char character, int row, int col){
        this.character = character;
        this.row = row;
        this.col = col;
    }

    public void undo(String[] text){
        StringBuilder builder = new StringBuilder(text[row]);
        builder.insert(character, col);
        text[row] = builder.toString();

    }

    public void redo(String[] text){
        StringBuilder builder = new StringBuilder(text[row]);
        builder.deleteCharAt(col);
        text[row] = builder.toString();
    }
}