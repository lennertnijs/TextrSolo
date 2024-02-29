package com.Textr.ViewDrawer;

import com.Textr.FileBuffer.Text;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.View;

import java.util.Objects;

public class ViewDrawer implements IViewDrawer {

    public ViewDrawer(){
    }

    /**
     * Draws a {@link View}.
     * @param view The view
     * @param text The text of the view's filebuffer
     * @param statusBar The status bar.
     * @throws IllegalArgumentException If any of them are null.
     */
    @Override
    public void drawView(View view, Text text, String statusBar) {
        validateParameters(view, text, statusBar);
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = text.getLines();
        for(int i = 0; i < Math.min(lines.length, height - 1); i++){
            TerminalService.printText(x, startY++, lines[i].substring(0, Math.min(view.getDimensions().getWidth(), lines[i].length())));
        }
        TerminalService.printText(x, maxY, statusBar.substring(0, Math.min(view.getDimensions().getWidth(), statusBar.length())));
    }

    private void validateParameters(View view, Text text, String string){
        try{
            Objects.requireNonNull(view);
            Objects.requireNonNull(text);
            Objects.requireNonNull(string);
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Cannot draw a view. One of the parameters is null.");
        }
    }
}
