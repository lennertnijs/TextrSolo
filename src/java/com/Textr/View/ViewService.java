package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.FileBuffer.InsertionPoint;
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

    public View createView(int fileBufferId, Point point, Dimension2D dimensions){
        if(fileBufferId < 0){
            throw new IllegalArgumentException("Cannot create a TerminalView with a negative FileBuffer id.");
        }
        Objects.requireNonNull(point, "Cannot create a TerminalView with a null Position.");
        Objects.requireNonNull(dimensions, "Cannot create a TerminalView with null dimensions");
        return View.builder().fileBufferId(fileBufferId).point(point).dimensions(dimensions).build();
    }

    public void store(View view){
        Objects.requireNonNull(view, "Cannot store a null TerminalView");
        viewRepo.add(view);
    }

    public void createAndStoreView(int fileBufferId, Point point, Dimension2D dimensions){
        View view = createView(fileBufferId, point, dimensions);
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
            Point point = Point.create(1, y);
            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
            Dimension2D dimensions = Dimension2D.create(terminalWidth, viewHeight);
            createAndStoreView(fileBuffer.getId(), point, dimensions);
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
               fileService.getFile(fileBuffer.getFileId()).getPath(), fileBuffer.getBufferText().getAmountOfLines(),
                fileBuffer.getBufferText().getAmountOfChars(), fileBuffer.getInsertionPosition(), fileBuffer.getState());
    }

    public void drawCursor(){
        InsertionPoint cursorPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        TerminalService.moveCursor(cursorPoint.getX() + 1, cursorPoint.getY() + 1);
    }
}
