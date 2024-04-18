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
        TextUpdateType type = update.type();
        if (update.updateIndex() < cursor.getInsertIndex()) {
            // Insertion/deletion before cursor must update cursor position
            int displacement = update.isInsertion() ? 1 : -1;
            cursor.setInsertIndex(cursor.getInsertIndex() + displacement, skeleton);
        } else if (update.updateIndex() == cursor.getInsertIndex() && update.isInsertion())
            // insertion on cursor must also move cursor
            cursor.setInsertIndex(cursor.getInsertIndex() + 1, skeleton);
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
        if (isInsertion)
            updateCursorLineInsertion(cursor, updatePoint, skeleton);
        else
            updateCursorLineDeletion(cursor, updatePoint, skeleton);
    }

    private void updateCursorLineInsertion(ICursor cursor, FixedPoint updatePoint, ITextSkeleton skeleton) {
        Point cursorPoint = cursor.getInsertPoint();
        if (updatePoint.getY() > cursorPoint.getY())
            return; // Line insertion only relevant to cursor if new line before/on this line
        if (updatePoint.getY() == cursorPoint.getY() && updatePoint.getX() <= cursorPoint.getX()) {
            // New line inserted before/on cursor (on same line) => Set to same position in text on new line
            int newX = cursorPoint.getX() - updatePoint.getX();
            int newY = updatePoint.getY() + 1;
            cursor.setInsertPoint(Point.create(newX, newY), skeleton);
            return;
        }
        if (updatePoint.getY() < cursorPoint.getY()) {
            // Only if new line is before cursor's line, move cursor down
            int newX = cursorPoint.getX();
            int newY = updatePoint.getY() + 1;
            cursor.setInsertPoint(Point.create(newX, newY), skeleton);
        }
    }

    private void updateCursorLineDeletion(ICursor cursor, FixedPoint updatePoint, ITextSkeleton skeleton) {
        Point cursorPoint = cursor.getInsertPoint();
        if (updatePoint.getY() >= cursorPoint.getY())
            return; // Line deletion only relevant to cursor if removed line before this line
        if (updatePoint.getY() + 1 == cursorPoint.getY()) {
            // Line right before got removed: update position to sit on same content
            int newX = updatePoint.getX() + cursorPoint.getX();
            int newY = updatePoint.getY();
            cursor.setInsertPoint(Point.create(newX, newY), skeleton);
        }
        else {
            // Simple move cursor up
            int newX = cursorPoint.getX();
            int newY = updatePoint.getY() - 1;
            cursor.setInsertPoint(Point.create(newX, newY), skeleton);
        }
    }
}
