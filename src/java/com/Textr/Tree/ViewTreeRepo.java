package com.Textr.Tree;

import com.Textr.Validator.Validator;
import com.Textr.View.View;

import java.util.ArrayList;
import java.util.List;

public class ViewTreeRepo {

    private final Tree<Integer> tree;
    private final List<View> views;

    public ViewTreeRepo(){
        tree = new Tree<>();
        views = new ArrayList<>();
    }

    /**
     * Stores the given View in the repository.
     * @param view The View. Cannot be null.
     *
     * @throws IllegalArgumentException If the given view is null.
     */
    public void add(View view){
        Validator.notNull(view, "Cannot store a null View.");
        views.add(view);
        //tree.addChildToRoot(Node.create(view));
    }

    /**
     * Stores the given List of Views in the repository.
     * @param views The List of Views. Cannot be null or contain null.
     *
     * @throws IllegalArgumentException If the given List of Views is or contains null.
     */
    public void addAll(List<View> views){
        Validator.notNull(views, "Cannot store views from a null List.");
        for(View view : views){
            Validator.notNull(view, "Cannot store a null View.");
        }
        for(View view : views){
            add(view);
        }
    }

    public void remove(int viewId){
        views.removeIf(e -> e.getId() == viewId);
    }
}
