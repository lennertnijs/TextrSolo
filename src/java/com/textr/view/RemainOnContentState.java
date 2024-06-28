package com.textr.view;

import com.textr.filebuffer.*;
import com.textr.filebufferV2.IText;
import com.textr.util.*;

/**
 * {@inheritDoc}
 *
 * A {@link RemainOnContentState} updates the cursor only when necessary, such as when adding a new character on the
 * same line before or on the cursor, or when adding a new line before or on the same line as the cursor. If the cursor
 * stands on the position of the edit, it should act as if it was the cursor responsible for the edit, and move
 * accordingly.
 */
public class RemainOnContentState implements UpdateState {

    /**
     * {@inheritDoc}
     *
     * When a new line gets added (resp. removed), moves the cursor down (resp. up) if necessary to stay on the same
     * content.
     * When a character gets inserted (resp. deleted) on the same line as the cursor, moves the cursor right (resp.
     * left) if necessary to stay on the same content. In the case of an addition, inserting on the cursor will also
     * move the cursor right.
     *
     * Then, it moves the anchor point up or down a line if it is necessary to keep the same content in view.
     */
    public void update(BufferView view, TextUpdateReference update, IText text) {
        updateCursor(view.getCursor(), update, text);
        updateAnchor(view.getAnchor(), view.getCursor(), update, view.getDimensions(), text);
    }

    private void updateCursor(ICursor cursor, TextUpdateReference update, IText text) {
        if (update.updateIndex() < cursor.getInsertIndex()) {
            // Insertion/deletion before cursor must update cursor position
            int displacement = update.isInsertion() ? 1 : -1;
            cursor.setInsertIndex(cursor.getInsertIndex() + displacement, text);
        } else if (update.updateIndex() == cursor.getInsertIndex() && update.isInsertion())
            // insertion on cursor must also move cursor
            cursor.setInsertIndex(cursor.getInsertIndex() + 1, text);
    }

    private void updateAnchor(Point anchor,
                              ICursor cursor,
                              TextUpdateReference update,
                              Dimension2D dimensions,
                              IText text) {
        Point updateLocation = text.convertToPoint(update.updateIndex());
        boolean isLineUpdate = update.type() == TextUpdateType.LINE_UPDATE;
        boolean isBeforeAnchor = updateLocation.getY() < anchor.getY();
        if (isLineUpdate && isBeforeAnchor) {
            int displacement = update.isInsertion() ? 1 : -1;
            anchor.setY(anchor.getY() + displacement);
        }
        AnchorUpdater.updateAnchor(anchor, cursor.getInsertPoint(), dimensions);
    }
}
