package com.textr.filebuffer;

import com.textr.filebufferV2.IText;

public interface TextListener {
    void update(TextUpdateReference update, IText text);
}
