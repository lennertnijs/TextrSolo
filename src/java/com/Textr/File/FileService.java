package com.Textr.File;

import com.Textr.FileRepo.FileRepo;
import com.Textr.FileRepo.IFileRepo;
import com.Textr.Validator.Validator;

import java.util.List;

public final class FileService {

    private final IFileRepo fileRepo;

    public FileService(IFileRepo fileRepo){
        this.fileRepo = fileRepo;
    }

    /**
     * Returns the File with the given id.
     * @param id The id
     * @return The File
     */
    public File getFile(int id){
        return fileRepo.get(id);
    }

    /**
     * Returns all existing Files.
     * @return All the Files
     */
    public List<File> getAllFiles(){
        return fileRepo.getAll();
    }

    /**
     * Initializes a {@link File} from the given url.
     * If successfully created, stores the File.
     * @param url The url. Cannot be null.
     */
    public void initialiseFile(String url){
        Validator.notNull(url, "Cannot initialise a File with a null url.");
        File file = File.create(url, FileReader.readContents(url));;
        fileRepo.add(file);
    }
}
