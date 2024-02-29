package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.FileBufferRepo.FileBufferRepo;
import com.Textr.FileBufferRepo.IFileBufferRepo;

import java.util.List;
import java.util.Objects;

public class FileBufferService {

    private final IFileBufferRepo fileBufferRepository;

    public FileBufferService(){
        this.fileBufferRepository = new FileBufferRepo();
    }

    public void initialisePassiveFileBuffer(File file){
        Objects.requireNonNull(file, "Cannot initialise a fileBuffer because the File is null.");
        FileBuffer fileBuffer = createFileBuffer(file.getId(), file.getText());
        storeFileBuffer(fileBuffer);
    }

    public void initialiseActiveFileBuffer(File file){
        Objects.requireNonNull(file, "Cannot initialise a fileBuffer because the File is null.");
        FileBuffer fileBuffer = createFileBuffer(file.getId(), file.getText());
        storeFileBuffer(fileBuffer);
        fileBufferRepository.setActiveBuffer(fileBuffer);
    }

    private FileBuffer createFileBuffer(int fileId, String text){
        if(fileId < 0){
            throw new IllegalArgumentException("Cannot create a FileBuffer with a negative fileId.");
        }
        Objects.requireNonNull(text, "Cannot create a FileBuffer because the File's text is null.");
        return FileBuffer.builder().fileId(fileId).bufferText(Text.create(text)).insertionPosition(Point.create(0,0)).state(BufferState.CLEAN).build();
    }

    private void storeFileBuffer(FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer, "Cannot store a null FileBuffer.");
        fileBufferRepository.addBuffer(fileBuffer);
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

    public boolean isActive(int id){
        return fileBufferRepository.getActiveBufferId() == id;
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