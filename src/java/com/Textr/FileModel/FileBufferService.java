package com.Textr.FileModel;

public class FileBufferService {

    private final FileBufferRepo fileBufferRepo;

    public FileBufferService(){
        this.fileBufferRepo = new FileBufferRepo();
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
