package com.textr.filebuffer;

import com.textr.filebufferV2.Action;
import com.textr.filebufferV2.DeleteAction;
import com.textr.filebufferV2.InsertAction;
import com.textr.filebufferV2.FileBuffer;
import com.textr.util.Direction;
import com.textr.util.Point;

import java.util.Objects;

public final class BufferEditor {

    private final History history;
    private final FileBuffer fileBuffer;
    private int insertIndex;

    public BufferEditor(History history, FileBuffer buffer){
        this.history = Objects.requireNonNull(history, "History is null.");
        this.fileBuffer = Objects.requireNonNull(buffer, "File buffer is null.");
        this.insertIndex = 0;
    }

    public FileBuffer getFileBuffer() {
        return fileBuffer;
    }

    public Point getInsertPoint(){
        return fileBuffer.getInsertPoint(insertIndex);
    }

    public void moveCursor(Direction direction){
        this.insertIndex = fileBuffer.move(insertIndex, direction);
    }

    public void insert(char c){
        Action insertAction = new InsertAction(c, insertIndex, fileBuffer);
        this.insertIndex = history.executeAndAddAction(insertAction);
    }


    public void deleteBefore(){
        if(insertIndex == 0)
            return;
        Action deleteAction = new DeleteAction( insertIndex - 1, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    public void deleteAfter(){
        if(insertIndex == fileBuffer.getText().getCharAmount())
            return;
        Action deleteAction = new DeleteAction(insertIndex, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    public void undo(){
        this.insertIndex = history.undo(insertIndex);
    }

    public void redo(){
        this.insertIndex = history.redo(insertIndex);
    }
}
