package com.Textr.Tree;

import java.util.List;

public interface ITree<T> {

    Node<T> getRoot();
    int getSize();
    int getSizeValuesOnly();
    boolean contains(T t);
    boolean contains(Node<T> node);
    int getDepth(T t);
    int getDepth(Node<T> node);
    Node<T> getNode(T t);
    void addChildToRoot(Node<T> child);
    void addChildToNode(Node<T> child, Node<T> parent);
    void remove(T t);
    void remove(Node<T> node);
    List<T> getAllValues();
    List<T> getAllAtDepth(int depth);
}
