package com.Textr.View;

import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Init.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.Tree.LayoutGenerator;
import com.Textr.Tree.ViewTreeRepo;
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
     * Constructor for the ViewService.
     * @param fileBufferService The {@link FileBuffer}'s service API.
     * @param viewRepo The {@link View} repository.
     */
    public ViewService(FileBufferService fileBufferService, IViewRepo viewRepo){
        this.fileBufferService = fileBufferService;
        this.viewRepo = viewRepo;
    }

    /**
     * Generates a vertically stacked layout of views for the currently existing buffers and stores them.
     * Presumes no views are currently stored.
     */
    public void initialiseViewsVertical(){
        List<View> views = ViewLayoutInitializer.generateVerticallyStackedViews(fileBufferService.getAllFileBuffers());
        viewRepo.addAll(views);
    }

    public void generateViews(){
        LayoutGenerator.generateViews((ViewTreeRepo) viewRepo, TerminalService.getTerminalArea());
    }

    public void rotateClockWise(){
        ((ViewTreeRepo)viewRepo).rotateClockWise(getActiveView().getId(), (getActiveView().getId() + 1) % viewRepo.getSize());
        generateViews();
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
     * Then calls for an update of the anchor.
     * @param direction The direction. Cannot be null.
     *
     * @throws IllegalArgumentException If the given direction is null.
     */
    public void moveCursor(Direction direction){
        Validator.notNull(direction, "Cannot move the cursor in the null direction.");
        fileBufferService.moveCursor(direction);
        updateAnchor();
    }

    /**
     * Creates a new line (\r\n on Windows) at the cursor in the active buffer.
     * Then calls for an update of the anchor.
     */
    public void createNewline(){
        getActiveBuffer().createNewLine();
        updateAnchor();
    }

    /**
     * Inserts the given character at the cursor of the active buffer.
     * @param character The input character
     */
    public void insertCharacter(char character){
        getActiveBuffer().insertCharacter(character);
        getActiveBuffer().setState(BufferState.DIRTY);
    }

    /**
     * Deletes the character just before the cursor of the active buffer.
     * Then calls for an update of the anchor.
     */
    public void deletePrevChar(){
        getActiveBuffer().removeCharacterBefore();
        updateAnchor();
    }

    public boolean attemptDeleteView(){
        if(getActiveBuffer().getState() == BufferState.DIRTY){
            InputHandlerRepo.setCloseDirtyBufferInputHandler();
            return false;
        }
        deleteView();
        return true;
    }

    /**
     * Deletes the view from the repository and moves the active file buffer to the next.
     * Then updates the views to take up the screen.
     */
    public void deleteView(){
        int id = getActiveBuffer().getId();
        fileBufferService.moveActiveBufferToNext();
        fileBufferService.deleteBuffer(id);
        viewRepo.removeAll();
        initialiseViewsVertical();
    }

    /**
     * Deletes the character just after the cursor of the active buffer.
     */
    public void deleteNextChar(){
        getActiveBuffer().removeCharacterAfter();
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
     * Updates the anchor point of the active buffer to adjust it to possible changes to the cursor point.
     */
    private void updateAnchor(){
        AnchorUpdater.updateAnchor(getAnchor(), getActiveBuffer().getCursor(), getActiveView().getDimensions());
    }
}
