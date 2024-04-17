package com.textr.snake;

import java.util.Objects;

/**
 * Represents a 2-dimensional location in a game context.
 * @param x The x coordinate. Can be a negative value.
 * @param y The y coordinate. Can be a negative value.
 */
public record GamePoint(int x, int y) {

    /**
     * Adds the given {@link Vector} to this {@link GamePoint} and returns the result.
     * Does not change the original {@link GamePoint}.
     * @param vector The translation {@link Vector}. Cannot be null.
     *
     * @return The translated {@link GamePoint}.
     */
    public GamePoint translate(Vector vector) {
        Objects.requireNonNull(vector, "Vector is null.");
        return new GamePoint(x + vector.getX(), y + vector.getY());
    }

    /**
     * Compares this {@link GamePoint} to the given {@link Object}.
     * Returns True if they're equal. False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GamePoint gamePoint))
            return false;
        return x == gamePoint.x && y == gamePoint.y;
    }

    /**
     * Creates a {@link String} representation of this {@link GamePoint} and returns it.
     */
    @Override
    public String toString() {
        return String.format("GamePoint[x=%d, y=%d]", x, y);
    }
}