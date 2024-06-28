package com.textr.view;

import com.textr.filebuffer.TextUpdateReference;
import com.textr.filebufferV2.IText;

/**
 * An {@link UpdateState} represents how a {@link View} should update its cursor and anchor upon receiving a
 * {@link TextUpdateReference}.
 */
public interface UpdateState {

    /**
     * Updates the given {@link BufferView}'s anchor point and cursor, using the {@link TextUpdateReference} and
     * {@link IText}.
     * The method should ensure that the cursor always remains within the View's borders.
     *
     * @param view          The View whose anchor point and cursor to update
     * @param update        The TextUpdateReference holding the data on how the text was updated
     */
    void update(BufferView view, TextUpdateReference update, IText text);

}
