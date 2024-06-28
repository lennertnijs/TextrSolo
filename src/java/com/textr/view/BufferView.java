package com.textr.view;

import com.textr.filebuffer.*;
import com.textr.filebufferV2.IText;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.terminal.Communicator;
import com.textr.util.*;

import java.io.IOException;
import java.util.Objects;

/**
 * Class to represent a view on a {@link FileBuffer}, i.e. a small window onto its contents. This class holds the buffer
 * in question, as well as a cursor to specify a location to edit said buffer. It also holds a {@link Point} to
 * represent the anchor of the view, i.e. the top left location of the rectangular section of test that is visible of
 * the file buffer within the view.
 */
public final class BufferView extends View implements TextListener {

    private final BufferEditor bufferEditor;
    private final Point anchor;

    /**
     * The current update state of the view, responsible for adjusting the cursor and anchor point when an edit is
     * observed on the contents of the view.
     */
    private UpdateState updater;

    private final Communicator communicator;

    private BufferView(Point position,
                       Dimension2D dimensions,
                       Point anchor,
                       Communicator communicator,
                       BufferEditor editor){
        super(position, dimensions);
        this.anchor = anchor;
        this.communicator = Objects.requireNonNull(communicator, "BufferView's communicator cannot be null");
        this.updater = new RemainOnContentState();
        editor.getFileBuffer().addTextListener(this);
        this.bufferEditor = editor;
    }

    public static BufferView createFromFilePath(String url,
                                                Point position,
                                                Dimension2D dimensions,
                                                Communicator communicator){
        Validator.notNull(url, "The url of a file buffer cannot be null.");
        Validator.notNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Validator.notNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = FileBuffer.createFromFilePath(url);
        return new BufferView(position.copy(),
                dimensions.copy(),
                Point.create(0,0),
                communicator,
                new BufferEditor(new History(), b));
    }

    public BufferEditor getBufferEditor(){
        return bufferEditor;
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor;
    }

    public Point getInsertPoint(){
        return bufferEditor.getFileBuffer().getInsertPoint();
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
        bufferEditor.moveCursor(Objects.requireNonNull(direction, "Direction is null."));
        AnchorUpdater.updateAnchor(anchor, bufferEditor.getFileBuffer().getInsertPoint(), getDimensions());
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
                bufferEditor.getFileBuffer().getFile().getPath(),
                bufferEditor.getFileBuffer().getText().getLineAmount(),
                bufferEditor.getFileBuffer().getText().getCharAmount(),
                bufferEditor.getFileBuffer().getInsertPoint().getY(),
                bufferEditor.getFileBuffer().getInsertPoint().getX(),
                bufferEditor.getFileBuffer().getState());
    }
    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public void handleInput(Input input){
        InputType inputType = input.getType();
        switch (inputType) {
            case CHARACTER -> bufferEditor.insert(input.getCharacter());
            case ENTER -> bufferEditor.insert('\n');
            case ARROW_UP -> moveCursor(Direction.UP);
            case ARROW_RIGHT -> moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> moveCursor(Direction.DOWN);
            case ARROW_LEFT -> moveCursor(Direction.LEFT);
            case DELETE -> bufferEditor.deleteAfter();
            case BACKSPACE -> bufferEditor.deleteBefore();
            case CTRL_U -> {
                setUpdateState(new JumpToEditState());
                bufferEditor.redo();
            }
            case CTRL_Z -> {
                setUpdateState(new JumpToEditState());
                bufferEditor.undo();
            }
            case CTRL_S -> {
                try {
                    bufferEditor.getFileBuffer().writeToDisk();
                } catch (IOException e) {
                    communicator.sendMessage("Something went wrong when saving, please try again");
                }
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
                bufferEditor.getFileBuffer(), getPosition(), getDimensions(), anchor);
    }

    /**
     * Duplicates this BufferView by creating a new BufferView with same buffer and communicator instances.
     * The cursor location of the duplicated view will be set to the location of the original.
     *
     * @return A duplicate version of this BufferView, with same buffer and communicator instances
     */
    @Override
    public BufferView duplicate(){
        return new BufferView(getPosition().copy(),
                getDimensions().copy(),
                anchor.copy(),
                communicator,
                bufferEditor);
    }

    @Override
    public void update(TextUpdateReference update, IText text) {
        updater.update(this, update, text);
    }

    @Override
    public boolean canBeClosed() {
        if (bufferEditor.getFileBuffer().getState() == BufferState.CLEAN) {
            bufferEditor.getFileBuffer().removeTextListener(this);
            return true;
        }
        if(bufferEditor.getFileBuffer().getReferenceCount() > 1){
            bufferEditor.getFileBuffer().removeTextListener(this);
            return true;
        }
        if (communicator.requestPermissions(
                "You have unsaved changes. Are you sure you want to close this FileBuffer?")) {
            bufferEditor.getFileBuffer().removeTextListener(this);
            return true;
        }
        return false;
    }
}
