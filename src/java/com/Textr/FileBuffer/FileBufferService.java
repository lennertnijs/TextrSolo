package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.FileBufferRepo.FileBufferRepo;
import com.Textr.FileBufferRepo.IFileBufferRepo;

import java.util.List;

/**
 * Class used to present a bunch of FileBuffer methods to the outside world. (probably create an interface for this)
 */
public final class FileBufferService {

    private final IFileBufferRepo fileBufferRepo;

    public FileBufferService(){
        this.fileBufferRepo = new FileBufferRepo();
    }

    public void initialisePassiveFileBuffer(File file){
        fileBufferRepo.addBuffer(FileBufferCreator.create(file));
    }

    public void initialiseActiveFileBuffer(File file){
        FileBuffer fileBuffer = FileBufferCreator.create(file);
        fileBufferRepo.addBuffer(fileBuffer);
        fileBufferRepo.setActiveBuffer(fileBuffer);
    }

    public List<FileBuffer> getAllFileBuffers(){
        return fileBufferRepo.getAllBuffers();
    }

    public int getAmountOfFileBuffers(){
        return fileBufferRepo.getSize();
    }

    public FileBuffer getFileBuffer(int id){
        return fileBufferRepo.getBuffer(id);
    }

    public void moveActiveBufferToNext(){
        fileBufferRepo.setActiveToNext();
    }

    public void moveActiveBufferToPrev(){
        fileBufferRepo.setActiveToPrevious();
    }

    public FileBuffer getActiveBuffer(){
        return fileBufferRepo.getActiveBuffer();
    }

    public void moveInsertionPointRight(){
        fileBufferRepo.getActiveBuffer().moveInsertionPointRight();
    }

    public void moveInsertionPointLeft(){
        fileBufferRepo.getActiveBuffer().moveInsertionPointLeft();
    }

    public void moveInsertionPointDown(){
        fileBufferRepo.getActiveBuffer().moveInsertionPointDown();
    }

    public void moveInsertionPointUp(){
        fileBufferRepo.getActiveBuffer().moveInsertionPointUp();
    }
}