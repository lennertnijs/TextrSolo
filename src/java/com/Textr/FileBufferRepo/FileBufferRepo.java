package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;

public final class FileBufferRepo implements IFileBufferRepo {

    private final IActiveFileBufferRepo activeFileBufferRepo;
    private final IAllFileBuffersRepo allFileBufferRepo;

    public FileBufferRepo(){
        this.activeFileBufferRepo = new ActiveFileBufferRepo();
        this.allFileBufferRepo = new AllFileBuffersRepo();
    }

    /**
     * @return True if an active {@link FileBuffer} is set. False otherwise.
     */
    public boolean hasActiveBuffer(){
        return !activeFileBufferRepo.isEmpty();
    }

    /**
     * @return The amount of existing {@link FileBuffer}s.
     */
    @Override
    public int getSize(){
        return allFileBufferRepo.getSize();
    }

    /**
     * Returns the {@link FileBuffer} with the given id.
     * @param id The id
     *
     * @return The {@link FileBuffer}
     *
     */
    @Override
    public FileBuffer getBuffer(int id){
        return allFileBufferRepo.get(id);
    }

    /**
     * Stores the given {@link FileBuffer}.
     * @param fileBuffer The {@link FileBuffer}. Cannot be null
     */
    @Override
    public void addBuffer(FileBuffer fileBuffer) {
        allFileBufferRepo.add(fileBuffer);
    }

    /**
     * Returns the id of the active {@link FileBuffer}.
     *
     * @return The id
     */
    @Override
    public int getActiveBufferId() {
        return activeFileBufferRepo.getBufferId();
    }

    /**
     * @return The active {@link FileBuffer}
     */
    @Override
    public FileBuffer getActiveBuffer(){
        return activeFileBufferRepo.getBuffer();
    }

    /**
     * Sets the active {@link FileBuffer} to the given {@link FileBuffer}.
     * @param fileBuffer The new active {@link FileBuffer}. Cannot be null
     */
    @Override
    public void setActiveBuffer(FileBuffer fileBuffer) {
        boolean existsInAllRepo = allFileBufferRepo.contains(fileBuffer.getId());
        if(!existsInAllRepo){
            throw new IllegalStateException("Cannot set the active FileBuffer to a buffer that does not exist.");
        }
        activeFileBufferRepo.setBuffer(fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id.
     * If no match is found, does nothing.
     * @param id The id
     *
     * @throws IllegalStateException If an attempt is made to remove the active {@link FileBuffer} indirectly.
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


    @Override
    public void removeAllBuffers(){
        activeFileBufferRepo.deleteBuffer();
        allFileBufferRepo.removeAll();
    }
    /**
     * Deletes the active {@link FileBuffer}.
     * DOES NOT DELETE IT FROM THE TOTAL REPOSITORY.
     */
    @Override
    public void removeActiveBuffer(){
        activeFileBufferRepo.deleteBuffer();
    }

    /**
     * @return All {@link FileBuffer}s
     */
    @Override
    public List<FileBuffer> getAllBuffers(){
        return allFileBufferRepo.getAll();
    }

    /**
     * Moves the active {@link FileBuffer} to the next {@link FileBuffer}.
     */
    @Override
    public void setActiveToNext(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getNext(id));
    }


    /**
     * Moves the active {@link FileBuffer} to the previous {@link FileBuffer}.
     */
    @Override
    public void setActiveToPrevious(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getPrevious(id));
    }
}