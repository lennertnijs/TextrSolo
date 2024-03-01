package com.Textr.ViewRepo;

import com.Textr.View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ViewRepo implements IViewRepo{

    // should still add some methods here
    private final List<View> views;
    public ViewRepo(){
        views = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void add(View view){
        Objects.requireNonNull(view, "Cannot add a null bufferView to the repository.");
        views.add(view);
    }

    @Override
    public View getByBufferId(int fileBufferId){
        for(View view : views){
            if(view.getFileBufferId() == fileBufferId){
                return view;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<View> getAll(){
        return this.views;
    }
}
