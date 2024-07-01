package com.textr.view;

import com.textr.filebuffer.BufferEditor;
import com.textr.filebuffer.FileBuffer;
import com.textr.filebuffer.TextUpdate;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.terminal.Communicator;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.textr.filebuffer.BufferState.CLEAN;

/**
 * Class to represent a view on a {@link FileBuffer}, i.e. a small window onto its contents. This class holds the buffer
 * in question, as well as a cursor to specify a location to edit said buffer. It also holds a {@link Point} to
 * represent the anchor of the view, i.e. the top left location of the rectangular section of test that is visible of
 * the file buffer within the view.
 */
public final class BufferView extends View implements TextListener {

    private final BufferEditor bufferEditor;
    private final Point anchor;
    private final Communicator communicator;

    private BufferView(Point position,
                       Dimension2D dimensions,
                       Point anchor,
                       Communicator communicator,
                       BufferEditor editor){
        super(position, dimensions);
        this.anchor = anchor;
        this.communicator = Objects.requireNonNull(communicator, "BufferView's communicator cannot be null");
        editor.getFileBuffer().addTextListener(this);
        this.bufferEditor = editor;
    }

    public static BufferView createFromFilePath(File file,
                                                Point position,
                                                Dimension2D dimensions,
                                                Communicator communicator){
        Objects.requireNonNull(file, "The url of a file buffer cannot be null.");
        Objects.requireNonNull(position, "The global position of the BufferView in the Terminal cannot be null.");
        Objects.requireNonNull(dimensions, "The dimensions of the BufferView cannot be null.");
        FileBuffer b = new FileBuffer(file);
        return new BufferView(position.copy(),
                dimensions,
                new Point(0,0),
                communicator,
                new BufferEditor(b));
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
        return bufferEditor.getInsertPoint();
    }

    /**
     * Generates and returns a status bar for the given FileBuffer.
     *
     * @return The status bar.
     * @throws IllegalArgumentException If the given buffer is null.
     */
    @Override
    public String generateStatusBar(){
        FileBuffer buffer = bufferEditor.getFileBuffer();
        return String.format("File path: %s - Lines: %d - Characters: %d - Cursor: (line, col) = (%d, %d) - State: %s",
                buffer.getFile().getPath(),
                buffer.getText().getLineAmount(),
                buffer.getText().getCharAmount(),
                bufferEditor.getInsertPoint().getY(),
                bufferEditor.getInsertPoint().getX(),
                buffer.getState());
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
            case DELETE -> bufferEditor.deleteAfter();
            case BACKSPACE -> bufferEditor.deleteBefore();
            case ARROW_UP -> bufferEditor.moveCursor(Direction.UP);
            case ARROW_RIGHT -> bufferEditor.moveCursor(Direction.RIGHT);
            case ARROW_DOWN -> bufferEditor.moveCursor(Direction.DOWN);
            case ARROW_LEFT -> bufferEditor.moveCursor(Direction.LEFT);
            case CTRL_U -> bufferEditor.redo();
            case CTRL_Z -> bufferEditor.undo();
            case CTRL_S -> {
                try {
                    bufferEditor.getFileBuffer().writeToDisk();
                } catch (IOException e) {
                    communicator.sendMessage("Something went wrong when saving, please try again");
                }
            }
        }
        AnchorUpdater.updateAnchor(anchor, bufferEditor.getInsertPoint(), getDimensions());
    }

    public void doUpdate(TextUpdate t){
    }

    @Override
    public boolean canBeClosed() {
        FileBuffer buffer = bufferEditor.getFileBuffer();
        String msg = "You have unsaved changes. Are you sure you want to close this FileBuffer?";
        if (buffer.getListenerCount() > 1 || buffer.getState() == CLEAN || communicator.requestPermissions(msg)) {
            buffer.removeTextListener(this);
            return true;
        }
        return false;
    }

    /**
     * Duplicates this BufferView by creating a new BufferView with same buffer and communicator instances.
     * The cursor location of the duplicated view will be set to the location of the original.
     *
     * @return A duplicate version of this BufferView, with same buffer and communicator instances
     */
    @Override
    public BufferView duplicate(){
        return new BufferView(getPosition().copy(), getDimensions(), anchor.copy(), communicator, bufferEditor.copy());
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString(){
        return String.format("BufferView[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                bufferEditor.getFileBuffer(), getPosition(), getDimensions(), anchor);
    }
}
