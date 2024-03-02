package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Validator.Validator;

import java.util.*;

public final class AllFileBuffersRepo implements IAllFileBuffersRepo {


    private final List<FileBuffer> buffers = new ArrayList<>();

    public AllFileBuffersRepo(){
    }


    /**
     * Returns the amount of {@link FileBuffer}s in the repository.
     *
     * @return The size
     */
    @Override
    public int getSize(){
        return buffers.size();
    }

    /**
     * Returns True if a {@link FileBuffer} with the given id exists in the repository. Returns False otherwise.
     * @param id The id
     *
     * @return True if a match was found, False otherwise.
     */
    @Override
    public boolean contains(int id){
        for(FileBuffer buffer : buffers){
            if(id == buffer.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * Fetches and returns the {@link FileBuffer} with the given id. Throws an Exception if no element was found.
     * @param id The id
     *
     * @return The {@link FileBuffer}
     * @throws NoSuchElementException If no {@link FileBuffer} with that id was found.
     */
    @Override
    public FileBuffer get(int id){
        for(FileBuffer buffer : buffers){
            if(buffer.getId() == id){
                return buffer;
            }
        }
        throw new NoSuchElementException("No FileBuffer with the given id was found.");
    }

    /**
     * Returns all the existing {@link FileBuffer}s. (Not copied)
     *
     * @return The {@link FileBuffer}s
     */
    @Override
    public List<FileBuffer> getAll(){
        return buffers;
    }

    /**
     * Adds the given {@link FileBuffer} to the repository.
     * @param fileBuffer The {@link FileBuffer}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link FileBuffer} is null.
     */
    @Override
    public void add(FileBuffer fileBuffer){
        Validator.notNull(fileBuffer, "Cannot store a null FileBuffer in the repository.");
        buffers.add(fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id from the repository.
     * If no match was found, does nothing.
     * @param id The id
     */
    @Override
    public void remove(int id){
        buffers.removeIf(fileBuffer -> fileBuffer.getId() == id);
    }

    @Override
    public void removeAll(){
        for(Iterator<FileBuffer> it = buffers.listIterator(); it.hasNext(); ){
            it.next();
            it.remove();
        }
    }

    /**
     * Finds and returns the next {@link FileBuffer} of the {@link FileBuffer} with the given id.
     * (Works because insertion order is maintained)
     * @param id The id
     *
     * @return The {@link FileBuffer}
     * @throws IllegalStateException If no next {@link FileBuffer} was found.
     */
    @Override
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
    @Override
    public FileBuffer getPrevious(int id){
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                int nextIndex = (i - 1) >= 0 ? (i - 1) : buffers.size() - 1;
                return buffers.get(nextIndex);
            }
        }
        throw new IllegalStateException("Could not find the previous FileBuffer");
    }
}
