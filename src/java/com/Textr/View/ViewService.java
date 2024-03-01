package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
import com.Textr.Validator.Validator;
import com.Textr.ViewDrawer.CursorDrawer;
import com.Textr.ViewDrawer.IViewDrawer;
import com.Textr.ViewDrawer.ViewDrawer;
import com.Textr.ViewRepo.IViewRepo;
import com.Textr.ViewRepo.ViewRepo;

import java.util.List;

public final class ViewService {
    private final FileBufferService fileBufferService;
    private final FileService fileService;
    private final IViewRepo viewRepo;
    private final IViewDrawer viewDrawer;

    public ViewService(FileBufferService fileBufferService, FileService fileService){
        this.fileBufferService = fileBufferService;
        this.fileService = fileService;
        this.viewRepo = new ViewRepo();
        this.viewDrawer = new ViewDrawer();
    }


    public void initialiseViewsVertical(){
        List<View> views = ViewLayoutInitializer.generateVerticallyStackedViews(fileBufferService.getAllFileBuffers());
        for(View view : views){
            viewRepo.add(view);
        }
    }

    public void drawAllViews(){
        for(View view: viewRepo.getAll()){
            FileBuffer fileBuffer = fileBufferService.getFileBuffer(view.getFileBufferId());
            viewDrawer.drawView(view, fileBuffer.getBufferText(), generateStatusBar(fileBuffer));
        }
    }

    private String generateStatusBar(FileBuffer fileBuffer){
        return String.format("File path: %s - Lines: %d - Characters: %d - InsertionPoint: %s - State: %s",
               fileService.getFile(fileBuffer.getFileId()).getUrl(), fileBuffer.getBufferText().getAmountOfLines(),
                fileBuffer.getBufferText().getAmountOfChars(), fileBuffer.getInsertionPosition(), fileBuffer.getState());
    }

    public void drawCursor(){
        CursorDrawer.draw(getActiveView().getPosition(), getAnchor(), getInsertionPoint());
    }

    /**
     * Moves the insertion {@link Point} of the active {@link FileBuffer} by 1 in the given {@link Direction}.
     * After moving the insertion {@link Point}, also adjusts the anchor {@link Point} appropriately.
     * @param direction The direction
     *
     * @throws IllegalArgumentException If the given {@link Direction} is null.
     */
    public void moveInsertionPoint(Direction direction){
        Validator.notNull(direction, "Cannot move the insertion point in a null Direction.");
        switch(direction){
            case UP -> fileBufferService.moveInsertionPointUp();
            case RIGHT -> fileBufferService.moveInsertionPointRight();
            case DOWN -> fileBufferService.moveInsertionPointDown();
            case LEFT -> fileBufferService.moveInsertionPointLeft();
        }
        AnchorUpdater.updateAnchor(getAnchor(), getInsertionPoint(), getActiveViewDimensions());
    }

    public void createNewline(){
        getActiveBuffer().createNewLine();
        AnchorUpdater.updateAnchor(getAnchor(), getInsertionPoint(), getActiveViewDimensions());
    }

    public void insertCharacter(char character){
        getActiveBuffer().insertCharacter(character);
    }

    public void deleteChar(){
        fileBufferService.getActiveBuffer().removeCharacter();
        AnchorUpdater.updateAnchor(getAnchor(), getInsertionPoint(), getActiveView().getDimensions());
    }

    private View getActiveView(){
        return viewRepo.getByBufferId(fileBufferService.getActiveBuffer().getId());
    }

    private Point getAnchor(){
        return getActiveView().getAnchor();
    }

    private Point getInsertionPoint(){
        return fileBufferService.getActiveBuffer().getInsertionPosition();
    }

    private Dimension2D getActiveViewDimensions(){
        return getActiveView().getDimensions();
    }

    private FileBuffer getActiveBuffer(){
        return fileBufferService.getActiveBuffer();
    }
}
