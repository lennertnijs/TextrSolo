package com.Textr.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewRepo {

    // should still add some methods here
    private final List<View> views = new ArrayList<>();
    protected ViewRepo(){
    }

    protected void add(View view){
        Objects.requireNonNull(view, "Cannot add a null bufferView to the repository.");
        views.add(view);
    }

    protected View getView(int fileBufferId){
        for(View view : views){
            if(view.getFileBufferId() == fileBufferId){
                return view;
            }
        }
        throw new IllegalArgumentException();
    }

    protected List<View> getAll(){
        return this.views;
    }
}
