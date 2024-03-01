package com.Textr.ViewDrawer;

import com.Textr.FileBuffer.Text;
import com.Textr.Terminal.TerminalService;
import com.Textr.Validator.Validator;
import com.Textr.View.View;

public final class ViewDrawer implements IViewDrawer {

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
        Validator.notNull(view, "Cannot draw a null View.");
        Validator.notNull(text, "Cannot draw the View because the given Text is null.");
        Validator.notNull(statusBar, "Cannot draw the View because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = text.getLines();
        for(int i = view.getAnchor().getY(); i < Math.min(lines.length, view.getAnchor().getY() + height - 1); i++){
            if(lines[i].length() > view.getAnchor().getX()){
                TerminalService.printText(x, startY, lines[i].substring(view.getAnchor().getX(), Math.min(view.getAnchor().getX() + view.getDimensions().getWidth(), lines[i].length())));
            }
            startY++;
        }
        TerminalService.printText(x, maxY, statusBar.substring(0, Math.min(view.getDimensions().getWidth(), statusBar.length())));
    }
}
