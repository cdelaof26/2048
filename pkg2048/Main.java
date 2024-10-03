package pkg2048;

import ui.GameWindow;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class Main {
    public static void main(String[] args) {
        LibUtilities.loadPreferences(null);
        new GameWindow().showWindow();
    }
}
