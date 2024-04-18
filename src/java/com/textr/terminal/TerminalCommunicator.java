package com.textr.terminal;

import com.textr.input.Input;
import com.textr.input.InputTranslator;
import com.textr.input.InputType;

import java.util.Objects;

public class TerminalCommunicator implements Communicator {

    private final TerminalService terminal;
    private final InputTranslator translator;

    public TerminalCommunicator(TerminalService terminal) {
        this.terminal = Objects.requireNonNull(terminal, "TerminalService in TerminalCommunicator cannot be null");
        this.translator = new InputTranslator(terminal);
    }

    @Override
    public boolean requestPermissions(String msg) {
        terminal.clearScreen();
        terminal.printText(1, 1, String.format("%s [Y/N]", msg));
        do {
            Input response = translator.getNextInput();
            if (response.getType() == InputType.CHARACTER) {
                if (response.getCharacter() == 'Y' || response.getCharacter() == 'y')
                    return true;
                if (response.getCharacter() == 'N' || response.getCharacter() == 'n')
                    return false;
            }
        } while (true); // Repeat until Y/N was given
    }

    @Override
    public void sendMessage(String msg) {
        terminal.clearScreen();
        terminal.printText(1, 1, msg);
    }
}
