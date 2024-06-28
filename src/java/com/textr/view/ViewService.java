package com.textr.view;

import com.textr.input.Input;
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
     * Rotates the active BufferView and the next BufferView.
     * Then updates the new positions and dimensions.
     * @param clockwise The boolean. Indicates whether clockwise, or not.
     */
    public void rotateView(boolean clockwise){
        viewRepo.rotate(clockwise);
        generateViewPositionsAndDimensions();
    }

    /**
     * @return The active view.
     */
    private View getActiveView(){
        return viewRepo.getActive();
    }

    /**
     * Makes this ViewService handle the given input as needed. Input that was not mapped here is sent to the active
     * view instead, and is handled there.
     * This method will draw this ViewService object after handling the input.
     *
     * Current handled input:
     * CTRL_P (previous active view)
     * CTRL_N (next active view)
     * CTRL_R (rotate clockwise)
     * CTRL_T (rotate counter-clockwise)
     * F4     (close active view)
     * CTRl_G (create snake game view)
     * CTRL_D (duplicate active view)
     * TICK   (redraws only if active updated)
     *
     * @param input The input to handle on the ViewService level
     */
    public void handleInput(Input input) {
        InputType inputType = input.getType();
        switch (inputType){
            case CTRL_P -> setActiveViewToPrevious();
            case CTRL_N -> setActiveViewToNext();
            case CTRL_R -> rotateView(false);
            case CTRL_T -> rotateView(true);
            case  F4-> attemptDeleteView();
            case CTRL_G -> addGame();
            case CTRL_D -> duplicateView();
            case TICK -> {
                if(!getActiveView().wasUpdated())
                    return;
            }
            default -> getActiveView().handleInput(input);
        }
        drawAll();
    }

    private void duplicateView() {
        View dupeView;
        try {
            dupeView = getActiveView().duplicate();
        } catch (UnsupportedOperationException e) {
            return;
        }
        viewRepo.addNextTo(dupeView, getActiveView());
        generateViewPositionsAndDimensions();
        drawAll();
    }

    private void addGame() {
        SnakeView newGame = new SnakeView(Point.create(0,0), Dimension2D.create(10,10));
        viewRepo.addNextTo(newGame, getActiveView());
        generateViewPositionsAndDimensions();
        setActiveViewToNext();
        ((SnakeView)getActiveView()).restartGame();
    }
}
