package com.Textr.TerminalModel;

import com.Textr.FileModel.File;

import java.util.Objects;

public class BufferView {

    private File file;
    private String text;
    private Dimension2D dimensions;
    private int insertionIndex;
    private State state;

    public BufferView(Builder builder){
        this.file = builder.file;
        this.text = builder.text;
        this.dimensions = builder.dimensions;
        this.insertionIndex = builder.insertionIndex;
        this.state = builder.state;
    }

    public File getFile(){
        return this.file;
    }

    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    public int getInsertionIndex(){
        return this.insertionIndex;
    }

    public int getAmountOfLines(){
        return (int)Math.ceil( ((float)this.text.length()) / ((float)this.dimensions.getWidth()));
    }

    public int getAmountOfChars(){
        return this.text.length();
    }

    public State getState(){
        return this.state;
    }

    protected void setFile(File file){
        Objects.requireNonNull(file, "Cannot set the file of a bufferView to null.");
        // make copy
        this.file = file;
    }

    protected void setDimensions(Dimension2D dimensions){
        Objects.requireNonNull(dimensions, "Cannot set the dimensions of a bufferView to null");
        // make a copy
        this.dimensions = dimensions;
    }

    protected void incrementInsertionIndex(){
        int newInsertionIndex = this.insertionIndex + 1;
        if(newInsertionIndex <= this.text.length()){
            this.insertionIndex = newInsertionIndex;
        }
    }

    protected void decrementInsertionIndex(){
        int newInsertionIndex = this.insertionIndex - 1;
        if(newInsertionIndex >= 0){
            this.insertionIndex = newInsertionIndex;
        }
    }

    protected void setDirty(){
        this.state = State.DIRTY;
    }

    protected void setClean(){
        this.state = State.CLEAN;
    }

    private static class Builder{

        private File file;
        private String text;
        private Dimension2D dimensions;
        private int insertionIndex = 0;
        private State state = State.CLEAN;

        private Builder(){

        }

        public Builder file(File file){
            this.file = file;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }

        public BufferView build(){
            Objects.requireNonNull(file, "Cannot build a bufferView because the file is null.");
            Objects.requireNonNull(dimensions, "Cannot build a bufferView because the dimensions are null.");
            return new BufferView(this);
        }
    }
}
