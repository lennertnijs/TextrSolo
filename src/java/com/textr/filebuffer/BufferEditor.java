package com.textr.filebuffer;

import com.textr.util.Direction;

import java.util.Objects;

public final class BufferEditor {

    private final History history;
    private final FileBuffer fileBuffer;

    public BufferEditor(History history, FileBuffer buffer){
        this.history = Objects.requireNonNull(history, "History is null.");
        this.fileBuffer = Objects.requireNonNull(buffer, "File buffer is null.");
    }

    public FileBuffer getFileBuffer() {
        return fileBuffer;
    }

    public void moveCursor(Direction direction){
        fileBuffer.moveCursor(direction);
    }

    public void insert(char c){
        Action insertAction = new InsertAction(fileBuffer.getInsertIndex(), c, fileBuffer.getText());
        history.executeAndAddAction(insertAction);
    }


    public void deleteBefore(){
        if(fileBuffer.getInsertIndex() == 0)
            return;
        Action deleteAction = new DeleteAction(fileBuffer.getInsertIndex() - 1,
                fileBuffer.getText().getCharacter(fileBuffer.getInsertIndex() - 1),
                fileBuffer.getText());
        history.executeAndAddAction(deleteAction);
    }

    public void deleteAfter(){
        if(fileBuffer.getInsertIndex() == fileBuffer.getText().getCharAmount())
            return;
        Action deleteAction = new DeleteAction(fileBuffer.getInsertIndex(),
                fileBuffer.getText().getCharacter(fileBuffer.getInsertIndex()),
                fileBuffer.getText());
        history.executeAndAddAction(deleteAction);
    }

    public void undo(){
        history.undo();
    }

    public void redo(){
        history.redo();
    }
}
