package com.textr.input;

public class Input {
    private final InputType type;
    private final char character;

    /**
     * Constructs an Input instance with the given type and character.
     *
     * @param type      The InputType of the input entered
     * @param character The character associated with the character input
     */
    private Input(InputType type, char character) {
        this.type = type;
        this.character = character;
    }

    /**
     * Constructs an Input instance with type {@link InputType}.CHARACTER from the given character.
     *
     * @param character The character associated with the character input
     */
    public static Input createCharacterInput(char character) {
        // Any character is allowed in Input. "Illegal" characters in the program have to be handled during input
        // translation phase.
        return new Input(InputType.CHARACTER, character);
    }

    /**
     * Constructs an Input instance with the given (special) type. This method does not accept the CHARACTER type,
     * as there should be a character associated with that type, use createCharacterInput instead.
     *
     * @param type The InputType of the new Input instance
     * @return A new Input instance with type set to the given type
     * @throws IllegalArgumentException when InputType.CHARACTER was given as type
     */
    public static Input createSpecialInput(InputType type) {
        if (type == InputType.CHARACTER)
            throw new IllegalArgumentException("Character input should be made with createCharacterInput");
        return new Input(type, '\0'); // Placeholder null character
    }

    /**
     * Returns the {@linkplain InputType} of the input.
     *
     * @return The InputType of the input
     */
    public InputType getType() {
        return type;
    }

    /**
     * Returns the character of the input, assuming the input type was a character. If the input type is not
     * a character, throws an {@linkplain IllegalStateException} instead.
     *
     * @return The InputType of the input
     */
    public char getCharacter() {
        if (type != InputType.CHARACTER) {
            throw new IllegalStateException("Cannot get character from non-character input");
        }
        return character;
    }
}
