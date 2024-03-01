package com.Textr.ViewRepo;

import com.Textr.View.View;

import java.util.List;

public interface IViewRepo {
    int getSize();
    View getByBufferId(int id);
    List<View> getAll();
    void add(View view);
}
