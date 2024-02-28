package com.Textr.FileBufferRepo;

import com.Textr.FileBuffer.FileBuffer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FileBufferRepo implements IFileBufferRepo {

    private final IActiveFileBufferRepo activeFileBufferRepo;
    private final IAllFileBuffersRepo allFileBufferRepo;

    public FileBufferRepo(){
        this.activeFileBufferRepo = new ActiveFileBufferRepo();
        this.allFileBufferRepo = new AllFileBuffersRepo();
    }


    @Override
    public int getSize(){
        return allFileBufferRepo.getSize();
    }

    @Override
    public Optional<FileBuffer> getBuffer(int id){
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
        Optional<FileBuffer> optional = activeFileBufferRepo.getBuffer();
        if(optional.isEmpty()){
            throw new NoSuchElementException("No active FileBuffer was found.");
        }
        return optional.get();
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
    public FileBuffer nextBuffer(int id){
        return allFileBufferRepo.next(id);
    }

    @Override
    public FileBuffer prevBuffer(int id){
        return allFileBufferRepo.prev(id);
    }
}
