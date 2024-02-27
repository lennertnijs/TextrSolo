package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.*;

public class FileBufferRepo {

    private final List<FileBuffer> fileBuffers;

    /**
     * The constructor for a repository where all the {@link FileBuffer}'s are stored and managed.
     */
    public FileBufferRepo(){
        this.fileBuffers = new ArrayList<>();
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
        return fileBuffers;
    }

    /**
     * Removes all the elements of this repository.
     * USE WITH CARE
     */
    public void removeAll(){
        for(Iterator<FileBuffer> it = fileBuffers.iterator(); it.hasNext(); ){
            it.next();
            it.remove();
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
        fileBuffers.add(fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id from the repository.
     * @param id The id
     */
    public void remove(int id){
        fileBuffers.removeIf(fileBuffer -> fileBuffer.getId() == id);
    }

    public FileBuffer next(int id){
        for(int i = 0; i < fileBuffers.size(); i++){
            if(fileBuffers.get(i).getId() == id){
                int nextIndex = (i + 1)% fileBuffers.size();
                return fileBuffers.get(nextIndex);
            }
        }
        throw new IllegalArgumentException();
    }

    public FileBuffer getActiveFileBuffer(){
        return fileBuffers.stream().filter(FileBuffer::isActive).findFirst().get();
    }

    public void replaceFileBuffer(int id, FileBuffer file){
        int index = fileBuffers.stream().filter( e -> e.getId() == id).findFirst().get().getId();
        fileBuffers.set(index, file);
    }
}
