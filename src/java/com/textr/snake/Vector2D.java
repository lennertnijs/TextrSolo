package com.textr.snake;

/**
 * Represents a 2-dimensional vector.
 * @param x The x coordinate. Can be a negative value.
 * @param y The y coordinate. Can be a negative value.
 */
public record Vector2D(int x, int y) {

    /**
     * Compares this {@link Vector2D} to the given {@link Object}.
     * Returns True if they're equal. False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector2D vector))
            return false;
        return x == vector.x && y == vector.y;
    }

    /**
     * Creates a {@link String} representation of this {@link Vector2D} and returns it.
     */
    @Override
    public String toString() {
        return String.format("Vector2D[x=%d, y=%d]", x, y);
    }
}
