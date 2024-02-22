package com.Textr.TerminalModel;

import io.github.btj.termios.Terminal;

import java.io.IOException;
import java.util.Optional;

public class TerminalService {

    public TerminalService(){

    }

    public Optional<Rectangle> getTerminalArea(){
        Terminal.reportTextAreaSize();
        try{
            int b = Terminal.readByte();
            while (b != ';'){
                b = Terminal.readByte();
            }
            b = Terminal.readByte();
            int height = 0;
            while(b != ';'){
                b = Terminal.readByte();
                if ('0' <= b && b <= '9') {
                    height *= 10;
                    height += b - '0';
                }
            }

            int width = 0;
            while(b != 't'){
                b = Terminal.readByte();
                if ('0' <= b && b <= '9') {
                    width *= 10;
                    width += b - '0';
                }
            }
            return Optional.of(Rectangle.builder().width(width).height(height).build());
        }catch(IOException e){
            System.out.println("Reading the window dimensions went wrong.");
        }
        return null;
    }
}
