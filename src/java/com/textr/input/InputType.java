package com.textr.input;

public enum InputType {

    CHARACTER, // b >= 32 && b <= 126
    ENTER, // 13
    CTRL_N, // 14
    CTRL_P, // 16
    CTRL_R, // 18
    CTRL_S, //19
    CTRL_T, //20
    ESCAPE, //27
    SPACE, //32
    ARROW_UP, //ESC [ A aka 27 91 65
    ARROW_RIGHT, // ESC [ C aka 27 91 67
    ARROW_DOWN, // ESC [ B aka 27 91 66
    ARROW_LEFT, // ESC [ D aka 27 91 68
    N_UPPER, //78
    Y_UPPER, // 89
    N_LOWER, // 110
    Y_LOWER, // 121
    DELETE, // ESC [ 3 ~ aka 27 91 51 127
    BACKSPACE, // 127
    F4, // ESC O S aka 27 79 83
    NOT_MAPPED
}
