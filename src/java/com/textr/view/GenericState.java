package com.textr.view;

import com.textr.filebuffer.ICursor;
import com.textr.filebuffer.ITextSkeleton;
import com.textr.filebuffer.TextUpdateReference;
import com.textr.filebuffer.TextUpdateType;
import com.textr.util.*;

/**
 * {@inheritDoc}
 *
 * A {@link GenericState} updates the cursor only when necessary, such as when adding a new character on the same line
 * before or on the cursor, or when adding a new line before or on the same line as the cursor. If the cursor stands on
 * the position of the edit, it should act as if it was the cursor responsible for the edit, and move accordingly.
 */
public class GenericState implements UpdateState {

    /**
     * {@inheritDoc}
     *
     * When a new line gets added (resp. removed), moves the cursor down (resp. up) if necessary to stay on the same
     * content.
     * When a character gets inserted (resp. deleted) on the same line as the cursor, moves the cursor right (resp.
     * left) if necessary to stay on the same content. In the case of an addition, inserting on the cursor will also
     * move the cursor right.
     * Then, it moves the anchor point if it is necessary to keep the cursor in view.
     */
    public void update(BufferView view, TextUpdateReference update, ITextSkeleton textStructure) {
        updateCursor(view.getCursor(), update, textStructure);
        updateAnchor(view.getAnchor(), view.getCursor(), view.getDimensions());
    }

    private void updateCursor(ICursor cursor, TextUpdateReference update, ITextSkeleton skeleton) {
        FixedPoint updatePoint = update.updateLocation();
        TextUpdateType type = update.type();
        switch (type) {
            case CHAR_UPDATE -> updateCursorChar(cursor, updatePoint, update.isInsertion(), skeleton);
            case LINE_UPDATE -> updateCursorLine(cursor, updatePoint, update.isInsertion(), skeleton);
        }
    }

    private void updateAnchor(Point anchor, ICursor cursor, Dimension2D dimensions) {
        AnchorUpdater.updateAnchor(anchor, cursor.getInsertPoint(), dimensions);
    }

    private void updateCursorChar(ICursor cursor, FixedPoint updatePoint, boolean isInsertion, ITextSkeleton skeleton) {
        if (cursor.getInsertPoint().getY() != updatePoint.getY())
            return; // Character insertion/deletion only relevant to cursor if on same line

        int cursorX = cursor.getInsertPoint().getX();
        int updateX = updatePoint.getX();

        if (isInsertion && updateX <= cursorX)
            cursor.move(Direction.RIGHT, skeleton); // If insertion before/on cursor, move cursor with insertion
        else if (updateX < cursorX)
            cursor.move(Direction.LEFT, skeleton); // If deletion before cursor, move cursor with deletion
    }

    private void updateCursorLine(ICursor cursor, FixedPoint updatePoint, boolean isInsertion, ITextSkeleton skeleton) {
        if (updatePoint.getY() + 1 > cursor.getInsertPoint().getY())
            return; // Line insertion/deletion only relevant to cursor if new/deleted line before/on this line
        if (isInsertion)
            cursor.move(Direction.DOWN, skeleton);
        else
            cursor.move(Direction.UP, skeleton);
    }
}
