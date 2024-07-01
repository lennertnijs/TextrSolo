package com.textr.filebuffer;

import com.textr.view.TextListener;

public class MockTextListener implements TextListener {

    private TextUpdate textUpdate;

    @Override
    public void doUpdate(TextUpdate t) {
        this.textUpdate = t;
    }

    public TextUpdate getTextUpdate(){
        return textUpdate;
    }
}
