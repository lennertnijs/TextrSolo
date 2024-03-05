package com.Textr.Tree;

import com.Textr.Util.Point;
import com.Textr.View.Dimension2D;

import java.util.List;

public class LayoutGenerator {

    public static void generateViews(ViewTreeRepo repo, Dimension2D dimension2D){
        Point topLeft = Point.create(0,0);
        generateVerticallyStackedViews(topLeft, dimension2D, 1, repo);
    }

    private static void generateVerticallyStackedViews(Point topLeft, Dimension2D dimensions, int depth, ViewTreeRepo repo){
        List<Integer> nodeCount = repo.getAllValuesAtDepth(depth);
        int amountOfViews = nodeCount.size();
        int heightPerView = dimensions.getHeight() / amountOfViews;
        int remainder = dimensions.getHeight() % amountOfViews;
        int y = topLeft.getY();
        for(Integer integer : nodeCount){
            Point position = Point.create(topLeft.getX(), y);
            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
            Dimension2D dimensionsOfView = Dimension2D.create(dimensions.getWidth(), viewHeight);
            if(integer == null){
                generateHorizontallyStackedViews(position, dimensionsOfView, depth + 1, repo);
            }else{
                repo.get(integer).setPosition(position);
                repo.get(integer).setDimensions(dimensionsOfView);
            }
            y += viewHeight;
        }
    }

    private static void generateHorizontallyStackedViews(Point topLeft, Dimension2D dimensions, int depth, ViewTreeRepo repo){
        List<Integer> nodeCount = repo.getAllValuesAtDepth(depth);
        int amountOfViews = nodeCount.size();
        int widthPerView = dimensions.getWidth() / amountOfViews;
        int remainder = dimensions.getWidth() % amountOfViews;
        int x = topLeft.getX();
        for(Integer integer : nodeCount){
            Point position = Point.create(x, topLeft.getY());
            int viewWidth = remainder-- > 0 ? widthPerView + 1 : widthPerView;
            Dimension2D dimensionsOfView = Dimension2D.create(viewWidth, dimensions.getHeight());
            if(integer == null){
                generateVerticallyStackedViews(position, dimensionsOfView, depth + 1, repo);
            }else{
                repo.get(integer).setPosition(position);
                repo.get(integer).setDimensions(dimensionsOfView);
            }
            x += viewWidth;
        }
    }
}
