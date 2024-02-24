package com.Textr.TerminalModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TerminalViewRepository {

    private final List<TerminalView> terminalViews = new ArrayList<>();
    protected TerminalViewRepository(){
    }

    protected void addBufferView(TerminalView terminalView){
        Objects.requireNonNull(terminalView, "Cannot add a null bufferView to the repository.");
        terminalViews.add(terminalView);
    }

    protected List<TerminalView> getBufferViews(){
        return this.terminalViews;
    }
}
