package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Point.Point;
import com.Textr.ViewDrawer.CursorDrawer;
import com.Textr.ViewDrawer.ViewDrawer;
import com.Textr.ViewRepo.IViewRepo;
import com.Textr.ViewRepo.ViewRepo;

import java.util.List;

public final class ViewService {
    private final FileBufferService fileBufferService;
    private final IViewRepo viewRepo;

    public ViewService(FileBufferService fileBufferService){
        this.fileBufferService = fileBufferService;
        this.viewRepo = new ViewRepo();
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
            ViewDrawer.drawView(view, fileBuffer.getText(), fileBufferService.generateStatusBar(fileBuffer));
        }
    }

    public void drawCursor(){
        CursorDrawer.draw(getActiveView().getPosition(), getAnchor(), getInsertionPoint());
    }


    public void moveInsertionPoint(Direction direction){
        fileBufferService.moveInsertionPoint(direction);
        updateAnchor();
    }

    public void createNewline(){
        getActiveBuffer().createNewLine();
        updateAnchor();
    }

    public void insertCharacter(char character){
        getActiveBuffer().insertCharacter(character);
    }

    public void deleteChar(){
        getActiveBuffer().removeCharacter();
        updateAnchor();
    }

    private void updateAnchor(){
        AnchorUpdater.updateAnchor(getAnchor(), getInsertionPoint(), getActiveView().getDimensions());
    }
    private View getActiveView(){
        return viewRepo.getByBufferId(fileBufferService.getActiveBuffer().getId());
    }

    private Point getAnchor(){
        return getActiveView().getAnchor();
    }

    private Point getInsertionPoint(){
        return fileBufferService.getActiveBuffer().getInsertionPosition();
    }

    private FileBuffer getActiveBuffer(){
        return fileBufferService.getActiveBuffer();
    }
}
