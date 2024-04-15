package com.textr.view;

import com.textr.filebuffer.Cursor;
import com.textr.filebuffer.FileBuffer;
import com.textr.filebuffer.ICursor;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;
import com.textr.util.Validator;

/**
 * Class to represent a view.
 */
public final class BufferView extends View {

    private final FileBuffer buffer;
    private final ICursor cursor;
    private final Point anchor;


    private BufferView(FileBuffer buffer, ICursor cursor, Point position, Dimension2D dimensions, Point anchor){
        super(position, dimensions);
        this.buffer = buffer;
        this.cursor = cursor;
        this.anchor = anchor;

    }

    public static BufferView createFromFilePath(String url, Point position, Dimension2D dimensions){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
        return new BufferView(b, Cursor.createNew(), position.copy(), dimensions.copy(), Point.create(0,0));
    }

    public FileBuffer getBuffer(){
        return this.buffer;
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor;
    }

    public ICursor getCursor(){
        return cursor;
    }

    /**
     * Resizes this BufferView
     * @param dimensions = the new dimensions of the view
     */
    public void resize(Dimension2D dimensions){
        setDimensions(dimensions);
    }

    /**
     * Compares this view to the given object and returns True if they're equal. Returns False otherwise.
     * @param o The other object
     *
     * @return True if they're equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof BufferView view)){
            return false;
        }
        return this.buffer.equals(view.buffer) &&
                this.getDimensions().equals(view.getDimensions()) &&
                this.getPosition().equals(view.getPosition()) &&
                this.anchor.equals(view.anchor);
    }

    /**
     * Generates and returns a hash code for this view.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        int result = buffer.hashCode();
        result = result * 31 + getDimensions().hashCode();
        result = result * 31 + getPosition().hashCode();
        result = result * 31 + anchor.hashCode();
        return result;
    }


    /**
     * Creates and returns a {@link String} representation of this view.
     *
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("BufferView[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                buffer, getPosition(), getDimensions(), anchor);
    }

    public BufferView copy(){
        return new BufferView(this.buffer.copy(), this.cursor, getPosition().copy(), getDimensions().copy(), this.anchor.copy());
    }

    /**
     * Moves the cursor of the active buffer by 1 unit in the given direction.
     * Then calls for an update of the anchor.
     * @param direction The direction. Cannot be null.
     *
     * @throws IllegalArgumentException If the given direction is null.
     */
    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the cursor in the null direction.");
        this.buffer.moveCursor(direction, cursor);
        updateAnchor();
    }

    /**
     * Creates a new line (\r\n on Windows) at the cursor in the active buffer.
     * Then calls for an update of the anchor.
     */
    public void insertNewLine(){
        buffer.createNewLine(cursor);
        updateAnchor();
    }

    /**
     * Inserts the given character at the cursor of the active buffer.
     * @param character The input character
     */
    public void insertCharacter(char character){
        buffer.insertCharacter(character, cursor);
        updateAnchor();
    }

    /**
     * Deletes the character just before the cursor of the active buffer.
     * Then calls for an update of the anchor.
     */
    public void deletePreviousCharacter(){
        buffer.removeCharacterBefore(cursor);
        updateAnchor();
    }

    /**
     * Deletes the character just after the cursor of the active buffer.
     */
    public void deleteNextCharacter(){
        buffer.removeCharacterAfter(cursor);
    }

    /**
     * Updates the anchor point of the active buffer to adjust it to possible changes to the cursor point.
     */
    private void updateAnchor(){
        AnchorUpdater.updateAnchor(getAnchor(), cursor.getInsertPoint(), getDimensions());
    }

    /**
     * Generates and returns a status bar for the given FileBuffer.
     *
     * @return The status bar.
     * @throws IllegalArgumentException If the given buffer is null.
     */
    @Override
    public String generateStatusBar(){
        return String.format("File path: %s - Lines: %d - Characters: %d - Cursor: (line, col) = (%d, %d) - State: %s",
                buffer.getFile().getPath(),
                buffer.getText().getAmountOfLines(),
                buffer.getText().getAmountOfChars(),
                cursor.getInsertPoint().getY(),
                cursor.getInsertPoint().getX(),
                buffer.getState());
    }
    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case CHARACTER -> insertCharacter(input.getCharacter());
            case ENTER -> insertNewLine();
            case ARROW_UP -> moveCursor(Direction.UP);
            case ARROW_RIGHT -> moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> moveCursor(Direction.DOWN);
            case ARROW_LEFT -> moveCursor(Direction.LEFT);
            case DELETE -> deleteNextCharacter();
            case BACKSPACE -> deletePreviousCharacter();
        }
    }
}
