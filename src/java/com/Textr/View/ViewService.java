package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.FileBuffer.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.ViewDrawer.IViewDrawer;
import com.Textr.ViewDrawer.ViewDrawer;

import java.util.List;
import java.util.Objects;

public class ViewService {

    private final ViewRepo viewRepo;
    private final FileBufferService fileBufferService;
    private final FileService fileService;
    private final IViewDrawer viewDrawer;

    public ViewService(FileBufferService fileBufferService, FileService fileService){
        this.viewRepo = new ViewRepo();
        this.fileBufferService = fileBufferService;
        this.fileService = fileService;
        this.viewDrawer = new ViewDrawer();
    }

    public View createView(int fileBufferId, Point1B point1B, Dimension2D dimensions){
        if(fileBufferId < 0){
            throw new IllegalArgumentException("Cannot create a TerminalView with a negative FileBuffer id.");
        }
        Objects.requireNonNull(point1B, "Cannot create a TerminalView with a null Position.");
        Objects.requireNonNull(dimensions, "Cannot create a TerminalView with null dimensions");
        return View.builder().fileBufferId(fileBufferId).position(point1B).dimensions(dimensions).anchorPoint(Point.create(0,0)).build();
    }

    public void store(View view){
        Objects.requireNonNull(view, "Cannot store a null TerminalView");
        viewRepo.add(view);
    }

    public void createAndStoreView(int fileBufferId, Point1B point1B, Dimension2D dimensions){
        View view = createView(fileBufferId, point1B, dimensions);
        store(view);
    }

    /**
     * Generates a collection of {@link View}'s in a vertical layout.
     */
    public void initialiseViewsVertical(){
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        int amountOfBuffers = fileBufferService.getAmountOfFileBuffers();
        if(amountOfBuffers == 0){
            return;
        }
        int heightPerView = ((terminalHeight) / amountOfBuffers);
        int remainder = ((terminalHeight) % amountOfBuffers);
        int y = 1;
        for(FileBuffer fileBuffer : fileBufferService.getAllFileBuffers()){
            Point1B point1B = Point1B.create(1, y);
            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
            Dimension2D dimensions = Dimension2D.create(terminalWidth, viewHeight);
            createAndStoreView(fileBuffer.getId(), point1B, dimensions);
            y += viewHeight;
        }
    }

    public List<View> getAllViews(){
        return viewRepo.getAll();
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
        Point cursorPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        View view = viewRepo.getView(fileBufferService.getActiveBuffer().getId());
        TerminalService.moveCursor(view.getPosition().getX() + cursorPoint.getX(), view.getPosition().getY() + cursorPoint.getY());
    }

    public void moveInsertionPointRight(){
        fileBufferService.moveInsertionPointRight();
        View view = viewRepo.getView(fileBufferService.getActiveBuffer().getId());
        if(view.getAnchorPoint().getX() + view.getDimensions().getWidth() < fileBufferService.getActiveBuffer().getInsertionPosition().getX()){
            view.getAnchorPoint().incrementX();
        }
    }
}
