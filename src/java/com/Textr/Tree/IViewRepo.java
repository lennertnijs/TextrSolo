package com.Textr.Tree;

import com.Textr.View.View;

import java.util.List;

public interface IViewRepo {

    View getActive();
    void setActive(View view);
    Tree<View> getTree();
    void add(View view);
    void addAll(List<View> views);
    void remove(View view);
    int getSize();
    List<View> getAll();
    void removeAll();
    void rotate(boolean clockwise);
    void setNextActive();
    void setPreviousActive();
}
