package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.FileBufferRepo.FileBufferRepo;
import com.Textr.FileBufferRepo.IFileBufferRepo;

import java.util.List;

public class FileBufferService {

    private final IFileBufferRepo fileBufferRepository;

    public FileBufferService(){
        this.fileBufferRepository = new FileBufferRepo();
    }

    public void initialisePassiveFileBuffer(File file){
        fileBufferRepository.addBuffer(FileBufferCreator.create(file));
    }

    public void initialiseActiveFileBuffer(File file){
        FileBuffer fileBuffer = FileBufferCreator.create(file);
        fileBufferRepository.addBuffer(fileBuffer);
        fileBufferRepository.setActiveBuffer(fileBuffer);
    }

    public List<FileBuffer> getAllFileBuffers(){
        return fileBufferRepository.getAllBuffers();
    }

    public int getAmountOfFileBuffers(){
        return fileBufferRepository.getSize();
    }

    public FileBuffer getFileBuffer(int id){
        return fileBufferRepository.getBuffer(id);
    }

    public void moveActiveBufferToNext(){
        fileBufferRepository.setActiveToNext();
    }

    public void moveActiveBufferToPrev(){
        fileBufferRepository.setActiveToPrevious();
    }

    public FileBuffer getActiveBuffer(){
        return fileBufferRepository.getActiveBuffer();
    }

    public void moveInsertionPointRight(){
        fileBufferRepository.getActiveBuffer().moveInsertionPointRight();
    }

    public void moveInsertionPointLeft(){
        fileBufferRepository.getActiveBuffer().moveInsertionPointLeft();
    }

    public void moveInsertionPointDown(){
        fileBufferRepository.getActiveBuffer().moveInsertionPointDown();
    }

    public void moveInsertionPointUp(){
        fileBufferRepository.getActiveBuffer().moveInsertionPointUp();
    }
}