package com.textr.util;

/**
 * A FixedPoint instance is an immutable two-dimensional point with non-negative coordinates.
 */
public class FixedPoint {

    /**
     * The X coordinate of the FixedPoint
     */
    private final int x;

    /**
     * The Y coordinate of the FixedPoint
     */
    private final int y;

    /**
     * Constructs a new FixedPoint with the given X and Y coordinates, if the coordinates given are non-negative.
     * If any of the coordinates are negative, an {@linkplain IllegalArgumentException} is thrown instead.
     *
     * @param x The X coordinate of the new FixedPoint
     * @param y The Y coordinate of the new FixedPoint
     */
    public FixedPoint(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("FixedPoint must have non-negative coordinates");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate of this FixedPoint instance.
     *
     * @return An integer representing the x coordinate of this FixedPoint instance
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate of this FixedPoint instance.
     *
     * @return An integer representing the Y coordinate of this FixedPoint instance
     */
    public int getY() {
        return this.y;
    }
}
