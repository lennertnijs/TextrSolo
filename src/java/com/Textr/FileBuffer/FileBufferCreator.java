package com.Textr.FileBuffer;

import com.Textr.File.File;
import com.Textr.Validator.Validator;

/**
 * This class is responsible for the creation of {@link FileBuffer} instances.
 */
public class FileBufferCreator {

    /**
     * Private constructor. No use.
     */
    private FileBufferCreator(){
    }

    /**
     * Creates and returns a new {@link FileBuffer} for the given {@link File}.
     * A new {@link FileBuffer} means:
     * * The insertion point starts at (0,0)
     * * The state of the buffer is CLEAN
     * @param file The {@link File} for which a {@link FileBuffer} is to be created.
     *
     * @return The newly created {@link FileBuffer}.
     * @throws IllegalArgumentException If the given {@link File} is null.
     */
    public static FileBuffer create(File file){
        Validator.validateNotNull(file, "Cannot create a FileBuffer for a null File.");
        int id = file.getId();
        Text text = Text.create(file.getText());
        Point insertionPoint = Point.create(0,0);
        BufferState state = BufferState.CLEAN;
        return FileBuffer.builder().fileId(id).bufferText(text).insertionPosition(insertionPoint).state(state).build();
    }
}
