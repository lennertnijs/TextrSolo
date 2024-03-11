package com.Textr.File;

import com.Textr.Util.Validator;

/**
 * Class to represent a File object.
 */
public final class File {

    private final int id;
    private final String url;

    /**
     * Constructor for a {@link File}.
     */
    private File(String url){
        this.id = FileIdGenerator.getId();
        this.url = url;
    }

    private File(int id, String url){
        this.id = id;
        this.url = url;
    }

    /**
     * Static factory method to create {@link File}s with.
     * @param url The uniform resource locator (URL) of the file.
     *
     * @return The newly created {@link File}.
     * @throws IllegalArgumentException If url/text is null.
     */
    public static File create(String url){
        Validator.notNull(url, "Cannot create a File with a null url.");
        return new File(url);
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
        return this.id == file.id && this.url.equals(file.url);
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
        return result;
    }

    /**
     * Creates and returns a {@link String} representation of this {@link File}.
     *
     * @return The {@link String}
     */
    @Override
    public String toString(){
        return String.format("File[id = %d, url = %s]", this.id, this.url);
    }

    public File copy(){
        return new File(this.id, this.url);
    }
}