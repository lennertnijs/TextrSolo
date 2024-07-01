package com.textr.view;

import com.textr.tree.Node;
import com.textr.tree.Tree;

import java.util.List;
import java.util.Objects;

public final class ViewTreeRepo implements IViewRepo {


    private Tree<View> tree;
    private View active;


    public ViewTreeRepo(){
        tree = new Tree<>();
    }

    /**
     * @return Whether the root orientation is vertical or not. If not, the orientation is horizontal.
     */
    @Override
    public boolean rootIsVertical(){
        return tree.isRootVertical();
    }

    @Override
    public Node<View> getRoot(){
        return tree.getRoot();
    }
    /**
     * @return The size of the Tree. This only counts the Nodes with a non-null value.
     */
    @Override
    public int getSize() {
        return tree.getSizeValuesOnly();

    }

    /**
     * @return The active BufferView. If none is set, returns null.
     */
    @Override
    public View getActive() {
        return  active;
    }

    /**
     * Sets the active BufferView to the given view.
     * @param view The new active BufferView. Cannot be null.
     *
     * @throws IllegalArgumentException If the given BufferView is null.
     */
    @Override
    public void setActive(View view) {
        Objects.requireNonNull(view, "Cannot set the active BufferView to a null.");
        this.active = view;
    }

    /**
     * Stores the given View.
     * More specifically, adds the View to the children of the root of the Tree.
     * @param view The BufferView. Cannot be null.
     *
     * @throws IllegalArgumentException If the given view is null.
     */
    @Override
    public void add(View view){
        Objects.requireNonNull(view, "Cannot store a null View.");
        tree.addChildToRoot(new Node<>(view));
    }

    /**
     * Stores the given List of Views.
     * More specifically, adds the Views to the children of the root of the Tree.
     * @param views The List of Views. Cannot be null or contain null.
     *
     * @throws IllegalArgumentException If the given List of Views is or contains null.
     */
    @Override
    public void addAll(List<View> views){
        Objects.requireNonNull(views, "Cannot store views from a null List.");
        for(View view : views){
            Objects.requireNonNull(view, "Cannot store a null View.");
        }
        for(View view : views){
            add(view);
        }
    }

    /**
     * Removes the given View from the Tree. If no match is found, does nothing.
     * @param view The View. Cannot be null.
     */
    @Override
    public void remove(View view) {
        Objects.requireNonNull(view, "Cannot remove a null BufferView from the Tree.");
        tree.remove(view);
        tree.restoreInvariants();
    }

    /**
     * Empties out the entire Tree.
     */
    @Override
    public void removeAll(){
        tree = new Tree<>();
    }

    /**
     * Fetches and returns the View at the given index.
     * @param index The view index. Cannot be negative or bigger than the size of the Tree - 1.
     *
     * @return The view.
     */
    @Override
    public View get(int index){
        if(index < 0 || index >= tree.getSize()){
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        return tree.getAllValues().get(index);
    }

    /**
     * @return All the Tree's values, in their correct order.
     */
    @Override
    public List<View> getAll(){
        return tree.getAllValues();
    }

    /**
     * Sets the active BufferView to the next in the Tree. Works circularly.
     */
    @Override
    public void setNextActive(){
        active = tree.getNextValue(active);
    }

    /**
     * Sets the active BufferView to the previous in the Tree. Works circularly.
     */
    @Override
    public void setPreviousActive(){
        active = tree.getPreviousValue(active);
    }
    /**
     * Stores the given View next to the existing one.
     * @param newView The new View. Cannot be null.
     * @param existing the view it'll be placed next to
     * @throws IllegalArgumentException If the given newview is null.
     */
    @Override
    public void addNextTo(View newView, View existing ){
        Objects.requireNonNull(newView, "Cannot store a null View.");
        Objects.requireNonNull(existing, "Cannot store a null View.");
        Node<View> sibling = tree.getNode(existing);
        int placement = sibling.getParent().getChildren().indexOf(sibling)+1;
        tree.addChildToNodeAt(new Node<>(newView),sibling.getParent(),placement);
    }
    /**
     * Rotates the active BufferView and the next BufferView CW / CCW and updates the tree appropriately, keeping its invariance.
     * @param clockwise The bool whether CW/CCW
     */
    @Override
    public void rotate(boolean clockwise){
        tree.rotate(clockwise, active);
    }

    /**
     * Returns a List with all the Tree's values, in order, at the given depth. Null values mean a non-leaf node.
     * @param depth The depth. Cannot be negative or 0.
     *
     * @return The values and nulls at the given depth.
     */
    @Override
    public List<View> getAllAtDepth(int depth){
        if(depth <= 0){
            throw new IllegalArgumentException("Depth is negative or 0.");
        }
        return tree.getAllAtDepth(depth);
    }
}
