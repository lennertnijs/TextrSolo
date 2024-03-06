package com.Textr.ViewLayout;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Validator;
import com.Textr.View.Dimension2D;
import com.Textr.View.View;
import com.Textr.View.ViewCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Initializes view layouts.
 */
public class ViewLayoutInitializer {

    /**
     * Private constructor. No use.
     */
    private ViewLayoutInitializer(){
    }

    /**
     * Generate a list of views that completely covers the terminal area, one stacked atop the other vertically.
     * @param buffers The buffers. Cannot be null or contain null.
     *
     * @return The list of views.
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public static List<View> generateVerticallyStackedViews(List<FileBuffer> buffers){
        validateBuffers(buffers);
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        int amountOfBuffers = buffers.size();
        if(amountOfBuffers == 0){
            throw new IllegalStateException("Cannot generate Views because there are no FileBuffers.");
        }
        int heightPerView = ((terminalHeight) / amountOfBuffers);
        int remainder = ((terminalHeight) % amountOfBuffers);
        int y = 0;
        List<View> views = new ArrayList<>();
        for(FileBuffer buffer : buffers){
            Point position = Point.create(0, y);
            int viewHeight = remainder-- > 0 ? heightPerView + 1 : heightPerView;
            Dimension2D dimensions = Dimension2D.create(terminalWidth, viewHeight);
            views.add(ViewCreator.create(buffer.getId(), position, dimensions));
            y += viewHeight;
        }
        return views;
    }

    /**
     * Generate a list of views that completely covers the terminal area, one stacked atop the other vertically.
     * @param rootlayout  the root of the hiërarchyTree
     * @return The list of views.
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public static List<View> generateHierarchicalViews(Layout rootlayout){
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        return generateLayoutsVerticalSubTree(Point.create(0,0), Point.create(terminalWidth, terminalHeight), rootlayout );
    }

    /**
     * Generate a list of sublayouts that completely covers the layout area, one stacked atop the other vertically.
     * These layouts are then themselves filled horizontally with layouts, and so forth until the layouts are all views.
     * @return The list of views.
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public static List<View> generateLayoutsVerticalSubTree(Point topLeft, Point bottomRight, Layout rootlayout){
        int heightPerLayout = ((bottomRight.getY()-topLeft.getY()) / rootlayout.getChildren().size());
        int remainder = ((bottomRight.getY()-topLeft.getY()) % rootlayout.getChildren().size());
        List<View> views = new ArrayList<>();
        int y = topLeft.getY();
        for(Layout child : rootlayout.getChildren()){
            Point position = Point.create(topLeft.getX(), y);
            int LayoutHeight = remainder-- > 0 ? heightPerLayout + 1 : heightPerLayout;
            Dimension2D dimensions = Dimension2D.create(bottomRight.getX()-topLeft.getX(), LayoutHeight);
            if(child.getView()!=null){
                View toAdd = child.getView();
                toAdd.setPosition(position);
                toAdd.setDimensions(dimensions);

                views.add(toAdd);
            }
            else{
                Point parameterbottomright = Point.create(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
                views.addAll(generateLayoutsHorizontalSubTree(position, parameterbottomright, child));
            }
            y += LayoutHeight;
        }
        return views;
    }

    /**
     * Generate a list of sublayouts that completely covers the layout area, one stacked beside the other horizontally.
     * These layouts are then themselves filled vertically with layouts, and so forth until the layouts are all views.
     * @return The list of views.
     * @throws IllegalArgumentException If the list of buffers is or contains null.
     * @throws IllegalStateException If there are no buffers.
     */
    public static List<View> generateLayoutsHorizontalSubTree(Point topLeft, Point bottomRight, Layout rootlayout){
        int widthPerLayout = ((bottomRight.getX()-topLeft.getX()) / rootlayout.getChildren().size());
        int remainder = ((bottomRight.getX()-topLeft.getX()) % rootlayout.getChildren().size());
        List<View> views = new ArrayList<>();
        int x = topLeft.getX();
        for(Layout child : rootlayout.getChildren()){
            Point position = Point.create(x, topLeft.getY());
            int LayoutWidth = remainder-- > 0 ? widthPerLayout + 1 : widthPerLayout;
            Dimension2D dimensions = Dimension2D.create(LayoutWidth, bottomRight.getY()-topLeft.getY() );
            if(child.getView()!=null){
                View toAdd = child.getView();
                toAdd.setPosition(position);
                toAdd.setDimensions(dimensions);
                views.add(toAdd);
            }
            else{
                Point parameterbottomright = Point.create(position.getX()+dimensions.getWidth(),position.getY()+dimensions.getHeight());
                views.addAll(generateLayoutsVerticalSubTree(position, parameterbottomright, child));
            }
            x += LayoutWidth;
        }
        return views;
    }

    /**
     * Validates the given list of buffers.
     * Checks whether this list is, or contains, a null.
     * @param buffers The buffer list.
     *
     * @throws IllegalArgumentException If the given list of buffers is or contains null.
     */
    private static void validateBuffers(List<FileBuffer> buffers){
        Validator.notNull(buffers, "Cannot generate views because the passed FileBuffers is null.");
        for(FileBuffer buffer : buffers){
            Validator.notNull(buffer, "Cannot generate views because one of the passed FileBuffers is null.");
        }
    }
}
