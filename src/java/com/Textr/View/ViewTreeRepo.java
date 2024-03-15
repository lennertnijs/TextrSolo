package com.Textr.View;

import com.Textr.Tree.Node;
import com.Textr.Tree.Tree;
import com.Textr.Util.Validator;

import java.util.List;

public final class ViewTreeRepo implements IViewRepo {


    private Tree<View> tree;
    private View active;
    private boolean rootIsVertical;


    public ViewTreeRepo(){
        tree = new Tree<>();
        rootIsVertical = true;
    }

    /**
     * @return Whether the root orientation is vertical or not. If not, the orientation is horizontal.
     */
    @Override
    public boolean rootIsVertical(){
        return rootIsVertical;
    }

    /**
     * @return The size of the Tree. This only counts the Nodes with a non-null value.
     */
    @Override
    public int getSize() {
        return tree.getSizeValuesOnly();

    }

    /**
     * @return The active View. If none is set, returns null.
     */
    @Override
    public View getActive() {
        return  active;
    }

    /**
     * Sets the active View to the given view.
     * @param view The new active View. Cannot be null.
     *
     * @throws IllegalArgumentException If the given View is null.
     */
    @Override
    public void setActive(View view) {
        Validator.notNull(view, "Cannot set the active View to a null.");
        this.active = view;
    }

    /**
     * Stores the given View.
     * More specifically, adds the View to the children of the root of the Tree.
     * @param view The View. Cannot be null.
     *
     * @throws IllegalArgumentException If the given view is null.
     */
    @Override
    public void add(View view){
        Validator.notNull(view, "Cannot store a null View.");
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
        Validator.notNull(views, "Cannot store views from a null List.");
        for(View view : views){
            Validator.notNull(view, "Cannot store a null View.");
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
        Validator.notNull(view, "Cannot remove a null View from the Tree.");
        tree.remove(view);
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
        Validator.withinRange(index, 0, tree.getSize() - 1, "Cannot retrieve an element at in invalid index.");
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
     * Sets the active View to the next in the Tree. Works circularly.
     */
    @Override
    public void setNextActive(){
        active = tree.getNextValue(active);
    }

    /**
     * Sets the active View to the previous in the Tree. Works circularly.
     */
    @Override
    public void setPreviousActive(){
        active = tree.getPreviousValue(active);
    }


    /**
     * Rotates the active View and the next View CW / CCW and updates the tree appropriately, keeping its invariance.
     * @param clockwise The bool whether CW/CCW
     */
    @Override
    public void rotate(boolean clockwise){
        rotateWithNext(clockwise);
        if(tree.getRoot().hasChildren()&& tree.getRoot().getChildren().size() == 1){
            flipRootOrientation();
        }
        tree.restoreInvariants();
    }

    /**
     * Returns a List with all the Tree's values, in order, at the given depth. Null values mean a non-leaf node.
     * @param depth The depth. Cannot be negative or 0.
     *
     * @return The values and nulls at the given depth.
     */
    @Override
    public List<View> getAllAtDepth(int depth){
        Validator.notNegativeOrZero(depth, "Cannot check the nodes at the given negative or zero depth.");
        return tree.getAllAtDepth(depth);
    }


    private void rotateWithNext(boolean clockwise){
        if(tree.isLastValue(active)){
            System.out.println((char)7);
            return;
        }
        Node<View> current = tree.getNode(active);
        Node<View> next = tree.getNode(tree.getNextValue(active));
        if(current.isSiblingWith(next)){
            rotateSiblings(current, next, clockwise);
            return;
        }
        rotateNonSibling(current, next, clockwise);
    }


    private void rotateSiblings(Node<View> current, Node<View> next, boolean clockwise){
        Node<View> nullNode = new Node<>(null);
        current.getParent().replaceChild(current, nullNode);
        View currentView = current.getValue();
        View nextView = next.getValue();
        boolean counterClockWise = !clockwise;
        boolean noSwap = clockwise && currentView.leftOff(nextView) || counterClockWise && !currentView.leftOff(nextView);
        tree.remove(current);
        tree.remove(next);
        if(noSwap){
            tree.addChildToNode(current, nullNode);
            tree.addChildToNode(next, nullNode);
            return;
        }
        tree.addChildToNode(next, nullNode);
        tree.addChildToNode(current,nullNode);
    }

    private void rotateNonSibling(Node<View> currentNode, Node<View> nextNode, boolean clockwise){
        View current = currentNode.getValue();
        View next = nextNode.getValue();
        boolean counterClockWise = !clockwise;
        boolean noSwap = clockwise && current.leftOff(next) || counterClockWise && !current.leftOff(next);
        Node<View> parent = currentNode.getParent();
        tree.remove(nextNode);
        int position = currentNode.getParent().getChildren().indexOf(currentNode);
        if (noSwap){
            position++;
        }
        tree.addChildToNodeAt(nextNode,parent,position);
    }

    private void flipRootOrientation() {
        rootIsVertical = !rootIsVertical;
    }
}
