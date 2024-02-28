package com.Textr.View;

import com.Textr.FileBuffer.Text;
import com.Textr.Terminal.TerminalService;

import java.util.Objects;

public class ViewDrawer implements IViewDrawer{

    public ViewDrawer(){

    }

    @Override
    public void drawView(View view, Text text) {
        Objects.requireNonNull(view);
        Objects.requireNonNull(text);
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = text.getLines();
        for(int i = 0; i < Math.min(lines.length, height - 1); i++){
            TerminalService.printText(x, startY++, lines[i]);
        }
        TerminalService.printText(x, maxY, "Status bar");
    }
}
