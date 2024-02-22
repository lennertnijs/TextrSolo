package com.Textr.TerminalModel;

import com.Textr.FileModel.File;

import java.util.Objects;

public class BufferView {

    private File file;
    private Dimension2D dimensions;
    private int insertionIndex;
    private int amountOfLines;
    private int amountOfChars;
    private Dirty isDirty;

    public BufferView(Builder builder){
        this.file = builder.file;
        this.dimensions = builder.dimensions;
        this.insertionIndex = builder.insertionIndex;
        this.amountOfLines = builder.amountOfLines;
        this.amountOfChars = builder.amountOfChars;
        this.isDirty = builder.isDirty;
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
        return this.amountOfLines;
    }

    public int getAmountOfChars(){
        return this.amountOfChars;
    }

    public boolean getIsDirty(){
        return this.isDirty == Dirty.YES;
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
        if(newInsertionIndex <= this.amountOfChars){
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
        this.isDirty = Dirty.YES;
    }

    protected void setNotDirty(){
        this.isDirty = Dirty.NO;
    }

    protected void setAmountOfChars(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("The amount of characters in a view cannot be negative.");
        }
        this.amountOfChars = amount;
        this.amountOfLines = amount / this.dimensions.getWidth();
    }


    private static class Builder{

        private File file;
        private Dimension2D dimensions;
        private int insertionIndex = 0;
        private int amountOfLines;
        private int amountOfChars;
        private Dirty isDirty = Dirty.NO;

        private Builder(){

        }

        public Builder file(File file){
            this.file = file;
            return this;
        }

        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }

        public BufferView build(){
            Objects.requireNonNull(file, "Cannot build a bufferView because the file is null.");
            Objects.requireNonNull(dimensions, "Cannot build a bufferView because the dimensions are null.");
            this.amountOfChars = file.getText().length();
            this.amountOfLines = (int)Math.ceil( ((float)this.amountOfChars) / ((float)this.dimensions.getWidth()));
            return new BufferView(this);
        }
    }
}
