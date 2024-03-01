package com.Textr.ViewRepo;

import com.Textr.Validator.Validator;
import com.Textr.View.View;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class ViewRepo implements IViewRepo{

    private final List<View> views;

    public ViewRepo(){
        views = new ArrayList<>();
    }

    /**
     * Checks whether a {@link View} with the given id is stored.
     * @param id The id
     *
     * @return True if a matching {@link View} was found, false otherwise.
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
     * @return The amount of stored {@link View}s.
     */
    @Override
    public int getSize() {
        return views.size();
    }

    /**
     * Finds the {@link View} with the given id and returns it. Throws an Exception if no match was found.
     * @param id The id
     *
     * @return The {@link View}
     * @throws NoSuchElementException If no {@link View} was found.
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
     * Finds the {@link View} with the given {@link FileBuffer} id and returns it.
     * Throws an Exception if no match was found.
     * @param fileBufferId The {@link FileBuffer} id.
     *
     * @return The {@link View}
     * @throws NoSuchElementException If no {@link View} was found.
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
     * @return All the stored {@link View}s.
     */
    @Override
    public List<View> getAll(){
        return this.views;
    }

    /**
     * Stores the given {@link View}.
     * @param view The {@link View}. Cannot be null.
     *
     * @throws IllegalArgumentException If the passed {@link View} is null.
     */
    public void add(View view){
        Validator.notNull(view, "Cannot store a null View.");
        views.add(view);
    }

    /**
     * Stores all the views in the view repository.
     * @param views The views
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
     * Removes the {@link View} with the given id.
     * Does nothing if no match was found.
     * @param id The id
     */
    @Override
    public void remove(int id) {
        views.removeIf(e -> e.getId() == id);
    }
}
