package com.Textr.Tree;

import com.Textr.Util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Generic Tree class that can optionally store an Object of type T.
 * @param <T> The Generic Type of the objects to be stored.
 */
public final class Tree<T> implements ITree<T>{

    private final Node<T> root;

    /**
     * Tree Constructor. Starts as a single root Node with no children, parent, or value.
     */
    public Tree(){
        this.root = new Node<>(null);
    }

    /**
     * @return The root of this Tree.
     */
    public Node<T> getRoot(){
        return root;
    }

    /**
     * @return The size of this Tree. This includes the Nodes with no value.
     */
    public int getSize(){
        return getSizeDFS(root);
    }

    /**
     * @return The size of this Tree, only counting the Nodes with a value.
     */
    public int getSizeValuesOnly(){
        return getSizeValuesOnlyDFS(root);
    }



    /**
     * Checks whether a Node with the given parameter as its value exists in the Tree. Returns true if so.
     * @param t The value. Cannot be null.
     *
     * @return True if a Node with the value exists, false otherwise.
     * @throws IllegalArgumentException If the given parameter is null.
     */
    public boolean contains(T t){
        Validator.notNull(t, "Cannot check whether the Tree contains a null Value.");
        return containsValueDFS(root, t);
    }

    /**
     * Checks whether the given Node exists in the Tree. Returns true if so.
     * @param node The Node. Cannot be null.
     *
     * @return True the Node was found in the tree, false otherwise.
     * @throws IllegalArgumentException If the given Node is null.
     */
    public boolean contains(Node<T> node){
        Validator.notNull(node, "Cannot check whether the Tree contains a null Node.");
        return containsDFS(root, node);
    }



    /**
     * Finds and returns the depth of the given value in the Tree.
     * @param t The value. Cannot be null.
     *
     * @return The depth of the value. (0-based)
     * @throws IllegalArgumentException If the given value is null.
     * @throws NoSuchElementException If the given value does not reside in a Node in the Tree.
     */
    public int getDepth(T t){
        Validator.notNull(t, "Cannot look for a null Value.");
        if(!contains(t)){
            throw new NoSuchElementException("No Node with the given value exists in the Tree.");
        }
        return getDepthOfValueDFS(root, t, 0);
    }


    /**
     * Finds and returns the depth of the given Node in the Tree.
     * @param node The Node. Cannot be null.
     *
     * @return The depth of the Node. (0-based)
     * @throws IllegalArgumentException If the given Node is null.
     * @throws NoSuchElementException If the given Node does not reside in the Tree.
     */
    public int getDepth(Node<T> node){
        Validator.notNull(node, "Cannot look for a null Node.");
        if(!contains(node)){
            throw new IllegalArgumentException("Cannot check the depth for a Node that does not exist.");
        }
        return getDepthDFS(root, node, 0);
    }


    /**
     * Finds and returns the Node with the given value.
     * @param t The value. Cannot be null.
     *
     * @return The Node in which the value resides.
     * @throws IllegalArgumentException If the given value is null.
     * @throws NoSuchElementException If this value does not reside inside the tree.
     */
    public Node<T> getNode(T t){
        Validator.notNull(t, "Cannot get a Tree Node with a null value.");
        if(!contains(t)){
            throw new NoSuchElementException("No matching element was found.");
        }
        return findNodeByValueDFS(root, t);
    }



    /**
     * Adds the Node as a child of the root. Also sets the parent of the Node as the root Node.
     * @param child The child Node. Cannot be null
     *
     * @throws IllegalArgumentException If the given Node is null.
     * @throws IllegalStateException If a Node with the given value already exists in the Tree.
     */
    public void addChildToRoot(Node<T> child){
        Validator.notNull(child, "Cannot add a null Node as a child to the root of the Tree.");
        if(child.hasValue() && contains(child.getValue())){
            throw new IllegalStateException("A Node with the given value already exists in the Tree.");
        }
        root.addChild(child);
    }

    /**
     * Adds the Node as a child of the given root. Also sets the parent of the child Node to the parent.
     * @param child The child Node. Cannot be null.
     * @param parent The parent Node. Cannot be null.
     *
     * @throws IllegalArgumentException If the child or parent Node are null.
     * @throws IllegalStateException If the child's value already resides in the Tree.
     */
    public void addChildToNode(Node<T> child, Node<T> parent){
        Validator.notNull(child, "Cannot add a null Node as a child.");
        Validator.notNull(parent, "Cannot add a child Node to a null parent Node.");
        if(child.hasValue() && contains(child.getValue())){
            throw new IllegalStateException("A Node with the given value already exists in the Tree.");
        }
        parent.addChild(child);
    }

    /**
     * Adds the Node as a child of the given node at the given position in the children. Also sets the parent of the child Node to the parent.
     * @param child The child Node. Cannot be null.
     * @param parent The parent Node. Cannot be null.
     *
     * @throws IllegalArgumentException If the child or parent Node are null.
     * @throws IllegalStateException If the child's value already resides in the Tree.
     */
    private void addChildToNodeAt(Node<T> child, Node<T> parent, int position) {
        Validator.notNull(child, "Cannot add a null Node as a child.");
        Validator.notNull(parent, "Cannot add a child Node to a null parent Node.");
        if(child.hasValue() && contains(child.getValue())){
            throw new IllegalStateException("A Node with the given value already exists in the Tree.");
        }
        parent.addChildat(child, position);
    }
    /**
     * Removes the Node with the given value from the Tree. Also removes the entire sub Tree under it.
     * @param t The value. Cannot be null.
     *
     * @throws IllegalArgumentException If the given value is null.
     */
    public void remove(T t){
        Validator.notNull(t, "Cannot remove a Node of a null Value.");
        removeNodeFromValueDFS(root, t);
    }


    /**
     * Removes the Node from the Tree. Also removes the entire sub Tree under it.
     * @param node The Node. Cannot be null.
     *
     * @throws IllegalArgumentException If the given Nodes is null.
     */
    public void remove(Node<T> node){
        Validator.notNull(node, "Cannot remove a null Node.");
        removeNodeDFS(root, node);
    }


    /**
     * Returns all the values (not null) in the Tree, from left to right, top to bottom. (DFS)
     *
     * @return All values in order from left to right, top to bottom.
     */
    public List<T> getAllValues(){
        return getAllValuesDFS(root);
    }


    /**
     * Finds and returns ALL the values at the given depth. ALSO includes empty values, aka null.
     * @param depth The depth
     *
     * @return The List of values.
     */
    public List<T> getAllAtDepth(int depth){
        return getAllValuesAtDepthBFS(root, depth);
    }



    /**
     * Private method that calculates the total size of the Tree using DFS.
     * @param current The current Node. Cannot be null.
     *
     * @return The amount of Nodes currently tracked.
     */
    private int getSizeDFS(Node<T> current){
        int count = 0;
        for(Node<T> child : current.getChildren()){
            count++;
            count += getSizeDFS(child);
        }
        return count;
    }

    /**
     * Private method that calculates the total amount of Nodes with a value in the Tree using DFS.
     * @param current The current Node. Cannot be null.
     *
     * @return The amount of Nodes with a value currently tracked.
     */
    private int getSizeValuesOnlyDFS(Node<T> current){
        int count = 0;
        for(Node<T> child : current.getChildren()){
            if(child.hasValue()){
                count += 1;
            }
            count += getSizeValuesOnlyDFS(child);
        }
        return count;
    }

    /**
     * Private method that uses DFS to search through the Tree for a Node with the given value. Returns true if a match was found.
     * @param current The current Node. Cannot be null.
     * @param t The value. Cannot be null.
     *
     * @return True if a match has yet been found. False otherwise.
     */
    private boolean containsValueDFS(Node<T> current, T t){
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().equals(t)){
                return true;
            }
            if(containsValueDFS(child, t)){
                return true;
            }
        }
        return false;
    }

    /**
     * Private method that uses DFS to search through the Tree for the given goal Node. Returns true if a match was found.
     * @param current The current Node. Cannot be null.
     * @param goal The goal Node. Cannot be null.
     *
     * @return True if a match has yet been found. False otherwise.
     */
    private boolean containsDFS(Node<T> current, Node<T> goal){
        for(Node<T> child : current.getChildren()){
            if(child.equals(goal)){
                return true;
            }
            boolean result = containsDFS(child, goal);
            if(result){
                return true;
            }
        }
        return false;
    }

    /**
     * Private method that uses DFS to find the depth of the Node with the given value T. 0-based. Returns this depth.
     * @param t The value. Cannot be null.
     * @param current The current Node. Cannot be null.
     * @param depth The current depth. Cannot be negative.
     *
     * @return The depth of the value.
     */
    private int getDepthOfValueDFS(Node<T> current, T t, int depth){
        depth += 1;
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().equals(t)){
                return depth;
            }
            int d = getDepthOfValueDFS(child, t, depth);
            if(d != -1){
                return d;
            }
        }
        return -1;
    }

    /**
     * Private method that uses DFS to find the depth of the Node. 0-based. Returns this depth.
     * @param goal The goal Node. Cannot be null.
     * @param current The current Node. Cannot be null.
     * @param depth The current depth. Cannot be negative.
     *
     * @return The depth of the Node.
     */
    private int getDepthDFS(Node<T> current, Node<T> goal, int depth){
        depth += 1;
        for(Node<T> child : current.getChildren()){
            if(goal.equals(child)){
                return depth;
            }
            int d = getDepthDFS(child, goal, depth);
            if(d != -1){
                return d;
            }
        }
        return -1;
    }

    /**
     * Private method that uses DFS to recursively look for the Node with the given value.
     * @param current The current Node. Cannot be null.
     * @param t The value. Cannot be null.
     *
     * @return The Node.
     */
    private Node<T> findNodeByValueDFS(Node<T> current, T t){
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().equals(t)){
                return child;
            }
            boolean hasChildren = child.getChildren().size() != 0;
            if(hasChildren){
                Node<T> node = findNodeByValueDFS(child, t);
                if(node != null){
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Private method that uses DFS to recursively look to remove the Node with the given value from the Tree.
     * @param t The value. Cannot be null.
     * @param current The current Node. Cannot be null.
     */
    private void removeNodeFromValueDFS( Node<T> current, T t){
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().equals(t)){
                current.getChildren().remove(child);
                return;
            }
            removeNodeFromValueDFS(child, t);
        }
    }

    /**
     * Private method that uses DFS to recursively look to remove the Node from the Tree.
     * @param goal The Node to remove. Cannot be null.
     * @param current The current Node. Cannot be null.
     */
    private void removeNodeDFS(Node<T> current, Node<T> goal){
        for(Node<T> child : current.getChildren()){
            if(child.equals(goal)){
                current.getChildren().remove(child);
                return;
            }
            removeNodeDFS(child, goal);
        }
    }


    private List<T> getAllValuesDFS(Node<T> current){
        List<T> values = new ArrayList<>();
        for(Node<T> child : current.getChildren()){
            if(child.hasValue()){
                values.add(child.getValue());
            }
            values.addAll(getAllValuesDFS(child));
        }
        return values;
    }

    private List<T> getAllValuesAtDepthBFS(Node<T> current, int depth){
        List<T> values = new ArrayList<>();
        for(Node<T> child : current.getChildren()){
            if(getDepth(child) == depth){
                T value = child.hasValue() ? child.getValue() : null;
                values.add(value);
            }
            values.addAll(getAllValuesAtDepthBFS(child, depth));
        }
        return values;
    }


    public Node<T> getNext(Node<T> current){
        List<Node<T>> siblings = current.getParent().getChildren();
        int position = siblings.indexOf(current);
        if(position+1==siblings.size()){
            if(current.getParent().getParent()!= null)
                return getNext(current.getParent());
            else
                return null;

        }
        else
            return siblings.get(position+1);
    }


    public Node<T> getFirstLeaf(Node<T> current){
        if(current.hasValue())
            return current;
        else
            return getFirstLeaf(current.getChildren().get(0));
    }
    public void restoreInvariants(){
        if(root.hasChildren()&& root.getChildren().size()==1){
            Node <T> onlyChild= root.getChildren().get(0);
            remove(onlyChild);
            for(Node<T> grandChild : onlyChild.getChildren()){
                addChildToRoot(grandChild);
            }
            flipRootOrientation();
        }
        restoreFromNode(root);
    }


    private void restoreFromNode(Node<T> node){
        if(node.hasChildren() && node.getChildren().size()==1){
            Node <T> onlyChild= node.getChildren().get(0);
            if(node.hasParent()){
                if(onlyChild.hasChildren()){
                    Node<T> parent = node.getParent();
                    int position = parent.getChildren().indexOf(node);
                    parent.removeChild(node);
                    for(Node<T> grandChild : onlyChild.getChildren()){
                        addChildToNodeAt(grandChild, parent, position);
                        position++;
                    }
                }
                else if (onlyChild.hasValue()){
                    node.getParent().replaceChild(node,onlyChild);
                }
            }
        }
        if (node.hasChildren()) {
            List<Node<T>> toRestore = new ArrayList<>(node.getChildren());
            for(Node<T> child : toRestore){
                restoreFromNode(child);
            }
        }

    }
    public T getNextValue(T t){
        List<T> valuesInOrder = getAllValues();
        int index = valuesInOrder.indexOf(t);
        if(index != -1){
            int nextIndex = (index + 1) % valuesInOrder.size();
            return valuesInOrder.get(nextIndex);
        }
        throw new NoSuchElementException("No element with value T was found.");
    }

    public T getPreviousValue(T t){
        List<T> valuesInOrder = getAllValues();
        int index = valuesInOrder.indexOf(t);
        if(index != -1){
            int nextIndex = (index - 1) > 0 ? (index - 1) : valuesInOrder.size() - 1;
            return valuesInOrder.get(nextIndex);
        }
        throw new NoSuchElementException("No element with value T was found.");
    }
}
