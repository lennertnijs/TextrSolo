package com.Textr.View;

import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.FileBuffer.BufferState;
import com.Textr.Terminal.TerminalService;

import java.util.List;
import java.util.Objects;

public class ViewService {

    private final ViewRepo viewRepo;
    private final FileBufferService fileBufferService;
    private final FileService fileService;

    public ViewService(FileBufferService fileBufferService, FileService fileService){
        this.viewRepo = new ViewRepo();
        this.fileBufferService = fileBufferService;
        this.fileService = fileService;
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

    public void drawAllViewsVertical(){
        for(View view: viewRepo.getAll()){
            FileBuffer fileBuffer = fileBufferService.getFileBuffer(view.getFileBufferId());
            boolean activeBuffer = fileBufferService.isActive(fileBuffer.getId());
            if(activeBuffer){
                drawActiveView(view, fileBuffer);
            }else{
                drawPassiveView(view, fileBuffer);
            }
        }
    }

    private void drawPassiveView(View view, FileBuffer fileBuffer){
        String[] textLines = fileBuffer.getBufferText().split(System.lineSeparator());
        int viewHeight = view.getDimensions().getHeight();
        int row = view.getPosition().getY();
        int lastRow = row + viewHeight - 1;
        for(int i = 0; i < viewHeight; i++){
            String line = textLines.length <= i ? "" : textLines[i];
            Point linePoint = Point.create(1, row + i);
            if(row + i == lastRow){
                drawStatusBar(fileBuffer, linePoint);
            }else{
                TerminalService.printText(linePoint, line);
            }
        }
    }

    private void drawActiveView(View view, FileBuffer fileBuffer){
        String[] textLines = fileBuffer.getBufferText().split(System.lineSeparator());
        int viewHeight = view.getDimensions().getHeight() - 2; // -2 for the two lines for the box around it
        int row = view.getPosition().getY() + 1;
        int lastRow = row + viewHeight - 1;
        for(int i = 0; i < viewHeight; i++){
            String line = textLines.length <= i ? "" : textLines[i];
            Point linePoint = Point.create(2, row + i);
            if(row + i == lastRow){
                drawStatusBar(fileBuffer, linePoint);
            }else{
                TerminalService.printText(linePoint, line);
            }
        }
        drawRectangle(view.getPosition(), view.getDimensions());
    }

    private void drawStatusBar(FileBuffer buffer, Point point){
        try{
            Objects.requireNonNull(buffer);
            Objects.requireNonNull(point);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Cannot draw a status bar because the passed values are invalid.");
        }
        String url = fileService.getFile(buffer.getFileId()).getPath();
        int amountOfLines = buffer.getBufferText().split(System.lineSeparator()).length;
        int amountOfChars = buffer.getBufferText().length();
        Point insertionPoint = buffer.getInsertionPosition();
        BufferState state = buffer.getState();
        String statusBar = String.format("Url: %s --- Lines: %d --- Characters: %d --- Insertion Point: %s --- State: %s",
                                         url, amountOfLines, amountOfChars, insertionPoint, state);
        TerminalService.printText(point, statusBar);
    }

    private void drawRectangle(Point point, Dimension2D dimensions){
        int x = point.getX();
        int y = point.getY();
        for(int i = 2; i < dimensions.getWidth(); i++){
            TerminalService.printText(i, y, "-");
            TerminalService.printText(i, y + dimensions.getHeight() - 1, "-");
        }
        for(int j = 2; j < dimensions.getHeight(); j++){
            TerminalService.printText(x, j + y - 1, "|");
            TerminalService.printText(x + dimensions.getWidth() - 1, j + y - 1, "|");
        }
        TerminalService.printText(x, y, "+");
        TerminalService.printText(x + dimensions.getWidth() - 1, y, "+");
        TerminalService.printText(x, y + dimensions.getHeight() - 1, "+");
        TerminalService.printText(x + dimensions.getWidth() - 1, y + dimensions.getHeight() - 1, "+");
    }

    public void drawCursor(){
        Point cursorPoint = fileBufferService.getActiveBuffer().getInsertionPosition();
        TerminalService.moveCursor(cursorPoint.getX()+1, cursorPoint.getY()+1);
    }
}
