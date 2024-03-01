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

import java.util.List;

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


    public void initialiseViewsVertical(){
        List<View> views = ViewLayoutInitializer.generateVerticallyStackedViews(fileBufferService.getAllFileBuffers());
        for(View view : views){
            viewRepo.add(view);
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
        Point anchor = view.getAnchor();
        TerminalService.moveCursor(view.getPosition().getX() + cursorPoint.getX() - anchor.getX(), view.getPosition().getY() + cursorPoint.getY() - anchor.getY());
    }

    public void moveInsertionPointRight(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        View view = getActiveView();
        fileBufferService.moveInsertionPointRight();
        if(insertionPoint.getX() > view.getAnchor().getX() + view.getDimensions().getWidth() - 1) {
            view.getAnchor().incrementX();
        }
    }

    public void moveInsertionPointLeft(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        View view = getActiveView();
        fileBufferService.moveInsertionPointLeft();
        if(insertionPoint.getX() < view.getAnchor().getX()){
            view.getAnchor().decrementX();
        }
    }

    public void moveInsertionPointDown(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        View view = getActiveView();
        fileBufferService.moveInsertionPointDown();
        if(fileBufferService.getActiveBuffer().getInsertionPosition().getX() < view.getAnchor().getX()){
            view.getAnchor().setX(fileBufferService.getActiveBuffer().getInsertionPosition().getX());
        }
        if(insertionPoint.getY() > view.getAnchor().getY() + view.getDimensions().getHeight() - 2){
            view.getAnchor().incrementY();
        }
    }

    public void moveInsertionPointUp(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        View view = getActiveView();
        fileBufferService.moveInsertionPointUp();
        if(fileBufferService.getActiveBuffer().getInsertionPosition().getX() < view.getAnchor().getX()){
            view.getAnchor().setX(fileBufferService.getActiveBuffer().getInsertionPosition().getX());
        }
        if(insertionPoint.getY() < view.getAnchor().getY()){
            view.getAnchor().decrementY();
        }
    }

    public void createNewline(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        Point anchor = getActiveView().getAnchor();
        fileBufferService.getActiveBuffer().getBufferText().breakLine(insertionPoint.getY(), insertionPoint.getX());
        View view = getActiveView();
        insertionPoint.setX(0);
        insertionPoint.incrementY();
        anchor.setX(0);
        if(insertionPoint.getY() > view.getAnchor().getY() + view.getDimensions().getHeight() - 2){
            view.getAnchor().incrementY();
        }
    }

    public void deleteChar(){
        Point insertionPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        int oldLength = fileBufferService.getActiveBuffer().getBufferText().getAmountOfLines();
        int oldPreviousLineLength = fileBufferService.getActiveBuffer().getBufferText().getLines()[Math.max(0, insertionPoint.getY()-1)].length();
        fileBufferService.getActiveBuffer().getBufferText().removeCharacter(insertionPoint.getY(), insertionPoint.getX());
        if(oldLength != fileBufferService.getActiveBuffer().getBufferText().getAmountOfLines()){
            fileBufferService.getActiveBuffer().getInsertionPosition().decrementY();
            fileBufferService.getActiveBuffer().getInsertionPosition().setX(oldPreviousLineLength);
            if(insertionPoint.getY() < getActiveView().getAnchor().getY()){
                getActiveView().getAnchor().decrementY();
            }
            if(insertionPoint.getX() > getActiveView().getAnchor().getX() + getActiveView().getDimensions().getWidth() - 1){
                getActiveView().getAnchor().setX(Math.max(0, fileBufferService.getActiveBuffer().getBufferText().getLines()[insertionPoint.getY()].length() - getActiveView().getDimensions().getWidth() + 1 ));
            }
        }else{
            fileBufferService.getActiveBuffer().getInsertionPosition().decrementX();
        }
    }

    private View getActiveView(){
        return viewRepo.getByBufferId(fileBufferService.getActiveBuffer().getId());
    }
}
