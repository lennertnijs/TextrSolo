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
     * Fetches and returns the amount of {@link FileBuffer}s.
     *
     * @return The amount of {@link FileBuffer}s.
     */
    @Override
    public int getSize(){
        return allFileBufferRepo.getSize();
    }

    /**
     * Fetches and returns the {@link FileBuffer} with the given id.
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
     * Fetches and returns the id of the active {@link FileBuffer}.
     *
     * @return The id
     */
    @Override
    public int getActiveBufferId() {
        return activeFileBufferRepo.getBufferId();
    }

    /**
     * Fetches and returns the active {@link FileBuffer}.
     *
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
        activeFileBufferRepo.setBuffer(fileBuffer);
    }

    /**
     * Removes the {@link FileBuffer} with the given id. If no match is found, does nothing.
     * DOES NOT ACCOUNT FOR ACTIVE VIEW REMOVAL YET
     * @param id The id
     */
    @Override
    public void removeBuffer(int id) {
        if(id == activeFileBufferRepo.getBufferId()){
            throw new IllegalStateException("Cannot remove the active FileBuffer.");
        }
        allFileBufferRepo.remove(id);
    }

    /**
     * Fetches and returns all the {@link FileBuffer}s.
     *
     * @return All {@link FileBuffer}s
     */
    @Override
    public List<FileBuffer> getAllBuffers(){
        return allFileBufferRepo.getAll();
    }

    /**
     * Moves the active {@link FileBuffer} to the next.
     */
    @Override
    public void setActiveToNext(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getNext(id));
    }


    /**
     * Moves the active {@link FileBuffer} to the previous.
     */
    @Override
    public void setActiveToPrevious(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getPrevious(id));
    }
}
