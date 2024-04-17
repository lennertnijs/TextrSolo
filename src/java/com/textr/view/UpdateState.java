package com.textr.view;

import com.textr.filebuffer.ITextSkeleton;
import com.textr.filebuffer.TextUpdateReference;

/**
 * An {@link UpdateState} represents how a {@link View} should update its cursor and anchor upon receiving a
 * {@link TextUpdateReference}.
 */
public interface UpdateState {

    /**
     * Updates the given {@link BufferView}'s anchor point and cursor, using the {@link TextUpdateReference} and
     * {@link ITextSkeleton}.
     * The method should ensure that the cursor always remains within the View's borders.
     *
     * @param view          The View whose anchor point and cursor to update
     * @param update        The TextUpdateReference holding the data on how the text was updated
     * @param textStructure The latest structure of the text that was updated
     */
    void update(BufferView view, TextUpdateReference update, ITextSkeleton textStructure);

}
