package com.textr.drawer;

import com.textr.snake.GamePoint;
import com.textr.snake.IGameBoard;
import com.textr.terminal.TerminalService;
import com.textr.util.Point;
import com.textr.util.Validator;
import com.textr.view.BufferView;
import com.textr.view.SnakeView;

import java.util.List;
import java.util.Objects;

/**
 * Draws a view.
 */
public final class ViewDrawer{

    private final TerminalService terminal;

    public ViewDrawer(TerminalService terminal){
        Validator.notNull(terminal, "Cannot instantiate ViewDrawer with null TerminalService");
        this.terminal = terminal;
    }

    /**
     * Draws the given view to the terminal.
     * @param view The view. Cannot be null.
     * @param statusBar The status bar. Cannot be null.
     *
     * @throws IllegalArgumentException If any parameter is null.
     */
    public void draw(BufferView view, String statusBar) {
        Validator.notNull(view, "Cannot draw a null BufferView.");
        Validator.notNull(statusBar, "Cannot draw the BufferView because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int startY = view.getPosition().getY();
        int maxY = startY + height - 1;
        String[] lines = view.getBufferEditor().getFileBuffer().getText().getLines();
        for(int i = view.getAnchor().getY(); i < Math.min(lines.length, view.getAnchor().getY() + height - 1); i++){
            // only need to draw if any text is in these columns
            if(lines[i].length() > view.getAnchor().getX()){
                int maxColIndex = Math.min(view.getAnchor().getX() + view.getDimensions().getWidth(), lines[i].length());
                terminal.printText(x, startY, lines[i].substring(view.getAnchor().getX(), maxColIndex));
            }
            startY++;
        }
        int maxStatusBarIndex = Math.min(view.getDimensions().getWidth(), statusBar.length());
        terminal.printText(x, maxY, statusBar.substring(0, maxStatusBarIndex));
        drawScrollBar(view);
    }

    /**
     * Draws the given view to the terminal.
     * @param view The view. Cannot be null.
     * @param statusBar The status bar. Cannot be null.
     *
     * @throws IllegalArgumentException If any parameter is null.
     */
    public void draw(SnakeView view, String statusBar) {
        Validator.notNull(view, "Cannot draw a null BufferView.");
        Validator.notNull(statusBar, "Cannot draw the BufferView because the status bar is null.");
        int height = view.getDimensions().getHeight();
        int x = view.getPosition().getX();
        int baseY = view.getPosition().getY()+ height - 2;
        int maxY = view.getPosition().getY() + height - 1;
        IGameBoard gameBoard = view.getGameBoard();
        if(view.gameIsRunning()){
            List<GamePoint> snake = gameBoard.getSnakePoints();
            GamePoint head = snake.remove(0);
            switch (gameBoard.getSnakeDirection()){
                case RIGHT -> terminal.printText(x + head.x(), baseY- head.y(), ">");
                case LEFT ->  terminal.printText(x+ head.x(), baseY- head.y() , "<");
                case UP -> terminal.printText(x+ head.x(), baseY- head.y(), "^");
                case DOWN -> terminal.printText(x+ head.x(), baseY- head.y(), "v");
            }
            for(GamePoint point : snake){
                terminal.printText(x+ point.x(), baseY- point.y(), "o");
            }
            List<GamePoint> foods = gameBoard.getFoods();
            for(GamePoint point : foods){
                terminal.printText(x + point.x(), baseY- point.y() , "f");
            }
        }
        else
            terminal.printText(x, baseY, "GAME OVER - Press Enter to restart");
        int maxStatusBarIndex = Math.min(view.getDimensions().getWidth(), statusBar.length());
        drawBordersGame(view);
        terminal.printText(x, maxY, statusBar.substring(0, maxStatusBarIndex));

    }
  
    private void drawScrollBar(BufferView view){
        int maxY = view.getBufferEditor().getFileBuffer().getText().getLineAmount() - 1;
        int currentY = view.getInsertPoint().getY();
        int yBar = Math.round(((float)currentY / (float)maxY) * (view.getDimensions().getHeight() - 1));
        terminal.printText(view.getPosition().getX() + view.getDimensions().getWidth() - 1,
                                                            yBar + view.getPosition().getY(), "|");
    }
    private void drawBordersGame(SnakeView view){
        for(int i = 0; i<view.getDimensions().getHeight(); i++) {
            terminal.printText(view.getPosition().getX() + view.getDimensions().getWidth() - 1,
                    view.getPosition().getY() + i, "|");
        }
        for (int j= 0; j<view.getDimensions().getWidth(); j++){
            terminal.printText(view.getPosition().getX()+j,view.getPosition().getY() + view.getDimensions().getHeight() - 1, "_");
        }

    }

    public void draw(Point position, Point anchor, Point cursor){
        Objects.requireNonNull(position, "Cannot draw the cursor because the position is null.");
        Objects.requireNonNull(anchor, "Cannot draw the cursor because the anchor is null.");
        Objects.requireNonNull(cursor, "Cannot draw the cursor because the cursor position is null.");
        int x = position.getX() + (cursor.getX() - anchor.getX());
        int y = position.getY() + (cursor.getY() - anchor.getY());
        terminal.moveCursor(x, y);
    }
}
