package com.textr.service;

import com.textr.file.FileReader;
import com.textr.file.FileWriter;
import com.textr.filebuffer.FileBuffer;
import com.textr.input.Input;
import com.textr.Settings;
import com.textr.input.InputType;
import com.textr.snake.SnakeGameInitializer;
import com.textr.terminal.Communicator;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.view.*;

import java.io.File;
import java.util.Objects;

public final class ViewService {

    private final IViewRepo viewRepo;
    private final ViewDrawer viewDrawer;
    private final Dimension2D dimensions;
    private final LayoutGenerator layoutGenerator;

    public ViewService(IViewRepo viewRepo, ViewDrawer viewDrawer,
                       Dimension2D dimensions, LayoutGenerator layoutGenerator){
        this.viewRepo = Objects.requireNonNull(viewRepo, "View repository is null.");
        this.viewDrawer = Objects.requireNonNull(viewDrawer, "View drawer is null.");
        this.dimensions = Objects.requireNonNull(dimensions, "Dimensions is null.");
        this.layoutGenerator = Objects.requireNonNull(layoutGenerator, "Layout generator is null.");
    }

    /**
     * Creates and stores Views for all the existing Files.
     */
    public void initialiseViews(String[] filePaths, Communicator communicator){
        if(filePaths.length == 0){
            Settings.RUNNING = false;
            return;
        }
        FileBuffer.setFileReader(new FileReader());
        FileBuffer.setFileWriter(new FileWriter());
        for(String url : filePaths){
            File file = new File(url);
            BufferView view = BufferView.builder().file(file).communicator(communicator).build();;
            viewRepo.add(view);
        }
        viewRepo.setActive(viewRepo.get(0));
        layoutGenerator.generate(dimensions);
        viewDrawer.drawAll(viewRepo);
    }

    /**
     * Attempts to delete the active BufferView. If this BufferView's buffer is Dirty, show user a warning. If it is clean, delete.
     */
    public void attemptDeleteView(){
        if(!viewRepo.getActive().canClose()){
            return;
        }
        if(viewRepo.getSize() == 1){
            Settings.RUNNING = false;
            return;
        }
        View oldActive = viewRepo.getActive();
        viewRepo.setNextActive();
        viewRepo.remove(oldActive);
        layoutGenerator.generate(dimensions);
        viewDrawer.drawAll(viewRepo);
    }

    public void input(Input input){
        if(handleInput(input)){
            layoutGenerator.generate(dimensions);
            viewDrawer.drawAll(viewRepo);
        }
    }

    /**
     * Makes this ViewService handle the given input as needed. Input that was not mapped here is sent to the active
     * view instead, and is handled there.
     * This method will draw this ViewService object after handling the input.
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
    public boolean handleInput(Input input) {
        InputType inputType = input.getType();
        switch (inputType){
            case CTRL_P -> viewRepo.setPreviousActive();
            case CTRL_N -> viewRepo.setNextActive();
            case CTRL_R -> viewRepo.rotate(false);
            case CTRL_T -> viewRepo.rotate(true);
            case F4 -> attemptDeleteView();
            case CTRL_G -> addSnakeGame();
            case CTRL_D -> duplicateView();
            default -> {return viewRepo.getActive().handleInput(input);}
        }
        return true;
    }

    private void duplicateView() {
        View dupeView;
        try {
            dupeView = viewRepo.getActive().duplicate();
        } catch (UnsupportedOperationException e) {
            return;
        }
        viewRepo.addNextTo(dupeView, viewRepo.getActive());
        layoutGenerator.generate(dimensions);
        viewDrawer.drawAll(viewRepo);
    }

    private void addSnakeGame() {
        SnakeView newGame = new SnakeView(new Point(0,0), new Dimension2D(10,10), new SnakeGameInitializer(6, 12));
        viewRepo.addNextTo(newGame, viewRepo.getActive());
        layoutGenerator.generate(dimensions);
        viewRepo.setNextActive();
        ((SnakeView)viewRepo.getActive()).restartGame();
    }
}
