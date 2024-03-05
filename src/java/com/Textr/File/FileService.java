package com.Textr.File;

import com.Textr.FileRepo.IFileRepo;
import com.Textr.Validator.Validator;

import java.io.IOException;
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
     *
     * @param data    The String to write to disk
     * @param fileID  The relevant fileID to write to.
     * @throws IOException when file could not be opened or created, or something went wrong during writing.
     */
    public void saveToFile(String data, int fileID) throws IOException {
        File file = getFile(fileID);
        String bufferUrl = file.getUrl();

        // Possible IOException:
        FileWriter.writeToFile(data, bufferUrl);

        // If this code is reached, writing was successful: update File text
        file.setText(data);
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
