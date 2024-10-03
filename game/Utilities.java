package game;

import java.awt.Color;

/**
 *
 * @author cristopher
 */
public class Utilities {
    public static final String RESET_KEY = "R";
    public static final String EXIT_KEY = "0";
    
    public static GameStroke stringToGameStroke(String s) {
        switch (s) {
            case "UP":
            case "W":
            return GameStroke.UP;
            
            case "DOWN":
            case "S":
            return GameStroke.DOWN;
            
            case "LEFT":
            case "A":
            return GameStroke.LEFT;
            
            case "RIGHT":
            case "D":
            return GameStroke.LEFT;
        }
        
        throw new AssertionError("Invalid string");
    }
    
    
    public static final Color [] GAME_COLORS = {
        new Color(86, 193, 255), // b1
        new Color(0, 162, 255), // b2
        new Color(0, 116, 186), // b3
        new Color(0, 77, 128), // b4
        new Color(22, 231, 207), // ab2
        new Color(0, 171, 142), // ab3
        new Color(0, 108, 101), // ab4
        new Color(97, 216, 54), // g2
        new Color(29, 177, 0), // g3
        new Color(1, 113, 0), // g4
        new Color(255, 217, 50), // y2
        new Color(254, 174, 0), // y3
        new Color(242, 114, 0), // y4
        new Color(255, 150, 141), // r1
        new Color(255, 100, 75), // r2
        new Color(238, 34, 12), // r3
        new Color(181, 23, 0), // r4
        new Color(255, 149, 202), // p1
        new Color(255, 66, 161), // p2
        new Color(212, 24, 118), // p3
        new Color(151, 14, 83), // p4
        
        new Color(165, 103, 253), // v1
        new Color(120, 74, 182), // v2
        new Color(68, 57, 148), // v3
        new Color(45, 40, 103) // v4
    };
    
    public static final int AVAILABLE_COLORS = GAME_COLORS.length;
}
