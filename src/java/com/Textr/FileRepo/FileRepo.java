package com.Textr.FileRepo;

import com.Textr.File.File;
import com.Textr.Validator.Validator;

import java.util.*;

public final class FileRepo implements IFileRepo {

    private final List<File> files;

    public FileRepo(){
        this.files = new ArrayList<>();
    }


    /**
     * Checks whether a file with the given id exists.
     * @param id The id
     *
     * @return True if a matching file was found. False otherwise.
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
     * @return The amount of files stored.
     */
    @Override
    public int getSize(){
        return files.size();
    }

    /**
     * Finds and returns the file with the given id.
     * @param id The id
     *
     * @return The file
     * @throws NoSuchElementException If no matching file was found.
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
     * @return All stored files.
     */
    public List<File> getAll(){
        return files;
    }


    /**
     * Stores the given file.
     * @param file The file. Cannot be null.
     *
     * @throws IllegalArgumentException If the given file is null.
     */
    public void add(File file){
        Validator.notNull(file, "Cannot store a null File.");
        files.add(file);
    }

    /**
     * Removes the file with the given id.
     * If no match was found, does nothing.
     * @param id The id
     */
    public void remove(int id){
        files.removeIf(e -> e.getId() == id);
    }
}