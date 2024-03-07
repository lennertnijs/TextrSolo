package com.Textr.Tree;

import com.Textr.Util.Validator;
import com.Textr.View.View;
import com.Textr.ViewRepo.IViewRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewTreeRepo implements IViewRepo {


    private Tree<View> tree;

    private View Active;
    public ViewTreeRepo(){
        tree = new Tree<>();
    }

    private void setActive(View view) {
        this.Active = view;
    }
    private View getActive() {
        return  Active;
    }

    public Tree<View> getTree(){
        return tree;
    }

    /**
     * Stores the given View.
     * @param view The View. Cannot be null.
     *
     * @throws IllegalArgumentException If the given view is null.
     */
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
    public void remove(int id) {
        List<View> views = tree.getAllValues();

        for(View view : views){
            if(view.getId() == id){
                tree.remove(view);
            }
        }
    }

    public boolean contains(View view){
        return tree.contains(view);

    }

    @Override
    public boolean contains(int id) {
        List<View> views = tree.getAllValues();

        for(View view : views){
            if(view.getId() == id){
                return true;
            }
        }
        return false;
    }

    public int getSize(){
        return tree.getSizeValuesOnly();

    }

    public View get(int viewId){
        List <View> views = tree.getAllValues();
        for(View view : views){
            if(view.getId() == viewId){
                return view;
            }
        }
        throw new NoSuchElementException();
    }

    public View getByBufferId(int bufferId){
        List<View> views = tree.getAllValues();
        for(View view : views){
            if(view.getFileBufferId() == bufferId){
                return view;
            }
        }
        throw new NoSuchElementException("No view with that buffer id exists.");
    }

    public List<View> getAll(){
        return tree.getAllValues();
    }

    public void remove(View view){
        if(tree.contains(view)){
            tree.remove(view);
        }
    }

    public void removeAll(){
        tree= new Tree<>();
    }
    @Override
    public void rotate(boolean clockwise, int id){
        View view = get(id);
        rotateWithNext(clockwise, tree.getNode(view));
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
