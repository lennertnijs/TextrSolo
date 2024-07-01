package com.textr.util;

/**
 * Class to represent 2D dimensions, aka a width and a height.
 */
public record Dimension2D(int width, int height) {

    /**
     * Creates an IMMUTABLE {@link Dimension2D}.
     */
    public Dimension2D {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width or height is negative or 0.");
        }
    }

    /**
     * Compares two objects and returns true if they're equal dimension2D's. Returns false otherwise.
     *
     * @return True if equal. False otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Dimension2D dimensions))
            return false;
        return this.width == dimensions.width && this.height == dimensions.height;
    }

    /**
     * @return The hash code
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    /**
     * @return The string representation
     */
    @Override
    public String toString() {
        return String.format("Dimension2D[width=%d, height=%d]", width, height);
    }
}