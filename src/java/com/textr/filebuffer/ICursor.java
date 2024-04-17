package com.textr.filebuffer;

import com.textr.util.Direction;
import com.textr.util.Point;

public interface ICursor {
    int getInsertIndex();
    Point getInsertPoint();
    void setInsertIndex(int index, ITextSkeleton skeleton);
    void setInsertPoint(Point point, ITextSkeleton skeleton);
    void move(Direction direction, ITextSkeleton skeleton);
}
