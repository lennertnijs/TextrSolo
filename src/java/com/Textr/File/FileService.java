package com.Textr.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FileService {

    private final IFileRepo fileRepo;

    public FileService(){
        this.fileRepo = new FileRepo();
    }

    public List<File> getAllFiles(){
        return fileRepo.getAll();
    }

    public void initialiseFile(String url){
        Objects.requireNonNull(url, "Cannot initialise a File with a null URL.");
        File file = createFile(url);
        storeFile(file);
    }

    /**
     * Creates a {@link File} for the given URL. Uses a {@link BufferedReader}.
     * @param url The file's url
     *
     * @return The {@link File}
     * @throws IllegalArgumentException When an error occurs during the reading of the file, or when the file contains
     *                                  non-ASCII characters.
     */
    public File createFile(String url){
        Objects.requireNonNull(url, "Cannot create a File object with a null URL.");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                checkForNonAscii(line);
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            return File.create(url, stringBuilder.toString());
        }catch(IOException e){
            throw new IllegalArgumentException("An error occurred during the reading of a File");
        }
    }

    private void checkForNonAscii(String text){
        for(char c : text.toCharArray()){
            boolean isNonASCII = (c < 32 && c != 10 && c != 13) || 127 <= c;
            if(isNonASCII){
                throw new IllegalArgumentException("A non-ASCII character was present in the File.");
            }
        }
    }

    public void storeFile(File file){
        Objects.requireNonNull(file, "Cannot store a null file.");
        fileRepo.add(file);
    }

    public File getFile(int id){
        return fileRepo.get(id);
    }
}
