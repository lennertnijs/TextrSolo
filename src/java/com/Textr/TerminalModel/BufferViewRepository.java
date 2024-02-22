package com.Textr.TerminalModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BufferViewRepository {

    private final List<BufferView> bufferViews = new ArrayList<>();
    protected BufferViewRepository(){
    }

    protected void addBufferView(BufferView bufferView){
        Objects.requireNonNull(bufferView, "Cannot add a null bufferView to the repository.");
        bufferViews.add(bufferView);
    }

    protected List<BufferView> getBufferViews(){
        return this.bufferViews;
    }
}
