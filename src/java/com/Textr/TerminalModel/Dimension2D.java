package com.Textr.TerminalModel;

public class Dimension2D {

    private final int width;
    private final int height;

    private Dimension2D(Builder builder){
        this.width = builder.width;
        this.height = builder.height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }


    @Override
    public String toString(){
        return String.format("Dimension2D[width = %d, height = %d]", width, height);
    }
    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private int width;
        private int height;

        private Builder(){

        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Dimension2D build(){
            if(width < 0 || height < 0){
                throw new IllegalArgumentException("Sizes cannot be non-positive");
            }
            return new Dimension2D(this);
        }
    }
}
