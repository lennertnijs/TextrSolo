package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Validator.Validator;

import java.util.List;

public final class FileBufferRepo implements IFileBufferRepo {

    private final IActiveFileBufferRepo activeFileBufferRepo;
    private final IAllFileBuffersRepo allFileBufferRepo;

    public FileBufferRepo(){
        this.activeFileBufferRepo = new ActiveFileBufferRepo();
        this.allFileBufferRepo = new AllFileBuffersRepo();
    }

    /**
     * @return True if an active file buffer is set. False otherwise.
     */
    public boolean hasActiveBuffer(){
        return !activeFileBufferRepo.isEmpty();
    }

    /**
     * @return The amount of existing file buffers.
     */
    @Override
    public int getSize(){
        return allFileBufferRepo.getSize();
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
        return allFileBufferRepo.get(id);
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
        allFileBufferRepo.add(fileBuffer);
    }

    /**
     * Returns the id of the active file buffer.
     *
     * @return The id
     */
    @Override
    public int getActiveBufferId() {
        return activeFileBufferRepo.getBufferId();
    }

    /**
     * @return The active file buffer.
     */
    @Override
    public FileBuffer getActiveBuffer(){
        return activeFileBufferRepo.getBuffer();
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
        boolean existsInAllRepo = allFileBufferRepo.contains(fileBuffer.getId());
        if(!existsInAllRepo){
            throw new IllegalStateException("Cannot set the active FileBuffer to a buffer that does not exist.");
        }
        activeFileBufferRepo.setBuffer(fileBuffer);
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
        if(activeFileBufferRepo.isEmpty()){
            allFileBufferRepo.remove(id);
        }else{
            if(id == activeFileBufferRepo.getBufferId()){
                throw new IllegalStateException("Cannot remove the active FileBuffer.");
            }
            allFileBufferRepo.remove(id);
        }
    }


    /**
     * Removes all the buffers from the repository. (including the active buffer)
     */
    @Override
    public void removeAllBuffers(){
        activeFileBufferRepo.deleteBuffer();
        allFileBufferRepo.removeAll();
    }

    /**
     * Deletes the active file buffer.
     * DOES NOT DELETE IT FROM THE REPOSITORY.
     */
    @Override
    public void removeActiveBuffer(){
        activeFileBufferRepo.deleteBuffer();
    }

    /**
     * @return All file buffers
     */
    @Override
    public List<FileBuffer> getAllBuffers(){
        return allFileBufferRepo.getAll();
    }

    /**
     * Moves the active file buffer to the next file buffer.
     */
    @Override
    public void setActiveToNext(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getNext(id));
    }


    /**
     * Moves the active file buffer to the previous file buffer.
     */
    @Override
    public void setActiveToPrevious(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getPrevious(id));
    }
}