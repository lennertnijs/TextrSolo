package com.Textr.Terminal;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferService;

import java.util.List;
import java.util.Objects;

public class ViewService {

    private final ViewRepo viewRepo;
    private final FileBufferService fileBufferService;

    public ViewService(FileBufferService fileBufferService){
        this.viewRepo = new ViewRepo();
        this.fileBufferService = fileBufferService;
    }

    public View createTerminalView(int fileBufferId, Position position, Dimension2D dimensions){
        if(fileBufferId < 0){
            throw new IllegalArgumentException("Cannot create a TerminalView with a negative FileBuffer id.");
        }
        Objects.requireNonNull(position, "Cannot create a TerminalView with a null Position.");
        Objects.requireNonNull(dimensions, "Cannot create a TerminalView with null dimensions");
        return View.builder().fileBufferId(fileBufferId).point(position).dimensions(dimensions).build();
    }

    public void storeTerminalView(View view){
        Objects.requireNonNull(view, "Cannot store a null TerminalView");
        viewRepo.add(view);
    }

    public void initialiseTerminalViewsVertical(){
        Dimension2D dimensions = TerminalService.getTerminalArea().get();
        int amountOfFileBuffers = fileBufferService.getAllFileBuffers().size();
        int heightPerView = dimensions.getHeight() / amountOfFileBuffers;
        int x = 1;
        int y = 1;
        for(FileBuffer fileBuffer : fileBufferService.getAllFileBuffers()){
            Position position = Position.builder().x(x).y(y).build();
            Dimension2D viewDimensions = Dimension2D.builder().width(dimensions.getWidth()).height(heightPerView).build();
            View view = createTerminalView(fileBuffer.getFileId(), position, viewDimensions);
            viewRepo.add(view);
            y += heightPerView;
        }
    }

    public List<View> getAllTerminalViews(){
        return viewRepo.getAll();
    }

    public void drawAllViews(){
        for(View view: viewRepo.getAll()){
            String text = fileBufferService.getFileBuffer(view.getFileBufferId()).get().getBufferText();
            TerminalService.printText(view.getPosition(), removeAllNonAscii(text));
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
     * 6) generate horizontal TerminalViews(List Filebuffers)
     * 7) generate vertical Terminal Views (List FileBuffers)
     * 8) ???
     */
}
