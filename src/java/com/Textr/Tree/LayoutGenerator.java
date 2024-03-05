package com.Textr.Tree;

import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Point;
import com.Textr.View.Dimension2D;
import com.Textr.View.View;

import java.util.List;

public class LayoutGenerator {

    public static void generateViews(ViewTreeRepo repo){
        Point topLeft = Point.create(0,0);
        generateVerticallyStackedViews(topLeft, TerminalService.getTerminalArea(), 1, repo);
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
                generateVerticallyStackedViews(position, dimensionsOfView, depth + 1, repo);
            }else{
                repo.get(integer).setPosition(position);
                repo.get(integer).setDimensions(dimensionsOfView);
            }
            y+= viewHeight;
        }
    }
}
