package com.Textr.File;

import com.Textr.Util.Validator;

/**
 * Class to represent a File object.
 */
public final class File {

    private final int id;
    private final String url;
    private String text;

    /**
     * Constructor for a {@link File}.
     */
    private File(String url, String text){
        this.id = FileIdGenerator.getId();
        this.url = url;
        this.text = text;
    }

    private File(int id, String url, String text){
        this.id = id;
        this.url = url;
        this.text = text;
    }

    /**
     * Static factory method to create {@link File}s with.
     * @param url The uniform resource locator (URL) of the file.
     * @param text The text content of the file.
     *
     * @return The newly created {@link File}.
     * @throws IllegalArgumentException If url/text is null.
     */
    public static File create(String url, String text){
        Validator.notNull(url, "Cannot create a File with a null url.");
        Validator.notNull(text, "Cannot create a File with a null text.");
        return new File(url, text);
    }

    /**
     * @return The {@link File}'s id.
     */
    public int getId(){
        return this.id;
    }

    /**
     * @return The {@link File}'s url. (file path)
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * @return The {@link File}'s contents (as text).
     */
    public String getText(){
        return this.text;
    }

    /**
     * Sets the text to the file to the given string.
     * Is used to update to represent the current data saved to disk.
     *
     * @param text The String of the text saved to disk
     */
    public void setText(String text) {
        Validator.notNull(text, "Cannot set the text of a File to null.");
        this.text = text;
    }

    /**
     * Compares this {@link File} to the given {@link Object} and returns True if they're equal. Returns false otherwise.
     * Equality means they have the same id.
     * @param o The {@link Object}
     *
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof File file)){
            return false;
        }
        return this.id == file.id && this.url.equals(file.url) && this.text.equals(file.text);
    }

    /**
     * Creates a hash code for this {@link File}.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode(){
        int result = id;
        result = result * 31 + url.hashCode();
        result = result * 31 + text.hashCode();
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link File}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("File[id = %d, url = %s, text = %s]", this.id, this.url, this.text);
    }

    @Override
    public File clone(){
        return new File(this.id, this.url, this.text);
    }
}