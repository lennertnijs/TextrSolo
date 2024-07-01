package com.textr.filebuffer;

import com.textr.filebuffer.OperationType;
import com.textr.util.Point;

import java.util.Objects;

/**
 * Represents a notification containing all the information necessary for all views looking at the same buffer
 * when this buffer is updated through one of them.
 * @param insertPoint The insert point of the change. Cannot be null.
 * @param operationType The type of operation. Cannot be null.
 */
public record TextUpdate(Point insertPoint, OperationType operationType) {

    public TextUpdate(Point insertPoint, OperationType operationType) {
        this.insertPoint = Objects.requireNonNull(insertPoint, "Insertion point is null.").copy();
        this.operationType = Objects.requireNonNull(operationType, "Operation type is null.");
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString() {
        return String.format("TextUpdate[insertPoint=%s, operationType=%s]", insertPoint, operationType);
    }
}
