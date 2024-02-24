package com.Textr.FileModel;

import java.util.Objects;

public class FileBuffer {

    private final int activeFileId;
    private final String  bufferText;
    private final int insertionIndex;
    private final State state;

    private FileBuffer(Builder builder){
        this.activeFileId = builder.activeFileId;
        this.bufferText = builder.bufferText;
        this.insertionIndex = builder.insertionIndex;
        this.state = builder.state;
    }

    public int getActiveFileId(){
        return this.activeFileId;
    }

    public String getBufferText(){
        return this.bufferText;
    }

    public int getInsertionIndex(){
        return this.insertionIndex;
    }

    public State getState(){
        return this.state;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof FileBuffer fileBuffer)){
            return false;
        }
        return this.activeFileId == fileBuffer.activeFileId &&
                this.bufferText.equals(fileBuffer.bufferText) &&
                this.insertionIndex == fileBuffer.insertionIndex &&
                this.state == fileBuffer.state;
    }

    @Override
    public int hashCode(){
        return Objects.hash(activeFileId, bufferText, insertionIndex, state);
    }

    @Override
    public String toString(){
        return String.format("FileBuffer[activeFileId = %d, bufferText = %s, insertionIndex = %d, state = %s]",
                activeFileId, bufferText, insertionIndex, state);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private int activeFileId;
        private String bufferText;
        private int insertionIndex;
        private State state;

        private Builder(){

        }

        public Builder activeFileId(int id){
            this.activeFileId = id;
            return this;
        }

        public Builder bufferText(String text){
            this.bufferText = text;
            return this;
        }

        public Builder insertionIndex(int index){
            this.insertionIndex = index;
            return this;
        }

        public Builder state(State state){
            this.state = state;
            return this;
        }

        public FileBuffer build(){
            if(activeFileId < 0){
                throw new IllegalArgumentException("The id the active File in the FileBuffer cannot be negative.");
            }
            try{
                Objects.requireNonNull(bufferText);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The buffer text in the FileBuffer cannot be null.");
            }
            if(insertionIndex < 0 || insertionIndex > bufferText.length()){
                throw new IllegalArgumentException("The insertion index of the FileBuffer cannot be outside the text's range.");
            }
            try{
                Objects.requireNonNull(state);
            }catch(NullPointerException e){
                throw new IllegalArgumentException("The state of the FileBuffer cannot be null.");
            }
            return new FileBuffer(this);
        }
    }
}
