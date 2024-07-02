package com.textr.view;

import com.textr.tree.Node;

import java.util.List;

public interface ViewRepository {

    boolean rootIsVertical();
    int getSize();
    View getActive();
    void setActive(View view);
    void add(View view);
    void addAll(List<View> views);
    void remove(View view);
    void removeAll();
    View get(int index);
    List<View> getAll();
    void setNextActive();
    void setPreviousActive();

    void addNextTo(View newView, View existing);

    void rotate(boolean clockwise);
    List<View> getAllAtDepth(int depth);

    Node<View> getRoot();

}
