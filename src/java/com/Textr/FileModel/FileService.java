package com.Textr.FileModel;

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
        String text;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(url))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(removeNonAscii(line));
                //stringBuilder.append("\n");
            }
            text = String.valueOf(stringBuilder);
        }catch(IOException e){
            System.out.println("The file could not be read.");
        }
        File file = File.builder().path(url).build();
        fileRepository.addFile(file);
    }

    public BufferedReader getBufferedReader(File file) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file.getPath()));
        this.bufferedReader = bufferedReader;
        return bufferedReader;
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
