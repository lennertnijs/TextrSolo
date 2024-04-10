package com.textr.view;

import com.textr.tree.Node;

import java.util.List;

public interface IViewRepo {

    boolean rootIsVertical();
    int getSize();
    BufferView getActive();
    void setActive(BufferView view);
    void add(BufferView view);
    void addAll(List<BufferView> views);
    void remove(BufferView view);
    void removeAll();
    BufferView get(int index);
    List<BufferView> getAll();
    void setNextActive();
    void setPreviousActive();
    void rotate(boolean clockwise);
    List<BufferView> getAllAtDepth(int depth);

    Node getRoot();
}
