package com.textr.snake;

import java.util.Objects;

public record GamePoint(int x, int y) {

    public GamePoint translate(Vector vector) {
        Objects.requireNonNull(vector, "Vector is null.");
        return new GamePoint(x + vector.getX(), y + vector.getY());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GamePoint p))
            return false;
        return x == p.x && y == p.y;
    }

    @Override
    public String toString() {
        return String.format("GamePoint[x=%d, y=%d]", x, y);
    }
}
