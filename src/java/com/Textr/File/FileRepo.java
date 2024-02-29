package com.Textr.File;

import com.Textr.Validator.Validator;

import java.util.*;

public final class FileRepo implements IFileRepo{

    private final List<File> files;

    public FileRepo(){
        this.files = new ArrayList<>();
    }


    /**
     * Checks whether a {@link File} with the given id is stored.
     * @param id The id
     *
     * @return True if a matching {@link File} was found. False otherwise.
     */
    @Override
    public boolean contains(int id) {
        for(File file : files){
            if(file.getId() == id){
                return true;
            }
        }
        return false;
    }


    /**
     * @return The amount of {@link File}s stored.
     */
    @Override
    public int getSize(){
        return files.size();
    }

    /**
     * Finds and returns the {@link File} with the given id.
     * @param id The id
     *
     * @return The {@link File}
     * @throws NoSuchElementException If no matching {@link File} was found.
     */
    public File get(int id){
        for(File file : files){
            if(file.getId() == id){
                return file;
            }
        }
        throw new NoSuchElementException("No File with the given id was found.");
    }

    /**
     * @return All stored {@link File}s.
     */
    public List<File> getAll(){
        return files;
    }


    /**
     * Stores the given {@link File}.
     * @param file The {@link File}. Cannot be null.
     *
     * @throws IllegalArgumentException If the {@link File} is null.
     */
    public void add(File file){
        Validator.notNull(file, "Cannot store a null File.");
        files.add(file);
    }

    /**
     * Removes the {@link File} with the given id.
     * If no match was found, does nothing.
     * @param id The id
     */
    public void remove(int id){
        files.removeIf(e -> e.getId() == id);
    }
}