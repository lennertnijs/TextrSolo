package com.Textr.Model;

public class File {

    private final String url;

    private File(Builder builder){
        this.url = builder.url;
    }

    public String getUrl(){
        return this.url;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String url;

        private Builder(){

        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public File build(){
            return new File(this);
        }
    }
}
