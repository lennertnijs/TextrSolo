package com.Textr.File;

import com.Textr.FileRepo.FileRepo;
import com.Textr.FileRepo.IFileRepo;

import java.util.List;

public final class FileService {

    private final IFileRepo fileRepo;

    public FileService(){
        this.fileRepo = new FileRepo();
    }

    public File getFile(int id){
        return fileRepo.get(id);
    }

    public List<File> getAllFiles(){
        return fileRepo.getAll();
    }

    public void initialiseFile(String url){
        File file = File.create(url, FileReader.readContents(url));;
        fileRepo.add(file);
    }
}
