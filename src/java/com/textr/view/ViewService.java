package com.textr.view;

import com.textr.filebuffer.BufferState;
import com.textr.filebuffer.FileBuffer;
import com.textr.input.Input;
import com.textr.input.InputHandlerRepo;
import com.textr.Settings;
import com.textr.input.InputType;
import com.textr.terminal.Communicator;
import com.textr.terminal.TerminalService;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.util.Validator;
import com.textr.drawer.CursorDrawer;
import com.textr.drawer.ViewDrawer;

import java.util.Objects;

public final class ViewService {

    private final IViewRepo viewRepo;
    private final TerminalService terminal; // FIXME: ViewService shouldn't be coupled to Terminal, only to Drawer(s)
    private final ViewDrawer viewDrawer;

    /**
     * The object used for communication with an external source
     */
    private final Communicator communicator;

    public ViewService(IViewRepo viewRepo, ViewDrawer viewDrawer, TerminalService terminal, Communicator communicator){
        Validator.notNull(viewRepo, "Cannot initiate a ViewService with a null IViewRepo");
        Validator.notNull(viewDrawer, "Cannot initiate a ViewService with a null ViewDrawer");
        Validator.notNull(terminal, "Cannot initiate a ViewService with a null TerminalService");
        this.viewRepo = viewRepo;
        this.viewDrawer = viewDrawer;
        this.terminal = terminal;
        this.communicator = Objects.requireNonNull(communicator,
                "Cannot initiate a ViewService with a null Communicator");
        LayoutGenerator.setViewRepo(viewRepo);
    }

    /**
     * Creates and stores Views for all the existing Files.
     */
    public void initialiseViews(String[] filePaths){
        if(filePaths.length == 0){
            Settings.RUNNING = false;
            return;
        }
        for(String url : filePaths){
            Point dummyPosition = Point.create(0, 0);
            Dimension2D dummyDimensions = Dimension2D.create(1,1);
            BufferView view = BufferView.createFromFilePath(url, dummyPosition, dummyDimensions, communicator);
            viewRepo.add(view);
        }
        viewRepo.setActive(viewRepo.get(0));
        generateViewPositionsAndDimensions();
    }


    /**
     * Sets positions & dimensions for all the existing Views, according to their hierarchical structure.
     */
    private void generateViewPositionsAndDimensions(){
        LayoutGenerator.generate(terminal.getTerminalArea()); // FIXME: Pass area as argument (perhaps?)
    }

    /**
     * Sets the active BufferView to the next BufferView.
     */
    public void setActiveViewToNext(){
        viewRepo.setNextActive();
    }

    /**
     * Sets the active BufferView to the previous BufferView.
     */
    public void setActiveViewToPrevious(){
        viewRepo.setPreviousActive();
    }

    /**
     * Draws all the Views and the cursor.
     */
    public void drawAll(){
        terminal.clearScreen(); // FIXME: Shouldn't drawing be in drawer?
        for(View view: viewRepo.getAll()){
                String statusBar = viewRepo.getActive().equals(view) ? "ACTIVE: " + view.generateStatusBar() : view.generateStatusBar();
                if(view instanceof BufferView)
                    viewDrawer.draw((BufferView) view, statusBar);
                else
                    viewDrawer.draw((SnakeView) view, statusBar);
        }
        if(viewRepo.getActive() instanceof BufferView){
            CursorDrawer.draw(
                    getActiveView().getPosition(),
                    ((BufferView)getActiveView()).getAnchor(),
                    ((BufferView) getActiveView()).getCursor().getInsertPoint(),
                    terminal
            );
        }
    }


    /**
     * Attempts to delete the active BufferView. If this BufferView's buffer is Dirty, show user a warning. If it is clean, delete.
     */
    public void attemptDeleteView(){
        if(getActiveView().markForDeletion()){
            deleteView();
        }
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
     * Saves only if the active view is a BufferView, otherwise does nothing.
     * Saves the active BufferView's buffer changes permanently.
     */
    public void saveBuffer(){
        if(getActiveView() instanceof BufferView)
            getActiveBuffer().writeToDisk();
    }

    /**
     * Rotates the active BufferView and the next BufferView.
     * Then updates the new positions and dimensions.
     * @param clockwise The boolean. Indicates whether clockwise, or not.
     */
    public void rotateView(boolean clockwise){
        viewRepo.rotate(clockwise);
        generateViewPositionsAndDimensions();
    }


    /**
     * Gets the active buffer. WARNING: this only works if the active view is a BufferView.
     * @return The active buffer.
     * @throws IllegalStateException if the active view isn't a BufferView
     */
    private FileBuffer getActiveBuffer(){
        if(viewRepo.getActive() instanceof BufferView)
            return ((BufferView) viewRepo.getActive()).getBuffer();
        else
            throw new IllegalStateException ();
    }

    /**
     * @return The active view.
     */
    private View getActiveView(){
        return viewRepo.getActive();
    }

    /**
     * Handling input at the ViewService level, allowing some to flow through to the active view.
     * @param input
     */
    public void handleInput(Input input) {
        InputType inputType = input.getType();
        switch (inputType){
            case CTRL_P -> setActiveViewToPrevious();
            case CTRL_N -> setActiveViewToNext();
            case CTRL_S -> saveBuffer();
            case CTRL_R -> rotateView(false);
            case CTRL_T -> rotateView(true);
            case  F4-> attemptDeleteView();
            case CTRL_G -> addGame();
            case TICK -> {
                if(!getActiveView().incrementTimer())
                    return;
            }
            default -> getActiveView().handleInput(input);
        }
        drawAll();
    }

    private void addGame() {
        SnakeView newGame = new SnakeView(Point.create(0,0), Dimension2D.create(10,10));
        viewRepo.addNextTo(newGame, getActiveView());
        generateViewPositionsAndDimensions();
        newGame.initializeGame();
        setActiveViewToNext();
    }
}
