package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.*;

public class AllFileBuffersRepo implements IAllFileBuffersRepo {


    private final List<FileBuffer> buffers = new ArrayList<>();

    public AllFileBuffersRepo(){
    }


    /**
     * Returns the amount of {@link FileBuffer}s in the repository. This includes the active buffer.
     *
     * @return The size
     */
    public int getSize(){
        return buffers.size();
    }

    /**
     * Finds and returns the {@link FileBuffer} with the given id.
     * @param id The id
     *
     * @return The {@link FileBuffer}
     * @throws NoSuchElementException If no {@link FileBuffer} with the id was found.
     */
    public FileBuffer get(int id){
        for(FileBuffer buffer : buffers){
            if(buffer.getId() == id){
                return buffer;
            }
        }
        throw new NoSuchElementException("No FileBuffer with the given id was found.");
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

    /**
     * Finds and returns the next {@link FileBuffer} of the {@link FileBuffer} with the given id.
     * (Works because insertion order is maintained)
     * @param id The id
     *
     * @return The {@link FileBuffer}
     * @throws IllegalStateException If no next {@link FileBuffer} was found.
     */
    public FileBuffer getNext(int id){
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                int nextIndex = (i + 1) % buffers.size();
                return buffers.get(nextIndex);
            }
        }
        throw new IllegalStateException("Could not find the next FileBuffer.");
    }

    /**
     * Finds and returns the previous {@link FileBuffer} of the {@link FileBuffer} with the given id.
     * (Works because insertion order is maintained)
     * @param id The id
     *
     * @return The {@link FileBuffer}
     * @throws IllegalStateException If no previous {@link FileBuffer} was found.
     */
    public FileBuffer getPrevious(int id){
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                int nextIndex = (i - 1) >= 0 ? (i - 1) : buffers.size()-1;
                return buffers.get(nextIndex);
            }
        }
        throw new IllegalStateException("Could not find the previous FileBuffer");
    }
}
