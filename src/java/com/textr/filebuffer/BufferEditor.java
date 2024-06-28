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

    public FileBuffer getFileBuffer(){
        return fileBuffer;
    }

    public Cursor getCursor(){
        return fileBuffer.getCursor();
    }

    public void moveCursor(Direction direction){
        fileBuffer.moveCursor(direction);
    }

    public void insert(char c){
        Action insertAction = new InsertAction(fileBuffer.getCursor().getInsertIndex(), c, fileBuffer.getText());
        history.executeAndAddAction(insertAction);
    }


    public void deleteBefore(){
        if(fileBuffer.getCursor().getInsertIndex() == 0)
            return;
        Action deleteAction = new DeleteAction(fileBuffer.getCursor().getInsertIndex() - 1,
                fileBuffer.getText().getCharacter(fileBuffer.getCursor().getInsertIndex() - 1),
                fileBuffer.getText());
        history.executeAndAddAction(deleteAction);
    }

    public void deleteAfter(){
        if(fileBuffer.getCursor().getInsertIndex() == fileBuffer.getText().getCharAmount())
            return;
        Action deleteAction = new DeleteAction(fileBuffer.getCursor().getInsertIndex(),
                fileBuffer.getText().getCharacter(fileBuffer.getCursor().getInsertIndex()),
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
