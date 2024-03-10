package com.Textr.File;

import com.Textr.Util.Validator;

import java.util.List;

/**
 * The Service class for the {@link File} object.
 * Used as an API to communicate with these objects.
 */
public final class FileService {

    private final IFileRepo fileRepo;

    /**
     * Constructor for a FileService.
     * @param fileRepo The {@link File} repository.
     */
    public FileService(IFileRepo fileRepo){
        Validator.notNull(fileRepo, "Cannot create a FileService with a null FileRepository.");
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
     * Saves the given data to disk, at the location specified by the File with given fileID.
     * @param text    The String to write to disk
     * @param fileId  The relevant fileId to write to.
     */
    public void saveToFile(String text, int fileId){
        File file = getFile(fileId);
        FileWriter.write(text, file.getUrl());
        file.setText(text);
    }

    /**
     * Initializes a {@link File} from the given url.
     * If successfully created, stores the File.
     * @param url The url. Cannot be null.
     *
     * @throws IllegalArgumentException If the url is null.
     */
    public void initialiseFile(String url){
        Validator.notNull(url, "Cannot initialise a File with a null url.");
        File file = File.create(url, FileReader.readContents(url));
        fileRepo.add(file);
    }
}
