package com.Textr.Tree;

import com.Textr.Util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a Generic Node that stores a value.
 * @param <T> The type
 */
public final class Node<T> {

    private Node<T> parent = null;
    private final T t;
    private final List<Node<T>> children = new ArrayList<>();


    /**
     * The constructor for a generic Node.
     * @param t The value.
     */
    public Node(T t){
        this.t = t;
    }

    /**
     * @return True if this Node contains a value that is not null.
     */
    public boolean hasValue(){
        return t != null;
    }

    /**
     * @return True if this Node has a parent.
     */
    public boolean hasParent(){
        return parent != null;
    }

    /**
     * @return True if this Node has any children.
     */
    public boolean hasChildren(){
        return !children.isEmpty();
    }

    /**
     * @return Returns this Node's parent. If it has no parent, returns null.
     */
    public Node<T> getParent(){
        return parent;
    }

    /**
     * @return Returns this Node's value. If it has no value, returns null.
     */
    public T getValue(){
        return t;
    }

    /**
     * @return A List of this Node's children. If it has no children, the List is empty.
     */
    public List<Node<T>> getChildren(){
        return children;
    }

    public boolean hasSingleChild(){
        return this.children.size() == 1;
    }

    /**
     * Adds the given Node as a child of this Node. Also sets the parent relationship.
     * @param child The child. Cannot be null.
     *
     * @throws IllegalArgumentException If the given child is null.
     */
    public void addChild(Node<T> child){
        Validator.notNull(child, "Cannot add a null child Node.");
        children.add(child);
        child.parent = this;
    }

    /**
     * Adds the given Node as a child of this Node. Also sets the parent relationship.
     * @param child The child. Cannot be null.
     *
     * @throws IllegalArgumentException If the given child is null.
     */
    public void addChildAt(Node<T> child, int position) {
        Validator.notNull(child, "Cannot add a null child Node.");
        children.add(position, child);
        child.parent = this;
    }


    /**
     * Adds the given List of Nodes as children of this Node. Also sets the parent relationship.
     * @param children The children Nodes. Cannot be null or contain a null.
     *
     * @throws IllegalArgumentException If the given list of children is or contains null.
     */
    public void addChildren(List<Node<T>> children){
        Validator.notNull(children, "Cannot add a null List of child Nodes.");
        this.children.addAll(children);
        for(Node<T> child : children){
            Validator.notNull(child, "Cannot add a null List of child Nodes.");
            child.parent = this;
        }
    }

    /**
     * Removes the child Node from this Node. Also removes the entire subtree underneath the removed child Node.
     * @param child The child Node. Cannot be null.
     *
     * @throws IllegalArgumentException If the given Node is null.
     */
    public void removeChild(Node<T> child){
        Validator.notNull(child, "Cannot remove a null child Node.");
        children.remove(child);
    }

    /**
     * Checks whether this Node and the given Node are siblings, i.e. they share the same parent.
     * @param node The second Node. Cannot be null.
     *
     * @return True if siblings, false otherwise.
     * @throws IllegalArgumentException If the given Node is null.
     */
    public boolean isSiblingWith(Node<T> node){
        Validator.notNull(node, "Cannot check sibling relationship with a null Node.");
        if(!this.hasParent() || !node.hasParent()){
            return false;
        }
        return this.parent.equals(node.parent);
    }

    /**
     * Replaces the old Node with the new Node. Also replaces the entire subtree.
     * @param oldChild The old Node. Cannot be null. Must be a child of this Node.
     * @param newChild The new Node. Cannot be null.
     *
     * @throws IllegalArgumentException If either Nodes are null.
     * @throws NoSuchElementException If the given old Node does not exist within this Node's children.
     */
    public void replaceChild(Node<T> oldChild, Node<T> newChild){
        Validator.notNull(oldChild, "Cannot replace a null Node.");
        Validator.notNull(newChild, "Cannot replace by a null Node.");
        int index = this.children.indexOf(oldChild);
        if(index != -1){
            this.children.set(index, newChild);
            newChild.parent= this;
            return;
        }
        throw new NoSuchElementException("Could not replace the Node, because it does not exist.");
    }

    /**
     * Compares this Node to the given Object and returns True if they're equal.
     * Equality means they have the same value, as well as their subtrees are equal.
     * @param o The object
     *
     * @return True if equal, false otherwise.
     */
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

    /**
     * @return The hash code of this Node.
     */
    @Override
    public int hashCode(){
        int result = t == null ? 0 : t.hashCode();
        for(Node<T> child : children){
            result = 31 * result + child.hashCode();
        }
        return result;
    }

    /**
     * @return The String representation of this Node.
     */
    @Override
    public String toString(){
        String viewStr = t == null ? "null" : t.toString();
        return String.format("Node[Value = %s, Children = %s]", viewStr, children);
    }
}
