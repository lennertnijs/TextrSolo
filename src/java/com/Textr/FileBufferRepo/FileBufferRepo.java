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
     *
     * @param id
     * @return
     */
    @Override
    public FileBuffer getBuffer(int id){
        return allFileBufferRepo.get(id);
    }
    @Override
    public void addBuffer(FileBuffer fileBuffer) {
        allFileBufferRepo.add(fileBuffer);
    }

    @Override
    public int getActiveBufferId() {
        return activeFileBufferRepo.getBufferId();
    }

    @Override
    public FileBuffer getActiveBuffer(){
        return activeFileBufferRepo.getBuffer();
    }

    @Override
    public void setActiveBuffer(FileBuffer fileBuffer) {
        activeFileBufferRepo.setBuffer(fileBuffer);
    }

    @Override
    public void removeBuffer(int id) {
        allFileBufferRepo.remove(id);
    }

    @Override
    public List<FileBuffer> getAllBuffers(){
        return allFileBufferRepo.getAll();
    }

    @Override
    public void setActiveToNext(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getNext(id));
    }

    @Override
    public void setActiveToPrevious(){
        int id = activeFileBufferRepo.getBufferId();
        activeFileBufferRepo.setBuffer(allFileBufferRepo.getPrevious(id));
    }
}
