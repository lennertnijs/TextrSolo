package com.Textr.ViewLayout;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Point;
import com.Textr.Terminal.TerminalService;
import com.Textr.Validator.Validator;
import com.Textr.View.Dimension2D;
import com.Textr.View.View;
import com.Textr.View.ViewCreator;

import java.util.ArrayList;
import java.util.List;

public class ViewLayoutInitializer {

    private ViewLayoutInitializer(){

    }

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
     * DOESNT WORK
     * @param buffers
     * @return
     */
    public static List<View> generateHorizontallyStackedViews(List<FileBuffer> buffers){
        validateBuffers(buffers);
        int terminalWidth = TerminalService.getTerminalArea().getWidth();
        int terminalHeight = TerminalService.getTerminalArea().getHeight();
        int amountOfBuffers = buffers.size();
        if(amountOfBuffers == 0){
            throw new IllegalStateException("Cannot generate Views because there are no FileBuffers.");
        }
        int widthPerView = ((terminalWidth) / amountOfBuffers);
        int remainder = ((terminalWidth) % amountOfBuffers);
        int x = 0;
        List<View> views = new ArrayList<>();
        for(FileBuffer buffer : buffers){
            Point position = Point.create(x, 0);
            int viewWidth = remainder-- > 0 ? widthPerView + 1 : widthPerView;
            Dimension2D dimensions = Dimension2D.create(viewWidth, terminalHeight);
            views.add(ViewCreator.create(buffer.getId(), position, dimensions));
            x += viewWidth;
        }
        return views;
    }

    private static void validateBuffers(List<FileBuffer> buffers){
        Validator.notNull(buffers, "Cannot generate views because the passed FileBuffers is null.");
        for(FileBuffer buffer : buffers){
            Validator.notNull(buffer, "Cannot generate views because one of the passed FileBuffers is null.");
        }
    }
}
