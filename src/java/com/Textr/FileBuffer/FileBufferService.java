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

    private FileBuffer createFileBuffer(int fileId, String text){
        if(fileId < 0){
            throw new IllegalArgumentException("Cannot create a FileBuffer with a negative fileId.");
        }
        Objects.requireNonNull(text, "Cannot create a FileBuffer because the File's text is null.");
        int uniqueId = atomicInteger.getAndIncrement();
        return FileBuffer.builder().id(uniqueId).fileId(fileId).bufferText(text).insertionIndex(0).state(State.CLEAN).build();
    }

    private void storeFileBuffer(FileBuffer fileBuffer){
        Objects.requireNonNull(fileBuffer, "Cannot store a null FileBuffer.");
        fileBufferRepo.add(fileBuffer);
    }

    public void initialiseFileBuffer(File file){
        Objects.requireNonNull(file, "Cannot initialise a fileBuffer because the File is null.");
        FileBuffer fileBuffer = createFileBuffer(file.getId(), file.getText());
        storeFileBuffer(fileBuffer);
    }

    public List<FileBuffer> getAllFileBuffers(){
        return fileBufferRepo.getAll();
    }

    public FileBuffer getFileBuffer(int id){
        if(fileBufferRepo.get(id).isPresent()){
            return fileBufferRepo.get(id).get();
        }
        throw new NoSuchElementException("No FileBuffer was found for the given id.");
    }

    /**
     * Needs methods to do the following:
     * 1) create a FileBuffer for a given File
     * 2) store a FileBuffer for a given File
     * 3) change a FileBuffer's buffer text (add input) -> should only allow ASCII
     * 4) change a FileBuffer's insertion point index
     * 5) change a FileBuffer's State
     * 6) save the FileBuffer to the File & (or?) store in the external .txt File
     * 7)
     */
}
