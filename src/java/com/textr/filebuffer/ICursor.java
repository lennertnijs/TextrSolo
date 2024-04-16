package com.textr.filebuffer;

import com.textr.util.Point;

public interface ICursor {
    int getInsertIndex();
    Point getInsertPoint();
    void setInsertIndex(int index, ITextSkeleton skeleton);
    void setInsertPoint(Point point, ITextSkeleton skeleton);
    void moveRight(ITextSkeleton skeleton);
    void moveLeft(ITextSkeleton skeleton);
}
