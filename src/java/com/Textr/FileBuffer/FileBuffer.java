package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.Point.Point;
import com.Textr.Validator.Validator;

import java.util.Objects;

public final class FileBuffer {

    private final int id;
    private final int fileId;
    private final Text text;
    private final Point insertionPoint;
    private BufferState state;

    /**
     * Constructor for a {@link FileBuffer}.
     * A {@link FileBuffer} is used to store changes to the {@link File}'s text until the changes are saved.
     * Uses a static {@link FileBuffer.Builder} to ensure creation of valid {@link FileBuffer}'s.
     * @param builder The {@link FileBuffer.Builder}. Cannot be null.
     */
    private FileBuffer(Builder builder){
        Objects.requireNonNull(builder, "Cannot build a FileBuffer because the Builder is null.");
        this.id = FileBufferIdGenerator.getId();
        this.fileId = builder.fileId;
        this.text = builder.text;
        this.insertionPoint = builder.insertionPoint;
        this.state = builder.state;
    }


    public int getId(){
        return this.id;
    }
    /**
     * Returns the id of the active {@link File} of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s active {@link File} id.
     */
    public int getFileId(){
        return this.fileId;
    }

    public Text getText(){
        return this.text;
    }

    /**
     * Returns the insertion point index of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s insertion point index.
     */
    public Point getInsertionPosition(){
        return this.insertionPoint;
    }

    public void moveInsertionPointDown(){
        boolean canMoveDown = insertionPoint.getY() + 1 < text.getAmountOfLines();
        if(canMoveDown){
            insertionPoint.incrementY();
            boolean validX =  insertionPoint.getX() < text.getLineLength(insertionPoint.getY());
            if(!validX){
                insertionPoint.setX(text.getLineLength(insertionPoint.getY()));
            }
        }
    }

    public void moveInsertionPointUp(){
        insertionPoint.decrementY();
        boolean validX = insertionPoint.getX() < text.getLine(insertionPoint.getY()).length();
        if(!validX){
            insertionPoint.setX(text.getLine(insertionPoint.getY()).length());
        }
    }

    public void moveInsertionPointLeft(){
        insertionPoint.decrementX();
    }

    public void moveInsertionPointRight(){
        boolean canMoveRight = insertionPoint.getX() < text.getLines()[insertionPoint.getY()].length();
        if(canMoveRight){
            insertionPoint.incrementX();
        }
    }

    /**
     * Returns the {@link BufferState} of this {@link FileBuffer}.
     * @return This {@link FileBuffer}'s state as a {@link BufferState}
     */
    public BufferState getState(){
        return this.state;
    }

    public void setDirty(){
        state = BufferState.DIRTY;
    }

    public void setClean(){
        state = BufferState.CLEAN;
    }

    /**
     * Inserts the given character into this {@link FileBuffer}'s buffer {@link Text} at the insertion {@link Point}.
     * @param character The character
     */
    public void insertCharacter(char character){
        text.insertCharacter(character, insertionPoint.getY(), insertionPoint.getX());
        insertionPoint.incrementX();
    }

    /**
     * Removes the character before the insertion {@link Point} from this {@link FileBuffer}'s buffer {@link Text}.
     * Used when backspace is pressed.
     */
    public void removeCharacter(){
        int lineAboveLength = text.getLineLength(Math.max(0, insertionPoint.getY() - 1));
        int oldAmountOfLines = text.getAmountOfLines();
        text.removeCharacter(insertionPoint.getY(), insertionPoint.getX());
        int newAmountOfLines = text.getAmountOfLines();
        boolean deletedALine = newAmountOfLines < oldAmountOfLines;
        if(deletedALine){
            insertionPoint.decrementY();
            insertionPoint.setX(lineAboveLength);
        }else{
            insertionPoint.decrementX();
        }
    }

    /**
     * Splits the text up into a new line at the insertion {@link Point}.
     * Used when Enter is pressed.
     * Also moves the insertion {@link Point} to the appropriate location.
     */
    public void createNewLine(){
        text.splitLineAtColumn(insertionPoint.getY(), insertionPoint.getX());
        insertionPoint.setX(0);
        insertionPoint.incrementY();
    }


    /**
     * Compares this {@link FileBuffer} to the given {@link Objects} and returns True if they're equal.
     * More precisely, returns true if all of their fields are equal.
     * @param o The other {@link Object}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof FileBuffer fileBuffer)){
            return false;
        }
        return this.id == fileBuffer.id;
    }

    /**
     * Creates and returns a hash code for this {@link FileBuffer}.
     *
     * @return The hash code
     */
    @Override
    public int hashCode(){
        return this.id;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link FileBuffer}.
     *
     * @return The {@link String} representation
     */
    @Override
    public String toString(){
        return String.format("FileBuffer[id = %d, activeFileId = %d, bufferText = %s, insertionPosition = %s, state = %s]",
                id, fileId, text, insertionPoint, state);
    }

    /**
     * Creates and returns a new {@link FileBuffer.Builder} to build a {@link FileBuffer} with.
     * @return The {@link FileBuffer.Builder}
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * A subclass {@link FileBuffer.Builder} used to build valid {@link FileBuffer} instances with.
     * To obtain a {@link FileBuffer.Builder}, use FileBuffer.builder();
     */
    public static class Builder{

        private int fileId = -1;
        private Text text = null;
        private Point insertionPoint = null;
        private BufferState state = null;

        /**
         * Constructor for the {@link FileBuffer.Builder}
         */
        private Builder(){
        }

        /**
         * Sets the active file id of this {@link FileBuffer.Builder} to the given id.
         * @param id The id
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        /**
         * Sets the buffer text of this {@link FileBuffer.Builder} to the given text.
         * @param text The text
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder text(Text text){
            this.text = text;
            return this;
        }

        /**
         * Sets the insertion point index of this {@link FileBuffer.Builder} to the given index.
         * @param insertionPoint The position
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder insertionPosition(Point insertionPoint){
            this.insertionPoint = insertionPoint;
            return this;
        }

        /**
         * Sets the {@link BufferState} of this {@link FileBuffer.Builder} to the given state.
         * @param state The state
         *
         * @return This {@link FileBuffer.Builder}
         */
        public Builder state(BufferState state){
            this.state = state;
            return this;
        }


        /**
         * Validates all the fields of this {@link FileBuffer.Builder}.
         * If all are valid, creates and returns a new immutable {@link FileBuffer} with these fields.
         * More precisely, the following conditions must hold on the fields:
         * - The active file id cannot be negative.
         * - The buffer text cannot be null.
         * - The insertion point index must be within: 0 <= insertionIndex <= bufferText.length().
         * - The state cannot be null.
         * @throws  IllegalArgumentException If any of the fields are invalid.
         *
         * @return a newly created valid & immutable {@link FileBuffer}
         */
        public FileBuffer build(){
            Validator.notNegative(fileId, "The id the File in the FileBuffer cannot be negative.");
            Validator.notNull(text, "The buffer text in the FileBuffer cannot be null.");
            Validator.notNull(insertionPoint, "The insertion point of the FileBuffer cannot be null.");
            Validator.notNull(state,"The state of the FileBuffer cannot be null.");
            return new FileBuffer(this);
        }
    }
}
