package com.textr.drawer;

import com.textr.terminal.TerminalService;
import com.textr.util.Point;
import com.textr.util.Validator;
import com.textr.view.BufferView;
import com.textr.view.SnakeView;

import java.util.ArrayList;
import java.util.LinkedList;

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
    public static void draw(BufferView view, String statusBar) {
        Validator.notNull(view, "Cannot draw a null BufferView.");
        Validator.notNull(statusBar, "Cannot draw the BufferView because the status bar is null.");
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
        drawScrollBar(view);
    }

    /**
     * Draws the given view to the terminal.
     * @param view The view. Cannot be null.
     * @param statusBar The status bar. Cannot be null.
     *
     * @throws IllegalArgumentException If any parameter is null.
     */
    public static void draw(SnakeView view, String statusBar) {
        Validator.notNull(view, "Cannot draw a null BufferView.");
        Validator.notNull(statusBar, "Cannot draw the BufferView because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int baseY = view.getPosition().getY()+ height - 2;
        int maxY = view.getPosition().getY() + height - 1;
        if(view.getRunning()){ArrayList<Point> snake = view.getSnake();
            Point head = snake.remove(0);
            switch (view.getHeadOrientation()){
                case RIGHT -> TerminalService.printText(x+ head.getX(), baseY- head.getY(), ">");
                case LEFT ->  TerminalService.printText(x+ head.getX(), baseY- head.getY(), "<");
                case UP -> TerminalService.printText(x+ head.getX(), baseY- head.getY(), "^");
                case DOWN -> TerminalService.printText(x+ head.getX(), baseY- head.getY(), "v");
            }
            for(Point point : snake){
                TerminalService.printText(x+ point.getX(), baseY- point.getY(), "o");
            }
            ArrayList<Point> foods = view.getFoods();
            for(Point point : foods){
                TerminalService.printText(x+ point.getX(), baseY- point.getY(), "f");
            }
        }
        else
            TerminalService.printText(x, baseY, "GAME OVER - Press Enter to restart");
        int maxStatusBarIndex = Math.min(view.getDimensions().getWidth(), statusBar.length());
        TerminalService.printText(x, maxY, statusBar.substring(0, maxStatusBarIndex));
    }

    private static void drawScrollBar(BufferView view){
        int maxY = view.getBuffer().getText().getLineAmount() - 1;
        int currentY = view.getCursor().getInsertPoint().getY();
        int yBar = Math.round(((float)currentY / (float)maxY) * (view.getDimensions().getHeight() - 1));
        TerminalService.printText(view.getPosition().getX() + view.getDimensions().getWidth() - 1,
                                                            yBar + view.getPosition().getY(), "|");
    }


}
