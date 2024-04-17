package com.textr.snake;

public class MockTimeProvider implements TimeProvider{

    private int mockValue;

    public MockTimeProvider(int mockValue){
        this.mockValue = mockValue;
    }
    @Override
    public long getTimeInMillis(){
        return mockValue;
    }

    public void increaseMockValue(){
        mockValue++;
    }
}
