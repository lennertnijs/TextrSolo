package com.textr.filebuffer;

import com.textr.util.Point;

public class TextUpdate {

    public final Point point;
    public final OperationType operationType;

    public TextUpdate(Point p, OperationType operationType){
        this.point = p;
        this.operationType = operationType;
    }
}
