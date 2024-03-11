package com.Textr.View;

import com.Textr.File.File;
import com.Textr.File.FileReader;
import com.Textr.File.FileService;
import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.Text;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Settings;
import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Direction;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;
import com.Textr.Drawer.CursorDrawer;
import com.Textr.Drawer.ViewDrawer;
import io.github.btj.termios.Terminal;

public final class ViewService {
    private final FileService fileService;
    private final IViewRepo viewRepo;

    public ViewService(FileService fileService, IViewRepo viewRepo){
        Validator.notNull(fileService, "Cannot initiate a ViewService with a null FileService.");
        Validator.notNull(viewRepo, "Cannot initiate a ViewService with a null IViewRepo");
        this.fileService = fileService;
        this.viewRepo = viewRepo;
        LayoutGenerator.setViewRepo(viewRepo);
    }

    /**
     * Creates and stores Views for all the existing Files.
     */
    public void initialiseViewsForAllFiles(){
        for(File file : fileService.getAllFiles()){
            FileBuffer buffer = createBuffer(file);
            View view = createView(buffer);
            viewRepo.add(view);
        }
        viewRepo.setActive(viewRepo.get(0));
        generateViewPositionsAndDimensions();
    }

    private FileBuffer createBuffer(File file){
        int id = file.getId();
        Point insertionPoint = Point.create(0,0);
        Text text = Text.create(FileReader.readContents(file.getUrl()));
        BufferState state = BufferState.CLEAN;
        return FileBuffer.builder().fileId(id).text(text).cursor(insertionPoint).state(state).build();
    }

    private View createView(FileBuffer buffer){
        Point dummyPoint = Point.create(0, 0);
        Dimension2D dummyDimensions = Dimension2D.create(1,1);
        Point anchor = Point.create(0,0);
        return View.builder().buffer(buffer).position(dummyPoint).dimensions(dummyDimensions).anchor(anchor).build();
    }

    /**
     * Sets positions & dimensions for all the existing Views, according to their hierarchical structure.
     */
    private void generateViewPositionsAndDimensions(){
        LayoutGenerator.generate(TerminalService.getTerminalArea());
    }

    /**
     * Sets the active View to the next View.
     */
    public void setActiveViewToNext(){
        viewRepo.setNextActive();
    }

    /**
     * Sets the active View to the previous View.
     */
    public void setActiveViewToPrevious(){
        viewRepo.setPreviousActive();
    }

    /**
     * Draws all the Views and the cursor.
     */
    public void drawAll(){
        TerminalService.clearScreen();
        for(View view: viewRepo.getAll()){
            FileBuffer buffer = view.getBuffer();
            String statusBar = viewRepo.getActive().equals(view) ? "ACTIVE: " + generateStatusBar(buffer) : generateStatusBar(buffer);
            ViewDrawer.draw(view, statusBar);
        }
        CursorDrawer.draw(getActiveView().getPosition(), getAnchor(), getActiveBuffer().getCursor());
    }

    /**
     * Generates and returns a status bar for the given FileBuffer.
     * @param buffer The file buffer. Cannot be null.
     *
     * @return The status bar.
     * @throws IllegalArgumentException If the given buffer is null.
     */
    private String generateStatusBar(FileBuffer buffer){
        Validator.notNull(buffer, "Cannot generate a status bar for a null FileBuffer.");
        return String.format("File path: %s - Lines: %d - Characters: %d - Cursor: (line, col) = (%d, %d) - State: %s",
                fileService.getFile(buffer.getFileId()).getUrl(),
                buffer.getText().getAmountOfLines(),
                buffer.getText().getAmountOfChars(),
                buffer.getCursor().getY(),
                buffer.getCursor().getX(),
                buffer.getState());
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
        getActiveBuffer().moveCursor(direction);
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

    /**
     * Deletes the character just after the cursor of the active buffer.
     */
    public void deleteNextChar(){
        getActiveBuffer().removeCharacterAfter();
    }

    /**
     * Attempts to delete the active View. If this View's buffer is Dirty, show user a warning. If it is clean, delete.
     */
    public void attemptDeleteView(){
        if(getActiveBuffer().getState() == BufferState.CLEAN){
            deleteView();
            return;
        }
        Terminal.clearScreen();
        TerminalService.printText(1, 1, "The buffer is dirty. Are you sure you want to delete it?");
        InputHandlerRepo.setCloseDirtyBufferInputHandler();
    }

    /**
     * Deletes the view from the repository and moves the active file buffer to the next.
     * Then updates the views to take up the screen.
     */
    public void deleteView(){
        if(viewRepo.getSize() == 1){
            Settings.RUNNING = false;
            return;
        }
        View oldActive = getActiveView();
        viewRepo.setNextActive();
        viewRepo.remove(oldActive);
        generateViewPositionsAndDimensions();
        drawAll();
    }


    /**
     * Saves the active View's buffer changes permanently.
     */
    public void saveBuffer(){
        String text = getActiveBuffer().getText().getText();
        fileService.saveToFile(text, getActiveBuffer().getFileId());
        getActiveBuffer().setState(BufferState.CLEAN);
    }

    /**
     * Rotates the active View and the next View.
     * Then updates the new positions and dimensions.
     * @param clockwise The boolean. Indicates whether clockwise, or not.
     */
    public void rotateView(boolean clockwise){
        viewRepo.rotate(clockwise);
        generateViewPositionsAndDimensions();
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
}
