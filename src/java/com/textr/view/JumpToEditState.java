package com.textr.view;

import com.textr.filebuffer.ICursor;
import com.textr.filebuffer.TextUpdateReference;
import com.textr.filebufferV2.IText;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import java.util.Objects;

/**
 * {@inheritDoc}
 *
 * The {@link JumpToEditState} updates the cursor to stand on the location of the text update, and updates the anchor if
 * necessary to keep the cursor in view.
 */
public class JumpToEditState implements UpdateState {

    /**
     * {@inheritDoc}
     *
     * This method moves the Cursor to the update location, and moves the anchor point if it is necessary to keep it in
     * view.
     */
    @Override
    public void update(BufferView view, TextUpdateReference update, IText text) {
        Objects.requireNonNull(view, "A state cannot update a null view");
        Objects.requireNonNull(update, "A state cannot update a view with a null update reference");
        Objects.requireNonNull(text, "A state cannot update a view using a null text structure");
        updateCursor(view.getCursor(), update, text);
        updateAnchor(view.getAnchor(), view.getCursor(), view.getDimensions());
        view.setUpdateState(new RemainOnContentState());
    }

    private void updateCursor(ICursor cursor, TextUpdateReference update, IText text) {
        int displacement = update.isInsertion() ? 1 : 0; // When insertion, cursor should move to point after
        cursor.setInsertIndex(update.updateIndex() + displacement, text);
    }

    private void updateAnchor(Point anchor, ICursor cursor, Dimension2D dimensions) {
        AnchorUpdater.updateAnchor(anchor, cursor.getInsertPoint(), dimensions);
    }
}
