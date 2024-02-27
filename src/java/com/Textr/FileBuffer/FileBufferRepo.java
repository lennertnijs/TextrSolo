package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.*;

public class FileBufferRepo {


    private final List<FileBuffer> buffers;
    private final List<FileBuffer> activeBuffers;

    /**
     * The constructor for a repository where all the {@link FileBuffer}'s are stored and managed.
     */
    public FileBufferRepo(){
        this.buffers = new ArrayList<>();
        this.activeBuffers = new ArrayList<>();
    }


    /**
     * Returns the size of the {@link FileBuffer} repository.
     *
     * @return The size of the {@link FileBuffer} repository
     */
    public int getSize(){
        return buffers.size();
    }

    /**
     * Finds and returns the {@link FileBuffer} with the given id.
     * If no {@link FileBuffer} was found, returns an empty {@link Optional}.
     * @param id the id
     *
     * @return An {@link Optional} of the {@link File} if a match was found. Returns an empty {@link Optional} otherwise.
     */
    public Optional<FileBuffer> get(int id){
        return buffers.get(id) != null ? Optional.of(buffers.get(id)) : Optional.empty();
    }

    /**
     * Returns all the existing {@link FileBuffer}s.
     *
     * @return The {@link FileBuffer}s
     */
    public List<FileBuffer> getAll(){
        return buffers;
    }

    /**
     * Removes all the elements of this repository.
     * USE WITH CARE
     */
    public void removeAll(){
        for(Iterator<FileBuffer> it = buffers.iterator(); it.hasNext(); ){
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
        buffers.add(fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id from the repository.
     * @param id The id
     */
    public void remove(int id){
        buffers.removeIf(fileBuffer -> fileBuffer.getId() == id);
    }

    public FileBuffer next(int id){
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                int nextIndex = (i + 1)% buffers.size();
                return buffers.get(nextIndex);
            }
        }
        throw new IllegalArgumentException();
    }

    public FileBuffer prev(int id){
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                int nextIndex = (i - 1) >= 0 ? (i - 1) : buffers.size()-1;
                return buffers.get(nextIndex);
            }
        }
        throw new IllegalArgumentException();
    }

    public void removeActive(int id){
        for(Iterator<FileBuffer> it = activeBuffers.iterator(); it.hasNext(); ){
            if(it.next().getId() == id){
                it.remove();
            }
        }
    }

    public void removeAllActive(){
        for(Iterator<FileBuffer> it = activeBuffers.iterator(); it.hasNext(); ){
            it.next();
            it.remove();
        }
    }


    public boolean isActive(int id){
        return activeBuffers.stream().anyMatch(e -> e.getId() == id);
    }

    public void setActive(FileBuffer fileBuffer){
        activeBuffers.add(fileBuffer);
    }

    public List<FileBuffer> getAllActives(){
        return activeBuffers;
    }
}
