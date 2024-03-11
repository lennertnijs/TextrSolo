package com.Textr.View;

import com.Textr.File.File;
import com.Textr.File.FileReader;
import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.Text;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.Util.Validator;

public class ViewFactory {

    public static View create(String url){
        Validator.notNull(url, "The url of a File for a View cannot be null.");
        File file = File.create(url);

        Point cursor = Point.create(0,0);
        Text text = Text.create(FileReader.readContents(file.getUrl()));
        BufferState state = BufferState.CLEAN;
        FileBuffer buffer = FileBuffer.create(file, text, cursor, state);

        Point dummyPoint = Point.create(0, 0);
        Dimension2D dummyDimensions = Dimension2D.create(1,1);
        Point anchor = Point.create(0,0);
        return View.create(buffer, dummyPoint, dummyDimensions, anchor);
    }
}
