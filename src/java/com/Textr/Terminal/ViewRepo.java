package com.Textr.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewRepo {

    private final List<View> views = new ArrayList<>();
    protected ViewRepo(){
    }

    protected void add(View view){
        Objects.requireNonNull(view, "Cannot add a null bufferView to the repository.");
        views.add(view);
    }

    protected List<View> getAll(){
        return this.views;
    }

    protected void removeTerminalView(View view){
        this.views.remove(view);
    }
}
