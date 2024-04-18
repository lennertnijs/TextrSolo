package com.textr.filebuffer;

import com.textr.util.FixedPoint;
import com.textr.util.Point;

public interface TextListener {
    void update(TextUpdateReference update, ITextSkeleton structure);
}
