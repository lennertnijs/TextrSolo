package com.Textr.TerminalModel;

import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.Optional;

public class TerminalService {

    public TerminalService(){

    }

    public Optional<Dimension2D> getTerminalArea(){
        Terminal.reportTextAreaSize();
        try{
            int b = Terminal.readByte();
            while (b != ';'){
                b = Terminal.readByte();
            }
            b = Terminal.readByte();
            int height = 0;
            while(b != ';'){
                if ('0' <= b && b <= '9') {
                    height *= 10;
                    height += b - '0';
                }
                b = Terminal.readByte();
            }

            int width = 0;
            while(b != 't'){
                b = Terminal.readByte();
                if ('0' <= b && b <= '9') {
                    width *= 10;
                    width += b - '0';
                }
            }
            return Optional.of(Dimension2D.builder().width(width).height(height).build());
        }catch(IOException e){
            System.out.println("Reading the window dimensions went wrong.");
        }
        return Optional.empty();
    }

    public void enterRawInputMode(){
        Terminal.enterRawInputMode();
    }

    public void leaveRawInputMode(){
        Terminal.leaveRawInputMode();
    }

    public void moveCursor(int row, int column, String idk){
        if(row <= 0 || column <= 0){
            throw new IllegalArgumentException("The system is 1-based. All indices must be strictly positive");
        }
        // idk wa die string doet
        Terminal.moveCursor(row, column, idk);
    }


    public void printText(int row, int column, String text){
        if(row <= 0 || column <= 0){
            throw new IllegalArgumentException("The system is 1-based. All indices must be strictly positive");
        }
        Terminal.printText(row, column, text);
    }

    public void clearScreen(){
        Terminal.clearScreen();
    }

    public void printText(Position position, String text){
        Terminal.printText(position.getX(), position.getY(), text);
    };
}
