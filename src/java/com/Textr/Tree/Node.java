package com.Textr.Tree;

import com.Textr.Validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Node<T> {

    private Node<T> parent = null;
    private final T t;
    private final List<Node<T>> children = new ArrayList<>();


    public Node(T t){
        this.t = t;
    }

    public boolean hasValue(){
        return t != null;
    }


    public boolean hasParent(){
        return parent != null;
    }

    public Node<T> getParent(){
        return parent;
    }

    public Optional<T> getValue(){
        return t == null ? Optional.empty() : Optional.of(t);
    }

    public boolean hasChildren(){
        return children.size() == 0;
    }

    public List<Node<T>> getChildren(){
        return children;
    }

    public void setParent(Node<T> parent){
        Validator.notNull(parent, "Cannot add a null Node as a parent.");
        this.parent = parent;
    }

    public void addChildren(List<Node<T>> children){
        Validator.notNull(children, "Cannot add a null List of child Nodes.");
        for(Node<T> child : children){
            Validator.notNull(child, "Cannot add a null List of child Nodes.");
        }
        this.children.addAll(children);
    }

    public void addChild(Node<T> child){
        Validator.notNull(child, "Cannot add a null child Node.");
        children.add(child);
    }

    public void removeChild(Node<T> child){
        Validator.notNull(child, "Cannot remove a null child Node.");
        children.remove(child);
    }

    public boolean isSiblingWith(Node<T> node){
        Validator.notNull(node, "Cannot check sibling relationship with a null Node.");
        if(this.hasParent()){
            return this.getParent().getChildren().contains(node);
        }
        throw new IllegalStateException("Cannot check for sibling relationship with the root.");
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Node<?> node)){
            return false;
        }
        boolean equalValues = (t != null && node.t != null) ? t.equals(node.t) : t == node.t;
        return equalValues && children.equals(node.children);
    }

    @Override
    public int hashCode(){
        int result = t == null ? 0 : t.hashCode();
        for(Node<T> child : children){
            result = 31 * result + child.hashCode();
        }
        return result;
    }
    @Override
    public String toString(){
        String viewStr = t == null ? "null" : t.toString();
        String childrenStr = children.toString();
        return String.format("Node[View = %s, Children = %s]", viewStr, childrenStr);
    }
}
