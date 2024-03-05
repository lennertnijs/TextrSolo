package com.Textr.ViewRepo;

import com.Textr.Validator.Validator;
import com.Textr.View.View;
import com.Textr.ViewLayout.Layout;

import java.util.List;
import java.util.NoSuchElementException;

public class ViewRepo2 implements IViewRepo {

    private Layout rootlayout;

    /**
     * Checks whether a view with the given id is stored.
     * @param id The id
     *
     * @return True if a matching view was found, false otherwise.
     */
    @Override
    public boolean contains(int id) {
        return rootlayout.containsView(id);
    }
    /**
     * @return The amount of stored views.
     */
    @Override
    public int getSize() {
        return rootlayout.getSize();
    }
    /**
     * Finds the view with the given id and returns it. Throws an Exception if no match was found.
     * @param id The id
     *
     * @return The view
     * @throws NoSuchElementException If no view was found.
     */
    @Override
    public View get(int id) {
        View result = rootlayout.getViewById(id);
        if(result!= null) {
            return result;
        }
        else
            throw new NoSuchElementException("Could not find a View with this id.");
    }
    /**
     * Finds the view with the given {@link FileBuffer} id and returns it.
     * Throws an Exception if no match was found.
     * @param fileBufferId The file buffer id.
     *
     * @return The view
     * @throws NoSuchElementException If no view was found.
     */
    @Override
    public View getByBufferId(int fileBufferId) {
        View result = rootlayout.getViewByBufferId(fileBufferId);
        if(result!= null) {
            return result;
        }
        else
            throw new NoSuchElementException("Could not find a View with this id.");
    }
    /**
     * @return All the stored views.
     */
    @Override
    public List<View> getAll() {
        return rootlayout.getAllViews();
    }
    /**
     * Stores the given view.
     * @param view The view. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed view is null.
     */
    @Override
    public void add(View view) {
        Validator.notNull(view, "Cannot store a null View.");
        Layout layout = new Layout(view);
        rootlayout.addSubLayout(layout);
    }
    /**
     * Stores all the views in the view repository.
     * @param views The views
     *
     * @throws IllegalArgumentException If the given list of views is or contains null.
     */
    @Override
    public void addAll(List<View> views) {
        Validator.notNull(views, "Cannot store a null List of views.");
        for (View view : views)
            add(view);
    }
    /**
     * Removes the view with the given id.
     * Does nothing if no match was found.
     * @param id The id
     */
    @Override
    public void remove(int id) {
        Layout leaf = rootlayout.getViewLocation(id);
        if(leaf!=null && leaf.getParent()!= null)
            leaf.getParent().removeSubLayout(leaf);
    }
    /**
     * Removes all the views.
     * USE WITH CAUTION
     */
    @Override
    public void removeAll() {
        rootlayout = new Layout();
    }

    public void rotate(boolean clockwise, int activeviewid){
        Layout leaf = rootlayout.getViewLocation(activeviewid);
        leaf.rotatewithnext(clockwise);
    }
}
