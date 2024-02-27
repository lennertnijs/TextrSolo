package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.FileBuffer.State;
import com.Textr.Terminal.TerminalService;

import java.util.List;
import java.util.Objects;

public class ViewService {

    private final ViewRepo viewRepo;
    private final FileBufferService fileBufferService;

    public ViewService(FileBufferService fileBufferService){
        this.viewRepo = new ViewRepo();
        this.fileBufferService = fileBufferService;
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

    /**
     * Generates a collection of {@link View}'s in a vertical layout.
     * Might be worth swapping to a List in the FileBuffer repo? To maintain order?
     */
    public void initialiseViewsVertical(){
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        int heightPerView = (terminalHeight / fileBufferService.getAmountOfFileBuffers());
        int y = 1;
        for(FileBuffer fileBuffer : fileBufferService.getAllFileBuffers()){
            Position viewPosition = Position.builder().x(1).y(y).build();
            Dimension2D viewDimensions = Dimension2D.builder().width(terminalWidth).height(heightPerView).build();
            store(createView(fileBuffer.getId(), viewPosition, viewDimensions));
            y += heightPerView;
        }
    }

    public List<View> getAllViews(){
        return viewRepo.getAll();
    }

    public void drawAllViewsVertical(){
        for(View view: viewRepo.getAll()){
            String text = fileBufferService.getFileBuffer(view.getFileBufferId()).getBufferText();
            String[] lines = text.split(System.lineSeparator());
            int x = view.getPosition().getX();
            int y = view.getPosition().getY();
            int maxY = y + view.getDimensions().getHeight() - 1;
            State viewState = fileBufferService.getFileBuffer(view.getFileBufferId()).getState();
            for(String line: lines){
                Position linePosition = Position.builder().x(x).y(y).build();
                boolean lastLine = y == maxY;
                if(lastLine){
                    TerminalService.printText(linePosition, String.valueOf(viewState));
                }
                if(y < maxY){
                    TerminalService.printText(linePosition, line);
                    y++;
                }
            }
        }
    }

    private String removeAllNonAscii(String text){
        StringBuilder builder = new StringBuilder();
        for(char c : text.toCharArray()){
            if(c >= 32 && c <= 126 || c == 10 || c == 13){
                builder.append(c);
            }
        }
        return builder.toString();
    }




    /**
     * Methods necessary:
     * 1) create terminalView
     * 2) store TerminalView
     * 3) remove terminalView
     * 4) getAllTerminalViews
     * 5) draw the terminal view ??
     * 6) generate horizontal TerminalViews(List FileBuffers)
     * 7) generate vertical Terminal Views (List FileBuffers)
     * 8) ???
     */
}
