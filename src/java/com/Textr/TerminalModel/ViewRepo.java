package com.Textr.TerminalModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Should store all the TerminalViews
// Should support addition, removal and retrieval at the least.
public class ViewRepo {

    private final List<View> views = new ArrayList<>();
    protected ViewRepo(){
    }

    protected void addBufferView(View view){
        Objects.requireNonNull(view, "Cannot add a null bufferView to the repository.");
        views.add(view);
    }

    protected List<View> getBufferViews(){
        return this.views;
    }

    protected void removeTerminalView(View view){
        this.views.remove(view);
    }
}
