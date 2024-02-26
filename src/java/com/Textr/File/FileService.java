package com.Textr.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FileService {

    private final FileRepo fileRepo;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public FileService(){
        this.fileRepo = new FileRepo();
    }

    public List<File> getAllFiles(){
        return fileRepo.getAll();
    }

    /**
     * Creates a {@link File}. Uses a {@link BufferedReader} to read from the given URL.
     * If an IOException occurs during the reading, will return an empty Optional.
     * If the {@link File} is successfully created, returns an Optional of this {@link File}.
     *  DOES NOT YET CHECK NON ASCII CHARACTERS
     *
     * @param url The file's url
     *
     * @return Optional.of(File) if successful, Optional.empty() if not.
     */
    public Optional<File> createFile(String url){
        Objects.requireNonNull(url, "Cannot create a File object with a null URL.");
        Optional<File> optionalFile;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
                //stringBuilder.append(System.lineSeparator());
            }
            File file = File.builder().id(atomicInteger.getAndIncrement()).path(url).text(stringBuilder.toString()).build();
            optionalFile = Optional.of(file);
        }catch(IOException e){
            optionalFile = Optional.empty();
        }
        return optionalFile;
    }


    public void storeFile(File file){
        Objects.requireNonNull(file, "Cannot store a null file.");
        fileRepo.add(file);
    }


    public void initialiseFile(String url){
        Objects.requireNonNull(url, "Cannot initialise a File with a null URL.");
        Optional<File> optionalFile = createFile(url);
        if(optionalFile.isEmpty()){
            return;
        }
        storeFile(optionalFile.get());
    }

    /**
     * Method to write text changes to the File
     * Method to write the text changes to the .txt file
     */
}
