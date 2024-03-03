package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.File.FileService;
import com.Textr.FileBufferRepo.FileBufferRepo;
import com.Textr.FileBufferRepo.IFileBufferRepo;
import com.Textr.Validator.Validator;
import com.Textr.View.Direction;

import java.util.List;

/**
 * The service class for the {@link FileBuffer} objects.
 * Used as an API to communicate with these objects.
 */
public final class FileBufferService {

    private final IFileBufferRepo fileBufferRepo;
    private final FileService fileService;

    /**
     * Constructor for a FileBufferService.
     * @param fileService The {@link File}'s service API.
     * @param fileBufferRepo The {@link FileBuffer} repository.
     *
     * @throws IllegalArgumentException If the file service or the file buffer repository is null.
     */
    public FileBufferService(FileService fileService, IFileBufferRepo fileBufferRepo){
        Validator.notNull(fileService, "Cannot create a FileBufferService with a null FileService.");
        Validator.notNull(fileBufferRepo, "Cannot create a FileBufferService with a null FileBufferRepository");
        this.fileBufferRepo = fileBufferRepo;
        this.fileService = fileService;
    }

    public String generateStatusBar(FileBuffer buffer){
        Validator.notNull(buffer, "Cannot generate a status bar for a null FileBuffer.");
        return String.format("File path: %s - Lines: %d - Characters: %d - InsertionPoint: %s - State: %s",
                fileService.getFile(buffer.getId()).getUrl(), buffer.getText().getAmountOfLines(),
                buffer.getText().getAmountOfChars(), buffer.getCursor(), buffer.getState());
    }

    public void initialisePassiveFileBuffer(File file){
        fileBufferRepo.addBuffer(FileBufferCreator.create(file));
    }

    public void initialiseActiveFileBuffer(File file){
        FileBuffer fileBuffer = FileBufferCreator.create(file);
        fileBufferRepo.addBuffer(fileBuffer);
        fileBufferRepo.setActiveBuffer(fileBuffer);
    }

    public List<FileBuffer> getAllFileBuffers(){
        return fileBufferRepo.getAllBuffers();
    }

    public FileBuffer getFileBuffer(int id){
        return fileBufferRepo.getBuffer(id);
    }

    public void moveActiveBufferToNext(){
        fileBufferRepo.setActiveToNext();
    }

    public void moveActiveBufferToPrev(){
        fileBufferRepo.setActiveToPrevious();
    }

    public FileBuffer getActiveBuffer(){
        return fileBufferRepo.getActiveBuffer();
    }

    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the insertion point in the null Direction.");
        getActiveBuffer().moveCursor(direction);
    }
}