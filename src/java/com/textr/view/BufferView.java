package com.textr.view;

import com.textr.filebuffer.*;
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
public final class BufferView implements View, TextListener {

    private Point position;
    private Dimension2D dimensions;
    private final Point anchor;
    private final BufferEditor bufferEditor;
    private final Communicator communicator;

    private BufferView(Builder builder){
        this.position = builder.position;
        this.dimensions = builder.dimensions;
        this.bufferEditor = builder.bufferEditor;
        this.anchor = builder.anchor;
        this.communicator = builder.communicator;
        bufferEditor.getFileBuffer().addTextListener(this);
    }

    @Override
    public Point getPosition(){
        return position.copy();
    }

    @Override
    public void setPosition(Point position){
        this.position = Objects.requireNonNull(position, "Position is null.");
    }

    @Override
    public Dimension2D getDimensions(){
        return dimensions;
    }

    @Override
    public void setDimensions(Dimension2D dimensions){
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
    }

    /**
     * Handle input at the view level. Only view specific operations happen here, and nothing flows to a deeper level in the chain.
     */
    @Override
    public boolean handleInput(Input input){
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
            case CTRL_Z -> bufferEditor.undo();
            case CTRL_U -> bufferEditor.redo();
            case CTRL_S -> {
                try {
                    bufferEditor.getFileBuffer().writeToDisk();
                } catch (IOException e) {
                    communicator.sendMessage("Something went wrong when saving, please try again");
                }
            }
            default -> {return false;}
        }
        updateAnchor();
        return true;
    }

    @Override
    public boolean canClose(){
        FileBuffer buffer = bufferEditor.getFileBuffer();
        String msg = "You have unsaved changes. Are you sure you want to close this FileBuffer?";
        return buffer.getListenerCount() > 1 || buffer.getState() == CLEAN || communicator.requestPermissions(msg);
    }

    @Override
    public void prepareToClose(){
        bufferEditor.getFileBuffer().removeTextListener(this);
    }

    /**
     * Generates and returns a status bar for the given FileBuffer.
     *
     * @return The status bar.
     * @throws IllegalArgumentException If the given buffer is null.
     */
    @Override
    public String getStatusBar(){
        return String.format("File path: %s - Lines: %d - Characters: %d - Cursor: (%d, %d) - State: %s",
                bufferEditor.getFileBuffer().getFile().getPath(),
                getText().getLineAmount(),
                getText().getCharAmount(),
                bufferEditor.getInsertPoint().getY(),
                bufferEditor.getInsertPoint().getX(),
                bufferEditor.getFileBuffer().getState());
    }

    /**
     * Duplicates this BufferView by creating a new BufferView with same buffer and communicator instances.
     * The cursor location of the duplicated view will be set to the location of the original.
     *
     * @return A duplicate version of this BufferView, with same buffer and communicator instances
     */
    @Override
    public BufferView duplicate(){
        return builder().fileBuffer(bufferEditor.getFileBuffer()).communicator(communicator).build();
    }

    public IText getText(){
        return bufferEditor.getFileBuffer().getText();
    }

    /**
     * @return This view's anchor point. (0-based)
     */
    public Point getAnchor(){
        return anchor.copy();
    }

    public Point getInsertPoint(){
        return bufferEditor.getInsertPoint();
    }

    public void doUpdate(TextUpdate t){
        if(t.insertPoint().getY() >= anchor.getY()){
            return;
        }
        if(t.operationType() == OperationType.INSERT_NEWLINE){
            bufferEditor.moveCursor(Direction.DOWN);
        }
        if(t.operationType() == OperationType.DELETE_NEWLINE){
            bufferEditor.moveCursor(Direction.UP);
        }
        updateAnchor();
    }

    private void updateAnchor(){
        Point cursor = bufferEditor.getInsertPoint();
        if(cursor.getX() < anchor.getX()){
            anchor.setX(cursor.getX());
        }
        if(cursor.getY() < anchor.getY()){
            anchor.setY(cursor.getY());
        }
        if(cursor.getX() > anchor.getX() + dimensions.width() - 1){
            anchor.setX(cursor.getX() - dimensions.width() + 1);
        }
        // -2 because the rows are have a status bar so -1
        if(cursor.getY() > anchor.getY() + dimensions.height() - 2){
            anchor.setY(cursor.getY() - dimensions.height() + 2);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private Point position = new Point(0, 0);
        private Dimension2D dimensions = new Dimension2D(1, 1);
        private Point anchor = new Point(0 ,0);
        private File file = null;
        private FileBuffer fileBuffer = null;
        private BufferEditor bufferEditor = null;
        private Communicator communicator = null;

        private Builder(){
        }

        public Builder file(File file){
            this.file = file;
            return this;
        }

        public Builder fileBuffer(FileBuffer fileBuffer){
            this.fileBuffer = fileBuffer;
            return this;
        }

        public Builder position(Point position){
            this.position = position;
            return this;
        }

        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }

        public Builder communicator(Communicator communicator){
            this.communicator = communicator;
            return this;
        }

        public Builder anchor(Point anchor){
            this.anchor = anchor;
            return this;
        }

        public BufferView build(){
            Objects.requireNonNull(position, "Position is null.");
            Objects.requireNonNull(dimensions, "Dimensions is null.");
            Objects.requireNonNull(anchor, "Anchor is null.");
            Objects.requireNonNull(communicator, "Communicator is null.");
            if(fileBuffer == null){
                if(file == null){
                    throw new NullPointerException("No file or file buffer is set.");
                }else{
                    this.fileBuffer =  new FileBuffer(file);
                    this.bufferEditor = new BufferEditor(fileBuffer);
                }
            }else{
                this.bufferEditor = new BufferEditor(fileBuffer);
            }
            return new BufferView(this);
        }
    }
}
