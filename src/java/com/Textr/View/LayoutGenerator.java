package com.Textr.View;

import com.Textr.Util.Point;

import java.util.List;

public final class LayoutGenerator {

    private static IViewRepo viewRepo;

    public static void setViewRepo(IViewRepo repo){
        viewRepo = repo;
    }
    public static void generate(Dimension2D dimension2D){
        Point topLeft = Point.create(0,0);
        if(viewRepo.rootIsVertical()){
            generateVerticallyStackedViews(topLeft, dimension2D, 1);
            return;
        }
        generateHorizontallyStackedViews(topLeft,dimension2D,1);
    }

    private static void generateVerticallyStackedViews(Point topLeft, Dimension2D dimensions, int depth){
        List<View> nodeCount = viewRepo.getAllAtDepth(depth);
        int amountOfViews = nodeCount.size();
        int heightPerView = dimensions.getHeight() / amountOfViews;
        int remainder = dimensions.getHeight() % amountOfViews;
        int y = topLeft.getY();
        for(View view : nodeCount){
            Point position = Point.create(topLeft.getX(), y);
            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
            Dimension2D dimensionsOfView = Dimension2D.create(dimensions.getWidth(), viewHeight);
            if(view == null){
                generateHorizontallyStackedViews(position, dimensionsOfView, depth + 1);
            }else{
                view.setPosition(position);
                view.setDimensions(dimensionsOfView);
            }
            y += viewHeight;
        }
    }

    private static void generateHorizontallyStackedViews(Point topLeft, Dimension2D dimensions, int depth){
        List<View> nodeCount = viewRepo.getAllAtDepth(depth);
        int amountOfViews = nodeCount.size();
        int widthPerView = dimensions.getWidth() / amountOfViews;
        int remainder = dimensions.getWidth() % amountOfViews;
        int x = topLeft.getX();
        for(View view : nodeCount){
            Point position = Point.create(x, topLeft.getY());
            int viewWidth = remainder-- > 0 ? widthPerView + 1 : widthPerView;
            Dimension2D dimensionsOfView = Dimension2D.create(viewWidth, dimensions.getHeight());
            if(view == null){
                generateVerticallyStackedViews(position, dimensionsOfView, depth + 1);
            }else{
                view.setPosition(position);
                view.setDimensions(dimensionsOfView);
            }
            x += viewWidth;
        }
    }
}
