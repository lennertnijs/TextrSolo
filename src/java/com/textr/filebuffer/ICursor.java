package com.textr.filebuffer;

import com.textr.filebufferV2.IText;
import com.textr.util.Direction;
import com.textr.util.Point;

public interface ICursor {
    int getInsertIndex();
    Point getInsertPoint();
    void setInsertIndex(int index, IText text);
    void setInsertPoint(Point point, IText text);
    void move(Direction direction, IText text);
}
