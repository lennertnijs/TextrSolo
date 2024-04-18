package com.textr.view;

import com.textr.filebuffer.ICursor;
import com.textr.filebuffer.ITextSkeleton;
import com.textr.filebuffer.TextUpdateReference;
import com.textr.filebuffer.TextUpdateType;
import com.textr.util.Dimension2D;
import com.textr.util.FixedPoint;
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
    public void update(BufferView view, TextUpdateReference update, ITextSkeleton textStructure) {
        Objects.requireNonNull(view, "A state cannot update a null view");
        Objects.requireNonNull(update, "A state cannot update a view with a null update reference");
        Objects.requireNonNull(textStructure, "A state cannot update a view using a null text structure");
        updateCursor(view.getCursor(), update, textStructure);
        updateAnchor(view.getAnchor(), view.getCursor(), view.getDimensions());
        view.setUpdateState(new GenericState());
    }

    private void updateCursor(ICursor cursor, TextUpdateReference update, ITextSkeleton skeleton) {
        int displacement = update.isInsertion() ? 1 : 0; // When insertion, cursor should move to point after
        cursor.setInsertIndex(update.updateIndex() + displacement, skeleton);
    }

    private void updateAnchor(Point anchor, ICursor cursor, Dimension2D dimensions) {
        AnchorUpdater.updateAnchor(anchor, cursor.getInsertPoint(), dimensions);
    }

    private void updateCursorChar(ICursor cursor, FixedPoint updatePoint, boolean isInsertion, ITextSkeleton skeleton) {
        int displacement = isInsertion ? 1 : 0;
        cursor.setInsertPoint(Point.create(updatePoint.getX() + displacement, updatePoint.getY()), skeleton);
    }

    private void updateCursorLine(ICursor cursor, FixedPoint updatePoint, boolean isInsertion, ITextSkeleton skeleton) {
        if (isInsertion)
            // Set cursor to start of new line
            cursor.setInsertPoint(Point.create(0, updatePoint.getY() + 1), skeleton);
        else
            // Set cursor to point of line deletion
            cursor.setInsertPoint(Point.create(updatePoint.getX(), updatePoint.getY()), skeleton);
    }
}
