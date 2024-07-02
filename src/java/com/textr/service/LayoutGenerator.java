package com.textr.service;

import com.textr.tree.Node;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.view.IViewRepo;
import com.textr.view.View;

public final class LayoutGenerator {
    private final IViewRepo viewRepo;

    public LayoutGenerator(IViewRepo repo){
        this.viewRepo = repo;
    }

    /** Generate a list of views that completely covers the terminal area, one stacked atop the other vertically.
     * @param dimension2D  the dimensions of the terminal
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public void generate(Dimension2D dimension2D){
        Point topLeft = new Point(0,0);
        int terminalWidth = dimension2D.width();
        int terminalHeight = dimension2D.height();
        if(viewRepo.rootIsVertical()){
            generateLayoutsVerticalSubTree(topLeft, new Point(terminalWidth, terminalHeight), viewRepo.getRoot() );
        }else{
            generateLayoutsHorizontalSubTree(topLeft,new Point(terminalWidth, terminalHeight),viewRepo.getRoot());
        }

}

/**
 * Generate a list of sub layouts that completely covers the layout area, one stacked atop the other vertically.
 * These layouts are then themselves filled horizontally with layouts, and so forth until the layouts are all views.
 * @throws IllegalArgumentException If the list of buffers is or contains null.
 * @throws IllegalStateException If there are no buffers.
 */
public void generateLayoutsVerticalSubTree(Point topLeft, Point bottomRight, Node<View> rootLayout){
    int heightPerLayout = ((bottomRight.getY()-topLeft.getY()) / rootLayout.getChildren().size());
    int remainder = ((bottomRight.getY()-topLeft.getY()) % rootLayout.getChildren().size());
    int y = topLeft.getY();
    for(Node<View> child : rootLayout.getChildren()){
        Point position = new Point(topLeft.getX(), y);
        int LayoutHeight = remainder-- > 0 ? heightPerLayout + 1 : heightPerLayout;
        Dimension2D dimensions = new Dimension2D(bottomRight.getX()-topLeft.getX(), LayoutHeight);
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.setDimensions(dimensions);
        }
        else{
            Point parameterbottomright = new Point(position.getX()+dimensions.width(),position.getY()+dimensions.height());
            generateLayoutsHorizontalSubTree(position, parameterbottomright, child);
        }
        y += LayoutHeight;
    }

}

/**
 * Generate a list of sub layouts that completely covers the layout area, one stacked beside the other horizontally.
 * These layouts are then themselves filled vertically with layouts, and so forth until the layouts are all views.
 *
 * @throws IllegalArgumentException If the list of buffers is or contains null.
 * @throws IllegalStateException If there are no buffers.
 */
public void generateLayoutsHorizontalSubTree(Point topLeft, Point bottomRight, Node<View> rootLayout){
    int widthPerLayout = ((bottomRight.getX()-topLeft.getX()) / rootLayout.getChildren().size());
    int remainder = ((bottomRight.getX()-topLeft.getX()) % rootLayout.getChildren().size());
    int x = topLeft.getX();
    for(Node<View> child : rootLayout.getChildren()){
        Point position = new Point(x, topLeft.getY());
        int LayoutWidth = remainder-- > 0 ? widthPerLayout + 1 : widthPerLayout;
        Dimension2D dimensions = new Dimension2D(LayoutWidth, bottomRight.getY()-topLeft.getY());
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.setDimensions(dimensions);
        }
        else{
            Point parameterbottomright = new Point(position.getX()+dimensions.width(),position.getY()+dimensions.height());
            generateLayoutsVerticalSubTree(position, parameterbottomright, child);
        }
        x += LayoutWidth;
    }
}
}
