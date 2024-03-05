package com.Textr.Tree;

import com.Textr.Validator.Validator;

import java.util.NoSuchElementException;

public final class Tree<T> {

    private final Node<T> root;

    public Tree(){
        this.root = new Node<>(null);
    }

    public Node<T> getRoot(){
        return root;
    }

    public int getSize(){
        return getSizeBFS(root, 0);
    }

    private int getSizeBFS(Node<T> node, int count){
        for(Node<T> child : node.getChildren()){
            if(child.hasValue()){
                count += 1;
            }
            count = getSizeBFS(child, count);
        }
        return count;
    }

    public boolean containsValue(T t){
        Validator.notNull(t, "Cannot check whether the Tree contains a null Value.");
        return containsValueBFS(t, root);
    }

    private boolean containsValueBFS(T t, Node<T> current){
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().get().equals(t)){
                return true;
            }
            boolean result = containsValueBFS(t, child);
            if(result){
                return true;
            }
        }
        return false;
    }


    public boolean contains(Node<T> node){
        Validator.notNull(node, "Cannot check whether the Tree contains a null Node.");
        return containsBFS(node, root);
    }

    private boolean containsBFS(Node<T> goal, Node<T> current){
        for(Node<T> child : current.getChildren()){
            if(child.equals(goal)){
                return true;
            }
            boolean result = containsBFS(goal, child);
            if(result){
                return true;
            }
        }
        return false;
    }


    public int getDepthOfValue(T t){
        Validator.notNull(t, "Cannot look for a null Value.");
        if(!containsValue(t)){
            throw new NoSuchElementException("No Node with the given value exists in the Tree.");
        }
        return getDepthOfValueDFS(t, root, 0);
    }

    private int getDepthOfValueDFS(T t, Node<T> current, int depth){
        depth += 1;
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().get().equals(t)){
                return depth;
            }
            int d = getDepthOfValueDFS(t, child, depth);
            if(d != -1){
                return d;
            }
        }
        return -1;
    }

    public int getDepth(Node<T> node){
        Validator.notNull(node, "Cannot look for a null Node.");
        if(!contains(node)){
            throw new IllegalArgumentException("Cannot check the depth for a Node that does not exist.");
        }
        return getDepthDFS(root, node, 0);
    }


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

    public Node<T> getNodeByValue(T t){
        Validator.notNull(t, "Cannot get a Tree Node with a null value.");
        Node<T> node = findNodeByValueDFS(t, root);
        if(node == null){
            throw new NoSuchElementException("No matching element was found.");
        }
        return node;
    }

    private Node<T> findNodeByValueDFS(T t, Node<T> current){
        for(Node<T> child : current.getChildren()){
            boolean hasValue = child.getValue().isPresent();
            boolean isMatch = child.getValue().get().equals(t);
            if(hasValue && isMatch){
                return child;
            }
            boolean hasChildren = child.getChildren().size() != 0;
            if(hasChildren){
                Node<T> node = findNodeByValueDFS(t, child);
                if(node != null){
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Adds the Node as a child of the root.
     * @param child The child Node. Cannot be null
     *
     * @throws IllegalArgumentException If the given Node is null.
     */
    public void addChildToRoot(Node<T> child){
        Validator.notNull(child, "Cannot add a null Node as a child to the root of the Tree.");
        root.addChild(child);
        child.setParent(root);
    }

    public void addChildToNode(Node<T> child, Node<T> parent){
        Validator.notNull(child, "Cannot add a null Node as a child.");
        Validator.notNull(parent, "Cannot add a child Node to a null parent Node.");
        parent.addChild(child);
        child.setParent(parent);
    }


    public void removeFromValue(T t){
        Validator.notNull(t, "Cannot remove a Node of a null Value.");
        removeChildFromValue(t, root);
    }

    private void removeChildFromValue(T t, Node<T> current){
        for(Node<T> child : current.getChildren()){
            if(child.hasValue() && child.getValue().get().equals(t)){
                current.getChildren().remove(child);
                return;
            }
            removeChildFromValue(t, child);
        }
    }

    public void remove(Node<T> remove){
        Validator.notNull(remove, "Cannot remove a null Node.");
        removeChild(remove, root);
    }

    private void removeChild(Node<T> remove, Node<T> current){
        for(Node<T> child : current.getChildren()){
            if(child.equals(remove)){
                current.getChildren().remove(child);
                return;
            }
            removeChild(remove, child);
        }
    }
}
