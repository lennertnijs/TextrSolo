package com.textr.filebuffer;

import com.textr.filebufferV2.Action;
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
        char toDelete = fileBuffer.getText().getCharacter(insertIndex - 1);
        Action deleteAction = new DeleteAction(insertIndex - 1, toDelete, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    public void deleteAfter(){
        if(insertIndex == fileBuffer.getText().getCharAmount())
            return;
        char toDelete = fileBuffer.getText().getCharacter(insertIndex);
        Action deleteAction = new DeleteAction(insertIndex, toDelete, fileBuffer);
        this.insertIndex = history.executeAndAddAction(deleteAction);
    }

    public void undo(){
        int index = history.undo();
        if(index != -1){
            this.insertIndex = index;
        }
    }

    public void redo(){
        int index = history.redo();
        if(index != -1){
            this.insertIndex = index;
        }
    }
}
