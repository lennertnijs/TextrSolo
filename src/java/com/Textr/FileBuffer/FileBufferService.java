package com.Textr.FileBuffer;

import com.Textr.File.File;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FileBufferService {

    private final FileBufferRepo fileBufferRepo;
    private final AtomicInteger atomicInteger = new AtomicInteger();

    public FileBufferService(){
        this.fileBufferRepo = new FileBufferRepo();
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
        ActiveFileBufferRepo.setBuffer(fileBuffer);
    }

    private FileBuffer createFileBuffer(int fileId, String text){
        if(fileId < 0){
            throw new IllegalArgumentException("Cannot create a FileBuffer with a negative fileId.");
        }
        Objects.requireNonNull(text, "Cannot create a FileBuffer because the File's text is null.");
        int uniqueId = atomicInteger.getAndIncrement();
        InsertionPoint point = InsertionPoint.create(0,0);
        return FileBuffer.builder().id(uniqueId).fileId(fileId).bufferText(text.split(System.lineSeparator())).insertionPosition(point).state(BufferState.CLEAN).build();
    }

    private void storeFileBuffer(FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer, "Cannot store a null FileBuffer.");
        fileBufferRepo.add(fileBuffer);
    }

    public List<FileBuffer> getAllFileBuffers(){
        return fileBufferRepo.getAll();
    }

    public int getAmountOfFileBuffers(){
        return fileBufferRepo.getSize();
    }

    public FileBuffer getFileBuffer(int id){
        if(fileBufferRepo.get(id).isPresent()){
            return fileBufferRepo.get(id).get();
        }
        throw new NoSuchElementException("No FileBuffer was found for the given id.");
    }

    public boolean isActive(int id){
        return ActiveFileBufferRepo.getBufferId() == id;
    }

    public void moveActiveBufferToNext(){
        int id = ActiveFileBufferRepo.getBufferId();
        ActiveFileBufferRepo.setBuffer(fileBufferRepo.next(id));
    }

    public void moveActiveBufferToPrev(){
        int id = ActiveFileBufferRepo.getBufferId();
        ActiveFileBufferRepo.setBuffer(fileBufferRepo.prev(id));
    }

    public FileBuffer getActiveBuffer(){
        return ActiveFileBufferRepo.getBuffer();
    }

    public void moveInsertionPointRight(){
        ActiveFileBufferRepo.getBuffer().getInsertionPosition().incrementX();
    }
}
