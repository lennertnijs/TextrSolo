package com.textr.filebuffer;

import com.textr.util.FixedPoint;

public interface ITextSkeleton {

    int getCharAmount();
    int getLineLength(int index);
    int getLineAmount();

    /**
     * Converts the given index to a {@link FixedPoint} object based on this text skeleton. Newlines are considered
     * to be at the end of the line, and they count as one character on that line.
     * @param index the index to convert to a point object
     * @return The 2D Point representing the same location as the given index in the text skeleton
     */
    default FixedPoint convertToPoint(int index) {
        if (index < 0 || index > this.getCharAmount())
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d out of bounds for length %d of text structure", index, this.getCharAmount()));
        int count = 0;
        int row = -1;
        for(int i = 0; i < this.getLineAmount() ; i++){
            if(index < count + this.getLineLength(i)) {
                row = i;
                break;
            }
            count += this.getLineLength(i);
        }
        int col = index - count;
        return new FixedPoint(col, row);
    }

    /**
     * Converts the given point into an index representing the same location in the text skeleton. Points at the end of
     * a line (i.e. with X at the line length) are considered to be on the newline character.
     * @param point The 2D point to convert to an index
     * @return The 1D index in the text structure representing the same position as the given 2D point
     * @throws IllegalArgumentException if the given point is not a valid point in the text structure
     */
    default int convertToIndex(FixedPoint point) {
        if (this.getLineAmount() >= point.getY() || this.getLineLength(point.getY()) < point.getX())
            throw new IllegalArgumentException("Given point does not hold a valid location in this TextSkeleton");
        int count = 0;
        for(int i = 0; i < point.getY(); i++){
            count += this.getLineLength(i);
        }
        count += point.getX();
        return count;
    }

}
