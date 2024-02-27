package com.Textr.View;

import com.Textr.File.File;
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

    public View createView(int fileBufferId, Position position, Dimension2D dimensions){
        if(fileBufferId < 0){
            throw new IllegalArgumentException("Cannot create a TerminalView with a negative FileBuffer id.");
        }
        Objects.requireNonNull(position, "Cannot create a TerminalView with a null Position.");
        Objects.requireNonNull(dimensions, "Cannot create a TerminalView with null dimensions");
        return View.builder().fileBufferId(fileBufferId).point(position).dimensions(dimensions).build();
    }

    public void store(View view){
        Objects.requireNonNull(view, "Cannot store a null TerminalView");
        viewRepo.add(view);
    }

    public void createAndStoreView(int fileBufferId, Position position, Dimension2D dimensions){
        View view = createView(fileBufferId, position, dimensions);
        store(view);
    }

    /**
     * Generates a collection of {@link View}'s in a vertical layout.
     * Might be worth swapping to a List in the FileBuffer repo? To maintain order?
     */
    public void initialiseViewsVertical(){
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        int amountOfBuffers = fileBufferService.getAmountOfFileBuffers();
        if(amountOfBuffers == 0){
            return;
        }
        int heightPerView = (terminalHeight / amountOfBuffers);
        int y = 1;
        for(FileBuffer fileBuffer : fileBufferService.getAllFileBuffers()){
            Position viewPosition = Position.builder().x(1).y(y).build();
            Dimension2D viewDimensions = Dimension2D.builder().width(terminalWidth).height(heightPerView).build();
            createAndStoreView(fileBuffer.getId(), viewPosition, viewDimensions);
            y += heightPerView;
        }
    }

    public List<View> getAllViews(){
        return viewRepo.getAll();
    }

    public void drawAllViewsVertical(){
        for(View view: viewRepo.getAll()){
            FileBuffer fileBuffer = fileBufferService.getFileBuffer(view.getFileBufferId());
            boolean activeBuffer = fileBufferService.isActive(fileBuffer.getId());
            File file = fileService.getFile(fileBuffer.getFileId());
            String text = fileBuffer.getBufferText();
            String[] lines = text.split(System.lineSeparator());
            int x = view.getPosition().getX();
            int y = view.getPosition().getY();
            int maxY = y + view.getDimensions().getHeight() - 1;
            BufferState viewBufferState = fileBuffer.getState();
            String url = file.getPath();
            for(int i = 0; i < view.getDimensions().getHeight(); i++){
                String line = lines.length <= i ? "" : lines[i];
                Position linePosition = Position.builder().x(x).y(y).build();
                boolean lastLine = y == maxY;
                if(activeBuffer){
                    if(lastLine){
                        TerminalService.printText(linePosition,
                                String.format("| path: %s --- lines: %d --- characters: %d --- insertion point: %s -- state: %s |",
                                        url, lines.length, text.toCharArray().length,fileBuffer.getInsertionPosition(), viewBufferState));
                    }
                    if(y < maxY){
                        TerminalService.printText(linePosition, line);
                        Position position = Position.builder().x(view.getDimensions().getWidth()).y(y).build();
                        TerminalService.printText(position, "|");
                        TerminalService.printText(linePosition, "|");
                    }
                }else{
                    if(lastLine){
                        TerminalService.printText(linePosition,
                                String.format("path: %s --- lines: %d --- characters: %d --- insertion point: %s -- state: %s",
                                        url, lines.length, text.toCharArray().length,fileBuffer.getInsertionPosition(), viewBufferState));
                    }
                    if(y < maxY){
                        TerminalService.printText(linePosition, line);
                    }
                }
                y++;

            }
        }
    }
}
