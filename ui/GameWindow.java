package ui;

import game.Utilities;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import utils.LibUtilities;

/**
 *
 * @author cristopher
 */
public class GameWindow extends Window {
    private final UIBoard uiBoard = new UIBoard(500, 500, 4);
    
    public GameWindow() {
        super(500, 500, true);
        
        setResizable(false);
        
        add(uiBoard);
        pack();
        uiBoard.initUI();
        
        container.externalComponents.add(uiBoard);
        
        installKeyListeners();
    }
    
    private void installKeyListeners() {
        for (int i = 3; i < 10; i++) {
            final int j = i;
            LibUtilities.addKeyBindingTo(container, "Press " + i, "" + i, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    uiBoard.setBoardSize(j);
                }
            });
        }
        
        for (String s : new String[]{"W", "S", "A", "D", "UP", "DOWN", "LEFT", "RIGHT"})
            LibUtilities.addKeyBindingTo(container, "Pressed " + s, "pressed " + s, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    uiBoard.performStroke(Utilities.stringToGameStroke(s));
                }
            });
        
        LibUtilities.addKeyBindingTo(container, "Restart", "pressed " + Utilities.RESET_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Exit", "pressed " + Utilities.EXIT_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Light", "pressed 1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIProperties.setLightColor();
                updateUITheme();
                LibUtilities.savePreferences();
            }
        });
        
        LibUtilities.addKeyBindingTo(container, "Dark", "pressed 2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIProperties.setDarkColor();
                updateUITheme();
                LibUtilities.savePreferences();
            }
        });
    }
}
