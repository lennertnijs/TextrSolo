package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileBufferRepo {

    private final Map<Integer, FileBuffer> fileBuffers;

    /**
     * The constructor for a repository where all the {@link FileBuffer}'s are stored and managed.
     */
    public FileBufferRepo(){
        this.fileBuffers = new ConcurrentHashMap<>();
    }


    /**
     * Returns the size of the {@link FileBuffer} repository.
     *
     * @return The size of the {@link FileBuffer} repository
     */
    public int getSize(){
        return fileBuffers.size();
    }

    /**
     * Finds and returns the {@link FileBuffer} with the given id.
     * If no {@link FileBuffer} was found, returns an empty {@link Optional}.
     * @param id the id
     *
     * @return An {@link Optional} of the {@link File} if a match was found. Returns an empty {@link Optional} otherwise.
     */
    public Optional<FileBuffer> get(int id){
        return fileBuffers.get(id) != null ? Optional.of(fileBuffers.get(id)) : Optional.empty();
    }

    /**
     * Returns all the existing {@link FileBuffer}s.
     *
     * @return The {@link FileBuffer}s
     */
    public List<FileBuffer> getAll(){
        List<FileBuffer> fileBufferList = new ArrayList<>(fileBuffers.size());
        fileBufferList.addAll(fileBuffers.values());
        return fileBufferList;
    }

    /**
     * Removes all the elements of this repository.
     * USE WITH CARE
     */
    public void removeAll(){
        for(Integer i : fileBuffers.keySet()){
            fileBuffers.remove(i);
        }
    }

    /**
     * Adds the given {@link FileBuffer} to the repository.
     * @param fileBuffer The {@link FileBuffer}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link FileBuffer} is null.
     */
    public void add(FileBuffer fileBuffer){
        try{
            Objects.requireNonNull(fileBuffer);
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Cannot store a null FileBuffer in the repository.");
        }
        fileBuffers.put(fileBuffer.getId(), fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id from the repository.
     * @param id The id
     */
    public void remove(int id){
        fileBuffers.remove(id);
    }
}
