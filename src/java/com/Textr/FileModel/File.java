package com.Textr.FileModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {


    // id to uniquely identify files
    private final String url;
    private final String text;

    private File(Builder builder){
        this.url = builder.url;
        this.text = builder.text;
    }

    public String getUrl(){
        return this.url;
    }

    public String getText(){
        return this.text;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private String url;
        private String  text = "dummy";

        private Builder(){
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public File build(){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(removeNonAscii(line));
                    //stringBuilder.append("\n");
                }
                this.text = String.valueOf(stringBuilder);
                return new File(this);
            }catch(IOException e){
                System.out.println("The file could not be read.");
            }
            ;
            return new File(this);
        }

        private String removeNonAscii(String line){
            StringBuilder builder = new StringBuilder();
            for(int i =0; i < line.length(); i++){
                char c = line.charAt(i);
                if(c >= 32 && c <= 126 || c == 10 || c == 13){
                    builder.append(c);
                }
            }
            return String.valueOf(builder);
        }
    }
}
