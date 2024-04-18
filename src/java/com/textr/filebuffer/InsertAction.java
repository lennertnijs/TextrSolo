package com.textr.filebuffer;

public final class InsertAction implements Action{

    private final char character;
    private final int index;
    private final IText text;

    /**
     * Creates a new {@link InsertAction}.
     * Stores the 1D index and the inserted character.
     * @param index The index.
     * @param character The character.
     */
    public InsertAction(int index, char character, IText text){
        this.index = index;
        this.character = character;
        this.text = text;
    }

    /**
     * Executes this {@link InsertAction}.
     */
    public void execute(){
        text.insert(index, character);
    }

    /**
     * Undoes this {@link InsertAction}.
     */
    public void undo(){
        text.remove(index);
    }
}
