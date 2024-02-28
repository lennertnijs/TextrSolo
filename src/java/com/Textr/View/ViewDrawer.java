package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;

import java.util.Objects;

public class ViewDrawer implements IViewDrawer{

    public ViewDrawer(){

    }

    @Override
    public void drawView(View view, FileBuffer fileBuffer) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(fileBuffer);
        int startY = view.getPosition().getY();
        int maxY = startY + view.getDimensions().getHeight() - 1;
        String[] textLines = fileBuffer.getBufferText().getLines();
        for(int i = startY; i <= maxY; i++){
            String line = i == maxY ? "statusBar" : (i - startY) >= textLines.length ? "" : textLines[i - startY];

        }
    }
}
