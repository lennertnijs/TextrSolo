package com.textr.view;

import com.textr.filebuffer.*;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.terminal.Communicator;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;
import com.textr.util.Validator;

import java.util.Objects;

/**
 * Class to represent a view.
 */
public final class BufferView extends View {

    private final FileBuffer buffer;
    private final ICursor cursor;
    private final Point anchor;

    private final Communicator communicator;

    private BufferView(FileBuffer buffer,
                       ICursor cursor,
                       Point position,
                       Dimension2D dimensions,
                       Point anchor,
                       Communicator communicator){
        super(position, dimensions);
        this.buffer = buffer;
        this.cursor = cursor;
        this.anchor = anchor;
        this.communicator = Objects.requireNonNull(communicator, "BufferView's communicator cannot be null");
    }

    public static BufferView createFromFilePath(String url,
                                                Point position,
                                                Dimension2D dimensions,
                                                Communicator communicator){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
        b.incrementReferenceCount();
        return new BufferView(b,
                Cursor.createNew(),
                position.copy(),
                dimensions.copy(),
                Point.create(0,0),
                communicator);
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
     * Moves the cursor of the active buffer by 1 unit in the given direction.
     * Then calls for an update of the anchor.
     * @param direction The direction. Cannot be null.
     *
     * @throws IllegalArgumentException If the given direction is null.
     */
    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the cursor in the null direction.");
        cursor.move(direction, buffer.getText().getSkeleton());
        updateAnchor();
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
                buffer.getText().getLineAmount(),
                buffer.getText().getCharAmount(),
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
            case CHARACTER -> {
                Action insertAction = new InsertAction(cursor.getInsertIndex(), input.getCharacter(), buffer.getText());
                buffer.executeAndStore(insertAction, cursor);
            }
            case ENTER -> {
                Action insertAction = new InsertAction(cursor.getInsertIndex(), '\n', buffer.getText());
                buffer.executeAndStore(insertAction, cursor);
            }
            case ARROW_UP -> moveCursor(Direction.UP);
            case ARROW_RIGHT -> moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> moveCursor(Direction.DOWN);
            case ARROW_LEFT -> moveCursor(Direction.LEFT);
            case DELETE -> {
                if(cursor.getInsertIndex() == buffer.getText().getCharAmount())
                    return;
                Action deleteAction = new DeleteAction(cursor.getInsertIndex(),
                        buffer.getText().getCharacter(cursor.getInsertIndex()),
                        buffer.getText(), Side.AFTER);
                buffer.executeAndStore(deleteAction, cursor);
            }
            case BACKSPACE -> {
                if(cursor.getInsertIndex() == 0)
                    return;
                Action deleteAction = new DeleteAction(cursor.getInsertIndex() - 1,
                        buffer.getText().getCharacter(cursor.getInsertIndex() - 1),
                        buffer.getText(), Side.BEFORE);
                buffer.executeAndStore(deleteAction, cursor);
            }
            case CTRL_U -> buffer.redo(cursor);
            case CTRL_Z -> buffer.undo(cursor);
        }
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
        return new BufferView(this.buffer.copy(),
                this.cursor,
                getPosition().copy(),
                getDimensions().copy(),
                this.anchor.copy(),
                this.communicator);
    }

    @Override
    public boolean markForDeletion() {
        if (getBuffer().getState() == BufferState.CLEAN || getBuffer().getReferenceCount() > 1) {
            getBuffer().decrementReferenceCount();
            return true;
        }
        if (communicator.requestPermissions(
                "You have unsaved changes. Are you sure you want to close this FileBuffer?")) {
            getBuffer().decrementReferenceCount();
            return true;
        }
        return false;
    }
}
