package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.ViewDrawer.IViewDrawer;
import com.Textr.ViewDrawer.ViewDrawer;
import com.Textr.ViewRepo.IViewRepo;
import com.Textr.ViewRepo.ViewRepo;

public final class ViewService {
    private final FileBufferService fileBufferService;
    private final FileService fileService;
    private final IViewRepo viewRepo;
    private final IViewDrawer viewDrawer;

    public ViewService(FileBufferService fileBufferService, FileService fileService){
        this.fileBufferService = fileBufferService;
        this.fileService = fileService;
        this.viewRepo = new ViewRepo();
        this.viewDrawer = new ViewDrawer();
    }


    public void createAndStoreView(int fileBufferId, Point point, Dimension2D dimensions){
        View view = ViewCreator.create(fileBufferId, point, dimensions);
        viewRepo.add(view);
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
        View view = viewRepo.getByBufferId(fileBufferService.getActiveBuffer().getId());
        TerminalService.moveCursor(view.getPosition().getX() + cursorPoint.getX(), view.getPosition().getY() + cursorPoint.getY());
    }
}
