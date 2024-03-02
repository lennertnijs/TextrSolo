package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Util.Point;
import com.Textr.Validator.Validator;
import com.Textr.ViewDrawer.CursorDrawer;
import com.Textr.ViewDrawer.ViewDrawer;
import com.Textr.ViewLayout.ViewLayoutInitializer;
import com.Textr.ViewRepo.IViewRepo;

import java.util.List;

public final class ViewService {
    private final FileBufferService fileBufferService;
    private final IViewRepo viewRepo;

    /**
     * Constructor for the {@link ViewService}.
     * @param fileBufferService The service to be used to talk to the file buffers.
     * @param viewRepo The repository to be used for storage and retrieval of views.
     */
    public ViewService(FileBufferService fileBufferService, IViewRepo viewRepo){
        this.fileBufferService = fileBufferService;
        this.viewRepo = viewRepo;
    }

    /**
     * Generates a vertically stacked layout of views for the currently existing buffers and stores them.
     */
    public void initialiseViewsVertical(){
        List<View> views = ViewLayoutInitializer.generateVerticallyStackedViews(fileBufferService.getAllFileBuffers());
        viewRepo.addAll(views);
    }

    /**
     * Draws all the currently existing views to the terminal screen.
     */
    public void drawAllViews(){
        for(View view: viewRepo.getAll()){
            FileBuffer fileBuffer = fileBufferService.getFileBuffer(view.getFileBufferId());
            String statusBar = fileBufferService.generateStatusBar(fileBuffer);
            ViewDrawer.draw(view, fileBuffer.getText(), statusBar);
        }
    }

    /**
     * Draws the terminal's cursor at the active buffer's cursor point.
     */
    public void drawCursor(){
        CursorDrawer.draw(getActiveView().getPosition(), getAnchor(), getActiveBuffer().getCursor());
    }

    /**
     * Moves the cursor of the active buffer by 1 unit in the given direction.
     * Then calls for a possible update on the anchor point of the active view.
     * @param direction The direction. Cannot be null.
     */
    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the cursor in the null direction.");
        fileBufferService.moveCursor(direction);
        updateAnchor();
    }

    /**
     * Creates a new line (\r\n on Windows) at the insertion point in the active buffer.
     * Then calls for a possible update on the anchor point of the active view.
     */
    public void createNewline(){
        getActiveBuffer().createNewLine();
        updateAnchor();
    }

    /**
     * Inserts the given character at the cursor point of the active buffer.
     * @param character The input character
     */
    public void insertCharacter(char character){
        getActiveBuffer().insertCharacter(character);
    }

    /**
     * Deletes the character just before the cursor of the active buffer.
     * Then calls for a possible update on the anchor point of the active view.
     */
    public void deleteChar(){
        getActiveBuffer().removeCharacter();
        updateAnchor();
    }

    public void deleteNextChar(){
        getActiveBuffer().removeNextCharacter();
    }


    /**
     * @return The active buffer.
     */
    private FileBuffer getActiveBuffer(){
        return fileBufferService.getActiveBuffer();
    }

    /**
     * @return The active view.
     */
    private View getActiveView(){
        return viewRepo.getByBufferId(getActiveBuffer().getId());
    }

    /**
     * @return The anchor point of the active view.
     */
    private Point getAnchor(){
        return getActiveView().getAnchor();
    }

    /**
     * Updates the anchor point of the active buffer to possible changes to the cursor point.
     */
    private void updateAnchor(){
        AnchorUpdater.updateAnchor(getAnchor(), getActiveBuffer().getCursor(), getActiveView().getDimensions());
    }
}
