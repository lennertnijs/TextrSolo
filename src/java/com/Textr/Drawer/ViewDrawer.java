package com.Textr.Drawer;

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
     * @param statusBar The status bar. Cannot be null.
     *
     * @throws IllegalArgumentException If any parameter is null.
     */
    public static void draw(View view, String statusBar) {
        Validator.notNull(view, "Cannot draw a null View.");
        Validator.notNull(statusBar, "Cannot draw the View because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = view.getBuffer().getText().getLines();
        for(int i = view.getAnchor().getY(); i < Math.min(lines.length, view.getAnchor().getY() + height - 1); i++){
            // only need to draw if any text is in these columns
            if(lines[i].length() > view.getAnchor().getX()){
                int maxColIndex = Math.min(view.getAnchor().getX() + view.getDimensions().getWidth(), lines[i].length());
                TerminalService.printText(x, startY, lines[i].substring(view.getAnchor().getX(), maxColIndex));
            }
            startY++;
        }
        int maxStatusBarIndex = Math.min(view.getDimensions().getWidth(), statusBar.length());
        TerminalService.printText(x, maxY, statusBar.substring(0, maxStatusBarIndex));
    }
}
