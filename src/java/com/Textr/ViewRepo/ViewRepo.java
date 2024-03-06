package com.Textr.ViewRepo;

import com.Textr.Util.Validator;
import com.Textr.View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class ViewRepo implements IViewRepo{

    private List<View> views;

    public ViewRepo(){
        views = new ArrayList<>();
    }

    /**
     * Checks whether a view with the given id is stored.
     * @param id The id
     *
     * @return True if a matching view was found, false otherwise.
     */
    @Override
    public boolean contains(int id) {
        for(View view : views){
            if(view.getId() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * @return The amount of stored views.
     */
    @Override
    public int getSize() {
        return views.size();
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
        for(View view : views){
            if(view.getId() == id){
                return view;
            }
        }
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
    public View getByBufferId(int fileBufferId){
        for(View view : views){
            if(view.getFileBufferId() == fileBufferId){
                return view;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return All the stored views.
     */
    @Override
    public List<View> getAll(){
        return this.views;
    }

    /**
     * Stores the given view.
     * @param view The view. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed view is null.
     */
    public void add(View view){
        Validator.notNull(view, "Cannot store a null View.");
        views.add(view);
    }

    /**
     * Stores all the views in the view repository.
     * @param views The views
     *
     * @throws IllegalArgumentException If the given list of views is or contains null.
     */
    @Override
    public void addAll(List<View> views){
        Validator.notNull(views, "Cannot store a null List of views.");
        for(View view : views){
            Validator.notNull(view, "Cannot store a null View");
            this.views.add(view);
        }
    }
    /**
     * Removes the view with the given id.
     * Does nothing if no match was found.
     * @param id The id
     */
    @Override
    public void remove(int id) {
        views.removeIf(e -> e.getId() == id);
    }

    /**
     * Removes all the views.
     * USE WITH CAUTION
     */
    @Override
    public void removeAll(){
        views = new ArrayList<>();
    }

    @Override
    public void rotate(boolean clockwise, int activeviewid) {
        //WARNING this does nothing
    }
}
