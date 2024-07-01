package com.textr.view;

import com.textr.tree.Node;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

public final class LayoutGenerator {
    private static IViewRepo viewRepo;

    public static void setViewRepo(IViewRepo repo){
        viewRepo = repo;
    }
    /** Generate a list of views that completely covers the terminal area, one stacked atop the other vertically.
     * @param dimension2D  the dimensions of the terminal
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public static void generate(Dimension2D dimension2D ){
        Point topLeft = new Point(0,0);
        int terminalWidth = dimension2D.getWidth();
        int terminalHeight = dimension2D.getHeight();
        if(viewRepo.rootIsVertical()){
            generateLayoutsVerticalSubTree(topLeft, new Point(terminalWidth, terminalHeight), viewRepo.getRoot() );
            return;
        }
        generateLayoutsHorizontalSubTree(topLeft,new Point(terminalWidth, terminalHeight),viewRepo.getRoot());

}

/**
 * Generate a list of sublayouts that completely covers the layout area, one stacked atop the other vertically.
 * These layouts are then themselves filled horizontally with layouts, and so forth until the layouts are all views.
 * @return The list of views.
 * @throws IllegalArgumentException If the list of buffers is or contains null.
 * @throws IllegalStateException If there are no buffers.
 */
public static void generateLayoutsVerticalSubTree(Point topLeft, Point bottomRight, Node<View> rootlayout){
    int heightPerLayout = ((bottomRight.getY()-topLeft.getY()) / rootlayout.getChildren().size());
    int remainder = ((bottomRight.getY()-topLeft.getY()) % rootlayout.getChildren().size());
    int y = topLeft.getY();
    for(Node<View> child : rootlayout.getChildren()){
        Point position = new Point(topLeft.getX(), y);
        int LayoutHeight = remainder-- > 0 ? heightPerLayout + 1 : heightPerLayout;
        Dimension2D dimensions = new Dimension2D(bottomRight.getX()-topLeft.getX(), LayoutHeight);
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.resize(dimensions);
        }
        else{
            Point parameterbottomright = new Point(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
            generateLayoutsHorizontalSubTree(position, parameterbottomright, child);
        }
        y += LayoutHeight;
    }

}

/**
 * Generate a list of sublayouts that completely covers the layout area, one stacked beside the other horizontally.
 * These layouts are then themselves filled vertically with layouts, and so forth until the layouts are all views.
 * @return The list of views.
 * @throws IllegalArgumentException If the list of buffers is or contains null.
 * @throws IllegalStateException If there are no buffers.
 */
public static void generateLayoutsHorizontalSubTree(Point topLeft, Point bottomRight, Node<View> rootlayout){
    int widthPerLayout = ((bottomRight.getX()-topLeft.getX()) / rootlayout.getChildren().size());
    int remainder = ((bottomRight.getX()-topLeft.getX()) % rootlayout.getChildren().size());
    int x = topLeft.getX();
    for(Node<View> child : rootlayout.getChildren()){
        Point position = new Point(x, topLeft.getY());
        int LayoutWidth = remainder-- > 0 ? widthPerLayout + 1 : widthPerLayout;
        Dimension2D dimensions = new Dimension2D(LayoutWidth, bottomRight.getY()-topLeft.getY());
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.resize(dimensions);
        }
        else{
            Point parameterbottomright = new Point(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
            generateLayoutsVerticalSubTree(position, parameterbottomright, child);
        }
        x += LayoutWidth;
    }
}
}
