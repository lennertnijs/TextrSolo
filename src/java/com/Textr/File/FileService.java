package com.Textr.File;

import com.Textr.Terminal.TerminalService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
     *  DOES NOT YET CHECK NON ASCII CHARACTERS
     * @param url The file's url
     *
     * @return The {@link File}
     * @throws IllegalArgumentException When an error occurs during the reading of the file.
     */
    public File createFile(String url){
        Objects.requireNonNull(url, "Cannot create a File object with a null URL.");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                checkForNonAscii(line);
                stringBuilder.append(line);
                //stringBuilder.append(System.lineSeparator());
            }
            return File.builder().id(atomicInteger.getAndIncrement()).path(url).text(stringBuilder.toString()).build();
        }catch(IOException e){
            TerminalService.enterRawInputMode();
            TerminalService.clearScreen();
            throw new IllegalArgumentException("An error occurred during the reading of a File");
        }
    }

    private void checkForNonAscii(String text){
        for(char c : text.toCharArray()){
            if((c < 32 && c != 10 && c != 13) || 127 <= c){
                throw new IllegalArgumentException("A non-ASCII character was present in the File.");
            }
        }
    }


    public void storeFile(File file){
        Objects.requireNonNull(file, "Cannot store a null file.");
        fileRepo.add(file);
    }


    public void initialiseFile(String url){
        Objects.requireNonNull(url, "Cannot initialise a File with a null URL.");
        File file = createFile(url);
        storeFile(file);
    }

    /**
     * Method to write text changes to the File
     * Method to write the text changes to the .txt file
     */
}
