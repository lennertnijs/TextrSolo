package com.Textr.FileBuffer;

import java.util.Arrays;
import java.util.Objects;

public class Text {

    private final String[] lines;

    private Text(String[] lines){
        this.lines = lines;
    }

    public static Text create(String[] lines){
        Objects.requireNonNull(lines, "Cannot create a Text with null lines.");
        String[] linesCopy = new String[lines.length];
        for(int i = 0; i < lines.length; i++){
            Objects.requireNonNull(lines[i], "Cannot create a Text with a null line.");
            linesCopy[i] = lines[i];
        }
        return new Text(linesCopy);
    }

    public static Text create(String text){
        Objects.requireNonNull(text, "Cannot create a Text because the text passed is null.");
        return new Text(text.split(System.lineSeparator()));
    }

    public String[] getLines(){
        String[] linesCopy = new String[lines.length];
        System.arraycopy(lines, 0, linesCopy, 0, lines.length);
        return linesCopy;
    }

    public String getLinesAsText(){
        return String.join(System.lineSeparator(), lines);
    }

    public int getAmountOfLines(){
        return lines.length;
    }

    public int getAmountOfChars(){
        return String.join("", lines).replace("\n", "").replace("\r", "").length();
    }

    public void addCharacter(char character, int row, int col){
        String line = lines[row];
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < line.length() + 1; i++){
            if(i < col){
                builder.append(line.charAt(i));
            }else if(i == col){
                builder.append(character);
            }else{
                builder.append(line.charAt(i-1));
            }
        }
        lines[row] = builder.toString();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!( o instanceof Text text)){
            return false;
        }
        return Arrays.equals(text.lines, this.lines);
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(lines);
    }

    @Override
    public String toString(){
        return String.format("Text[Lines: %s]", String.join("\r\n", lines));
    }
}
