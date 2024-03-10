package com.Textr.Tree;

import com.Textr.Util.Validator;
import com.Textr.View.View;

import java.util.List;

public class ViewTreeRepo implements IViewRepo {


    private Tree<View> tree;
    private View active;
    private boolean rootIsVertical;


    public ViewTreeRepo(){
        tree = new Tree<>();
        rootIsVertical = true;
    }


    private void flipRootOrientation() {
        rootIsVertical = !rootIsVertical;
    }

    public boolean rootIsVertical(){
        return rootIsVertical;
    }
    @Override
    public void setActive(View view) {
        this.active = view;
    }

    @Override
    public View getActive() {
        return  active;
    }

    @Override
    public Tree<View> getTree(){
        return tree;
    }

    /**
     * Stores the given View.
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
     * Stores the given List of Views in the repository.
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

    @Override
    public void remove(View view) {
        tree.remove(view);
    }

    @Override
    public int getSize() {
        return tree.getSizeValuesOnly();

    }

    @Override
    public List<View> getAll(){
        return tree.getAllValues();
    }

    @Override
    public void setNextActive(){
        active = tree.getNextValue(active);
    }

    @Override
    public void setPreviousActive(){
        active = tree.getPreviousValue(active);
    }

    @Override
    public void removeAll(){
        tree = new Tree<>();
    }


    @Override
    public void rotate(boolean clockwise){
        rotateWithNext(clockwise);
        tree.restoreInvariants();
        if(tree.getRoot().hasChildren()&& tree.getRoot().getChildren().size() == 1){
            flipRootOrientation();
        }
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
        boolean swap = !noSwap;
        Node<View> parent = currentNode.getParent();
        tree.remove(nextNode);
        tree.addChildToNode(nextNode, parent);
        if (swap){
            tree.remove(currentNode);
            tree.addChildToNode(currentNode, parent);
        }
    }

    public List<View> getAllValuesAtDepth(int depth){
        return tree.getAllAtDepth(depth);
    }
}
