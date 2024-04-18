package com.textr.filebuffer;

import com.textr.util.FixedPoint;

public interface ITextSkeleton {

    int getCharAmount();
    int getLineLength(int index);
    int getLineAmount();

    /**
     * Converts the given index to a {@link FixedPoint} object based on this text skeleton. Newlines are considered
     * to be at the end of the line, and they count as one character on that line.
     * @param index
     * @return
     */
    default FixedPoint convertToPoint(int index) {
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

    default int convertToIndex(FixedPoint point) {
        if (this.getLineAmount() >= point.getY() || this.getLineLength(point.getY()) > point.getX())
            throw new IllegalArgumentException("Given point does not hold a valid location in this TextSkeleton");
        int count = 0;
        for(int i = 0; i < point.getY(); i++){
            count += this.getLineLength(i);
        }
        count += point.getX();
        return count;
    }

}
