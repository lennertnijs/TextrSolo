package com.Textr.TerminalModel;

import com.Textr.FileModel.FileBuffer;
import com.Textr.FileModel.FileBufferService;

import java.util.List;
import java.util.Objects;

public class TerminalViewService {

    private final TerminalViewRepository terminalViewRepository;

    private final FileBufferService fileBufferService;
    public TerminalViewService(FileBufferService fileBufferService){
        this.terminalViewRepository = new TerminalViewRepository();
        this.fileBufferService = fileBufferService;
    }

    public TerminalView createTerminalView(int fileBufferId, Position position, Dimension2D dimensions){
        if(fileBufferId < 0){
            throw new IllegalArgumentException("Cannot create a TerminalView with a negative FileBuffer id.");
        }
        Objects.requireNonNull(position, "Cannot create a TerminalView with a null Position.");
        Objects.requireNonNull(dimensions, "Cannot create a TerminalView with null dimensions");
        return TerminalView.builder().fileId(fileBufferId).point(position).dimensions(dimensions).build();
    }

    public void storeTerminalView(TerminalView terminalView){
        Objects.requireNonNull(terminalView, "Cannot store a null TerminalView");
        terminalViewRepository.addBufferView(terminalView);
    }

    public void initialiseTerminalViewsVertical(Dimension2D dimensions){
        int amountOfFileBuffers = fileBufferService.getAllFileBuffers().size();
        int heightPerView = dimensions.getHeight() / amountOfFileBuffers;
        int x = 1;
        int y = 1;
        for(FileBuffer fileBuffer : fileBufferService.getAllFileBuffers()){
            Position position = Position.builder().x(x).y(y).build();
            Dimension2D viewDimensions = Dimension2D.builder().width(dimensions.getWidth()).height(heightPerView).build();
            TerminalView terminalView = TerminalView.builder().fileId(fileBuffer.getActiveFileId())
                    .point(position).dimensions(viewDimensions).build();
            terminalViewRepository.addBufferView(terminalView);
            y += heightPerView;
        }
    }

    public List<TerminalView> getAllTerminalViews(){
        return terminalViewRepository.getBufferViews();
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
