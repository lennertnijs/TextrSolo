package com.Textr.FileModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileService {

    private final FileRepository fileRepository;
    private BufferedReader bufferedReader;
    public FileService(){
        this.fileRepository = new FileRepository();
    }

    /**
     * Methods necessary:
     * 1) create a File
     * 2) Store a File
     * 3) remove a File
     * 4) get all Files()
     * 5) ???
     */



    public List<File> getAllFiles(){
        return fileRepository.getFiles();
    }

    public void addFileToRepo(File file){
        fileRepository.addFile(Objects.requireNonNull(file, "Cannot add a null file"));
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
                stringBuilder.append(removeNonAscii(line));
                //stringBuilder.append(System.lineSeparator());
            }
            File file = File.builder().id(0).path(url).text(stringBuilder.toString()).build();
            optionalFile = Optional.of(file);
        }catch(IOException e){
            optionalFile = Optional.empty();
        }
        return optionalFile;
    }


    public void storeFile(File file){
        Objects.requireNonNull(file, "Cannot store a null file.");
        fileRepository.addFile(file);
    }


    public void initialiseFile(String url){
        Objects.requireNonNull(url, "Cannot initialise a File with a null URL.");
        Optional<File> optionalFile = createFile(url);
        if(optionalFile.isEmpty()){
            return;
        }
        fileRepository.addFile(optionalFile.get());
    }


    private String removeNonAscii(String line){
        StringBuilder builder = new StringBuilder();
        for(int i =0; i < line.length(); i++){
            char c = line.charAt(i);
            if(c >= 32 && c <= 126 || c == 10 || c == 13){
                builder.append(c);
            }
        }
        return String.valueOf(builder);
    }
}
