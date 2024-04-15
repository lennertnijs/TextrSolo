package com.textr.filebuffer;

import com.textr.util.Point;

public interface Action {

    Point getPosition();
    void undo(Text text);
    void redo(Text text);
}
