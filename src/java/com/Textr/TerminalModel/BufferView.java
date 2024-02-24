package com.Textr.TerminalModel;

import java.util.Objects;

public class BufferView {

    private final int fileId;
    private final BufferPoint point;
    private final Dimension2D dimensions;
    private final String text;
    private final int insertionIndex;
    private final State state;

    public BufferView(Builder builder){
        this.fileId = builder.fileId;
        this.point = builder.point;
        this.dimensions = builder.dimensions;
        this.text = builder.text;
        this.insertionIndex = builder.insertionIndex;
        this.state = builder.state;
    }

    public int getFileId(){
        return this.fileId;
    }

    public BufferPoint getPoint(){
        return this.point;
    }
    public Dimension2D getDimensions(){
        return this.dimensions;
    }

    public String getText(){
        return this.text;
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
        if(!(o instanceof BufferView view)){
            return false;
        }
        return this.fileId == view.fileId &&
                this.point.equals(view.point) &&
                this.dimensions.equals(view.dimensions) &&
                this.text.equals(view.text) &&
                this.insertionIndex == view.insertionIndex &&
                this.state == view.state;
    }

    @Override
    public int hashCode(){
        return Objects.hash(fileId, point, dimensions, text, insertionIndex, state);
    }

    @Override
    public String toString(){
        return String.format("BufferView[fileId = %d, point = %s, dimensions = %s, " +
                                "text = %s, insertionIndex = %d, state = %s]",
                                fileId, point.toString(), dimensions, text, insertionIndex, state);
    }

    private static class Builder{

        private int fileId;
        private BufferPoint point;
        private Dimension2D dimensions;
        private String text;
        private int insertionIndex = 0;
        private final State state = State.CLEAN;

        private Builder(){

        }

        public Builder fileId(int id){
            this.fileId = id;
            return this;
        }

        public Builder point(BufferPoint point){
            this.point = point;
            return this;
        }

        public Builder dimensions(Dimension2D dimensions){
            this.dimensions = dimensions;
            return this;
        }

        public Builder text(String text){
            this.text = text;
            return this;
        }

        public Builder insertionIndex(int insertionIndex){
            this.insertionIndex = insertionIndex;
            return this;
        }

        public BufferView build(){
            if(fileId < 0){
                throw new IllegalArgumentException("The file id of a BufferView cannot be negative.");
            }
            try{
                Objects.requireNonNull(point, "The buffer point of a bufferView cannot be null");
                Objects.requireNonNull(dimensions, "The dimensions of a bufferView cannot be null");
                Objects.requireNonNull(text, "The text of a bufferView cannot be null");
            }catch(NullPointerException e){
                throw new IllegalArgumentException("Cannot build a bufferView with a null parameter");
            }
            if(insertionIndex < 0 || insertionIndex > text.length()){
                throw new IllegalArgumentException("The insertion index of a bufferView must be within the text size range.");
            }
            return new BufferView(this);
        }
    }
}
