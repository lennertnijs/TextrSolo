package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.Text;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.Tree.LayoutGenerator;
import com.Textr.Tree.ViewTreeRepo;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;
import com.Textr.Drawer.CursorDrawer;
import com.Textr.Drawer.ViewDrawer;
import com.Textr.Tree.IViewRepo;
import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.List;

public final class ViewService {
    private final FileService fileService;
    private final IViewRepo viewRepo;

    /**
     * Constructor for the ViewService.
     * @param viewRepo The {@link View} repository.
     */
    public ViewService(FileService fileService, IViewRepo viewRepo){
        this.fileService = fileService;
        this.viewRepo = viewRepo;
    }

    public void storeViews(List<View> views){
        viewRepo.addAll(views);
        viewRepo.setActive(views.get(0));
    }

    public void setActiveToNext(){
        // move to next;
    }

    public void setActiveToPrevious(){
        // move to previous
    }

    public void generateViews(){
        LayoutGenerator.generateViews((ViewTreeRepo) viewRepo, TerminalService.getTerminalArea());
    }


    /**
     * Draws all the currently existing views to the terminal screen.
     */
    public void drawAllViews(){
        TerminalService.clearScreen();
        for(View view: viewRepo.getAll()){
            ViewDrawer.draw(view, view.getBuffer().getText(), generateStatusBar(view.getBuffer()));
        }
    }

    private String generateStatusBar(FileBuffer buffer){
        Validator.notNull(buffer, "Cannot generate a status bar for a null FileBuffer.");
        return String.format("File path: %s - Lines: %d - Characters: %d - InsertionPoint: %s - State: %s",
                fileService.getFile(buffer.getFileId()).getUrl(), buffer.getText().getAmountOfLines(),
                buffer.getText().getAmountOfChars(), buffer.getCursor(), buffer.getState());
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
        viewRepo.getActive().getBuffer().moveCursor(direction);
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

    public void attemptDeleteView(){
        if(getActiveBuffer().getState() == BufferState.DIRTY){
            Terminal.clearScreen();
            TerminalService.printText(1, 1, "The buffer is dirty. Are you sure you want to delete it?");
            InputHandlerRepo.setCloseDirtyBufferInputHandler();
        }
        deleteView();
    }

    /**
     * Deletes the view from the repository and moves the active file buffer to the next.
     * Then updates the views to take up the screen.
     */
    public void deleteView(){
        viewRepo.remove(viewRepo.getActive());
    }

    /**
     * Deletes the character just after the cursor of the active buffer.
     */
    public void deleteNextChar(){
        getActiveBuffer().removeCharacterAfter();
    }

    public void saveBuffer(){
        Text bufferTextObj = getActiveBuffer().getText();
        String bufferText = bufferTextObj.getText(); // Double ".getText()", might need refactoring
        try {
            fileService.saveToFile(bufferText, getActiveBuffer().getFileId());
        } catch (IOException e) {
            // Something went wrong during writing, move to new input handler for error messages
            InputHandlerRepo.setAnythingInputHandler();
        } finally {
            // Writing was successful, buffer holds text saved on disk
            getActiveBuffer().setState(BufferState.CLEAN);
        }
    }


    /**
     * @return The active buffer.
     */
    private FileBuffer getActiveBuffer(){
        return viewRepo.getActive().getBuffer();
    }

    /**
     * @return The active view.
     */
    private View getActiveView(){
        return viewRepo.getActive();
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


    public void rotateView(boolean clockwise){
        viewRepo.rotate(clockwise);
        generateViews();
    }
}
