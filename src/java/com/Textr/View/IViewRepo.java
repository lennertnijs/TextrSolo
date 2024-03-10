package com.Textr.View;

import com.Textr.View.View;

import java.util.List;

public interface IViewRepo {

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
    void rotate(boolean clockwise);
    List<View> getAllAtDepth(int depth);
}
