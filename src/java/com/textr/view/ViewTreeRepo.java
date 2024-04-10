package com.textr.view;

import com.textr.tree.Node;
import com.textr.tree.Tree;
import com.textr.util.Validator;

import java.util.List;

public final class ViewTreeRepo implements IViewRepo {


    private Tree<BufferView> tree;
    private BufferView active;


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
    public Node getRoot(){
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
    public BufferView getActive() {
        return  active;
    }

    /**
     * Sets the active BufferView to the given view.
     * @param view The new active BufferView. Cannot be null.
     *
     * @throws IllegalArgumentException If the given BufferView is null.
     */
    @Override
    public void setActive(BufferView view) {
        Validator.notNull(view, "Cannot set the active BufferView to a null.");
        this.active = view;
    }

    /**
     * Stores the given BufferView.
     * More specifically, adds the BufferView to the children of the root of the Tree.
     * @param view The BufferView. Cannot be null.
     *
     * @throws IllegalArgumentException If the given view is null.
     */
    @Override
    public void add(BufferView view){
        Validator.notNull(view, "Cannot store a null BufferView.");
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
    public void addAll(List<BufferView> views){
        Validator.notNull(views, "Cannot store views from a null List.");
        for(BufferView view : views){
            Validator.notNull(view, "Cannot store a null BufferView.");
        }
        for(BufferView view : views){
            add(view);
        }
    }

    /**
     * Removes the given BufferView from the Tree. If no match is found, does nothing.
     * @param view The BufferView. Cannot be null.
     */
    @Override
    public void remove(BufferView view) {
        Validator.notNull(view, "Cannot remove a null BufferView from the Tree.");
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
     * Fetches and returns the BufferView at the given index.
     * @param index The view index. Cannot be negative or bigger than the size of the Tree - 1.
     *
     * @return The view.
     */
    @Override
    public BufferView get(int index){
        Validator.withinRange(index, 0, tree.getSize() - 1, "Cannot retrieve an element at in invalid index.");
        return tree.getAllValues().get(index);
    }

    /**
     * @return All the Tree's values, in their correct order.
     */
    @Override
    public List<BufferView> getAll(){
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
    public List<BufferView> getAllAtDepth(int depth){
        Validator.notNegativeOrZero(depth, "Cannot check the nodes at the given negative or zero depth.");
        return tree.getAllAtDepth(depth);
    }
}
