package com.textr.input;

import java.util.Objects;

/**
 * Represents an input given by the user.
 */
public final class Input {

    /**
     * The input type.
     */
    private final InputType type;
    /**
     * The character. Only relevant when the type is CHARACTER.
     */
    private final char character;

    private Input(InputType type, char character) {
        this.type = type;
        this.character = character;
    }

    /**
     * Creates and returns an IMMUTABLE {@link Input} of type CHARACTER with the given character.
     * @param character The character
     */
    public static Input createCharacterInput(char character) {
        return new Input(InputType.CHARACTER, character);
    }

    /**
     * Creates and returns an IMMUTABLE {@link Input} of the given type.
     * @param inputType The input type. Cannot be null. Cannot be CHARACTER.
     */
    public static Input createSpecialInput(InputType inputType) {
        Objects.requireNonNull(inputType, "Input type is null.");
        if (inputType == InputType.CHARACTER) {
            throw new IllegalArgumentException("Character input should be made with createCharacterInput");
        }
        return new Input(inputType, '\0'); // Placeholder null character
    }

    /**
     * @return The input type.
     */
    public InputType getType() {
        return type;
    }

    /**
     * @return The character.
     * @throws IllegalStateException If the Input was not of type CHARACTER.
     */
    public char getCharacter() {
        if (type != InputType.CHARACTER) {
            throw new IllegalStateException("Cannot get character from non-character input");
        }
        return character;
    }

    /**
     * Compares two objects and returns true if they're equal inputs.
     * Two inputs are equal if they're the same type, and the same character.
     *
     * @return True if equal. False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Input input))
            return false;
        return type.equals(input.type) && character == input.character;
    }

    /**
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + type.hashCode();
        result = 31 * result + character;
        return result;
    }

    /**
     * @return The string representation.
     */
    @Override
    public String toString() {
        return String.format("Input[type=%s, characterByte=%d]", type.toString(), (byte) character);
    }
}
