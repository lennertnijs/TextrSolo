package com.textr.filebuffer;

import com.textr.util.Point;

import java.util.List;

public interface ICursor {
    int getInsertIndex();
    Point getInsertPoint();
    void setInsertIndex(int index, List<Integer> lengths);
    void setInsertPoint(Point point, List<Integer> lengths);
}
