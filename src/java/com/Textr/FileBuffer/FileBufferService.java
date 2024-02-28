package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.FileBufferRepo.FileBufferRepository;
import com.Textr.FileBufferRepo.IFileBufferRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FileBufferService {

    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final IFileBufferRepository fileBufferRepository;

    public FileBufferService(){
        this.fileBufferRepository = new FileBufferRepository();
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
        int uniqueId = atomicInteger.getAndIncrement();
        InsertionPoint point = InsertionPoint.create(0,0);
        return FileBuffer.builder().id(uniqueId).fileId(fileId).bufferText(text.split(System.lineSeparator())).insertionPosition(point).state(BufferState.CLEAN).build();
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
        if(fileBufferRepository.getBuffer(id).isPresent()){
            return fileBufferRepository.getBuffer(id).get();
        }
        throw new NoSuchElementException("No FileBuffer was found for the given id.");
    }

    public boolean isActive(int id){
        return fileBufferRepository.getActiveBufferId() == id;
    }

    public void moveActiveBufferToNext(){
        int id = fileBufferRepository.getActiveBufferId();
        fileBufferRepository.setActiveBuffer(fileBufferRepository.nextBuffer(id));
    }

    public void moveActiveBufferToPrev(){
        int id = fileBufferRepository.getActiveBufferId();
        fileBufferRepository.setActiveBuffer(fileBufferRepository.prevBuffer(id));
    }

    public FileBuffer getActiveBuffer(){
        return fileBufferRepository.getActiveBuffer();
    }

    public void moveInsertionPointRight(){
        fileBufferRepository.getActiveBuffer().getInsertionPosition().incrementX();
    }
}
