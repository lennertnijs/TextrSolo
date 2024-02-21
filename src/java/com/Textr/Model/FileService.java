package com.Textr.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class FileService {

    private final FileRepository fileRepository;
    private BufferedReader bufferedReader;
    public FileService(){
        this.fileRepository = new FileRepository();
    }

    public List<File> getAllFiles(){
        return fileRepository.getFiles();
    }

    public void addFileToRepo(File file){
        fileRepository.addFile(Objects.requireNonNull(file, "Cannot add a null file"));
    }

    public void createAndStoreFile(String url){
        File file = File.builder().url(url).build();
        fileRepository.addFile(file);
    }

    public BufferedReader getBufferedReader(File file) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file.getUrl()));
        this.bufferedReader = bufferedReader;
        return bufferedReader;
    }
}
