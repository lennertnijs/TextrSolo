package com.Textr.Tree;

import com.Textr.Validator.Validator;
import com.Textr.View.View;
import com.Textr.ViewRepo.IViewRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewTreeRepo implements IViewRepo {


    private Tree<Integer> tree;
    private List<View> views;

    public ViewTreeRepo(){
        views = new ArrayList<>();
        tree = new Tree<>();
    }



    public Tree<Integer> getTree(){
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
        tree.addChildToRoot(new Node<>(view.getId()));
        views.add(view);
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

    public boolean contains(int viewId){
        boolean existsInList = views.stream().anyMatch(e -> e.getId() == viewId);
        boolean existsInTree = tree.containsValue(viewId);
        if(existsInTree != existsInList){
            throw new IllegalStateException("The element did exist in 1 of them, but not the other.");
        }
        return existsInList;
    }

    public int getSize(){
        int treeSize = tree.getSizeValuesOnly();
        int listSize = views.size();
        if(treeSize != listSize){
            throw new IllegalStateException("The size of the tree and the list are not equal.");
        }
        return treeSize;
    }

    public View get(int viewId){
        for(View view : views){
            if(view.getId() == viewId){
                return view;
            }
        }
        throw new NoSuchElementException();
    }

    public View getByBufferId(int bufferId){
        for(View view : views){
            if(view.getFileBufferId() == bufferId){
                return view;
            }
        }
        throw new NoSuchElementException("No view with that buffer id exists.");
    }

    public List<View> getAll(){
        return views;
    }

    public void remove(int viewId){
        if(tree.containsValue(viewId) && views.stream().anyMatch(e -> e.getId() == viewId)){
            tree.removeFromValue(viewId);
            views.removeIf(e -> e.getId() == viewId);
        }
    }

    public void removeAll(){
        views = new ArrayList<>();
        tree= new Tree<>();
    }

    public void rotateClockWise(int viewId, int nextViewId){
        Node<Integer> node1 = tree.getNodeByValue(viewId);
        Node<Integer> node2 = tree.getNodeByValue(nextViewId);
        if(node1.isSiblingWith(node2)){
            rotateSiblingsClockWise(node1, node2);
            return;
        }
        rotateNonSiblingClockWise(node1, node2);
    }

    private void rotateSiblingsClockWise(Node<Integer> sibling1, Node<Integer> sibling2){
        Node<Integer> parent = sibling1.getParent();
        //parent.removeChild(sibling1);
        parent.removeChild(sibling2);
        Node<Integer> emptyNode = new Node<>(null);
        emptyNode.addChild(sibling1);
        emptyNode.addChild(sibling2);
        parent.replaceChild(sibling1, emptyNode);
    }

    private void rotateNonSiblingClockWise(Node<Integer> node1, Node<Integer> node2){
        Node<Integer> parentOfFirst = node1.getParent();
        node2.getParent().removeChild(node2);
        parentOfFirst.addChild(node2);
    }

    public int getAmountAtDepth(int depth){
        int count = 0;
        for(View view: views){
            if(tree.getDepthOfValue(view.getId()) == depth){
                count++;
            }
        }
        return count;
    }

    public List<View> getViewsAtDepth(int depth){
        List<View> views1 = new ArrayList<>();
        for(View view: views){
            if(tree.getDepthOfValue(view.getId()) == depth){
                views1.add(view);
            }
        }
        return views1;
    }

    public List<Integer> getAllValuesAtDepth(int depth){
        return tree.getAllValuesAtDepth(depth);
    }
}
