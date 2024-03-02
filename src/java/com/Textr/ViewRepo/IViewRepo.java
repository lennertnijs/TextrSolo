package com.Textr.ViewRepo;

import com.Textr.View.View;

import java.util.List;

public interface IViewRepo {
    boolean contains(int id);
    int getSize();
    View get(int id);
    View getByBufferId(int fileBufferId);
    List<View> getAll();
    void add(View view);
    void addAll(List<View> views);
    void remove(int id);
    void removeAll();
}
