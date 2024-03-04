package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.File.FileService;
import com.Textr.File.FileWriter;
import com.Textr.FileBufferRepo.IFileBufferRepo;
import com.Textr.Init.InputHandlerRepo;
import com.Textr.Validator.Validator;
import com.Textr.View.Direction;

import java.io.IOException;
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

    public void setActiveFileBuffer(int id){
        fileBufferRepo.setActiveBuffer(fileBufferRepo.getBuffer(id));
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

    public void deleteBuffer(int id){
        fileBufferRepo.removeBuffer(id);
    }

    /**
     * Saves the current active buffer to disk, and sets the buffer's state to CLEAN.
     */
    public void saveActiveBuffer(){
        FileBuffer activeBuffer = fileBufferRepo.getActiveBuffer();
        String bufferText = activeBuffer.getText().getText(); // Double ".getText()", might need refactoring
        File bufferedFile = fileService.getFile(activeBuffer.getFileId());
        String bufferUrl = bufferedFile.getUrl();
        try {
            FileWriter.writeToFile(bufferText, bufferUrl);
        } catch (IOException e) {
            // Something went wrong when writing (e.g. file could not be created/opened, writing itself failed...)
            InputHandlerRepo.setAnythingInputHandler();
        } finally {
            // Writing was successful, buffer holds text saved on disk
            activeBuffer.setState(BufferState.CLEAN);
            // TODO: File instance now holds incorrect "original" text, must be updated.
        }
    }

    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the insertion point in the null Direction.");
        getActiveBuffer().moveCursor(direction);
    }
}