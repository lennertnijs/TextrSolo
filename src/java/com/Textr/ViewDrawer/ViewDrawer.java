package com.Textr.ViewDrawer;

import com.Textr.FileBuffer.Text;
import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Validator;
import com.Textr.View.View;

/**
 * Draws a view.
 */
public final class ViewDrawer{

    private ViewDrawer(){
    }

    /**
     * Draws the given view to the terminal.
     * @param view The view. Cannot be null.
     * @param text The text of the view's file buffer. Cannot be null.
     * @param statusBar The status bar. Cannot be null.
     *
     * @throws IllegalArgumentException If any parameter is null.
     */
    public static void draw(View view, Text text, String statusBar) {
        Validator.notNull(view, "Cannot draw a null View.");
        Validator.notNull(text, "Cannot draw the View because the given Text is null.");
        Validator.notNull(statusBar, "Cannot draw the View because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = text.getLines();
        for(int i = view.getAnchor().getY(); i < Math.min(lines.length, view.getAnchor().getY() + height - 1); i++){
            // only need to draw if any text is in these columns
            if(lines[i].length() > view.getAnchor().getX()){
                int maxLineIndex = Math.min(view.getAnchor().getX() + view.getDimensions().getWidth(), lines[i].length());
                TerminalService.printText(x, startY, lines[i].substring(view.getAnchor().getX(), maxLineIndex));
            }
            startY++;
        }
        int maxStatusBarIndex = Math.min(view.getDimensions().getWidth(), statusBar.length());
        TerminalService.printText(x, maxY, statusBar.substring(0, maxStatusBarIndex));
    }
}
