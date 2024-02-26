package com.Textr.FileModel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileRepo {

    private final Map<Integer, File> files;

    /**
     * The constructor for a repository where all the {@link File}'s are stored and managed.
     */
    public FileRepo(){
        this.files = new ConcurrentHashMap<>();
    }

    /**
     * Returns the size of the {@link File} repository.
     *
     * @return The size of the {@link File} repository
     */
    public int getSize(){
        return files.size();
    }

    /**
     * Finds and returns the {@link File} with the given id.
     * If no {@link File} was found, returns an empty {@link Optional}.
     * @param id the id
     *
     * @return An {@link Optional} of the {@link File} if a match was found. Returns an empty {@link Optional} otherwise.
     */
    public Optional<File> get(int id){
        return files.get(id) != null ? Optional.of(files.get(id)) : Optional.empty();
    }

    /**
     * Returns all the existing {@link File}s.
     *
     * @return The {@link File}s
     */
    public List<File> getAll(){
        List<File> filesList = new ArrayList<>(files.size());
        filesList.addAll(files.values());
        return filesList;
    }

    /**
     * Removes all the elements of this repository.
     * USE WITH CARE
     */
    public void removeAll(){
        for(Integer id : files.keySet()){
            files.remove(id);
        }
    }

    /**
     * Adds the given {@link File} to the repository.
     * @param file The file. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link File} is null.
     */
    public void add(File file){
        try{
            Objects.requireNonNull(file);
        }catch(NullPointerException n){
            throw new IllegalArgumentException("Cannot add a null file to the repository.");
        }
        files.put(file.getId(), file);
    }

    /**
     * Removes the {@link File} with the given id from the repository.
     * @param id The id
     */
    public void remove(int id){
        files.remove(id);
    }
}