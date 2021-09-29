package ru.vsu.cs.game;

import java.awt.*;

public enum Turn {
    D1(0, Color.ORANGE),
    D2(1, Color.MAGENTA),
    D3(2, Color.GREEN),
    D4(3, Color.YELLOW),
    D5(4, Color.RED),
    MrX(100, Color.BLACK);

    public final int TURN_NB;
    public final Color COLOR;

    Turn(int turnNb, Color c) {
        TURN_NB = turnNb;
        COLOR = c;
    }

    public Turn getNextTurn() {
        switch (this) {
            case D1:
                return D2;
            case D2:
                return D3;
            case D3:
                return D4;
            case D4:
                return D5;
            case D5:
                return MrX;
            default:
                return D1;
        }
    }

    public static Turn recognize(int tNb) {
        switch (tNb) {
            case 0:
                return D1;
            case 1:
                return D2;
            case 2:
                return D3;
            case 3:
                return D4;
            case 4:
                return D5;
            default:
                return MrX;
        }
    }
}
