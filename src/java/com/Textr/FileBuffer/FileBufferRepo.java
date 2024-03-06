package com.Textr.FileBuffer;

import com.Textr.Util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class FileBufferRepo implements IFileBufferRepo {

    private FileBuffer activeBuffer;
    private List<FileBuffer> buffers;

    public FileBufferRepo(){
        this.activeBuffer = null;
        this.buffers = new ArrayList<>();
    }

    /**
     * @return True if an active file buffer is set. False otherwise.
     */
    public boolean hasActiveBuffer(){
        return activeBuffer != null;
    }

    /**
     * @return The amount of existing file buffers.
     */
    @Override
    public int getSize(){
        return buffers.size();
    }

    /**
     * Returns the file buffer with the given id.
     * @param id The id
     *
     * @return The buffer
     *
     */
    @Override
    public FileBuffer getBuffer(int id){
        for(FileBuffer buffer : buffers){
            if(buffer.getId() == id){
                return buffer;
            }
        }
        throw new NoSuchElementException("No buffer with the given id exists.");
    }

    /**
     * Stores the given file buffer.
     * @param fileBuffer The file buffer. Cannot be null
     *
     * @throws IllegalArgumentException If the given file buffer is null.
     */
    @Override
    public void addBuffer(FileBuffer fileBuffer) {
        Validator.notNull(fileBuffer, "Cannot store a null FileBuffer.");
        buffers.add(fileBuffer);
    }

    /**
     * Returns the id of the active file buffer.
     *
     * @return The id
     */
    @Override
    public int getActiveBufferId() {
        if(activeBuffer == null){
            throw new IllegalStateException("Cannot fetch the id of the active buffer because it is null.");
        }
        return activeBuffer.getId();
    }

    /**
     * @return The active file buffer.
     */
    @Override
    public FileBuffer getActiveBuffer(){
        return activeBuffer;
    }

    /**
     * Sets the active file buffer to the given file buffer.
     * @param fileBuffer The new active file buffer. Cannot be null
     *
     * @throws IllegalArgumentException If the given file buffer is null.
     * @throws IllegalStateException If the given buffer does not yet exist in the repository.
     */
    @Override
    public void setActiveBuffer(FileBuffer fileBuffer) {
        Validator.notNull(fileBuffer, "Cannot set the active file buffer to a null buffer.");
        boolean existsInAllRepo = buffers.contains(fileBuffer);
        if(!existsInAllRepo){
            throw new IllegalStateException("Cannot set the active FileBuffer to a buffer that does not exist.");
        }
        activeBuffer = fileBuffer;
    }

    /**
     * Removes the file buffer with the given id.
     * If no match is found, does nothing.
     * @param id The id
     *
     * @throws IllegalStateException If an attempt is made to remove the active buffer indirectly.
     */
    @Override
    public void removeBuffer(int id) {
        if(activeBuffer == null) {
            buffers.removeIf(e -> e.getId() == id);
            return;
        }
        if(id != activeBuffer.getId()){
            buffers.removeIf(e -> e.getId() == id);
            return;
        }
        throw new IllegalStateException("Cannot remove the active FileBuffer.");
    }


    /**
     * Removes all the buffers from the repository. (including the active buffer)
     */
    @Override
    public void removeAllBuffers(){
        activeBuffer = null;
        buffers = new ArrayList<>();
    }

    /**
     * Deletes the active file buffer.
     * DOES NOT DELETE IT FROM THE REPOSITORY.
     */
    @Override
    public void removeActiveBuffer(){
        activeBuffer = null;
    }

    /**
     * @return All file buffers
     */
    @Override
    public List<FileBuffer> getAllBuffers(){
        return buffers;
    }

    /**
     * Moves the active file buffer to the next file buffer.
     */
    @Override
    public void setActiveToNext(){
        int id = getActiveBufferId();
        int nextId = -1;
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                nextId = (i + 1) % buffers.size();
            }
        }
        if(nextId == -1){
            throw new NoSuchElementException("No next buffer found.");
        }
        activeBuffer = getBuffer(nextId);
    }


    /**
     * Moves the active file buffer to the previous file buffer.
     */
    @Override
    public void setActiveToPrevious(){
        int id = getActiveBufferId();
        int prevId = -1;
        for(int i = 0; i < buffers.size(); i++){
            if(buffers.get(i).getId() == id){
                prevId = (i - 1) >= 0 ? i - 1 : buffers.size() - 1;
            }
        }
        if(prevId == -1){
            throw new NoSuchElementException("No next buffer found.");
        }
        activeBuffer = getBuffer(prevId);
    }
}