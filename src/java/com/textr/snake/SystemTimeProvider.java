package com.textr.snake;

public class SystemTimeProvider implements TimeProvider{

    public SystemTimeProvider(){
    }

    @Override
    public long getTimeInMillis() {
        return System.currentTimeMillis();
    }
}
