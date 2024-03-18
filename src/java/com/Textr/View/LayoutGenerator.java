package com.Textr.View;

import com.Textr.Tree.Node;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;

import java.util.List;

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
        Point topLeft = Point.create(0,0);
        int terminalWidth = dimension2D.getWidth();
        int terminalHeight = dimension2D.getHeight();
        if(viewRepo.rootIsVertical()){
            generateLayoutsVerticalSubTree(topLeft, Point.create(terminalWidth, terminalHeight), viewRepo.getRoot() );
            return;
        }
        generateLayoutsHorizontalSubTree(topLeft,Point.create(terminalWidth, terminalHeight),viewRepo.getRoot());

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
        Point position = Point.create(topLeft.getX(), y);
        int LayoutHeight = remainder-- > 0 ? heightPerLayout + 1 : heightPerLayout;
        Dimension2D dimensions = Dimension2D.create(bottomRight.getX()-topLeft.getX(), LayoutHeight);
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.setDimensions(dimensions);
        }
        else{
            Point parameterbottomright = Point.create(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
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
        Point position = Point.create(x, topLeft.getY());
        int LayoutWidth = remainder-- > 0 ? widthPerLayout + 1 : widthPerLayout;
        Dimension2D dimensions = Dimension2D.create(LayoutWidth, bottomRight.getY()-topLeft.getY());
        if(child.hasValue()){
            View toAdd = child.getValue();
            toAdd.setPosition(position);
            toAdd.setDimensions(dimensions);
        }
        else{
            Point parameterbottomright = Point.create(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
            generateLayoutsVerticalSubTree(position, parameterbottomright, child);
        }
        x += LayoutWidth;
    }
}

//    private static void generateVerticallyStackedViews(Point topLeft, Dimension2D dimensions, int depth){
//        List<View> nodeCount = viewRepo.getAllAtDepth(depth);
//        int amountOfViews = nodeCount.size();
//        int heightPerView = dimensions.getHeight() / amountOfViews;
//        int remainder = dimensions.getHeight() % amountOfViews;
//        int y = topLeft.getY();
//        for(View view : nodeCount){
//            Point position = Point.create(topLeft.getX(), y);
//            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
//            Dimension2D dimensionsOfView = Dimension2D.create(dimensions.getWidth(), viewHeight);
//            if(view == null){
//                generateHorizontallyStackedViews(position, dimensionsOfView, depth + 1);
//            }else{
//                view.setPosition(position);
//                view.setDimensions(dimensionsOfView);
//            }
//            y += viewHeight;
//        }
//    }
//
//    private static void generateHorizontallyStackedViews(Point topLeft, Dimension2D dimensions, int depth){
//        List<View> nodeCount = viewRepo.getAllAtDepth(depth);
//        int amountOfViews = nodeCount.size();
//        int widthPerView = dimensions.getWidth() / amountOfViews;
//        int remainder = dimensions.getWidth() % amountOfViews;
//        int x = topLeft.getX();
//        for(View view : nodeCount){
//            Point position = Point.create(x, topLeft.getY());
//            int viewWidth = remainder-- > 0 ? widthPerView + 1 : widthPerView;
//            Dimension2D dimensionsOfView = Dimension2D.create(viewWidth, dimensions.getHeight());
//            if(view == null){
//                generateVerticallyStackedViews(position, dimensionsOfView, depth + 1);
//            }else{
//                view.setPosition(position);
//                view.setDimensions(dimensionsOfView);
//            }
//            x += viewWidth;
//        }
//    }
}
