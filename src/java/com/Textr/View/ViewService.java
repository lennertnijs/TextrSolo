package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
import com.Textr.ViewDrawer.CursorDrawer;
import com.Textr.ViewDrawer.ViewDrawer;
import com.Textr.ViewRepo.IViewRepo;
import com.Textr.ViewRepo.ViewRepo;

import java.util.List;

public final class ViewService {
    private final FileBufferService fileBufferService;
    private final IViewRepo viewRepo;

    public ViewService(FileBufferService fileBufferService){
        this.fileBufferService = fileBufferService;
        this.viewRepo = new ViewRepo();
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
            ViewDrawer.drawView(view, fileBuffer.getText(), fileBufferService.generateStatusBar(fileBuffer));
        }
    }

    public void drawCursor(){
        CursorDrawer.draw(getActiveView().getPosition(), getAnchor(), getActiveBuffer().getCursor());
    }

    /**
     *
     * @param direction
     */
    public void moveInsertionPoint(Direction direction){
        fileBufferService.moveInsertionPoint(direction);
        updateAnchor();
    }

    /**
     * Creates a new line (\r\n on Windows) at the insertion {@link Point} in the active {@link FileBuffer}.
     * Then calls for a possible update of the active {@link View}'s anchor {@link Point}.
     */
    public void createNewline(){
        getActiveBuffer().createNewLine();
        updateAnchor();
    }

    /**
     * Inserts the given character into the active {@link FileBuffer} at the insertion {@link Point}.
     * @param character The input character
     */
    public void insertCharacter(char character){
        getActiveBuffer().insertCharacter(character);
    }

    /**
     * Deletes the character just before the insertion {@link Point} in the active {@link FileBuffer}.
     * Then calls for a possible update of the active {@link View}'s anchor {@link Point}.
     */
    public void deleteChar(){
        getActiveBuffer().removeCharacter();
        updateAnchor();
    }

    /**
     * @return The active {@link FileBuffer}.
     */
    private FileBuffer getActiveBuffer(){
        return fileBufferService.getActiveBuffer();
    }

    /**
     * @return The active {@link View}
     */
    private View getActiveView(){
        return viewRepo.getByBufferId(getActiveBuffer().getId());
    }

    /**
     * @return The anchor {@link Point} of the active {@link FileBuffer}.
     */
    private Point getAnchor(){
        return getActiveView().getAnchor();
    }

    /**
     * Updates the anchor {@link Point} of the active {@link View} to adjust to a possibly new insertion {@link Point}.
     */
    private void updateAnchor(){
        AnchorUpdater.updateAnchor(getAnchor(), getActiveBuffer().getCursor(), getActiveView().getDimensions());
    }
}
