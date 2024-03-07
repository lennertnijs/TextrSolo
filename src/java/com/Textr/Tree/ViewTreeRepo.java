package com.Textr.Tree;

import com.Textr.Util.Validator;
import com.Textr.View.View;

import java.util.ArrayList;
import java.util.List;

public class ViewTreeRepo implements IViewRepo {


    private Tree<View> tree;
    private View active;


    public ViewTreeRepo(){
        tree = new Tree<>();
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
        active = tree.getNext(tree.getNode(active)).getValue();
    }

    @Override
    public void removeAll(){
        tree = new Tree<>();
    }
    @Override
    public void rotate(boolean clockwise){
        rotateWithNext(clockwise, tree.getNode(active));
    }


    public void rotateWithNext(boolean clockwise, Node<View> currentNode){
        Node<View> nextNode = tree.getNext(currentNode);
        if(nextNode!= null){
            Node<View> nextLeaf =  tree.getFirstLeaf(nextNode);
            if(currentNode.getParent().equals(nextLeaf.getParent())){
                rotateSiblings(currentNode, nextNode, clockwise);
            }
            else{
                rotateNonSibling(currentNode, nextLeaf, clockwise);
            }

        }
        else{
            String ding = "DING";
            //PING-sound
        }
    }
    private void rotateSiblings(Node<View> currentNode, Node<View> nextLeaf, boolean clockwise){
        Node<View> newsubLayout = new Node<View>(null);
        currentNode.getParent().replaceChild(currentNode,newsubLayout);
        View current = currentNode.getValue();
        View next = nextLeaf.getValue();
        if(clockwise && current.leftOff(next) || !clockwise && !current.leftOff(next)){
            tree.remove(currentNode);
            tree.addChildToNode(currentNode,newsubLayout);
            tree.remove(nextLeaf);
            tree.addChildToNode(nextLeaf, newsubLayout);
        }
        else {
            tree.remove(nextLeaf);
            tree.addChildToNode(nextLeaf, newsubLayout);
            tree.remove(currentNode);
            tree.addChildToNode(currentNode,newsubLayout);
        }
    }

    private void rotateNonSibling(Node<View> currentNode, Node<View> nextLeaf, boolean clockwise){
        View current = (View) currentNode.getValue();
        View next = (View) nextLeaf.getValue();
        if (clockwise && current.leftOff(next) || !clockwise && !current.leftOff(next)){
            tree.remove(nextLeaf);
            tree.addChildToNode(nextLeaf, currentNode.getParent());
        }
        else {
            Node <View> parent = currentNode.getParent();
            tree.remove(currentNode);
            tree.remove(nextLeaf);
            tree.addChildToNode(nextLeaf, parent);
            tree.addChildToNode(currentNode, parent);
        }
    }



    public int getAmountAtDepth(int depth){
        int count = 0;
        List<View> views = tree.getAllValues();
        for(View view: views){
            if(tree.getDepth(view) == depth){
                count++;
            }
        }
        return count;
    }

    public List<View> getViewsAtDepth(int depth){
        List<View> views = tree.getAllValues();
        List<View> views1 = new ArrayList<View>();
        for(View view: views){
            if(tree.getDepth(view) == depth){
                views1.add(view);
            }
        }
        return views1;
    }

    public List<View> getAllValuesAtDepth(int depth){
        return tree.getAllAtDepth(depth);
    }

}
