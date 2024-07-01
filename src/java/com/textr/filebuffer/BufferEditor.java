package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.Objects;

/**
 * Represents an editor for a single {@link FileBuffer}. Also keeps track of this file buffer's edit history.
 * While duplicate windows of the same File have the same file buffer, their buffer editors will be different,
 * as to keep their cursors and edit history separate.
 */
public final class BufferEditor {

    /**
     * The file buffer of this buffer editor.
     */
    private final FileBuffer fileBuffer;
    /**
     * The edit history of the buffer editor.
     */
    private final History history;
    /**
     * The insert index for this buffer editor.
     */
    private int insertIndex;

    /**
     * Creates a MUTABLE {@link BufferEditor}.
     * Will automatically initialise the insert index at the beginning, and add a new edit history.
     * @param buffer The file buffer. Cannot be null.
     */
    public BufferEditor(FileBuffer buffer){
        this.fileBuffer = Objects.requireNonNull(buffer, "File buffer is null.");
        this.history = new History();
        this.insertIndex = 0;
    }

    /**
     * @return The file buffer.
     */
    public FileBuffer getFileBuffer() {
        return fileBuffer;
    }

    /**
     * @return The insert point.
     */
    public Point getInsertPoint(){
        return fileBuffer.getInsertPoint(insertIndex);
    }

    /**
     * Moves the cursor one unit in the given direction.
     * @param direction The direction. Cannot be null.
     */
    public void moveCursor(Direction direction){
        this.insertIndex = fileBuffer.move(insertIndex, Objects.requireNonNull(direction, "Direction is null."));
    }

    /**
     * Inserts the given character into the file buffer's text at the insert index.
     * @param c The character.
     */
    public void insert(char c){
        Action insertAction = new InsertAction(c, insertIndex, fileBuffer);
        this.insertIndex = history.executeAndAddAction(insertAction);
    }

    /**
     * Attempts to delete the character before the insert index.
     * However, if the insert index is at the start of the text (= 0), does nothing.
     */
    public void deleteBefore(){
        if(insertIndex == 0)
            return;
        Action deleteAction = new DeleteAction( insertIndex - 1, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    /**
     * Attempts to delete the character after the insert index.
     * However, if the insert index is at the end of the text (= text.size()), does nothing.
     */
    public void deleteAfter(){
        if(insertIndex == fileBuffer.getText().getCharAmount())
            return;
        Action deleteAction = new DeleteAction(insertIndex, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    /**
     * Undoes the most recent Action performed, if any.
     */
    public void undo(){
        this.insertIndex = history.undo(insertIndex);
    }

    /**
     * Redoes the most recent undone Action, if any.
     */
    public void redo(){
        this.insertIndex = history.redo(insertIndex);
    }

    /**
     * @return A deep copy, with the same file buffer, with fresh insert index and edit history.
     */
    public BufferEditor copy(){
        return new BufferEditor(fileBuffer);
    }
}
