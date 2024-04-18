package com.textr.view;

import com.textr.filebuffer.*;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.terminal.Communicator;
import com.textr.util.*;

import java.util.Objects;

/**
 * Class to represent a view on a {@link FileBuffer}, i.e. a small window onto its contents. This class holds the buffer
 * in question, as well as a {@link ICursor} to specify a location to edit said buffer. It also holds a {@link Point} to
 * represent the anchor of the view, i.e. the top left location of the rectangular section of test that is visible of
 * the file buffer within the view.
 */
public final class BufferView extends View implements TextListener {

    private final FileBuffer buffer;
    private final ICursor cursor;
    private final Point anchor;

    /**
     * The current update state of the view, responsible for adjusting the cursor and anchor point when an edit is
     * observed on the contents of the view.
     */
    private UpdateState updater;

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
        this.updater = new GenericState();
        buffer.addTextListener(this);
    }

    public static BufferView createFromFilePath(String url,
                                                Point position,
                                                Dimension2D dimensions,
                                                Communicator communicator){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
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
     * Returns the current update state of this view.
     * @return The current UpdateState of this view
     */
    public UpdateState getUpdateState() {
        return this.updater;
    }

    /**
     * Sets this view's update state to the given UpdateState, if it is not null.
     * @param newState The new UpdateState of this view
     * @throws NullPointerException if given state is null
     */
    public void setUpdateState(UpdateState newState) {
        if (newState == null)
            throw new NullPointerException("New update state cannot be null");
        this.updater = newState;
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
                buffer.executeAndStore(insertAction);
            }
            case ENTER -> {
                Action insertAction = new InsertAction(cursor.getInsertIndex(), '\n', buffer.getText());
                buffer.executeAndStore(insertAction);
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
                        buffer.getText());
                buffer.executeAndStore(deleteAction);
            }
            case BACKSPACE -> {
                if(cursor.getInsertIndex() == 0)
                    return;
                Action deleteAction = new DeleteAction(cursor.getInsertIndex() - 1,
                        buffer.getText().getCharacter(cursor.getInsertIndex() - 1),
                        buffer.getText());
                buffer.executeAndStore(deleteAction);
            }
            case CTRL_U -> {
                setUpdateState(new JumpToEditState());
                buffer.redo();
            }
            case CTRL_Z -> {
                setUpdateState(new JumpToEditState());
                buffer.undo();
            }
        }
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

    /**
     * Duplicates this BufferView by creating a new BufferView with same buffer and communicator instances.
     * The cursor location of the duplicated view will be set to the location of the original.
     *
     * @return A duplicate version of this BufferView, with same buffer and communicator instances
     */
    @Override
    public BufferView duplicate(){
        ICursor newCursor = Cursor.createNew(); // TODO: Make clone method for cursor
        newCursor.setInsertIndex(cursor.getInsertIndex(), buffer.getText().getSkeleton());
        return new BufferView(this.buffer,
                newCursor,
                getPosition().copy(),
                getDimensions().copy(),
                this.anchor.copy(),
                this.communicator);
    }

    @Override
    public void update(TextUpdateReference update, ITextSkeleton structure) {
        updater.update(this, update, structure);
    }

    @Override
    public boolean markForDeletion() {
        if (getBuffer().getState() == BufferState.CLEAN || getBuffer().getReferenceCount() > 1) {
            getBuffer().removeTextListener(this);
            return true;
        }
        if (communicator.requestPermissions(
                "You have unsaved changes. Are you sure you want to close this FileBuffer?")) {
            getBuffer().removeTextListener(this);
            return true;
        }
        return false;
    }
}
